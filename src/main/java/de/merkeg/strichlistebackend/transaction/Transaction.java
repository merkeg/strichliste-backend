package de.merkeg.strichlistebackend.transaction;

import de.merkeg.strichlistebackend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table
public class Transaction {

    @Getter
    @Id
    @SequenceGenerator(
            name = "transaction_seq",
            sequenceName = "transaction_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "transaction_seq",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Getter
    private Float amount;

    @Getter
    private LocalDateTime creationDate;

    @Getter
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;


    public Transaction() {
        this.creationDate = LocalDateTime.now();
    }

    public Transaction(Float amount, User owner) {
        this.amount = amount;
        this.owner = owner;
        this.creationDate = LocalDateTime.now();
    }

    public static class NewTransactionRequest{
        @Getter
        private Long owner;

        @Getter
        private Float amount;
    }
}
