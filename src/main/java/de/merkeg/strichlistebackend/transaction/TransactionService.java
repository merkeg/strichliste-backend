package de.merkeg.strichlistebackend.transaction;

import de.merkeg.strichlistebackend.exception.TransactionDoesNotExistException;
import de.merkeg.strichlistebackend.exception.TransactionUndoTimePassedException;
import de.merkeg.strichlistebackend.user.User;
import de.merkeg.strichlistebackend.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Value("${MAX_UNDO_TIME:10}")
    private long maxUndoTime;

    public Transaction createTransaction(Long userId, Float amount) {
        User user = userService.getUser(userId);

        Transaction transaction = new Transaction(amount, user);
        transactionRepository.save(transaction);
        log.info("Created Transaction: {} ({} Euro)", transaction.getId(), transaction.getAmount());

        Float oldValue = user.getBalance();
        user.setBalance(oldValue + transaction.getAmount());
        user.setLastTransaction(LocalDateTime.now());
        userService.saveUser(user);
        log.info("Applied Transaction {} ({} Euro) to user {} [{}->{}]", transaction.getId(), transaction.getAmount(), user.getName(), oldValue, user.getBalance());
        return transaction;
    }

    public Transaction getTransaction(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        if(transaction.isEmpty()) {
            throw new TransactionDoesNotExistException();
        }

        return transaction.get();
    }

    public void undoTransaction(long id) {
        Transaction transaction = this.getTransaction(id);

        long minutesPassed = ChronoUnit.MINUTES.between(transaction.getCreationDate(), LocalDateTime.now());
        if(minutesPassed <= maxUndoTime) {
            throw new TransactionUndoTimePassedException();
        }

        User user = transaction.getOwner();

        // Undo transaction value
        float oldValue = user.getBalance();
        user.setBalance(oldValue - transaction.getAmount());

        this.userService.saveUser(user);
        this.transactionRepository.delete(transaction);
        log.info("Applied Undo transaction {} ({} Euro) to user {} [{}->{}]", transaction.getId(), transaction.getAmount(), user.getName(), oldValue, user.getBalance());
    }
}
