package dev.jingyi.TransactFlow.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private Long userId;
    private BigDecimal amount;
    private BigDecimal balanceAfterTransaction;
    private String status;
    private LocalDateTime transactionDate;

    public TransactionDTO() {}

    // 构造函数
    public TransactionDTO(Long id, Long userId, BigDecimal amount, BigDecimal balanceAfterTransaction, String status, LocalDateTime transactionDate) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    // Getters 和 Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
