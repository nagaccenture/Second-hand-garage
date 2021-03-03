package com.cognizant.garage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "car_in_order")
public class CarInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderMain orderMain;


    @NotEmpty
    private String carId;


    @NotEmpty
    private String carName;

    private String carImage;

    @NotNull
    private BigDecimal carPrice;
    

    @Min(1)
    private Integer count;


    public CarInOrder(CarInfo car, Integer quantity) {
        this.carId = car.getCarId();
        this.carName = car.getCarName();
        this.carImage = car.getCarImage();
        this.carPrice = car.getCarPrice();
        this.count = quantity;
    }

    @Override
    public String toString() {
        return "CarInOrder{" +
                "id=" + id +
                ", carId='" + carId + '\'' +
                ", carName='" + carName + '\'' +
                ", carImage='" + carImage + '\'' +
                ", carPrice=" + carPrice +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarInOrder that = (CarInOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(carId, that.carId) &&
                Objects.equals(carName, that.carName) &&
                Objects.equals(carImage, that.carImage) &&
                Objects.equals(carPrice, that.carPrice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, carId, carName, carImage, carPrice);
    }
}
