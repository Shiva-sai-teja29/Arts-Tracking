package com.arts.Arts.Tracking.Users;

import com.arts.Arts.Tracking.Entity.Arts;
import com.arts.Arts.Tracking.Entity.Role;
import com.arts.Arts.Tracking.Entity.SubArts;
import com.arts.Arts.Tracking.Entity.User;
import com.arts.Arts.Tracking.Users.UserDTO.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserProfileResponse getLoggedInUser(User user);

    UserProfileResponse updateProfile(@Valid UpdateUserRequest request, User user);

    void changePassword(@Valid ChangePasswordRequest request, User user);

    void updateMobile(@Valid UpdateMobileRequest request, User user);

    void sendMobileVerificationOtp(VerifyOtpRequest request, String message);

    void verifyMobileOtp(VerifyOtpRequest request);

    void uploadProfile(MultipartFile file);
    
    Page<UserSummaryResponse> getAllUsers(Pageable pageable);

    UserProfileResponse getUser(UUID id, User user) throws AccessDeniedException;

    void enableUser(UUID id);

    void disableUser(UUID id);

    void deleteUser(UUID id);

    List<StudentResponse> getStudents(User currentUser) throws AccessDeniedException;

    List<CoachResponse> getCoaches(User user,String category,String categoryName) throws AccessDeniedException;

    Page<UserProfileResponse> search(String keyword, Pageable pageable);

    List<AcademyResponse> getAcademies(User user) throws AccessDeniedException;

    List<BranchResponse> getBranches(User user) throws AccessDeniedException;

    Page<SubArts> getAllSubArts(Pageable pageable, User user);

    Page<Arts> getAllArts(Pageable pageable, User user);

    Long getStudentsCount(User user, String category, String categoryName);

    List<CoachResponse> getCoachesForStudents(User user);

    List<BranchResponse> getAllBranches(User user, String academy);

    String addRole(User user, Role role);
}
