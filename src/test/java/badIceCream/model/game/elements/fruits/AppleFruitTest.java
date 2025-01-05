package badIceCream.model.game.elements.fruits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppleFruitTest {

    private AppleFruit appleFruit;

    @BeforeEach
    public void setUp() {
        appleFruit = new AppleFruit(5, 7);
    }

    @Test
    @DisplayName("Constructor sets X and Y")
    public void testConstructor() {
        assertEquals(5, appleFruit.getPosition().getX());
        assertEquals(7, appleFruit.getPosition().getY());
    }

    @Test
    @DisplayName("getType() returns 1")
    public void testGetType() {
        assertEquals(1, appleFruit.getType());
    }
}
