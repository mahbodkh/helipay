package app.helipay.um.service;

import app.helipay.um.api.UserManagerApi;
import app.helipay.um.service.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserService implements UserManagerApi {
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
        return null;
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
        return List.of();
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
    public Void banUser(Long userId) {
        return null;
    }

    @Override
    public Void frozenUser(Long userId) {
        return null;
    }

    @Override
    public Void deleteUserByAdmin(Long userId) {
        return null;
    }

    @Override
    public Void deleteUserBySuperUser(Long userId) {
        return null;
    }
}
