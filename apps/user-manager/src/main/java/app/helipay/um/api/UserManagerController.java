package app.helipay.um.api;

import app.helipay.um.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserManagerController {
    private final UserService userService;

    //  ----------------------
    //        ADMIN APIs
    //  ----------------------
//    @PostMapping("/users")
//    public ResponseEntity<UserReply> createUser(@RequestBody UserRequest request) {
//        return ResponseEntity.ok(userService.createUser(request));
//    }

//    @GetMapping("/admin/users")
//    public ResponseEntity<List<UserReply>> getAllUsers(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "20") int size
//    ) {
//        Pageable pageable = PaginationValidator.validatePaginationOrThrow(page, size);
//        return ResponseEntity.ok(userService.getAllUsers(pageable));
//    }

//    @GetMapping("/admin/users/{role}")
//    public ResponseEntity<List<UserReply>> getAllUsersByRole(@PathVariable("role") String roleName) {
//        return ResponseEntity.ok(userService.getAdminUsers());
//    }

//    @PutMapping("/admin/users/{id}/frozen")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Void> frozenUser(@PathVariable("id") Long userId) {
//        userService.frozenUser(userId);
//        return ResponseEntity.noContent().build();
//    }

//    @PutMapping("/admin/users/{id}/ban")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Void> banUser(@PathVariable("id") Long userId) {
//        userService.banUser(userId);
//        return ResponseEntity.noContent().build();
//    }

//    @DeleteMapping("/admin/users/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
//        userService.deleteUser(userId);
//        return ResponseEntity.noContent().build();
//    }

//    @DeleteMapping("/admin/users/{id}/safe")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Void> safeDeleteUser(@PathVariable("id") Long userId) {
//        userService.safeDeleteUser(userId);
//        return ResponseEntity.noContent().build();
//    }

    //  ----------------------
    //        CLIENT APIs
    //  ----------------------
//    @GetMapping("/users/{id}")
//    public ResponseEntity<UserReply> getUser(@PathVariable("id") Long userId) {
//        return ResponseEntity.ok(userService.getUserById(userId));
//    }

    //  ----------------------
    //      COMMON APIs
    //  ----------------------
//    @PutMapping("/users/{id}")
//    public ResponseEntity<UserReply> updateUser(@PathVariable("id") Long userId, @RequestBody UserRequest request, MultipartFile file) {
//        return ResponseEntity.of(userService.updateUser(userId, request));
//    }

}
