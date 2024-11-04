package app.helipay.um.api;

import app.helipay.um.service.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserManagerApi {

    //  ----------------------
    //        CLIENT APIs
    //  ----------------------
    UserReply registerUser(RegisterRequest request);

    LoginReply loginUser(final LoginRequest request);

    UserReply getUser();

    UserReply getUserById(final Long userId);

    UserReply updateUser(final Long userId, final UserRequest request, MultipartFile file);

    List<UserReply> getUsersByCity(final String city);

    List<UserReply> getUsersByLanguage(final String language);

    List<UserReply> getUsersByDefaultLanguage();

    List<UserReply> getUsersByLocation(final long userFrom, final double radius);

    //  ----------------------
    //        ADMIN APIs
    //  ----------------------
    UserReply createUser(final UserRequest request);

    UserReply getUserByAdmin(final Long userId);

    List<UserReply> getAllUsers(final int page, final int size);

    List<UserReply> getAllUsersByRole(final String roleName);

    UserReply updateUserByAdmin(final Long userId, final UserRequest request, MultipartFile file);

    void banUser(final Long userId);

    void unBanUser(final Long userId);

    void frozenUser(final Long userId);

    void unFrozenUser(final Long userId);

    void safeDeleteUser(Long userId);


    //  ----------------------
    //      SUPER_USER APIs
    //  ----------------------
    void deleteUserBySuperUser(final Long userId);

    void deleteUserByAdmin(final Long userId);

    void deleteUserById(Long userId);
}
