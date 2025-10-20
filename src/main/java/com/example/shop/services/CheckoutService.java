package com.example.shop.services;

import com.example.shop.dtos.CheckoutRequest;
import com.example.shop.dtos.CheckoutResponce;
import com.example.shop.entities.Order;
import com.example.shop.exceptions.CartEmptyException;
import com.example.shop.exceptions.NotFoundCartException;
import com.example.shop.repositories.CartRepository;
import com.example.shop.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class CheckoutService {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

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


        cartService.clearCart(request.getCartId());
        var checkoutResponce = new CheckoutResponce(order.getId());
        return checkoutResponce;


    }
}
