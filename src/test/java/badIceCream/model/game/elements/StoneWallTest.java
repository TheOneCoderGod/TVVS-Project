package badIceCream.model.game.elements;

import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoneWallTest {

    private StoneWall stoneWall;

    @BeforeEach
    public void setUp() {
        stoneWall = new StoneWall(3,3);
    }

    @Test
    @DisplayName("Constructor sets position")
    public void testConstructor() {
        assertEquals(3, stoneWall.getPosition().getX());
        assertEquals(3, stoneWall.getPosition().getY());
    }

    @Test
    @DisplayName("getType() returns 2")
    public void testGetType() {
        assertEquals(2, stoneWall.getType());
    }
}
