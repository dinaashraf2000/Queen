package com.example.shop.dtos;

import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutResponce {
private long orderId;


}
