package dev.jingyi.TransactFlow.service;

import dev.jingyi.TransactFlow.entity.PaymentMethod;
import dev.jingyi.TransactFlow.entity.User;
import dev.jingyi.TransactFlow.repository.PaymentMethodRepository;
import dev.jingyi.TransactFlow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private UserRepository userRepository;

    // Add a new payment method
    public PaymentMethod addPaymentMethod(Long userId, String paymentMethodId, String cardBrand, String last4, String expirationDate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setUser(user);
        paymentMethod.setPaymentMethodId(paymentMethodId);  // api payment id
        paymentMethod.setCardBrand(cardBrand);
        paymentMethod.setLast4(last4);
        paymentMethod.setExpirationDate(expirationDate);

        return paymentMethodRepository.save(paymentMethod);
    }

    // Retrieve payment methods by user
    public List<PaymentMethod> getPaymentMethodsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return paymentMethodRepository.findByUser(user);
    }

    // Delete payment method by ID
    public void deletePaymentMethod(Long paymentMethodId) {
        paymentMethodRepository.deleteById(paymentMethodId);
    }
}
