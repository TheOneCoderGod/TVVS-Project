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
 * We cannot instantiate State directly (it's abstract),
 * so we create a dummy subclass for testing.
 */
class StateTest {

    private static class DummyState extends State<String> {
        public DummyState(String model, Controller<String> controller, Viewer<String> viewer, int level) {
            super(model, controller, viewer, level);
        }
    }

    private String model;
    private Controller<String> controller;
    private Viewer<String> viewer;
    private State<String> state;

    @BeforeEach
    void setUp() {
        model = "TestModel";
        controller = mock(Controller.class);
        viewer = mock(Viewer.class);
        state = new DummyState(model, controller, viewer, 3);
    }

    @Test
    @DisplayName("Constructor sets model, controller, viewer, and level")
    void testConstructor() {
        assertEquals(model, state.getModel());
        assertEquals(3, state.getLevel());
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 500L);

        verify(controller).step(eq(game), any(), eq(500L));
        verify(viewer).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(250L);
        verify(controller).stepMonsters(250L);
    }

    @Test
    @DisplayName("increaseLevel() does not exceed 5")
    void testIncreaseLevel() {
        assertEquals(3, state.getLevel());
        state.increaseLevel();
        assertEquals(4, state.getLevel());
        state.increaseLevel();
        assertEquals(5, state.getLevel());
        // Additional calls won't exceed 5
        for(int i=0; i<10; i++){
            state.increaseLevel();
        }
        assertEquals(5, state.getLevel());
    }
}
