package app.helipay.um.service.dto;

import java.util.Objects;

public record RegisterRequest(String username, String firstName, String lastName, String email, String imageUrl,
                              String langKey) {
    public RegisterRequest {
        Objects.requireNonNull(username, "username must not be null");
        Objects.requireNonNull(email, "email must not be null");
    }
}

