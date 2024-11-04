package app.helipay.ce.service;

import app.helipay.ce.conversation.UserManagerProxy;
import app.helipay.um.service.dto.UserReply;
import lombok.RequiredArgsConstructor;
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
        userManagerProxy.getAllUsers(1,2);
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
}
