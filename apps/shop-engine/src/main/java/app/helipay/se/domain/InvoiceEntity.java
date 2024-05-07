package app.helipay.se.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class InvoiceEntity {

    public enum StatusType {
        APPROVED,
        CANCEL,
        ENTERED,
        HOLD,
        PAID,
        PENDING_REVERSAL,
        REVERSED,
        SCHEDULED,
        WAITING_ON_APPROVAL;
    }


    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusType status;
}