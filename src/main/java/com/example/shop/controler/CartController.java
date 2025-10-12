package com.example.shop.controler;

import com.example.shop.dtos.AddItemToCartRequest;
import com.example.shop.dtos.CartDto;
import com.example.shop.dtos.CartItemDto;
import com.example.shop.dtos.UpdateItemRequest;
import com.example.shop.entities.Cart;
import com.example.shop.entities.CartItem;
import com.example.shop.mappers.CartMapper;
import com.example.shop.repositories.CartRepository;
import com.example.shop.repositories.ProductRepository;
import com.example.shop.services.CartService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> creatCart(UriComponentsBuilder uriBuilder) {
        var cartDto = cartService.creatCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

@PostMapping("/{cartId}/items")
   public ResponseEntity<CartItemDto>addItemtoCart(@PathVariable UUID cartId,@RequestBody AddItemToCartRequest request) {

       var cartDto=cartService.addtoCart(cartId, request.getProductId());

    return ResponseEntity.status(HttpStatus.CREATED).body(cartDto);
}

@GetMapping("/{cartId}")
public ResponseEntity<CartDto>getCart(@PathVariable UUID cartId) {
     var cartDto=cartService.getCart(cartId);

        return ResponseEntity.ok(cartDto);

}

@PutMapping("/{cartId}/items/{productId}")
public ResponseEntity<CartItemDto>updateCartItem(@PathVariable UUID cartId,@PathVariable Long productId,@Valid @RequestBody UpdateItemRequest request) {
     var cartItem=cartService.updateCart(cartId,productId,request.getQuantity());
return ResponseEntity.ok(cartItem);
}

@DeleteMapping("/{cartId}/items/{productId}")
public ResponseEntity<Void> deleteCartItem(@PathVariable UUID cartId,@PathVariable Long productId) {
   cartService.deleteCartItem(cartId,productId);
    return ResponseEntity.noContent().build();
}

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> deleteallCartItems(@PathVariable UUID cartId) {

       cartService.deleteallCartItems(cartId);
        return ResponseEntity.noContent().build();
    }

}