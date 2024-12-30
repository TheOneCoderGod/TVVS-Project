package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.MainMenuController;
import badIceCream.model.menu.MainMenu;
import badIceCream.viewer.menu.MainMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainMenuStateTest {

    private MainMenu model;
    private MainMenuController controller;
    private MainMenuViewer viewer;
    private MainMenuState state;

    @BeforeEach
    void setUp() {
        model = mock(MainMenu.class);
        controller = mock(MainMenuController.class);
        viewer = mock(MainMenuViewer.class);
        state = new MainMenuState(model, controller, viewer, 1);
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

        state.step(game, graphics, 100L);

        verify(controller).step(eq(game), any(), eq(100L));
        verify(viewer).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(99L);
        verify(controller).stepMonsters(99L);
    }
}
