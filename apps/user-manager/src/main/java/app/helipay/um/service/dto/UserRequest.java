package app.helipay.um.service.dto;


import java.util.Objects;
import java.util.Set;

// it becomes with id since it used in update as well!
public record UserRequest(Long id, String username, String firstName, String lastName, String email,
                          String imageUrl, Boolean isActivated, String langKey, Set<String> authorities) {
    public UserRequest {
        Objects.requireNonNull(username, "username must not be null");
        Objects.requireNonNull(email, "email must not be null");
    }
}

