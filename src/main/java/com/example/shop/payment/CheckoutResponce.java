package com.example.shop.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutResponce {
private long orderId;
private String url;
}
