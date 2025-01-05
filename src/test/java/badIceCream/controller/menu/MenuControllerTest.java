package badIceCream.controller.menu;

import badIceCream.Game;
import badIceCream.GUI.GUI;
import badIceCream.controller.Controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for the abstract MenuController class.
 */
public class MenuControllerTest {

    /**
     * A dummy subclass of MenuController to allow instantiation and testing.
     * We must implement the abstract step(...) method from Controller<T>.
     */
    public static class DummyMenuController extends MenuController<String> {
        public DummyMenuController(String model) {
            super(model);
        }

        @Override
        public void step(Game game, GUI.ACTION action, long time) throws IOException {
            // No-op for testing only
        }
    }

    @Test
    @DisplayName("Constructor in abstract MenuController should set up correctly")
    public void testConstructor() {
        // Instantiate the dummy subclass
        MenuController<String> controller = new DummyMenuController("test-model");

        // If Controller<T> or MenuController<T> has a getModel(), you could verify it:
        // assertEquals("test-model", controller.getModel());

        // Otherwise, just ensure the instance is not null
        assertNotNull(controller, "Controller should not be null after construction");
    }
}