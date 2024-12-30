package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.Controller;
import badIceCream.viewer.Viewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * We can't instantiate MenuState directly because it's abstract.
 * We'll create a dummy subclass for testing.
 */
class MenuStateTest {

    private static class DummyMenuState extends MenuState<String> {
        public DummyMenuState(String menu, Controller<String> controller, Viewer<String> viewer, int level) {
            super(menu, controller, viewer, level);
        }
    }

    private String model;
    private Controller<String> controller;
    private Viewer<String> viewer;
    private MenuState<String> state;

    @BeforeEach
    void setUp() {
        model = "TestMenu";
        controller = mock(Controller.class);
        viewer = mock(Viewer.class);
        state = new DummyMenuState(model, controller, viewer, 2);
    }

    @Test
    @DisplayName("Constructor sets model, controller, viewer, level")
    void testConstructor() {
        assertEquals(model, state.getModel());
        assertEquals(2, state.getLevel());
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 42L);

        verify(controller).step(eq(game), any(), eq(42L));
        verify(viewer).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(500L);
        verify(controller).stepMonsters(500L);
    }

    @Test
    @DisplayName("increaseLevel() up to 5")
    void testIncreaseLevel() {
        assertEquals(2, state.getLevel());
        state.increaseLevel();
        assertEquals(3, state.getLevel());
        for(int i=0; i<10; i++){
            state.increaseLevel();
        }
        assertEquals(5, state.getLevel());
    }
}
