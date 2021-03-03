import {CarInOrder} from "./CarInOrder";

export class CarInfo {
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
    warehouse: string


    constructor(carInOrder?: CarInOrder) {
            this.carId = carInOrder.carId;
            this.carName = carInOrder.carName;
            this.milesperGallon = carInOrder.milesperGallon;
            this.cylinders = carInOrder.cylinders;
            this.displacement = carInOrder.displacement;
            this.carImage = carInOrder.carImage;
            this.horsepower = carInOrder.horsepower;
            this.carPrice = carInOrder.carPrice;
            this.acceleration = carInOrder.acceleration;
            this.licensed = carInOrder.licensed;
            this.addedDate = carInOrder.addedDate;
            this.warehouse = carInOrder.warehouse;
    }
}

