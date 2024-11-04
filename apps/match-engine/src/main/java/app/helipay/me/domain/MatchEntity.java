package app.helipay.me.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
public class MatchEntity {

    enum StatusType {
        MATCH, UNMATCH, PENDING, REJECTED, BLOCK
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID matchId;


    private long userFrom;
    private long userTo;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private StatusType status;


    @Column(name = "created")
    @CreationTimestamp
    private Instant created;
    @Column(name = "changed")
    @UpdateTimestamp
    private Instant changed;
}
