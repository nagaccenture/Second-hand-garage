import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {apiUrl} from '../../environments/environment';
import {CookieService} from 'ngx-cookie-service';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {catchError, map, tap} from 'rxjs/operators';
import {UserService} from './user.service';
import {Cart} from '../models/Cart';
import {Item} from '../models/Item';
import {JwtResponse} from '../response/JwtResponse';
import {CarInOrder} from '../models/CarInOrder';

@Injectable({
    providedIn: 'root'
})
export class CartService {


    private cartUrl = `${apiUrl}/cart`;

    localMap = {};


    private itemsSubject: BehaviorSubject<Item[]>;
    private totalSubject: BehaviorSubject<number>;
    public items: Observable<Item[]>;
    public total: Observable<number>;


    private currentUser: JwtResponse;

    constructor(private http: HttpClient,
                private cookieService: CookieService,
                private userService: UserService) {
        this.itemsSubject = new BehaviorSubject<Item[]>(null);
        this.items = this.itemsSubject.asObservable();
        this.totalSubject = new BehaviorSubject<number>(null);
        this.total = this.totalSubject.asObservable();
        this.userService.currentUser.subscribe(user => this.currentUser = user);


    }

    private getLocalCart(): CarInOrder[] {
        if (this.cookieService.check('cart')) {
            this.localMap = JSON.parse(this.cookieService.get('cart'));
            return Object.values(this.localMap);
        } else {
            this.localMap = {};
            return [];
        }
    }

    getCart(): Observable<CarInOrder[]> {
        const localCart = this.getLocalCart();
        if (this.currentUser) {
            if (localCart.length > 0) {
                return this.http.post<Cart>(this.cartUrl, localCart).pipe(
                    tap(_ => {
                        this.clearLocalCart();
                    }),
                    map(cart => cart.carInOrders),
                    catchError(_ => of([]))
                );
            } else {
                return this.http.get<Cart>(this.cartUrl).pipe(
                    map(cart => cart.carInOrders),
                    catchError(_ => of([]))
                );
            }
        } else {
            return of(localCart);
        }
    }

    addItem(carInOrder): Observable<boolean> {
        if (!this.currentUser) {
          console.log('no user');
            if (this.cookieService.check('cart')) {
                this.localMap = JSON.parse(this.cookieService.get('cart'));
            }
            if (!this.localMap[carInOrder.carId]) {
                this.localMap[carInOrder.carId] = carInOrder;
            } else {
                this.localMap[carInOrder.carId].count += carInOrder.count;
            }
            this.cookieService.set('cart', JSON.stringify(this.localMap));
            return of(true);
        } else {
          console.log(' user');
          const url = `${this.cartUrl}/add`;
            return this.http.post<boolean>(url, {
                'quantity': carInOrder.count,
                'carId': carInOrder.carId
            });
        }
    }

    update(carInOrder): Observable<CarInOrder> {

        if (this.currentUser) {
            const url = `${this.cartUrl}/${carInOrder.carId}`;
            return this.http.put<CarInOrder>(url, carInOrder.count);
        }
    }


    remove(carInOrder) {
        if (!this.currentUser) {
            delete this.localMap[carInOrder.carId];
            return of(null);
        } else {
            const url = `${this.cartUrl}/${carInOrder.carId}`;
            return this.http.delete(url).pipe( );
        }
    }


    checkout(): Observable<any> {
        const url = `${this.cartUrl}/checkout`;
        return this.http.post(url, null).pipe();
    }

    storeLocalCart() {
        this.cookieService.set('cart', JSON.stringify(this.localMap));
    }

    clearLocalCart() {
        console.log('clear local cart');
        this.cookieService.delete('cart');
        this.localMap = {};
    }

}
