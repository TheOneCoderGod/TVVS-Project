package badIceCream.model.game.elements.fruits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StrawberryFruitTest {

    private StrawberryFruit strawberryFruit;

    @BeforeEach
    public void setUp() {
        strawberryFruit = new StrawberryFruit(9, 9);
    }

    @Test
    @DisplayName("Constructor sets X and Y")
    public void testConstructor() {
        assertEquals(9, strawberryFruit.getPosition().getX());
        assertEquals(9, strawberryFruit.getPosition().getY());
    }

    @Test
    @DisplayName("getType() returns 5")
    public void testGetType() {
        assertEquals(5, strawberryFruit.getType());
    }
}
