package com.example.shop.controler;

import com.example.shop.dtos.CheckoutRequest;
import com.example.shop.dtos.CheckoutResponce;
import com.example.shop.dtos.ErrorDto;
import com.example.shop.entities.Order;
import com.example.shop.exceptions.CartEmptyException;
import com.example.shop.exceptions.NotFoundCartException;
import com.example.shop.repositories.CartRepository;
import com.example.shop.repositories.OrderRepository;
import com.example.shop.services.AuthService;
import com.example.shop.services.CartService;
import com.example.shop.services.CheckoutService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutControler {

private final CheckoutService checkoutService;
private final CartService cartService;
    @PostMapping
    ResponseEntity<CheckoutResponce> checkout(@RequestBody CheckoutRequest request) {

           var checkoutResponce =checkoutService.checkout(request);

           return ResponseEntity.status(HttpStatus.CREATED).body(checkoutResponce);


  }
  @ExceptionHandler({NotFoundCartException.class, CartEmptyException.class})
  public ResponseEntity<ErrorDto> handleException(Exception e) {

        return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
  }
}
