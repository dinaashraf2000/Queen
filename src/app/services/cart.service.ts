
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';

// Define the structure of a Cart and CartItem
export interface CartItem {
  productId: number;
  productName: string;
  quantity: number;
  price: number;
}

export interface Cart {
  id: string; // UUID
  items: CartItem[];
  total: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = 'http://localhost:8081/carts';
  private cartIdKey = 'cart_id';

  // BehaviorSubject to keep track of the current cart state
  private cartSubject = new BehaviorSubject<Cart | null>(null);
  public cart$ = this.cartSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadInitialCart();
  }

  private getCartId(): string | null {
    return localStorage.getItem(this.cartIdKey);
  }

  private setCartId(cartId: string): void {
    localStorage.setItem(this.cartIdKey, cartId);
  }

  // Load cart from backend if a cartId exists in local storage
  private loadInitialCart(): void {
    const cartId = this.getCartId();
    if (cartId) {
      this.http.get<Cart>(`${this.apiUrl}/${cartId}`).subscribe({
        next: cart => this.cartSubject.next(cart),
        error: () => localStorage.removeItem(this.cartIdKey) // Clear invalid cartId
      });
    }
  }

  // Create a new cart if one doesn't exist, or return the existing one
  private createOrGetCart(): Observable<string> {
    const cartId = this.getCartId();
    if (cartId) {
      return of(cartId);
    }
    return this.http.post<Cart>(this.apiUrl, {}).pipe(
      tap(cart => this.setCartId(cart.id)),
      switchMap(cart => of(cart.id))
    );
  }

  /**
   * Adds an item to the shopping cart.
   * @param productId The ID of the product to add.
   */
  addItemToCart(productId: number): Observable<any> {
    return this.createOrGetCart().pipe(
      switchMap(cartId => {
        return this.http.post(`${this.apiUrl}/${cartId}/items`, { productId });
      }),
      tap(() => this.loadInitialCart()) // Refresh cart state after adding
    );
  }

  /**
   * Fetches the current cart from the backend.
   * @returns An observable of the cart.
   */
  getCart(): Observable<Cart> {
    const cartId = this.getCartId();
    if (!cartId) {
      return of({ id: '', items: [], total: 0 }); // Return empty cart if no ID
    }
    return this.http.get<Cart>(`${this.apiUrl}/${cartId}`);
  }
}
