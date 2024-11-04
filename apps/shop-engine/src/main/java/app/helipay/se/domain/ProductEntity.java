package app.helipay.se.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

import static app.helipay.se.service.SlugHandler.getSlugger;


/**
 * Created by Ebrahim Kh.
 */

//@Entity
//@Table(name = "se_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@TypeDef(name = "jsonb", typeClass = JsonType.class)
@Builder
public class ProductEntity
{

  private static final ObjectMapper mapper
      = new ObjectMapper();

  public enum StatusType
  {
    AVAILABLE,
    DISCONTINUE,
    DISABLE
  }

  @Setter
  @Getter
  @ToString
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Detail
  {
    // the size of product
    // - the weight of product
    // - the width of product
    // - the height of product
    // the color of product
    // the brand of product
    // the vendor of product
    // more details. E.g. [ weight: 1.5 oz, weight: 100 gr ]
    @Builder.Default
    private Map<String, String> data = new HashMap<>();
  }

  @Transient
  public Detail getDetailProperties() {
    return getPropertiesByType(Detail.class);
  }

  @Transient
  public void setDetailProperties(Detail detail) {
    setPropertiesByType(detail);
  }

  @SuppressWarnings("unchecked")
  @Transient
  private <T> void setPropertiesByType(T t) {
    var newMap = mapper.convertValue(t, Map.class);
    setProperties(newMap);
  }

  @Transient
  private <T> T getPropertiesByType(Class<T> classType) {
    var props = Optional.ofNullable(getProperties()).orElseGet(HashMap::new);
    setProperties(props);
    return mapper.convertValue(props, classType);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String sku;
  @NaturalId
  @Column(nullable = false, unique = true)
  private String slug; // url and unique
  // https://stackoverflow.com/questions/13370221/persistentobjectexception-detached-entity-passed-to-persist-thrown-by-jpa-and-h
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "category_id")
  private CategoryEntity category;
  private String name;                    // name of the product
  private String description;             //
  private Integer stack;                  // the number of qty product in the warehouse

  // https://stackoverflow.com/questions/15726535/postgresql-which-datatype-should-be-used-for-currency
  @Builder.Default
//  @Digits(integer = 10 /*precision*/, fraction = 3 /*scale*/)
  //  @Column(name="price", precision = 10, scale = 3, nullable = false)
  //  @Type(type = "big_decimal")
  private Double price = 0.0;
  private Boolean exist;                  // the product isThere in store
  private Integer sellCount;                  // sell product count

  @OneToOne
  @JoinColumn(name = "post_id", referencedColumnName = "id")
  private PostEntity post;
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusType status;
  @Column(name = "coverUrl")
  private String coverUrl;                 // main image url

  //  @Builder.Default
  //  @Column(name = "imageUrls")
  //  @Convert(converter = SetStringConverter.class)
  //  private Set<String> imageUrls = new HashSet<>();                          // Other images url

  //  @Builder.Default
  //  @Type(type = "jsonb")
  //  @Convert(converter = SetStringConverter.class)
  //  @Column(name = "keywords")
  //  private Set<String> keywords = new HashSet<>();                           // tag or keyword for better search


  @Builder.Default
//  @Type(type = "jsonb")
  @Column(name = "properties", columnDefinition = "jsonb")
  private Map<String, Object> properties = new HashMap<>();

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



  @Transient
  public static ProductEntity getBasicProduct(String name, String slug, Double price, Integer stack, CategoryEntity category) {
    if (name == null || name.isEmpty()) {
//      throw new ErrorException("Name is null or empty!");
    }
    slug = getSlugger(name, slug);

    return ProductEntity.builder()
                        .status(StatusType.AVAILABLE)
                        .category(category)
                        .price(price)
                        .stack(stack)
                        .name(name)
                        .slug(slug)
                        .build();
  }




  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProductEntity)) {
      return false;
    }
    ProductEntity that = (ProductEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }

  @Override
  public String toString() {
    return "ProductEntity{" +
        "id=" + id +
        ", sku='" + sku + '\'' +
        ", category=" + category +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", stack=" + stack +
        ", price='" + price + '\'' +
        ", exist=" + exist +
        ", sellCount=" + sellCount +
//        ", post=" + post +
        ", status=" + status +
        ", coverUrl='" + coverUrl + '\'' +
        //        ", imageUrls=" + imageUrls +
        //        ", keywords=" + keywords +
        ", properties=" + properties +
        '}';
  }
}