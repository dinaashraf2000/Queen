package com.example.shop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders", schema = "store")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;


    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDateTime createdAt;


    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private Set<OrderItem> items = new LinkedHashSet<>();

public static Order createOrder(User user, Cart cart){
    var order=new Order();
    order.setCustomer(user);
    order.setStatus(OrderStatus.PENDING);
    order.setTotalPrice(cart.getTotalPrice());

    cart.getItems().forEach(item->{
        var orderItem = new OrderItem();
        orderItem.setProduct(item.getProduct());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setUnitPrice(item.getProduct().getPrice());
        orderItem.setTotalPrice(item.getTotalPrice());
        orderItem.setOrder(order);
        order.items.add(orderItem);

    });
    return order;
}


}