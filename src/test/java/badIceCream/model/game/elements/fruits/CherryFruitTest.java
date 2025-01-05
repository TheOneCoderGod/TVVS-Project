package badIceCream.model.game.elements.fruits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CherryFruitTest {

    private CherryFruit cherryFruit;

    @BeforeEach
    public void setUp() {
        cherryFruit = new CherryFruit(10, 0);
    }

    @Test
    @DisplayName("Constructor sets X and Y")
    public void testConstructor() {
        assertEquals(10, cherryFruit.getPosition().getX());
        assertEquals(0, cherryFruit.getPosition().getY());
    }

    @Test
    @DisplayName("getType() returns 3")
    public void testGetType() {
        assertEquals(3, cherryFruit.getType());
    }
}
