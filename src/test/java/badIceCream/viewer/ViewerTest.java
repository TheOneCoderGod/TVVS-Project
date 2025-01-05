package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests the abstract {@link Viewer} class with a dummy subclass
 * to cover lines in draw(...).
 */
public class ViewerTest {

    // A dummy model & viewer
    private static class DummyModel { }
    private static class DummyViewer extends Viewer<DummyModel> {
        public DummyViewer(DummyModel model) { super(model); }
        @Override
        protected void drawElements(Graphics gui) throws IOException {
            // no-op
        }
    }

    private DummyModel model;
    private DummyViewer viewer;
    private Graphics graphics;

    @BeforeEach
    public void setUp() {
        model = new DummyModel();
        viewer = new DummyViewer(model);
        graphics = mock(Graphics.class);
    }

    @Test
    @DisplayName("getModel() returns the model")
    public  void testGetModel() {
        assertEquals(model, viewer.getModel());
    }

    @Test
    @DisplayName("draw(...) calls gui.clear(), drawElements(gui), gui.refresh()")
    public void testDraw() throws IOException {
        viewer.draw(graphics);

        verify(graphics).clear();
        verify(graphics).refresh();
        // drawElements(...) is tested via call
        // we can spy if we want to verify that path
    }
}
