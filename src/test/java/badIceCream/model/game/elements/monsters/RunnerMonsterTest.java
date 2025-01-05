package badIceCream.model.game.elements.monsters;

import badIceCream.GUI.GUI;
import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link RunnerMonster}.
 */
public class RunnerMonsterTest {

    private RunnerMonster monster;

    @BeforeEach
    public void setUp() {
        monster = new RunnerMonster(10,10);
    }

    @Test
    @DisplayName("Constructor sets position, lastAction=DOWN, isRunning=false")
    public void testConstructor() {
        assertEquals(10, monster.getPosition().getX());
        assertEquals(10, monster.getPosition().getY());
        assertEquals(GUI.ACTION.DOWN, monster.getLastAction());
        assertFalse(monster.isRunning());
    }

    @Test
    @DisplayName("getType() returns 3 for RunnerMonster")
    public void testGetType() {
        assertEquals(3, monster.getType());
    }

    @Test
    @DisplayName("startRunning() => isRunning=true, stopRunning() => isRunning=false")
    public void testRunning() {
        assertFalse(monster.isRunning());
        monster.startRunning();
        assertTrue(monster.isRunning());
        monster.stopRunning();
        assertFalse(monster.isRunning());
    }

    @Test
    @DisplayName("setLastAction(...) changes lastAction")
    public void testSetLastAction() {
        monster.setLastAction(GUI.ACTION.LEFT);
        assertEquals(GUI.ACTION.LEFT, monster.getLastAction());
    }
}
