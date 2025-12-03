package com.example.shop.payment;

import com.example.shop.entities.Order;
import com.example.shop.exceptions.CartEmptyException;
import com.example.shop.exceptions.NotFoundCartException;
import com.example.shop.repositories.CartRepository;
import com.example.shop.repositories.OrderRepository;
import com.example.shop.services.AuthService;
import com.example.shop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final PaymentGatway paymentGatway;

    @Value("${stripe.webhookSwcretKey}")
    private String webhookSecretKey;
      @Transactional
    public CheckoutResponce checkout(CheckoutRequest request) {

        var cart= cartRepository.findById(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new NotFoundCartException();
        }
        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }
        var user= authService.getCurrentUser();

        var order= Order.createOrder(user,cart);

        orderRepository.save(order);
try {

 var session= paymentGatway.creatCheckoutSession(order);

    cartService.clearCart(request.getCartId());
    var checkoutResponce = new CheckoutResponce(order.getId(),session.getCheckoutUrl());
    return checkoutResponce;

}
catch (PaymentException e){
    orderRepository.delete(order);
    throw e;
}
    }

    public void handleWebhookEvent(WebhookRequest request) {
          paymentGatway.parseWebhookRequest(request).ifPresent(paymentResult->{
            var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
            order.setStatus(paymentResult.getStatus());
            orderRepository.save(order);
        });

    }
}
