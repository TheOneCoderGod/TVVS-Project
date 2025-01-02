package badIceCream.model.game.elements.monsters;

import badIceCream.GUI.GUI;
import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JumperMonster}.
 */
class JumperMonsterTest {

    private JumperMonster monster;

    @BeforeEach
    void setUp() {
        monster = new JumperMonster(5,5);
    }

    @Test
    @DisplayName("Constructor sets position, lastAction=DOWN, running=false")
    void testConstructor() {
        assertEquals(5, monster.getPosition().getX());
        assertEquals(5, monster.getPosition().getY());
        assertEquals(GUI.ACTION.DOWN, monster.getLastAction());
        assertFalse(monster.isRunning());
    }

    @Test
    @DisplayName("getType() returns 2 for JumperMonster")
    void testGetType() {
        assertEquals(2, monster.getType());
    }

    @Test
    @DisplayName("setLastAction(...) changes the last action")
    void testSetLastAction() {
        monster.setLastAction(GUI.ACTION.UP);
        assertEquals(GUI.ACTION.UP, monster.getLastAction());
    }

    @Test
    @DisplayName("startRunning / stopRunning remain no-ops => running=false always")
    void testRunning() {
        monster.startRunning();
        assertFalse(monster.isRunning());
        monster.stopRunning();
        assertFalse(monster.isRunning());
    }
}
