package com.arts.Arts.Tracking.Users;

import com.arts.Arts.Tracking.Entity.*;
import com.arts.Arts.Tracking.Repo.UserRepository;
import com.arts.Arts.Tracking.Security.SMSService;
import com.arts.Arts.Tracking.Security.UserInfo;
import com.arts.Arts.Tracking.Users.UserDTO.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SMSService smsService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getProfile() {
        return ResponseEntity.ok(userService.getLoggedInUser(extractUser()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateProfile(request, extractUser()));
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(request, extractUser());

        return ResponseEntity.ok(
                new ApiResponse(true, "Password changed successfully")
        );
    }

    @PutMapping("/me/mobile")
    public ResponseEntity<ApiResponse> updateMobile(@Valid @RequestBody UpdateMobileRequest request) {
        userService.updateMobile(request, extractUser());
        return ResponseEntity.ok(new ApiResponse(true, "Mobile Number updated"));
    }

    @PostMapping("/me/mobile/send-otp")
    public ResponseEntity<ApiResponse> sendMobileOtp(VerifyOtpRequest request) {
        String otp = smsService.getOtpCode(request.getMobile());
        userService.sendMobileVerificationOtp(request, otp);
        return ResponseEntity.ok(new ApiResponse(true, "OTP sent"));
    }

    @PostMapping("/me/mobile/verify")
    public ResponseEntity<ApiResponse> verifyMobile(
            @RequestBody VerifyOtpRequest request) {

        userService.verifyMobileOtp(request);

        return ResponseEntity.ok(
                new ApiResponse(true, "Mobile verified")
        );
    }

    @PutMapping(value="/me/profile-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadProfilePhoto(@RequestParam MultipartFile file) {
        userService.uploadProfile(file);
        return ResponseEntity.ok(new ApiResponse(true, "Profile updated"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUser(@PathVariable UUID id) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getUser(id, extractUser()));
    }

//    @GetMapping("/students")
//    public ResponseEntity<Page<StudentResponse>> students(Pageable pageable) throws AccessDeniedException {
//        return ResponseEntity.ok(userService.getStudents(pageable, extractUser()));
//    }

//    @GetMapping("/coaches")
//    public ResponseEntity<List<CoachResponse>> coaches() throws AccessDeniedException {
//        return ResponseEntity.ok(userService.getCoaches(extractUser()));
//    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserProfileResponse>> search(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(userService.search(keyword,pageable));
    }

    public User extractUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        UserInfo userInfo = (UserInfo) auth.getPrincipal();
        assert userInfo != null;
        return userRepository.findByUsername(userInfo.getUsername()).orElseThrow(()->new RuntimeException("User not matched with Role"));
    }

    /// ////////////////////////////////////////////////////////////////////////////
    /// From coach perspective
    // To get the academies for the coach
    @GetMapping("/coach/getAcademies")
    public ResponseEntity<List<AcademyResponse>> getAcademies() throws AccessDeniedException {
        return ResponseEntity.ok(userService.getAcademies(extractUser()));
    }

    // To get the branches for the coach
    @GetMapping("/coach/getBranches")
    public ResponseEntity<List<BranchResponse>> getBranches() throws AccessDeniedException {
        return ResponseEntity.ok(userService.getBranches(extractUser()));
    }

    // To get the students for the coach
    @GetMapping("/coach/getStudents")
    public ResponseEntity<List<StudentResponse>> getStudents() throws AccessDeniedException {
        return ResponseEntity.ok(userService.getStudents(extractUser()));
    }

    /// ////////////////////////////////////////////////////////////////////////////
    /// From Student perspective
    // To get the academies for the Students
    @GetMapping("/students/getAcademies")
    public ResponseEntity<List<AcademyResponse>> getAcademiesStudents() throws AccessDeniedException {
        return ResponseEntity.ok(userService.getAcademies(extractUser()));
    }

    // To get the branches for the Students
    @GetMapping("/students/getBranches")
    public ResponseEntity<List<BranchResponse>> getBranchesStudents() throws AccessDeniedException {
        return ResponseEntity.ok(userService.getBranches(extractUser()));
    }

    // To get the coaches for the Students
    @GetMapping("/students/getCoaches")
    public ResponseEntity<List<CoachResponse>> getCoaches() throws AccessDeniedException {
        return ResponseEntity.ok(userService.getCoachesForStudents(extractUser()));
        //TODO
    }

    /// ////////////////////////////////////////////////////////////////////////////
    /// From Everyone perspective
    // To get the academies for Everyone
    @GetMapping("/getAcademies")
    public ResponseEntity<List<AcademyResponse>> getAllAcademies() throws AccessDeniedException {
        return ResponseEntity.ok(userService.getAcademies(extractUser()));
    }

    // To get the branches for Everyone for different Academy
    @GetMapping("/getBranches")
    public ResponseEntity<List<BranchResponse>> getAllBranches(@RequestParam String academy) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getAllBranches(extractUser(),academy));
    }

    // To get the coaches for Everyone for different branch/Academy
    @GetMapping("/getCoaches")
    public ResponseEntity<List<CoachResponse>> getAllCoaches(@RequestParam String category, @RequestParam String categoryName) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getCoaches(extractUser(), category, categoryName));
    }

    // To get the students count for category wise
    @GetMapping("/getStudentsCount")
    public ResponseEntity<Long> getStudentsCount(@RequestParam String category, @RequestParam String categoryName) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getStudentsCount(extractUser(), category, categoryName));
        //TODO
    }

    // To get the Arts for Everyone
    @GetMapping("/getArts")
    public ResponseEntity<Page<Arts>> getAllArts(Pageable pageable) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getAllArts(pageable, extractUser()));
    }

    // To get the SubArts for Everyone
    @GetMapping("/getSubArts")
    public ResponseEntity<Page<SubArts>> getAllSubArts(Pageable pageable) throws AccessDeniedException {
        return ResponseEntity.ok(userService.getAllSubArts(pageable, extractUser()));
    }
/// //////////////////////////////////////////////////////////////////////////////////////////////

    // To get the SubArts for Everyone
    @PostMapping("/addRole")
    public ResponseEntity<String> addRole(@RequestBody Role role) throws AccessDeniedException {
        return ResponseEntity.ok(userService.addRole(extractUser(),role));
    }
}