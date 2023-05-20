package de.merkeg.strichlistebackend.transaction;

import de.merkeg.strichlistebackend.user.User;
import de.merkeg.strichlistebackend.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

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
}
