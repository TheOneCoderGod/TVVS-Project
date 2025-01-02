package badIceCream.model.game.elements.monsters;

import badIceCream.GUI.GUI;
import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link DefaultMonster}.
 */
class DefaultMonsterTest {

    private DefaultMonster monster;

    @BeforeEach
    void setUp() {
        monster = new DefaultMonster(2,3);
    }

    @Test
    @DisplayName("Constructor sets position and default lastAction=DOWN, isRunning=false")
    void testConstructor() {
        assertEquals(2, monster.getPosition().getX());
        assertEquals(3, monster.getPosition().getY());
        assertEquals(GUI.ACTION.DOWN, monster.getLastAction());
        assertFalse(monster.isRunning());
    }

    @Test
    @DisplayName("getType() returns 1 for DefaultMonster")
    void testGetType() {
        assertEquals(1, monster.getType());
    }

    @Test
    @DisplayName("setLastAction(...) updates the monster's last action")
    void testSetLastAction() {
        monster.setLastAction(GUI.ACTION.LEFT);
        assertEquals(GUI.ACTION.LEFT, monster.getLastAction());
    }

    @Test
    @DisplayName("startRunning() and stopRunning() are no-ops in base class => isRunning remains false")
    void testRunningNoOps() {
        monster.startRunning();
        assertFalse(monster.isRunning());

        monster.stopRunning();
        assertFalse(monster.isRunning());
    }
}
