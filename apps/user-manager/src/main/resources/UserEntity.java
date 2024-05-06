package app.helipay.um.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
public class UserEntity {
    private long id;

    private Instant created;
    private Instant updated;

    private UUID userId;

    private String username;
    private String email;
    private String name;
    private String family;

    private String phone;

    private String address;
}
