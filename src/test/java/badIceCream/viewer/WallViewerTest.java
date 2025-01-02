package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.Wall;
import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link WallViewer}.
 * We verify each 'case' in the switch, including default.
 */
class WallViewerTest {

    private WallViewer viewer;
    private Graphics graphics;
    private Wall wall;

    @BeforeEach
    void setUp() {
        viewer = new WallViewer();
        graphics = mock(Graphics.class);
        wall = mock(Wall.class);
        when(wall.getPosition()).thenReturn(new Position(4,4));
    }

    @Test
    @DisplayName("draw(...) with type=2 => drawStoneWall(...)")
    void testDrawStoneWall() {
        viewer.draw(wall, graphics, 2);
        verify(graphics).drawStoneWall(new Position(4,4));
    }

    @Test
    @DisplayName("draw(...) with type=1 => drawIceWall(...) (case 1,3,4,5,6,7,8,9,10,11 => iceWall)")
    void testDrawIceWall() {
        viewer.draw(wall, graphics, 1);
        verify(graphics).drawIceWall(new Position(4,4), 1);
    }

    @Test
    @DisplayName("draw(...) with type=3 => drawIceWall(...)")
    void testDrawIceWall3() {
        viewer.draw(wall, graphics, 3);
        verify(graphics).drawIceWall(new Position(4,4), 3);
    }

    @Test
    @DisplayName("draw(...) with type=4 => drawIceWall(...)")
    void testDrawIceWall4() {
        viewer.draw(wall, graphics, 4);
        verify(graphics).drawIceWall(new Position(4,4), 4);
    }

    @Test
    @DisplayName("draw(...) with type=11 => drawIceWall(...)")
    void testDrawIceWall11() {
        viewer.draw(wall, graphics, 11);
        verify(graphics).drawIceWall(new Position(4,4), 11);
    }

    @Test
    @DisplayName("draw(...) with unknown type => no calls (but covers default behavior in the switch if any)")
    void testUnknownType() {
        viewer.draw(wall, graphics, 99);
        verify(graphics, never()).drawStoneWall(any());
        verify(graphics, never()).drawIceWall(any(), anyInt());
    }
}
