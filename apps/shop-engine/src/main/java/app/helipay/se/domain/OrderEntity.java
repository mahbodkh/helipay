package app.helipay.se.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Table(name = "se_order")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderEntity {

    public enum StatusType
    {
        CREATED,
        ISSUED,
        SUSPEND,
        DELIVERED,
        DONE,
        ;
    }

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String trackingNumber;
    @Column
    @Builder.Default
    private Double total = 0.0;
    @Column
    @Builder.Default
    private Double discount = 0.0;
    @Column
    @Builder.Default
    private Double finalPrice = 0.0;
    @Builder.Default
    private List<ProductEntity> products = new ArrayList<>();
    private UUID userId;
    @Column(name = "created")
    @CreationTimestamp
    private Instant created;
    @Column(name = "changed")
    @UpdateTimestamp
    private Instant changed;
}
