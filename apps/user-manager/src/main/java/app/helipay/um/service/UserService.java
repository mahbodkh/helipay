package app.helipay.um.service;

import app.helipay.um.api.UserManagerApi;
import app.helipay.um.handler.UserDbHandler;
import app.helipay.um.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserManagerApi {

    private final UserDbHandler userDbHandler;

    @Override
    public UserReply registerUser(RegisterRequest request) {
        return null;
    }

    @Override
    public LoginReply loginUser(LoginRequest request) {
        return null;
    }

    @Override
    public UserReply getUser() {
        return userDbHandler.getUser();
    }

    @Override
    public UserReply getUserById(Long userId) {
        return userDbHandler.getUserById(userId);
    }

    @Override
    public UserReply updateUser(Long userId, UserRequest request, MultipartFile file) {
        return null;
    }

    @Override
    public List<UserReply> getUsersByCity(String city) {
        return List.of();
    }

    @Override
    public List<UserReply> getUsersByLanguage(String language) {
        return List.of();
    }

    @Override
    public List<UserReply> getUsersByDefaultLanguage() {
        return List.of();
    }

    @Override
    public List<UserReply> getUsersByLocation(long userFrom, double radius) {
        return List.of();
    }

    @Override
    public UserReply createUser(UserRequest request) {
        return null;
    }

    @Override
    public UserReply getUserByAdmin(Long userId) {
        return null;
    }

    @Override
    public List<UserReply> getAllUsers(int page, int size) {
        final Pageable pageable = PageRequest.of(0, 10);
        return userDbHandler.getAllUsers(pageable);
    }

    @Override
    public List<UserReply> getAllUsersByRole(String roleName) {
        return List.of();
    }

    @Override
    public UserReply updateUserByAdmin(Long userId, UserRequest request, MultipartFile file) {
        return null;
    }

    @Override
    public void banUser(Long userId) {
    }

    @Override
    public void frozenUser(Long userId) {
    }

    @Override
    public void deleteUserByAdmin(Long userId) {
    }

    @Override
    public void deleteUserBySuperUser(Long userId) {
    }

    @Override
    public void safeDeleteUser(Long userId) {
    }

    @Override
    public void unBanUser(Long userId) {
        
    }

    @Override
    public void unFrozenUser(Long userId) {

    }

    @Override
    public void deleteUserById(Long userId) {

    }
}
