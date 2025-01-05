package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.HotFloor;
import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link HotFloorViewer}.
 */
public class HotFloorViewerTest {

    private HotFloorViewer viewer;
    private Graphics graphics;
    private HotFloor hotFloor;

    @BeforeEach
    public void setUp() {
        viewer = new HotFloorViewer();
        graphics = mock(Graphics.class);
        hotFloor = mock(HotFloor.class);

        when(hotFloor.getPosition()).thenReturn(new Position(2,2));
    }

    @Test
    @DisplayName("draw(...) calls gui.drawHotFloor(...) with the given type")
    public  void testDraw() {
        viewer.draw(hotFloor, graphics, 7);
        verify(graphics).drawHotFloor(new Position(2,2), 7);
    }
}
