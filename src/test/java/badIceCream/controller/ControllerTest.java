package badIceCream.controller;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    /**
     * A normal, no-op implementation of Controller.
     * step(...) does nothing but demonstrates typical usage.
     */
    public static class NoThrowController extends Controller<String> {
        public NoThrowController(String model) {
            super(model);
        }

        @Override
        public void step(Game game, GUI.ACTION action, long time) throws IOException {
            // no-op
        }
    }

    /**
     * A subclass that throws IOException if action is null,
     * demonstrating an error-handling path.
     */
    public static class ThrowingController extends Controller<String> {
        public ThrowingController(String model) {
            super(model);
        }

        @Override
        public void step(Game game, GUI.ACTION action, long time) throws IOException {
            if (action == null) {
                throw new IOException("Action cannot be null");
            }
            // Otherwise no-op
        }
    }

    // ------------------------------------------------------
    // Tests for Constructor & getModel()
    // ------------------------------------------------------

    @Nested
    @DisplayName("Constructor & getModel() tests")
    public class ConstructorAndModelTests {

        @Test
        @DisplayName("Construct with non-null model => getModel() should return the same object")
        public void testConstructorWithNonNullModel() {
            Controller<String> controller = new NoThrowController("NonNullModel");
            assertNotNull(controller, "Controller should be instantiated");
            assertEquals("NonNullModel", controller.getModel(),
                    "getModel() should return the same instance passed to the constructor");
        }

        @Test
        @DisplayName("Construct with null model => getModel() should return null (if allowed by design)")
        public void testConstructorWithNullModel() {
            Controller<String> controller = new NoThrowController(null);
            // If your code is supposed to disallow null, you'd expect an NPE or assert.
            // But the given code doesn't do that, so we can test the current behavior:
            assertNotNull(controller, "Controller should still be instantiated even with null model");
            assertNull(controller.getModel(), "getModel() should return null if the model was null");
        }
    }

    // ------------------------------------------------------
    // Tests for step(...)
    // ------------------------------------------------------

    @Nested
    @DisplayName("step(...) tests")
    public class StepMethodTests {

        @Test
        @DisplayName("step(...) on NoThrowController with normal args => no exception")
        public void testStepNoThrowNormal() throws IOException {
            Controller<String> controller = new NoThrowController("Model");
            // No exceptions expected, even if arguments are null
            controller.step(null, GUI.ACTION.UP, 100L);
        }

        @Test
        @DisplayName("step(...) on NoThrowController with null action => still no exception by default")
        public void testStepNoThrowNullAction() throws IOException {
            Controller<String> controller = new NoThrowController("Model");
            // The NoThrowController doesn't mind null
            controller.step(null, null, 0L);
        }

        @Test
        @DisplayName("step(...) on ThrowingController with normal action => no exception")
        public void testStepThrowingControllerNormalAction() throws IOException {
            Controller<String> controller = new ThrowingController("Model");
            controller.step(null, GUI.ACTION.DOWN, 200L);
        }

        @Test
        @DisplayName("step(...) on ThrowingController with null action => throws IOException")
        public void testStepThrowingControllerNullAction() {
            Controller<String> controller = new ThrowingController("Model");
            assertThrows(IOException.class, () ->
                            controller.step(null, null, 0L),
                    "Expected an IOException if action is null in ThrowingController"
            );
        }
    }

    // ------------------------------------------------------
    // Tests for stepMonsters(...)
    // ------------------------------------------------------

    @Nested
    @DisplayName("stepMonsters(...) tests")
    public class StepMonstersTests {

        @Test
        @DisplayName("stepMonsters(...) with negative time => no exception by default")
        public void testStepMonstersNegativeTime() throws IOException {
            Controller<String> controller = new NoThrowController("Model");
            controller.stepMonsters(-100L);
            // The base class is a no-op, so it should not throw
        }

        @Test
        @DisplayName("stepMonsters(...) with zero time => no exception")
        public void testStepMonstersZeroTime() throws IOException {
            Controller<String> controller = new NoThrowController("Model");
            controller.stepMonsters(0L);
            // Also a no-op
        }

        @Test
        @DisplayName("stepMonsters(...) with positive time => no exception")
        public void testStepMonstersPositiveTime() throws IOException {
            Controller<String> controller = new NoThrowController("Model");
            controller.stepMonsters(12345L);
            // Also a no-op
        }

        @Test
        @DisplayName("Subclasses can override stepMonsters(...) if needed — demonstration only")
        public void testStepMonstersOverriddenPotentially() throws IOException {
            // If you had a subclass that overrides stepMonsters(...) to do something, you'd test it here.
            // For now, we test the base no-op behavior.
            Controller<String> controller = new NoThrowController("Model");
            controller.stepMonsters(999L);
        }
    }
}