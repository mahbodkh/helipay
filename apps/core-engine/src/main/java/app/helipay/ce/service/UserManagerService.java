package app.helipay.ce.service;

import app.helipay.ce.conversation.UserManagerProxy;
import app.helipay.um.service.dto.RegisterRequest;
import app.helipay.um.service.dto.UserReply;
import app.helipay.um.service.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagerService {

    private final UserManagerProxy userManagerProxy;


    public UserReply getUser() {
        return userManagerProxy.getUser();
    }


    public void getUsers() {
        userManagerProxy.getAllUsers(1, 2);
    }

    public List<UserReply> getAllUsersByCity() {
        return userManagerProxy.getUsersByCity("");
    }

    public List<UserReply> getUsersByLanguage() {
        return userManagerProxy.getUsersByLanguage("");
    }

    public List<UserReply> getUsersByDefaultLanguage() {
        return userManagerProxy.getUsersByDefaultLanguage();
    }

    public UserReply registerUser(RegisterRequest request, Object o) {
        return userManagerProxy.registerUser(request);
    }

    public UserReply getUserById(Long userId) {
        return userManagerProxy.getUserById(userId);
    }

    public UserReply createUser(UserRequest request) {
        return userManagerProxy.createUser(request);
    }

    public void safeDeleteUser(Long userId) {
        userManagerProxy.safeDeleteUser(userId);
    }


    public List<UserReply> getAllUsers(int pageSize, int pageNumber) {
        return userManagerProxy.getAllUsers(pageSize, pageNumber);
    }

    public List<UserReply> getUsersByRole(String roleName) {
        return userManagerProxy.getAllUsersByRole(roleName);
    }

    public void frozenUser(Long userId) {
        userManagerProxy.frozenUser(userId);
    }


    public void banUser(Long userId) {
        userManagerProxy.banUser(userId);
    }

    public void deleteUserById(Long userId) {
        userManagerProxy.deleteUserById(userId);
    }
}
