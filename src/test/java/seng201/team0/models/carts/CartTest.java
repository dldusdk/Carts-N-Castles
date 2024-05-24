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
        cart = new Cart(image,1, "bronze",2,
                new ArrayList<>(),new ArrayList<>(),0,"1");
    }

    @Test
    void setLoad() {
        assertEquals(cart.getLoadPercent(),10);
    }

    @Test
    void getLoadPercent() {
        assert(cart.getLoadPercent() == 4);
    }

}