package de.merkeg.strichlistebackend.user;

import de.merkeg.strichlistebackend.transaction.Transaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_list")
public class User {

    @Getter
    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_seq",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Getter
    private String name;

    @Getter
    @Setter
    private Float balance;

    @Getter
    private LocalDateTime creationDate;

    @Getter
    @Setter
    private LocalDateTime lastTransaction;

    @OneToMany(mappedBy = "owner")
    @Getter
    private List<Transaction> transactions;

    public User(String name, float balance) {
        this.name = name;
        this.balance = balance;
        this.creationDate = LocalDateTime.now();
        this.lastTransaction = LocalDateTime.now();
    }

    public User() {
        this.creationDate = LocalDateTime.now();
        this.lastTransaction = LocalDateTime.now();
    }

    public static class NewUserRequestBody {
        @Getter
        private String name;
        @Getter
        private Float startingBalance;
    }
}
