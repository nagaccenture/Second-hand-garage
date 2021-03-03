import {Component, OnInit} from '@angular/core';
import {CarService} from '../../services/car.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CartService} from '../../services/cart.service';
import {CookieService} from 'ngx-cookie-service';
import {CarInOrder} from '../../models/CarInOrder';
import {CarInfo} from '../../models/carInfo';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {
  title: string;
  count: number;
  carInfo: CarInfo;

  constructor(
      private carService: CarService,
      private cartService: CartService,
      private cookieService: CookieService,
      private route: ActivatedRoute,
      private router: Router
  ) {
  }

  ngOnInit() {
    this.getCar();
    this.title = 'Car Detail';
    this.count = 1;
  }

  getCar(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.carService.getCarDetail(id).subscribe(
        prod => {
          this.carInfo = prod;
        },
        _ => console.log('Get Cart Failed')
    );
  }

  addToCart() {
    this.cartService
        .addItem(new CarInOrder(this.carInfo, this.count))
        .subscribe(
            res => {
              if (!res) {
                console.log('Add Cart failed' + res);
                throw new Error();
              }
              this.router.navigateByUrl('/cart');
            },
            _ => console.log('Add Cart Failed')
        );
  }


}
