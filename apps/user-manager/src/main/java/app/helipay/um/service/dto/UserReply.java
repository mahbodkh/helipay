package app.helipay.um.service.dto;

import java.util.Objects;
import java.util.Set;

public record UserReply(Long id, String username, String firstName, String lastName, String email,
                        String imageUrl, boolean isActivated, String langKey, Set<String> authorities) {
    public UserReply {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(username, "username must not be null");
//        Objects.requireNonNull(email, "email must not be null");
    }
}
