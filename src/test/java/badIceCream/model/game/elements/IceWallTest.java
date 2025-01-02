package badIceCream.model.game.elements;

import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IceWallTest {

    private IceWall iceWall;

    @BeforeEach
    void setUp() {
        iceWall = new IceWall(1,1);
    }

    @Test
    @DisplayName("Constructor sets position")
    void testConstructor() {
        assertEquals(1, iceWall.getPosition().getX());
        assertEquals(1, iceWall.getPosition().getY());
    }

    @Test
    @DisplayName("getType() returns 1")
    void testGetType() {
        assertEquals(1, iceWall.getType());
    }
}
