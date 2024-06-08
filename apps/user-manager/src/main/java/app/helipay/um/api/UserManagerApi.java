package app.helipay.um.api;

import app.helipay.um.service.dto.RegisterRequest;
import app.helipay.um.service.dto.UserReply;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserManagerApi {

    @PostMapping("/admin/users")
    ResponseEntity<UserReply> registerUser(@RequestBody RegisterRequest request);
}
