package app.helipay.um.service.dto;

import java.util.Objects;

public record ResetPasswordRequest(String login, String newPassword) {
    public ResetPasswordRequest {
        Objects.requireNonNull(login, "username/email/mobile must not be null");
        Objects.requireNonNull(newPassword, "newPassword must not be null");
    }
}
