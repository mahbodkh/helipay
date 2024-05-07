package app.helipay.um.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * Created by Ebrahim Kh.
 */

public class BaseEntity {

    @Setter(AccessLevel.NONE)
    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "created")
    @CreationTimestamp
    private Date created;
    @Column(name = "changed")
    @UpdateTimestamp
    private Date changed;
}