package com.example.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {


        SpringApplication.run(ShopApplication.class, args);

      // var Servce = context.getBean(ProducteService.class);

    }
}
