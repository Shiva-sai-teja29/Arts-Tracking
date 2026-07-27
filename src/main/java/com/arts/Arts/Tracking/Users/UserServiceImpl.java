package com.arts.Arts.Tracking.Users;

import com.arts.Arts.Tracking.Entity.*;
import com.arts.Arts.Tracking.Repo.*;
import com.arts.Arts.Tracking.Users.UserDTO.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ArtsRepository artsRepository;

    @Autowired
    private SubArtsRepository subArtsRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserProfileResponse getLoggedInUser(User user) {
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return modelMapper.map(user1, UserProfileResponse.class);
    }

    @Override
    public UserProfileResponse updateProfile(UpdateUserRequest request, User user) {
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user1.setUpdatedAt(LocalDateTime.now());
        user1.setFullName(request.getFullName());
        user1.setNickName(request.getNickName());
        user1.setRoles(request.getRoles());
        userRepository.save(user1);
        return modelMapper.map(user1, UserProfileResponse.class);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, User user) {
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (passwordEncoder.matches(user1.getPassword(), request.oldPassword)){
            user1.setPassword(request.newPassword);
            userRepository.save(user1);
        }
    }

    @Override
    public void updateMobile(UpdateMobileRequest request, User user) {
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found"));


        OtpVerification otpVerification = otpVerificationRepository.findByMobileNumber(request.getMobile());
        if (passwordEncoder.matches(otpVerification.getOtp(), request.getOtp()) && otpVerification.getExpiryTime().isBefore(LocalDateTime.now())){
            user1.setMobileNumber(request.getMobile());
            user1.setMobileVerified(true);
        }

        userRepository.save(user1);
    }

    @Override
    public void sendMobileVerificationOtp(VerifyOtpRequest request, String otp) {
        try {
            Twilio.init(accountSid, authToken);
            String phoneNumber1 = "+91" + request.getMobile();
            Message.creator(new PhoneNumber(phoneNumber1), new PhoneNumber(twilioPhoneNumber),
                    "Your OTP code is Generated successfully <otp>"+otp).create();
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        OtpVerification otpSent = new OtpVerification();

        OtpVerification existingPhoneNumber = otpVerificationRepository.findByMobileNumber(request.getMobile());

        if (existingPhoneNumber==null){

            otpSent.setOtp(passwordEncoder.encode(otp));
            otpSent.setUsed(false);

            otpSent.setExpiryTime(LocalDateTime.now().plusMinutes(5));
            otpSent.setMobileNumber(request.getMobile());
            otpSent.setCreatedAt(LocalDateTime.now());
            otpSent.setAttempts(1);
            otpSent.setUpdatedAt(LocalDateTime.now());
        } else {
            otpSent.setOtp(passwordEncoder.encode(otp));
            otpSent.setUsed(false);

            otpSent.setExpiryTime(LocalDateTime.now().plusMinutes(5));
            otpSent.setMobileNumber(request.getMobile());
            otpSent.setCreatedAt(existingPhoneNumber.getCreatedAt());
            otpSent.setAttempts(existingPhoneNumber.getAttempts()+1);
            otpSent.setUpdatedAt(LocalDateTime.now());
        }

        otpVerificationRepository.save(otpSent);
    }

    @Override
    public void verifyMobileOtp(VerifyOtpRequest request) {
        // TODO Still not sure where to implement
    }

    @Override
    public void uploadProfile(MultipartFile file) {
        // TODO Azure Blob Storage
    }

    @Override
    public Page<UserSummaryResponse> getAllUsers(Pageable pageable) {
        return null;
    }

    @Override
    public UserProfileResponse getUser(UUID id, User current) throws AccessDeniedException {

        User target = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (current.getRoles().stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_ADMIN")))
            return modelMapper.map(target, UserProfileResponse.class);

        if(current.getRoles().stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_HEAD_COACH"))){
           // if(target.getAcademy().equals(current.getAcademy())) return modelMapper.map(target, UserProfileResponse.class);
        }

        if(current.getId().equals(target.getId())) return modelMapper.map(target, UserProfileResponse.class);

        throw new AccessDeniedException("Forbidden");
    }

    @Override
    public void enableUser(UUID id) {

    }

    @Override
    public void disableUser(UUID id) {

    }

    @Override
    public void deleteUser(UUID id) {

    }

    @Override
    public List<StudentResponse> getStudents(User currentUser) throws AccessDeniedException {
        if(currentUser.getRoles().stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_COACH"))){
            List<User> students = enrollmentRepository.findByCoach(currentUser).stream()
                    .map(Enrollment::getStudent).distinct().toList();
            return students.stream().map(student -> modelMapper.map(student, StudentResponse.class)).toList();
        }
        throw new AccessDeniedException("Access denied");
    }

    @Override
    public List<CoachResponse> getCoaches(User currentUser,String category, String categoryName) throws AccessDeniedException {
        if(currentUser.getRoles().stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_ADMIN"))){
            return userRepository.findAllCoaches().stream()
                    .map(b -> modelMapper.map(b, CoachResponse.class))
                    .toList();
        }else if("Academy".equalsIgnoreCase(category)){
            return userRepository.findAllCoachesWithAcademy(categoryName).stream()
                    .map(b -> modelMapper.map(b, CoachResponse.class))
                    .toList();
        } else if ("Branch".equalsIgnoreCase(category)) {
            return userRepository.findAllCoachesWithBranch(categoryName).stream().map(b -> modelMapper.map(b, CoachResponse.class)).toList();
        }
        throw new AccessDeniedException("Access denied");
    }

    @Override
    public Page<UserProfileResponse> search(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public List<AcademyResponse> getAcademies(User user) throws AccessDeniedException {
        User target;
        List<Academy> academy;
        if(user.getRoles().stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_COACH"))){
            target = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            academy = academyRepository.findByCoaches(target);
        }
        else if(user.getRoles().stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_STUDENT"))){
            target = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            academy = academyRepository.findAcademiesByStudent(target);
        }else if(user.getRoles().stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_HEAD_COACH"))) {
            target = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            academy = academyRepository.findByCoaches(target);
        }else if(user.getRoles().stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_ADMIN"))) {
            academy=academyRepository.findAll();
        }
        else throw new AccessDeniedException("Access denied");
        return academy.stream().map(a -> modelMapper.map(a, AcademyResponse.class)).toList();
    }

    @Override
    public List<BranchResponse> getBranches(User user) throws AccessDeniedException {
        User target;
        List<Branch> branch;
        if(user.getRoles().stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_COACH"))){
            target = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            branch = branchRepository.findByCoaches(target);
        }
        else if(user.getRoles().stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_STUDENT"))){
            target = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            branch = branchRepository.findBranchesByStudent(target);
        }else {
            branch = branchRepository.findAll();
        }
        return branch.stream().map(b -> modelMapper.map(b, BranchResponse.class)).toList();
    }

    @Override
    public Page<SubArts> getAllSubArts(Pageable pageable, User user) {
        return subArtsRepository.findAll(pageable);
    }

    @Override
    public Page<Arts> getAllArts(Pageable pageable,User user) {
        return artsRepository.findAll(pageable);
    }

    @Override
    public Long getStudentsCount(User user, String category, String categoryName) {
        return switch (category) {
            case "Academy" ->{
                Academy academy = academyRepository.findByAcademyName(categoryName);
                yield enrollmentRepository.countByAcademy(academy);
            }
            case "Branch" -> {
                Branch branch = branchRepository.findByBranchName(categoryName);
                yield enrollmentRepository.countByBranch(branch);
            }
            case "Coach" -> {
                User coach = userRepository.findByUsername(categoryName).orElseThrow(()->new RuntimeException("User not matched with Role"));
                yield enrollmentRepository.countByCoach(coach);
            }
            default -> 0L;
        };
    }

    @Override
    public List<CoachResponse> getCoachesForStudents(User user) {

        return List.of();
    }

    @Override
    public List<BranchResponse> getAllBranches(User user, String academy) {
        List<Branch> branch = branchRepository.findByAcademy(academy);
        return branch.stream()
                .map(b -> modelMapper.map(b, BranchResponse.class))
                .toList();
    }

    @Override
    public String addRole(User user, Role role) {
        Role existingRole = roleRepository.findByName(role.getName()).orElseThrow(() -> new RuntimeException("Role not found"));
        User user1 = userRepository.findByUsername(user.getUsername()).orElseThrow(()->new RuntimeException("User not matched with Role"));
        user1.getRoles().add(existingRole);
        userRepository.save(user1);
        return role.getName()+" - Role added Successfully";
    }

}
