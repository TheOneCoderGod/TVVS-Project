package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.GameOverMenuController;
import badIceCream.model.menu.GameOverMenu;
import badIceCream.viewer.menu.GameOverMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameOverMenuStateTest {

    private GameOverMenu model;
    private GameOverMenuController controller;
    private GameOverMenuViewer viewer;
    private GameOverMenuState state;

    @BeforeEach
    void setUp() {
        model = mock(GameOverMenu.class);
        controller = mock(GameOverMenuController.class);
        viewer = mock(GameOverMenuViewer.class);
        state = new GameOverMenuState(model, controller, viewer, 2);
    }

    @Test
    @DisplayName("Constructor sets model, controller, viewer, and level")
    void testConstructor() {
        assertEquals(model, state.getModel());
        assertEquals(2, state.getLevel());
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 123L);

        verify(controller, times(1)).step(eq(game), any(), eq(123L));
        verify(viewer, times(1)).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(999L);
        verify(controller, times(1)).stepMonsters(999L);
    }

    @Test
    @DisplayName("increaseLevel() does not exceed 5")
    void testIncreaseLevel() {
        state.increaseLevel();
        assertEquals(3, state.getLevel());

        // If we call enough times, max is 5
        for(int i=0; i<10; i++) {
            state.increaseLevel();
        }
        assertEquals(5, state.getLevel());
    }
}
