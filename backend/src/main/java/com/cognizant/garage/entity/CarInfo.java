package com.cognizant.garage.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@Table(name = "car_info")
public class CarInfo implements Serializable {
    @Id
    private String carId;

    @NotNull
    private String carName;

    private Double milesperGallon;

    private Integer cylinders;
    private Integer displacement;
    private String carImage;
    private Integer horsepower;
    private BigDecimal carPrice;
    private Double acceleration;
    private Boolean licensed;

    private Date addedDate;

    private String warehouse;
    public CarInfo() {
    }
}
