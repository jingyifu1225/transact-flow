package dev.jingyi.TransactFlow.repository;

import dev.jingyi.TransactFlow.entity.Transaction;
import dev.jingyi.TransactFlow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
