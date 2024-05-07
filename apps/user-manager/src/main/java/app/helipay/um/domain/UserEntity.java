package app.helipay.um.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.*;

/**
 * Created by Ebrahim Kh.
 */

@Entity
@Table(name = "um_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {

    public enum GenderType {
        MALE,
        FEMALE
    }

    public enum StatusType {
        ACTIVE,
        PENDING,
        DEACTIVATE,
        FROZEN,
        DELETED,
        BANNED
    }

    public enum ForcePasswordChangeType {
        UNKNOWN,
        UNNECESSARY,
        REQUIRED,
        MANDATORY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //    @Pattern(regexp = "^(9)[0-9]{9}")
    @Column(length = 20)
    private String mobile;

    @Size(min = 5, max = 50)
    @Column(unique = true)
    private String username;

    //    @JsonIgnore
    @Column(length = 60)
    private String password;

    @Size(max = 50)
    @Column(length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 60)
    @Column(length = 60, unique = true)
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @ColumnDefault("false")
    @Builder.Default
    private Boolean activated = false;

    @Column(length = 10)
    private String langKey;

    @Column
    private String imageUrl;

    //    @JsonIgnore
    @Size(max = 20)
    @Column(length = 20)
    private String activationKey;

    @Column(length = 20)
    //    @JsonIgnore
    private String resetKey;

    @Column
    @Builder.Default
    private Date resetDate = null;

    @Setter(AccessLevel.NONE)
    @Version
    @Column(name = "version")
    private Integer version;

//    @JsonIgnore
//    @ManyToMany
//    @JoinTable(
//            name = "user_authority",
//            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
//    )
//    @BatchSize(size = 5)
//    @Builder.Default
//    private Set<Authority> authorities = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_permission",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")}
    )
//    @BatchSize(size = 20)
//    @Builder.Default
//    private Set<Permission> permissions = new HashSet<>();




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

//    @Transient
//    public static UserEntity getBasicUser(Set<Authority> authorities, String username, String email, String firstName, String lastName) {
//        return UserEntity.builder()
//                .authorities(authorities)
//                .firstName(firstName)
//                .lastName(lastName)
//                .username(username)
//                .email(email)
//                .build();
//    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", status=" + status +
                ", activated=" + activated +
                ", langKey='" + langKey + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", activationKey='" + activationKey + '\'' +
                ", resetKey='" + resetKey + '\'' +
                ", resetDate=" + resetDate + '\'' +
//                ", authorities=" + authorities +
//                ", permissions=" + permissions +
//                ", created=" + created +
//                ", changed=" + changed +
                '}';
    }
}