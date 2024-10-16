package dev.jingyi.TransactFlow.service;

import dev.jingyi.TransactFlow.entity.Transaction;
import dev.jingyi.TransactFlow.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // create new transaction
    public Transaction createTransaction(Transaction transaction) {
        // later balance tracking
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
}
