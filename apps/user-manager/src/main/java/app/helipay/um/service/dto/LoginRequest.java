package app.helipay.um.service.dto;

import java.util.Objects;

public record LoginRequest(String login, String password, String ipAddress) {
    public LoginRequest {
        Objects.requireNonNull(login, "login must not be null");
        Objects.requireNonNull(password, "password must not be null");
    }
}
