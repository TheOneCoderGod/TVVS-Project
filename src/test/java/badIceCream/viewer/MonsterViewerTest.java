package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.GUI.GUI;
import badIceCream.model.Position;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link MonsterViewer}.
 * We verify each 'type' leads to the correct Graphics draw.
 */
public class MonsterViewerTest {

    private MonsterViewer viewer;
    private Graphics graphics;
    private Monster monster;

    @BeforeEach
    public  void setUp() {
        viewer = new MonsterViewer();
        graphics = mock(Graphics.class);
        monster = mock(Monster.class);
        when(monster.getPosition()).thenReturn(new Position(5,5));
        when(monster.getLastAction()).thenReturn(GUI.ACTION.UP);
    }

    @Test
    @DisplayName("draw(...) with type=1 => drawDefaultMonster(...)")
    public void testDrawDefaultMonster() {
        when(monster.getType()).thenReturn(1);
        viewer.draw(monster, graphics, 1);
        verify(graphics).drawDefaultMonster(new Position(5,5), GUI.ACTION.UP);
    }

    @Test
    @DisplayName("draw(...) with type=2 => drawJumperMonster(...)")
    public void testDrawJumperMonster() {
        when(monster.getType()).thenReturn(2);
        viewer.draw(monster, graphics, 2);
        verify(graphics).drawJumperMonster(new Position(5,5), GUI.ACTION.UP);
    }

    @Test
    @DisplayName("draw(...) with type=3 => drawRunnerMonster(...)")
    public void testDrawRunnerMonster() {
        when(monster.getType()).thenReturn(3);
        when(monster.isRunning()).thenReturn(true);
        viewer.draw(monster, graphics, 3);
        verify(graphics).drawRunnerMonster(new Position(5,5), GUI.ACTION.UP, true);
    }

    @Test
    @DisplayName("draw(...) with type=4 => drawWallBreakerMonster(...)")
    public void testDrawWallBreakerMonster() {
        when(monster.getType()).thenReturn(4);
        viewer.draw(monster, graphics, 4);
        verify(graphics).drawWallBreakerMonster(new Position(5,5), GUI.ACTION.UP);
    }

    @Test
    @DisplayName("draw(...) with unknown type => do nothing (mutation coverage)")
    public void testUnknownType() {
        when(monster.getType()).thenReturn(99);
        viewer.draw(monster, graphics, 99);
        verify(graphics, never()).drawDefaultMonster(any(), any());
        verify(graphics, never()).drawJumperMonster(any(), any());
        verify(graphics, never()).drawRunnerMonster(any(), any(), anyBoolean());
        verify(graphics, never()).drawWallBreakerMonster(any(), any());
    }
}
