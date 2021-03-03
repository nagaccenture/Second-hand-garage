package com.cognizant.garage.api;

import com.cognizant.garage.form.ItemForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;

public class CartControllerTest {
    @Autowired
    CartController cartController;
    private Principal  Principal;

   /* @Test
    public void getCart() {
        cartController.getCart(Principal);
    }

    @Test
    public void addCart() {

        ItemForm  form = new ItemForm();
        form.setCarId("123") ;
        form.setQuantity(1);

        cartController.addToCart(form,Principal);
    }*/
}
