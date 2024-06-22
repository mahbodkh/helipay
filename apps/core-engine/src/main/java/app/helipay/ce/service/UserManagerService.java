package app.helipay.ce.service;

import app.helipay.ce.conversation.UserManagerProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagerService {

    private final UserManagerProxy userManagerProxy;


    public void getUsers() {
        userManagerProxy.getAllUsers(1,2);
    }
}
