import {CarInfo} from './carInfo';

export class CarInOrder {
  carId: string;
  carName: string;
  milesperGallon: number;
  cylinders: number;
  displacement: number;
  carImage: string;
  horsepower: number;
  carPrice: number;
  acceleration: number;
  licensed: boolean;
  addedDate: Date;
  warehouse: string;
  count: number

    constructor(carInfo: CarInfo, quantity = 1) {
      this.carId = carInfo.carId;
      this.carName = carInfo.carName;
      this.milesperGallon = carInfo.milesperGallon;
      this.cylinders = carInfo.cylinders;
      this.displacement = carInfo.displacement;
      this.carImage = carInfo.carImage;
      this.horsepower = carInfo.horsepower;
      this.carPrice = carInfo.carPrice;
      this.acceleration = carInfo.acceleration;
      this.licensed = carInfo.licensed;
      this.addedDate = carInfo.addedDate;
      this.warehouse = carInfo.warehouse;
        this.count = quantity;
    }
}
