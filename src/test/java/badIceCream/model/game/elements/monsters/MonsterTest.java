package badIceCream.model.game.elements.monsters;

import badIceCream.GUI.GUI;
import badIceCream.model.Position;
import badIceCream.model.game.elements.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Monster is abstract. We'll create a dummy subclass
 * to test the shared logic in {@link Monster}.
 */
class MonsterTest {

    private static class DummyMonster extends Monster {
        public DummyMonster(int x, int y) { super(x, y); }
        @Override
        public int getType() { return 999; }
    }

    private Monster monster;

    @BeforeEach
    void setUp() {
        monster = new DummyMonster(0,0);
    }

    @Test
    @DisplayName("Constructor sets position, lastAction=DOWN, isRunning=false")
    void testConstructor() {
        assertEquals(0, monster.getPosition().getX());
        assertEquals(0, monster.getPosition().getY());
        assertEquals(GUI.ACTION.DOWN, monster.getLastAction());
        assertFalse(monster.isRunning());
    }

    @Test
    @DisplayName("getType() in dummy returns 999")
    void testGetType() {
        assertEquals(999, monster.getType());
    }

    @Test
    @DisplayName("setLastAction(...) changes lastAction")
    void testSetLastAction() {
        monster.setLastAction(GUI.ACTION.RIGHT);
        assertEquals(GUI.ACTION.RIGHT, monster.getLastAction());
    }

    @Test
    @DisplayName("startRunning() / stopRunning() do nothing => isRunning remains false")
    void testRunningNoOps() {
        monster.startRunning();
        assertFalse(monster.isRunning());
        monster.stopRunning();
        assertFalse(monster.isRunning());
    }
}
