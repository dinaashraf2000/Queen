package com.example.shop.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    // تفاصيل المنتج (يجب تضمينها لكي يعرف العميل ما هو العنصر)
    private Long productId;       // معرّف المنتج
    private String productName;   // اسم المنتج
    private BigDecimal price;     // سعر الوحدة الواحدة من المنتج

    // بيانات الكمية والإجمالي
    private int quantity;     // الكمية المُحدَّثة
    private BigDecimal totalPrice;  // الإجمالي الفرعي لهذا العنصر (الكمية * السعر)
}