package badIceCream.model.game.elements.fruits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BananaFruitTest {

    private BananaFruit bananaFruit;

    @BeforeEach
    void setUp() {
        bananaFruit = new BananaFruit(2, 3);
    }

    @Test
    @DisplayName("Constructor sets X and Y")
    void testConstructor() {
        assertEquals(2, bananaFruit.getPosition().getX());
        assertEquals(3, bananaFruit.getPosition().getY());
    }

    @Test
    @DisplayName("getType() returns 2")
    void testGetType() {
        assertEquals(2, bananaFruit.getType());
    }
}
