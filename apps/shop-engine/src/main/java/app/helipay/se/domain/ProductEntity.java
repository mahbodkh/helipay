package app.helipay.se.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;


@Getter
@Setter
@ToString
@Table(name = "bs_product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProductEntity {

    public enum ProductStatus {

    }

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    @Builder.Default
    private Double price = 0.0;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    @Column(name = "created")
    @CreationTimestamp
    private Instant created;
    @Column(name = "changed")
    @UpdateTimestamp
    private Instant changed;
}
