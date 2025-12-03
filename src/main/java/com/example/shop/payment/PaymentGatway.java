package com.example.shop.payment;

import com.example.shop.entities.Order;

import java.util.Optional;

public interface PaymentGatway {
    CheckoutSession creatCheckoutSession(Order order);
   Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}