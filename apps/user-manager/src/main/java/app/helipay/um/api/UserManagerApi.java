package app.helipay.um.api;

import app.helipay.um.service.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequestMapping("/um/api/v1")
public interface UserManagerApi extends UserManagerReportApi {

    //  ----------------------
    //        CLIENT APIs
    //  ----------------------
    @PostMapping("/users/register")
    ResponseEntity<UserReply> registerUser(@RequestBody RegisterRequest request);

    @PostMapping("/users/login")
    ResponseEntity<LoginReply> loginUser(@RequestBody LoginRequest request);

    @GetMapping("/users")
    ResponseEntity<UserReply> getUser();

    @PutMapping("/users/{id}/update")
    ResponseEntity<UserReply> updateUser(@PathVariable("id") Long userId, @RequestBody UserRequest request, MultipartFile file);


    //  ----------------------
    //        ADMIN APIs
    //  ----------------------
    @PostMapping("/admin/users/create")
    ResponseEntity<UserReply> createUser(@RequestBody UserRequest request);

    @GetMapping("/admin/users/{id}")
    ResponseEntity<UserReply> getUserByAdmin(@PathVariable("id") Long userId);

    @GetMapping("/admin/users/all")
    ResponseEntity<List<UserReply>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    );

    @GetMapping("/admin/users/{role}")
    ResponseEntity<List<UserReply>> getAllUsersByRole(@PathVariable("role") String roleName);

    @PutMapping("/admin/users/{id}/update")
    ResponseEntity<UserReply> updateUserByAdmin(@PathVariable("id") Long userId, @RequestBody UserRequest request, MultipartFile file);

    @PutMapping("/admin/users/{id}/ban")
    ResponseEntity<Void> banUser(@PathVariable("id") Long userId);

    @PutMapping("/admin/users/{id}/frozen")
    ResponseEntity<Void> frozenUser(@PathVariable("id") Long userId);

    @DeleteMapping("/admin/users/{id}/delete")
    ResponseEntity<Void> deleteUserByAdmin(@PathVariable("id") Long userId);

    //  ----------------------
    //      SUPER_USER APIs
    //  ----------------------

    @DeleteMapping("/super/users/{id}/delete")
    ResponseEntity<Void> deleteUserBySuperUser(@PathVariable("id") Long userId);
}
