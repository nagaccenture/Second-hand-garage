import {AfterContentChecked, Component, OnDestroy, OnInit} from '@angular/core';
import {CartService} from '../../services/cart.service';
import {Subject, Subscription} from 'rxjs';
import {UserService} from '../../services/user.service';
import {JwtResponse} from '../../response/JwtResponse';
import {CarInOrder} from '../../models/CarInOrder';
import {debounceTime, switchMap} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';
import {Role} from '../../enum/Role';

@Component({
    selector: 'app-cart',
    templateUrl: './cart.component.html',
    styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit, OnDestroy, AfterContentChecked {

    constructor(private cartService: CartService,
                private userService: UserService,
                private router: Router) {
        this.userSubscription = this.userService.currentUser.subscribe(user => this.currentUser = user);
    }

    carInOrders = [];
    total = 0;
    currentUser: JwtResponse;
    userSubscription: Subscription;

    private updateTerms = new Subject<CarInOrder>();
    sub: Subscription;

    static validateCount(carInOrder) {
        const max = carInOrder.productStock;
        if (carInOrder.count > max) {
            carInOrder.count = max;
        } else if (carInOrder.count < 1) {
            carInOrder.count = 1;
        }
        console.log(carInOrder.count);
    }

    ngOnInit() {
        this.cartService.getCart().subscribe(prods => {
            this.carInOrders = prods;
          console.log(prods);
            console.log(this.carInOrders);
        });

        this.sub = this.updateTerms.pipe(
            // wait 300ms after each keystroke before considering the term
            debounceTime(300),
            //
            // ignore new term if same as previous term
            //
            // switch to new search observable each time the term changes
            switchMap((carInOrder: CarInOrder) => this.cartService.update(carInOrder))
        ).subscribe(prod => {
                if (prod) { throw new Error(); }
            },
            _ => console.log('Update Item Failed'));
    }

    ngOnDestroy() {
        if (!this.currentUser) {
            this.cartService.storeLocalCart();
        }
        this.userSubscription.unsubscribe();
    }

    ngAfterContentChecked() {
      console.log(this.carInOrders);
        this.total = this.carInOrders.reduce(
            (prev, cur) => prev + cur.count * cur.carPrice, 0);
    }

    addOne(carInOrder) {
        carInOrder.count++;
        CartComponent.validateCount(carInOrder);
        if (this.currentUser) { this.updateTerms.next(carInOrder); }
    }

    minusOne(carInOrder) {
        carInOrder.count--;
        CartComponent.validateCount(carInOrder);
        if (this.currentUser) { this.updateTerms.next(carInOrder); }
    }

    onChange(carInOrder) {
        CartComponent.validateCount(carInOrder);
        if (this.currentUser) { this.updateTerms.next(carInOrder); }
    }


    remove(carInOrder: CarInOrder) {
        this.cartService.remove(carInOrder).subscribe(
            success => {
               this.carInOrders = this.carInOrders.filter(e => e.carId !== carInOrder.carId);
                console.log('Cart: ' + this.carInOrders);
            },
            _ => console.log('Remove Cart Failed'));
    }

    checkout() {
        if (!this.currentUser) {
            this.router.navigate(['/login'], {queryParams: {returnUrl: this.router.url}});
      /*  } else if (this.currentUser.role !== Role.Customer) {
            this.router.navigate(['/seller']);*/
        } else {
            this.cartService.checkout().subscribe(
                _ => {
                    this.carInOrders = [];
                },
                error1 => {
                    console.log('Checkout Cart Failed');
                });
            this.router.navigate(['/']);
        }

    }
}

