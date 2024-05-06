package app.helipay.am.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
public class AccountEntity {
    private long id;
    private Instant created;
    private Instant updated;


    private UUID usedId;
    private UUID accountId;

    private BigDecimal balance;
}
