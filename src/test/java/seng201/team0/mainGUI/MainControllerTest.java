package test.java.seng201.team0.mainGUI;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng201.team0.gui.mainGUI.MainController;

public class MainControllerTest {
    private static MainController controller;

    @BeforeAll
    static void init() {
        controller = new MainController();
    }

    @Test
    void emptyNameTest() {
        boolean result = controller.validNameTest("");
        assertFalse(result);
    }
    @Test
    void doubleEmptyNameTest() {
        boolean result = controller.validNameTest("  ");
        assertFalse(result);
    }
    @Test
    void invalidShortNameTest() {
        boolean result = controller.validNameTest("a");
        assertFalse(result);
    }
    @Test
    void invalidSpaceInBetweenTest(){
        boolean result = controller.validNameTest("a ");
        assertFalse(result);
    }
    @Test
    void invalidLengthMidNameTest() {
        boolean result = controller.validNameTest("ab");
        assertFalse(result);
    }
    @Test
    void invalidSpaceAfterTest() {
        boolean result = controller.validNameTest("ab ");
        assertFalse(result);
    }
    @Test
    void invalidmaxLengthTest(){
        boolean result = controller.validNameTest("abcdefghijklmnop");
        assertFalse(result);
    }
    @Test
    void invalidSpecialCharaTest() {
        boolean result = controller.validNameTest("!");
        assertFalse(result);
    }
    @Test
    void invalidLengthySpecialCharTest() {
        boolean result = controller.validNameTest("!!@#$%^@!@#$%!!");
        assertFalse(result);
    }
    @Test
    void minLengthNameTest() {
        boolean result = controller.validNameTest("mic");
        assertTrue(result);
    }
    @Test
    void maxLengthNameTest() {
        boolean result = controller.validNameTest("abcdefghijklmno");
        assertTrue(result);
    }
}

