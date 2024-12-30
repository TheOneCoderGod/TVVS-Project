package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.SelectLevelMenuController;
import badIceCream.model.menu.SelectLevelMenu;
import badIceCream.viewer.menu.SelectLevelMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SelectLevelMenuStateTest {

    private SelectLevelMenu model;
    private SelectLevelMenuController controller;
    private SelectLevelMenuViewer viewer;
    private SelectLevelMenuState state;

    @BeforeEach
    void setUp() {
        model = mock(SelectLevelMenu.class);
        controller = mock(SelectLevelMenuController.class);
        viewer = mock(SelectLevelMenuViewer.class);
        state = new SelectLevelMenuState(model, controller, viewer, 1);
    }

    @Test
    @DisplayName("Constructor sets model, level")
    void testConstructor() {
        assertEquals(model, state.getModel());
        assertEquals(1, state.getLevel());
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 10L);

        verify(controller).step(eq(game), any(), eq(10L));
        verify(viewer).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(600L);
        verify(controller).stepMonsters(600L);
    }
}
