package app.helipay.nc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Objects;

/**
 * Created by Ebrahim Kh.
 */

@Entity
@Table(name = "se_messages")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessageEntity {
    public enum Type
    {
        SMS,
        EMAIL,
        WEB
    }

    public enum Status
    {
        DELIVERED,
        FAILED,
        REJECTED,
        SENT
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
    @Size(max = 100)
    @Column(name = "reject_reason", length = 100)
    private String rejectReason;
    @NotNull
    @Column(name = "to_user_entity_id")
    private Long userEntity;
    @Size(max = 100)
    @Column(name = "recipient", length = 100)
    private String recipient;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageEntity)) {
            return false;
        }

        MessageEntity that = (MessageEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Builder(toBuilder = true)
    public MessageEntity(Long id, Status status, Type type, String rejectReason, Long userEntity,
                              String recipient, Date created, Date changed) {
//    setId(id);
//    setStatus(status);
//    setType(type);
//    setUserEntity(userEntity);
//    setRejectReason(rejectReason);
//    setRecipient(recipient);
//    setCreated(created);
//    setChanged(changed);
    }

    @Transient
    public static MessageEntity getBasicNotification(Long user, Type type, String recipient) {
        return MessageEntity.builder()
                .userEntity(user)
                .type(type)
                .recipient(recipient)
                .build();
    }
}
