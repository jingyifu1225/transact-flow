package dev.jingyi.TransactFlow.controller;

import dev.jingyi.TransactFlow.dto.PaymentMethodDTO;
import dev.jingyi.TransactFlow.entity.PaymentMethod;
import dev.jingyi.TransactFlow.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    // Add a new payment method
    @PostMapping("/add")
    public ResponseEntity<PaymentMethod> addPaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = paymentMethodService.addPaymentMethod(
                paymentMethodDTO.getUserId(),
                paymentMethodDTO.getPaymentMethodId(),
                paymentMethodDTO.getCardBrand(),
                paymentMethodDTO.getLast4(),
                paymentMethodDTO.getExpirationDate()
        );
        return ResponseEntity.ok(paymentMethod);
    }

    // Get all payment methods for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<PaymentMethod>> getPaymentMethodsByUser(@PathVariable Long userId) {
        List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethodsByUser(userId);
        return ResponseEntity.ok(paymentMethods);
    }

    // Delete a payment method
    @DeleteMapping("/delete/{paymentMethodId}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long paymentMethodId) {
        paymentMethodService.deletePaymentMethod(paymentMethodId);
        return ResponseEntity.noContent().build();
    }
}

