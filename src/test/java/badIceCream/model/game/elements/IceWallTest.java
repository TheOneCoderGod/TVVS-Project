package badIceCream.model.game.elements;

import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IceWallTest {

    private IceWall iceWall;

    @BeforeEach
    public void setUp() {
        iceWall = new IceWall(1,1);
    }

    @Test
    @DisplayName("Constructor sets position")
    public void testConstructor() {
        assertEquals(1, iceWall.getPosition().getX());
        assertEquals(1, iceWall.getPosition().getY());
    }

    @Test
    @DisplayName("getType() returns 1")
    public void testGetType() {
        assertEquals(1, iceWall.getType());
    }
}
