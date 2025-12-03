package com.example.shop.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
@AllArgsConstructor
@Getter
public class WebhookRequest {
    String payload;
    Map<String, String> headers;
}
