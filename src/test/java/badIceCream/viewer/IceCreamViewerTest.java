package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.IceCream;
import badIceCream.GUI.GUI;
import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link IceCreamViewer}.
 */
public class IceCreamViewerTest {

    private IceCreamViewer viewer;
    private Graphics graphics;
    private IceCream iceCream;

    @BeforeEach
    public void setUp() {
        viewer = new IceCreamViewer();
        graphics = mock(Graphics.class);
        iceCream = mock(IceCream.class);
        when(iceCream.getPosition()).thenReturn(new Position(3,3));
        when(iceCream.getLastMovement()).thenReturn(GUI.ACTION.DOWN);
        when(iceCream.isStrawberryActive()).thenReturn(false);
    }

    @Test
    @DisplayName("draw(...) calls gui.drawIceCream(...) with position, lastMovement, and strawberryActive")
    public void testDraw() {
        viewer.draw(iceCream, graphics, 1);
        verify(graphics).drawIceCream(
                new Position(3,3),
                GUI.ACTION.DOWN,
                false
        );
    }
}
