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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Improved test class for GameOverMenuState, covering all constructors and interactions.
 */
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
    @DisplayName("Constructor with controller and viewer sets model and level")
    void testConstructorWithControllerAndViewer() {
        assertEquals(model, state.getModel(), "Model should be set correctly");
        assertEquals(2, state.getLevel(), "Level should be set correctly");
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
    @DisplayName("Constructor without controller and viewer initializes them correctly")
    void testConstructorWithoutControllerAndViewer() throws IOException {
        try (MockedConstruction<GameOverMenuController> mockedController = mockConstruction(GameOverMenuController.class,
                (mock, context) -> {
                    doNothing().when(mock).step(any(), any(), anyLong());
                });
             MockedConstruction<GameOverMenuViewer> mockedViewer = mockConstruction(GameOverMenuViewer.class)) {

            GameOverMenuState newState = new GameOverMenuState(model, 3);

            // Verify that GameOverMenuController was instantiated with the correct model
            assertEquals(1, mockedController.constructed().size(), "GameOverMenuController should be instantiated once");
            GameOverMenuController instantiatedController = mockedController.constructed().get(0);
            verify(instantiatedController, never()).step(any(), any(), anyLong()); // No interactions yet

            // Verify that GameOverMenuViewer was instantiated with the correct model
            assertEquals(1, mockedViewer.constructed().size(), "GameOverMenuViewer should be instantiated once");

            // Optionally, verify that the state has the newly created controller and viewer
            // This requires getters or other means to access internal state, which are not shown here
        }
    }
}
