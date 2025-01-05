package badIceCream.model.game.elements;

import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HotFloorTest {

    private HotFloor hotFloor;

    @BeforeEach
    public void setUp() {
        hotFloor = new HotFloor(3,4);
    }

    @Test
    @DisplayName("Constructor sets position")
    public void testConstructor() {
        assertEquals(3, hotFloor.getPosition().getX());
        assertEquals(4, hotFloor.getPosition().getY());
    }

    @Test
    @DisplayName("getType() returns 0")
    public void testGetType() {
        assertEquals(0, hotFloor.getType());
    }
}
