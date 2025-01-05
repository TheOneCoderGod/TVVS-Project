package badIceCream.model.game.elements;

import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Wall is abstract, so we create a dummy subclass
 * to test basic "Wall" functionality from the parent.
 */
public class WallTest {

    private static class DummyWall extends Wall {
        public DummyWall(int x, int y) {
            super(x,y);
        }
        @Override
        public int getType() {
            return 999;
        }
    }

    private Wall wall;

    @BeforeEach
    public void setUp() {
        wall = new DummyWall(2,2);
    }

    @Test
    @DisplayName("Constructor sets position")
    public void testConstructor() {
        assertEquals(2, wall.getPosition().getX());
        assertEquals(2, wall.getPosition().getY());
    }

    @Test
    @DisplayName("getType() in dummy returns 999")
    public void testGetType() {
        assertEquals(999, wall.getType());
    }
}
