package com.example.shop.payment;

import com.example.shop.repositories.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

private final CheckoutService checkoutService;
private final OrderRepository orderRepository;


    @PostMapping
    ResponseEntity<CheckoutResponce> checkout(@Valid @RequestBody CheckoutRequest request)    {
    var checkoutResponce = checkoutService.checkout(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(checkoutResponce);

  }
  @PostMapping("/webhook")
  public void handleWebhook(@RequestHeader Map<String, String> headers, @RequestBody String payload)  {
    checkoutService.handleWebhookEvent(new WebhookRequest(payload, headers));
  }

}
