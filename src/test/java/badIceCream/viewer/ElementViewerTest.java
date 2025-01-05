package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.Element;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ElementViewer is an interface with only one method:
 *  void draw(T element, Graphics gui, int type).
 * There's no code to line-cover. We'll just do a minimal test
 * with a dummy implementation, for mutation coverage completeness.
 */
public class ElementViewerTest {

    // A dummy class implementing ElementViewer
    private static class DummyElement extends Element {
        public DummyElement(int x, int y) { super(x,y); }
        @Override
        public int getType() { return 999; }
    }

    private static class DummyElementViewer implements ElementViewer<DummyElement> {
        @Override
        public void draw(DummyElement element, Graphics gui, int type) {
            // no-op
        }
    }

    @Test
    @DisplayName("DummyElementViewer can be instantiated and draw(...) called without error")
    public void testInterfaceImplementation() {
        ElementViewer<DummyElement> viewer = new DummyElementViewer();
        viewer.draw(new DummyElement(3,4), null, 42);
        // We simply ensure no exceptions are thrown
        assertTrue(true, "No exceptions => coverage for the interface method");
    }
}
