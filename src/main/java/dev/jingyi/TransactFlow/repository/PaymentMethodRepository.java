package dev.jingyi.TransactFlow.repository;

import dev.jingyi.TransactFlow.entity.PaymentMethod;
import dev.jingyi.TransactFlow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    List<PaymentMethod> findByUser(User user);
}
