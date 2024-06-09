package app.helipay.um.api;

import app.helipay.um.service.UserService;
import app.helipay.um.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserManagerController implements UserManagerApi {
    private final UserService userService;

    //  ----------------------
    //        CLIENT APIs
    //  ----------------------
    @Override
    public ResponseEntity<UserReply> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }


    @Override
    public ResponseEntity<UserReply> registerUser(RegisterRequest request) {
        return ResponseEntity.ok(userService.registerUser(request, null));
    }

    @Override
    public ResponseEntity<LoginReply> loginUser(LoginRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<UserReply> updateUser(Long userId, UserRequest request, MultipartFile file) {
        return null;
    }

    //----------------------
    //     ADMIN APIs
    //----------------------
    @Override
    public ResponseEntity<UserReply> getUserByAdmin(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Override
    public ResponseEntity<UserReply> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @Override
    public ResponseEntity<List<UserReply>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        final Pageable pageable = PaginationValidator.validatePaginationOrThrow(page, size);
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @Override
    public ResponseEntity<List<UserReply>> getAllUsersByRole(@PathVariable("role") String roleName) {
        return ResponseEntity.ok(userService.getUsersByRole(roleName));
    }


    @Override
    public ResponseEntity<UserReply> updateUserByAdmin(Long userId, UserRequest request, MultipartFile file) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteUserByAdmin(@PathVariable("id") Long userId) {
        userService.safeDeleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> frozenUser(@PathVariable("id") Long userId) {
        userService.frozenUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> banUser(@PathVariable("id") Long userId) {
        userService.banUser(userId);
        return ResponseEntity.noContent().build();
    }


    //  ----------------------
    //      COMMON APIs
    //  ----------------------
//    @PutMapping("/users/{id}")
//    public ResponseEntity<UserReply> updateUser(@PathVariable("id") Long userId, @RequestBody UserRequest request, MultipartFile file) {
//        return ResponseEntity.of(userService.updateUser(userId, request));
//    }

    //  ----------------------
    //      SUPER_USER APIs
    //  ----------------------
    @Override
    public ResponseEntity<Void> deleteUserBySuperUser(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
