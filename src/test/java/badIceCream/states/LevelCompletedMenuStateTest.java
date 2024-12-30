package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.LevelCompletedMenuController;
import badIceCream.model.menu.LevelCompletedMenu;
import badIceCream.viewer.menu.LevelCompletedMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LevelCompletedMenuStateTest {

    private LevelCompletedMenu model;
    private LevelCompletedMenuController controller;
    private LevelCompletedMenuViewer viewer;
    private LevelCompletedMenuState state;

    @BeforeEach
    void setUp() {
        model = mock(LevelCompletedMenu.class);
        controller = mock(LevelCompletedMenuController.class);
        viewer = mock(LevelCompletedMenuViewer.class);
        state = new LevelCompletedMenuState(model, controller, viewer, 4);
    }

    @Test
    @DisplayName("Constructor sets model and level")
    void testConstructor() {
        assertEquals(model, state.getModel());
        assertEquals(4, state.getLevel());
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 11L);

        verify(controller).step(eq(game), any(), eq(11L));
        verify(viewer).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(200L);
        verify(controller).stepMonsters(200L);
    }
}
