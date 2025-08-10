package com.usta_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "ads")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ad {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=120)
    private String title;

    @Column(nullable=false, length=4000)
    private String description;

    @Column(nullable=false)
    private BigDecimal price;

    @Column(nullable=false, length=64)
    private String category;   // пока строкой (потом вынесем в таблицу Category)

    @Column(nullable=false, length=64)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=16)
    private Status status;     // ACTIVE / HIDDEN / ARCHIVED

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable=false, updatable=false)
    private Instant createdAt;

    @Column(nullable=false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
        if (status == null) status = Status.ACTIVE;
    }
    @PreUpdate
    void preUpdate() { updatedAt = Instant.now(); }

    public enum Status { ACTIVE, HIDDEN, ARCHIVED }
}

