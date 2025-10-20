package com.example.shop;

import com.example.shop.entities.Product;
import com.example.shop.entities.User;
import com.example.shop.services.ProducteService;
import com.example.shop.services.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {


        SpringApplication.run(ShopApplication.class, args);

      // var Servce = context.getBean(ProducteService.class);

    }
}
