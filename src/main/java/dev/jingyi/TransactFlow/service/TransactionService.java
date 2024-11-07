package dev.jingyi.TransactFlow.service;

import dev.jingyi.TransactFlow.dto.TransactionDTO;
import dev.jingyi.TransactFlow.entity.Transaction;
import dev.jingyi.TransactFlow.entity.User;
import dev.jingyi.TransactFlow.repository.TransactionRepository;
import dev.jingyi.TransactFlow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;


    // create new transaction
    public Transaction createTransaction(Transaction transaction) {
        User user = transaction.getUser();

        // check balance
        if (user.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // update balance and user information
        BigDecimal newBalance = user.getBalance().subtract(transaction.getAmount());
        user.setBalance(newBalance);
        userRepository.save(user);

        // save transaction record
        transaction.setBalanceAfterTransaction(newBalance);
        transaction.setStatus("SUCCESS");
        return transactionRepository.save(transaction);
    }

    // get transaction by ID
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    // get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // update transaction status
    public Transaction updateTransactionStatus(Long id, String status) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isPresent()) {
            Transaction transaction = transactionOptional.get();
            transaction.setStatus(status);
            return transactionRepository.save(transaction);
        }
        throw new RuntimeException("Transaction not found with id " + id); // transaction not found
    }

    // delete transaction
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getUser().getId(),
                transaction.getAmount(),
                transaction.getBalanceAfterTransaction(),
                transaction.getStatus(),
                transaction.getTransactionDate()
        );
    }
}
