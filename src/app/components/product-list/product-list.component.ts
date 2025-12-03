
import { Component, OnInit } from '@angular/core';
import { Product, ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  errorMessage: string = '';

  constructor(
    private productService: ProductService, 
    private cartService: CartService
  ) { }

  ngOnInit(): void {
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
      },
      error: (err) => {
        console.error('Failed to load products', err);
        this.errorMessage = 'Could not load products. Please try again later.';
      }
    });
  }

  addToCart(productId: number): void {
    this.cartService.addItemToCart(productId).subscribe({
      next: () => {
        console.log(`Product ${productId} added to cart`);
        // Optionally, show a success notification
      },
      error: (err) => {
        console.error('Failed to add item to cart', err);
        // Optionally, show an error notification
      }
    });
  }
}
