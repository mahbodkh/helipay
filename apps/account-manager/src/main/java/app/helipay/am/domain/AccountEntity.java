//package app.helipay.am.domain;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Digits;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.Type;
//import org.hibernate.annotations.UpdateTimestamp;
//
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * Created by Ebrahim Kh.
// */
//
//@Entity
//@Table(name = "am_accounts")
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class AccountEntity
//{
//
//  public enum Status
//  {
//    UNKNOWN,
//    ACTIVE,
//    DISABLED,
//    PENDING,
//    FROZEN,
//    DELETED
//  }
//
//  @Id
//  @Column(name = "id")
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private long id;
//  @Column(name = "status")
//  @NotNull
//  @Enumerated(EnumType.STRING)
//  private Status status;
//  @Column(name = "currency")
//  @NotNull
//  private Long currency;
////  @NotNull
////  @ManyToOne
////  @JoinColumn(name = "user_id", nullable = false)
////  private UserEntity user;
//  @Column(name = "iban")
//  @Size(max = 30)
//  @NotNull
//  private String iban;
//  @Column(name = "comment")
//  @Size(max = 200)
//  private String comment;
//  @Column(name = "available")
//  @Digits(integer = 10, fraction = 3)
//  @NotNull
//  private Double available;
//  @Column(name = "blocked")
//  @Digits(integer = 10, fraction = 3)
//  @NotNull
//  private Double blocked;
//
//
//  @Setter(AccessLevel.NONE)
//  @Version
//  @Column(name = "version")
//  private Integer version;
//
////  @Type(type = "jsonb")
//  @Column(name = "properties")
//  @Builder.Default
//  private Map<String, Object> properties = new HashMap<>();
//
////  @Column(name = "created")
////  @CreationTimestamp
//  private Date created;
//  @Column(name = "changed")
////  @UpdateTimestamp
//  private Date changed;
//
//  @Builder(toBuilder = true)
//  public AccountEntity(Long id, Status status, Long currency,
////                       UserEntity user,
//                       String iban, Date created,
//                       Date changed, String comment, Double available, Double blocked) {
//    setId(id);
//    setStatus(status);
//    setCurrency(currency);
////    setUser(user);
//    setIban(iban);
//    setCreated(created);
//    setChanged(changed);
//    setComment(comment);
//    setAvailable(available);
//    setBlocked(blocked);
//  }
//
////  @Transient
////  public static AccountEntity getAccount(UserEntity user, Long currency) {
////    return AccountEntity.builder()
////                        .user(user)
////                        .currency(currency)
////                        .status(Status.ACTIVE)
////                        .created(new Date())
////                        .changed(new Date())
////                        .available(BigDecimal.ZERO.doubleValue())
////                        .blocked(BigDecimal.ZERO.doubleValue())
////                        .build();
////  }
//
////  @Transient
////  public void blockBalance(BigDecimal value) throws AccountFrozenException, InsufficientFundsException {
////    if (value.compareTo(BigDecimal.ZERO) < 0) {
////      throw new AccountFrozenException("cannot block negative amount (" + value + ")");
////    }
////    checkTransferFromPossible(value); // checks isActive and enough money
////    available = BigDecimal.valueOf(available).subtract(value).doubleValue();
////    blocked = BigDecimal.valueOf(blocked).add(value).doubleValue();
////  }
//
////  @Transient
////  public void spendFromBlocked(BigDecimal value) throws IllegalBalanceOperationException, InsufficientFundsException {
////    if (value.compareTo(BigDecimal.ZERO) < 0) {
////      throw new IllegalBalanceOperationException("cannot release negative amount (" + value + ")");
////    }
////    // NB: skip "account is active" check
////    if (blocked < 0) {
////      throw new InsufficientFundsException("unable to release ("
////                                               + value + ") from account ("
////                                               + getId() + ") having only ("
////                                               + getBlocked() + ") blocked");
////    }
////    blocked = BigDecimal.valueOf(blocked).subtract(value).doubleValue();
////  }
//
////  @Transient
////  public void unlockIntoAvailable(BigDecimal value) throws IllegalBalanceOperationException, InsufficientFundsException {
////    if (value.compareTo(BigDecimal.ZERO) < 0) {
////      throw new IllegalBalanceOperationException("cannot unlock negative amount (" + value + ")");
////    }
////    // NB: skip "account is active" check
////    if (blocked < 0) {
////      throw new InsufficientFundsException("unable to unlock (" + value + ") from account (" + getId() + ") having only (" + getBlocked() + ") blocked");
////    }
////    blocked = BigDecimal.valueOf(blocked).subtract(value).doubleValue();
////    available = BigDecimal.valueOf(available).add(value).doubleValue();
////  }
//
////  @Transient
////  public void addAvailable(BigDecimal value) throws IllegalBalanceOperationException {
////    if (value.compareTo(BigDecimal.ZERO) < 0) {
////      throw new IllegalBalanceOperationException("cannot deposit negative amount (" + value + ")");
////    }
////    available = BigDecimal.valueOf(available).add(value).doubleValue();
////  }
//
////  @Transient
////  public void subAvailable(BigDecimal value) throws IllegalBalanceOperationException, InsufficientFundsException {
////    if (value.compareTo(BigDecimal.ZERO) < 0) {
////      throw new IllegalBalanceOperationException("cannot sub negative amount (" + value.toPlainString() + ")");
////    }
////    else if (BigDecimal.valueOf(available).compareTo(value) < 0) {
////      throw new InsufficientFundsException("you don't have enough funds, you need (" + value.toPlainString() + ")");
////    }
////    available = BigDecimal.valueOf(available).subtract(value).doubleValue();
////  }
//
////  @Transient
////  public void subAvailableNegativeAllowed(BigDecimal value) throws IllegalBalanceOperationException {
////    if (value.compareTo(BigDecimal.ZERO) < 0) {
////      throw new IllegalBalanceOperationException("cannot sub negative amount (" + value.toPlainString() + ")");
////    }
////    available = BigDecimal.valueOf(available).subtract(value).doubleValue();
////  }
//
////  @Transient
////  public void checkTransferFromPossible(BigDecimal value) throws AccountFrozenException, InsufficientFundsException {
////    if (isActiveOrPending()) {
////      throw new AccountFrozenException("unable to transfer ("
////                                           + value + ") funds from/to not active ("
////                                           + getStatus() + ") account ("
////                                           + getId() + ")");
////    }
////    else if (BigDecimal.valueOf(available).compareTo(value) < 0) {
////      throw new InsufficientFundsException("unable to transfer ("
////                                               + value + ") from account which has only ("
////                                               + getAvailable() + ") available");
////    }
////  }
//
////  @Transient
////  public void checkAvailableToSpend(BigDecimal value) {
////    if (BigDecimal.valueOf(available).compareTo(value) < 0) {
////      throw new InsufficientFundsException("Your Balance is not enough ("
////                                               + value + ") from account ("
////                                               + getId() + ") having only ("
////                                               + getAvailable() + ") available");
////    }
////  }
//
////  @Transient
//  public boolean isActiveOrPending() {
//    return getStatus() != Status.ACTIVE && getStatus() != Status.PENDING;
//  }
//
////  @Transient
////  public void checkTransferStatusToPossible(BigDecimal value) throws AccountFrozenException {
////    if (isActiveOrPending()) {
////      throw new AccountFrozenException("unable to transfer ("
////                                           + value + ") funds to not active ("
////                                           + getStatus() + ") account ("
////                                           + getId() + ")");
////    }
////  }
//
////  @Transient
////  public boolean isFrozen() {
////    return getStatus() == Status.FROZEN;
////  }
//
////  @Override
////  public boolean equals(Object o) {
////    if (this == o) {
////      return true;
////    }
////    if (!(o instanceof AccountEntity)) {
////      return false;
////    }
////    AccountEntity that = (AccountEntity) o;
////    return Objects.equals(id, that.id);
////  }
//
////  @Override
////  public int hashCode() {
////    return this.getClass().hashCode();
////  }
//
////  @Override
////  public String toString() {
////    return "AccountEntity{" +
////        "id=" + id +
////        ", status=" + status +
////        ", currency=" + currency +
////        ", userEntity=" + user +
////        ", iban='" + iban + '\'' +
////        ", comment='" + comment + '\'' +
////        ", available=" + available +
////        ", blocked=" + blocked +
////        ", properties=" + properties +
////        ", created=" + created +
////        ", changed=" + changed +
////        '}';
//  }
//
//
