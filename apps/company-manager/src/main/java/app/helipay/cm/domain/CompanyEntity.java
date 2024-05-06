package app.helipay.cm.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class CompanyEntity {
    private long id;
    private Instant created;
    private Instant updated;

    private String name;

    private String country;
    private String address;
    private String codeZip;
    private long locationLong;
    private long locationLat;

    private String phone;
}
