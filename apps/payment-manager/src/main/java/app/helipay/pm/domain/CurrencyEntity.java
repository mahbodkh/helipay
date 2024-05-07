package app.helipay.pm.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

/**
 * Created by Ebrahim Kh.
 */

@Entity
@Table(name = "pm_currencies")
    //    , uniqueConstraints = @UniqueConstraint(columnNames = {"tag"}, name = "currency_entity_constraint")
//)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@TypeDef(name = "jsonb", typeClass = JsonType.class)
public class CurrencyEntity
{

  public enum CurrencyFeatureType
  {
    TRANSFER,
    WITHDRAW,
    DEPOSIT
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Size(max = 10)
  @NotNull
  @Column(length = 10)
  private String tag;     // EUR, USD, IRR, GBP
  @Size(max = 1)
  @Column(length = 5)
  private String symbol;  // [$ € £]

  //  @ElementCollection(fetch = FetchType.EAGER, targetClass = CurrencyFeatureType.class)
  //  @CollectionTable(name = "features", joinColumns = @JoinColumn(name = "currency_id"))
  //  @Enumerated(EnumType.STRING)
  @NotNull
//  @Convert(converter = SetCurrencyFeatureConverter.class)
  @Column(name = "features")
  private Set<CurrencyFeatureType> features = new HashSet<>();  // [TRANSFER, WITHDRAW]

  @Column(name = "created")
  @CreationTimestamp
  private Date created;
  @Column(name = "changed")
  @UpdateTimestamp
  private Date changed;

  public boolean hasFeature(CurrencyFeatureType feature) {
    if (features == null) {
      return false;
    }
    return List.of(features).contains(feature);
  }

  @Builder(toBuilder = true)
  public CurrencyEntity(
      Long id, String tag, Set<CurrencyFeatureType> features, String symbol, Date created, Date changed
  ) {
    setId(id);
    setTag(tag);
    setFeatures(features);
    setSymbol(symbol);
    setCreated(created);
    setChanged(changed);
  }

  @Transient
  public static CurrencyEntity getBasicCurrency(String tag, Set<CurrencyFeatureType> features, String symbol) {
    return CurrencyEntity.builder()
                         .tag(tag)
                         .features(features)
                         .symbol(symbol)
                         .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CurrencyEntity)) {
      return false;
    }

    CurrencyEntity that = (CurrencyEntity) o;

    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}