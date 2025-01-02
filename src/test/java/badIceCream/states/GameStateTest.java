package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.game.ArenaController;
import badIceCream.model.game.arena.Arena;
import badIceCream.viewer.ArenaViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Improved test class for GameState, covering all constructors and interactions.
 */
class GameStateTest {

    private Arena model;
    private ArenaController controller;
    private ArenaViewer viewer;
    private GameState state;

    @BeforeEach
    void setUp() {
        model = mock(Arena.class);
        controller = mock(ArenaController.class);
        viewer = mock(ArenaViewer.class);
        state = new GameState(model, controller, viewer, 3);
    }

    @Test
    @DisplayName("Constructor with controller and viewer sets model and level")
    void testConstructorWithControllerAndViewer() {
        assertEquals(model, state.getModel(), "Model should be set correctly");
        assertEquals(3, state.getLevel(), "Level should be set correctly");
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 500L);

        verify(controller, times(1)).step(eq(game), any(), eq(500L));
        verify(viewer, times(1)).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(200L);
        verify(controller, times(1)).stepMonsters(200L);
    }

    @Test
    @DisplayName("Constructor without controller and viewer initializes them correctly")
    void testConstructorWithoutControllerAndViewer() throws IOException {
        try (MockedConstruction<ArenaController> mockedController = mockConstruction(ArenaController.class,
                (mock, context) -> {
                    doNothing().when(mock).step(any(), any(), anyLong());
                });
             MockedConstruction<ArenaViewer> mockedViewer = mockConstruction(ArenaViewer.class)) {

            GameState newState = new GameState(model, 4);

            // Verify that ArenaController was instantiated with the correct parameters
            assertEquals(1, mockedController.constructed().size(), "ArenaController should be instantiated once");
            ArenaController instantiatedController = mockedController.constructed().get(0);
            verify(instantiatedController, never()).step(any(), any(), anyLong());

            // Verify that ArenaViewer was instantiated with the correct parameters
            assertEquals(1, mockedViewer.constructed().size(), "ArenaViewer should be instantiated once");
        }
    }
}
