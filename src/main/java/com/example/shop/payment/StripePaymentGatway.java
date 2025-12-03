package com.example.shop.payment;

import com.example.shop.entities.Order;
import com.example.shop.entities.OrderStatus;
import com.example.shop.repositories.OrderRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StripePaymentGatway implements PaymentGatway {
    private final OrderRepository orderRepository;

    @Value("${stripe.webhookSwcretKey}")
    private String webhookSecretKey;

    @Override
    public CheckoutSession creatCheckoutSession(Order order) {
        try {

            var builder=SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:4200/success")
                    .setCancelUrl("http://localhost:4200/cancel")
                    .putMetadata("orderId", String.valueOf(order.getId()));

            order.getItems().forEach(item->{
                var lineItem=SessionCreateParams.LineItem.builder()
                        .setQuantity(Long.valueOf(item.getQuantity()))
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(item.getProduct().getName())
                                        .build())
                                .build())
                        .build();
                builder.addLineItem(lineItem);
            });
            var session= Session.create(builder.build());
            return new CheckoutSession(session.getUrl());

        }
        catch (StripeException e){
            System.out.println(e.getMessage());
            throw new PaymentException(e.getMessage());
        }
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        try {
            var payload = request.getPayload();
            var signature = request.getHeaders().get("Stripe-Signature");
            var event = Webhook.constructEvent(payload, signature, webhookSecretKey);
            System.out.println(event.getType());
            var orderId = ExtractOrderId(event);
          return switch (event.getType()) {
                case "payment_intent.secceeded" ->
                        Optional.of(new PaymentResult(orderId, OrderStatus.PAID));

                case "payment_intent.failed" ->
                        Optional.of(new PaymentResult(orderId, OrderStatus.FAILED));

              default ->
                Optional.empty();
          };
        }
        catch (SignatureVerificationException e) {
throw new PaymentException("Invalid signature");
        }
    }

    private Long ExtractOrderId(Event event) {
        var stripeObject=event.getDataObjectDeserializer().getObject().orElseThrow(
                () -> new PaymentException("Invalid payment intent"));
            var paymentIntent = (PaymentIntent) stripeObject;
            return Long.valueOf(paymentIntent.getMetadata().get("orderId"));
        }

    }

