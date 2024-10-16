package dev.jingyi.TransactFlow.controller;

import dev.jingyi.TransactFlow.entity.Transaction;
import dev.jingyi.TransactFlow.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Create a new transaction
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(createdTransaction);
    }

    // Get transaction by ID
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transactionOptional = transactionService.getTransactionById(id);
        return transactionOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all transactions
    @GetMapping(produces = "application/json")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // Update the transaction status
    @PutMapping(value = "/{id}/status", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Transaction> updateTransactionStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Transaction updatedTransaction = transactionService.updateTransactionStatus(id, status);
            return ResponseEntity.ok(updatedTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete transaction by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
