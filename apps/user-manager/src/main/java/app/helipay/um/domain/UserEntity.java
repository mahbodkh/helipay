package app.helipay.um.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.BatchSize;


import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
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
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

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
    private Long id;

    //    @Pattern(regexp = "^(9)[0-9]{9}")
    @Column(length = 20)
    private String mobile;

    @Size(min = 5, max = 50)
    @Column(unique = true)
//    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
//    @Column(length = 50, unique = true, nullable = false)
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
    private int age;

    @Column
    private String location;

    @Column(length = 100)
    private String description;

    @Column
    @Builder.Default
    private Instant resetDate = null;

    @Setter(AccessLevel.NONE)
    @Version
    @Column(name = "version")
    private Integer version;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

//    @JsonIgnore
//    @ManyToMany
//    @JoinTable(
//            name = "user_permission",
//            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")}
//    )
//    @BatchSize(size = 20)
//    @Builder.Default
//    private Set<Permission> permissions = new HashSet<>();

    // Lowercase the login before saving it in database
    public void setUsername(String username) {
        this.username = StringUtils.lowerCase(username, Locale.ENGLISH);
    }

    @Column(name = "created")
    @CreationTimestamp
    private Instant created;
    @Column(name = "changed")
    @UpdateTimestamp
    private Instant changed;

    public void setStatus(@NotNull StatusType status) {
        setActivated(status.equals(StatusType.ACTIVE));
        this.status = status;
    }

    public Boolean isActivated() {
        return activated;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserEntity)) {
            return false;
        }
        return id != null && id.equals(((UserEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Transient
    public static UserEntity getBasicUser(Set<Authority> authorities, String username, String email, String firstName, String lastName) {
        return UserEntity.builder()
                .authorities(authorities)
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .email(email)
                .build();
    }

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
                ", authorities=" + authorities +
//                ", permissions=" + permissions +
                ", created=" + created +
//                ", changed=" + changed +
                '}';
    }
}