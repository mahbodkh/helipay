package app.helipay.um.service.dto;

import java.time.Instant;
import java.util.Objects;

public record LoginReply(String token, Instant expiration) {
    public LoginReply {
        Objects.requireNonNull(token, "token must not be null");
        Objects.requireNonNull(expiration, "expiration must not be null");
    }
}
