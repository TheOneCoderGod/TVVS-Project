package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.PauseMenuController;
import badIceCream.model.menu.PauseMenu;
import badIceCream.viewer.menu.PauseMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PauseMenuStateTest {

    private PauseMenu model;
    private PauseMenuController controller;
    private PauseMenuViewer viewer;
    private GameState parent;
    private PauseMenuState state;

    @BeforeEach
    void setUp() {
        model = mock(PauseMenu.class);
        controller = mock(PauseMenuController.class);
        viewer = mock(PauseMenuViewer.class);
        parent = mock(GameState.class);
        state = new PauseMenuState(model, parent, controller, viewer, 2);
    }

    @Test
    @DisplayName("Constructor sets model, parent, viewer, controller, level")
    void testConstructor() {
        assertEquals(model, state.getModel());
        assertEquals(2, state.getLevel());
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 999L);

        verify(controller).step(eq(game), any(), eq(999L));
        verify(viewer).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(50L);
        verify(controller).stepMonsters(50L);
    }
}
