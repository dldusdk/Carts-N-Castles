package seng201.team0.models.carts;

import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    Cart cart;

    @BeforeEach
    void setUp() {
        ImageView image = new ImageView("p");
        ArrayList<RotateTransition> rotatepath = new ArrayList<>();
        cart = new Cart(image,2,
                "bronze",4,new ArrayList<>(),new ArrayList<>(),7,"1");
    }

    @AfterEach
    void tearDown() {

    }

    int number = 1;
    @Test
    void setLoad() {
    }

    @Test
    void getLoadPercent() {
        assert(cart.getLoadPercent() == 4);
    }

    @Test
    void updateImage() {
    }

    @Test
    void loadCart() {
    }

    @Test
    void animateCart() {
    }

    @Test
    void despawn() {
    }

    @Test
    void getResourceType() {
    }

    @Test
    void setDestroyed() {
    }

    @Test
    void getDestroyed() {
    }

    @Test
    void explode() {
    }

    @Test
    void getCartObject() {
    }
}