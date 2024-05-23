package seng201.team0.models.carts;

import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    Cart cart;

    @BeforeEach
    void setUp() {
        ImageView image = new ImageView("p");
        ArrayList<RotateTransition> rotatepath = new ArrayList<>();
        cart = new Cart(image,1, "bronze",2,
                new ArrayList<>(),new ArrayList<>(),0,"1");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void setLoad() {
        assertEquals(cart.getLoadPercent(),10);
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
        //assert
    }

    @Test
    void getCartObject() {
    }
}