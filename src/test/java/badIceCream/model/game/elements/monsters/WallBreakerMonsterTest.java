package badIceCream.model.game.elements.monsters;

import badIceCream.GUI.GUI;
import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link WallBreakerMonster}.
 */
public class WallBreakerMonsterTest {

    private WallBreakerMonster monster;

    @BeforeEach
    public void setUp() {
        monster = new WallBreakerMonster(7,2);
    }

    @Test
    @DisplayName("Constructor sets position, lastAction=DOWN, isRunning=false")
    public void testConstructor() {
        assertEquals(7, monster.getPosition().getX());
        assertEquals(2, monster.getPosition().getY());
        assertEquals(GUI.ACTION.DOWN, monster.getLastAction());
        assertFalse(monster.isRunning());
    }

    @Test
    @DisplayName("getType() returns 4 for WallBreakerMonster")
    public void testGetType() {
        assertEquals(4, monster.getType());
    }

    @Test
    @DisplayName("startRunning / stopRunning => no effect => isRunning remains false")
    public void testRunningNoOps() {
        monster.startRunning();
        assertFalse(monster.isRunning());
        monster.stopRunning();
        assertFalse(monster.isRunning());
    }

    @Test
    @DisplayName("setLastAction(...) changes lastAction")
    void testSetLastAction() {
        monster.setLastAction(GUI.ACTION.RIGHT);
        assertEquals(GUI.ACTION.RIGHT, monster.getLastAction());
    }
}
