package app.helipay.um.api;

import app.helipay.um.service.dto.RegisterRequest;
import app.helipay.um.service.dto.UserReply;
import app.helipay.um.service.dto.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/um/api/v1")
public interface UserManagerApi extends UserManagerReportApi {

    @PostMapping("/admin/users")
    ResponseEntity<UserReply> registerUser(@RequestBody RegisterRequest request);

    @PostMapping("/admin/users")
    ResponseEntity<UserReply> createUser(@RequestBody UserRequest request);
}
