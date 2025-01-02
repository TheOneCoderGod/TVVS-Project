package badIceCream.model.game.elements.fruits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PineappleFruitTest {

    private PineappleFruit pineappleFruit;

    @BeforeEach
    void setUp() {
        pineappleFruit = new PineappleFruit(1, 2);
    }

    @Test
    @DisplayName("Constructor sets X and Y")
    void testConstructor() {
        assertEquals(1, pineappleFruit.getPosition().getX());
        assertEquals(2, pineappleFruit.getPosition().getY());
    }

    @Test
    @DisplayName("getType() returns 4")
    void testGetType() {
        assertEquals(4, pineappleFruit.getType());
    }
}
