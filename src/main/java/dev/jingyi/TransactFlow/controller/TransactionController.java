package dev.jingyi.TransactFlow.controller;

import dev.jingyi.TransactFlow.dto.TransactionDTO;
import dev.jingyi.TransactFlow.entity.Transaction;
import dev.jingyi.TransactFlow.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Create a new transaction
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody Transaction transaction) {
        // Call the service to create a new transaction
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        // Convert the created transaction to a DTO for response
        TransactionDTO transactionDTO = transactionService.convertToDTO(createdTransaction);
        return ResponseEntity.ok(transactionDTO);
    }

    // Get a transaction by ID
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        // Find the transaction by ID
        Optional<Transaction> transactionOptional = transactionService.getTransactionById(id);
        // If found, convert to DTO and return; if not, return 404 Not Found
        return transactionOptional
                .map(transactionService::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all transactions
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        // Retrieve all transactions and convert each to DTO
        List<TransactionDTO> transactionDTOs = transactionService.getAllTransactions().stream()
                .map(transactionService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactionDTOs);
    }

    // Update the status of a transaction
    @PutMapping(value = "/{id}/status", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TransactionDTO> updateTransactionStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            // Update transaction status by ID
            Transaction updatedTransaction = transactionService.updateTransactionStatus(id, status);
            // Convert updated transaction to DTO and return
            TransactionDTO transactionDTO = transactionService.convertToDTO(updatedTransaction);
            return ResponseEntity.ok(transactionDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a transaction by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        // Delete transaction by ID
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}

