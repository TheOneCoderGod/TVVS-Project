package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.PauseMenuController;
import badIceCream.model.menu.PauseMenu;
import badIceCream.viewer.menu.PauseMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Improved test class for PauseMenuState, covering all constructors and interactions.
 */
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
    @DisplayName("Constructor with controller and viewer sets model, parent, and level")
    void testConstructorWithControllerAndViewer() {
        assertEquals(model, state.getModel(), "Model should be set correctly");
        assertEquals(2, state.getLevel(), "Level should be set correctly");
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 999L);

        verify(controller, times(1)).step(eq(game), any(), eq(999L));
        verify(viewer, times(1)).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(50L);
        verify(controller, times(1)).stepMonsters(50L);
    }

    @Test
    @DisplayName("Constructor without controller and viewer initializes them correctly")
    void testConstructorWithoutControllerAndViewer() throws IOException {
        try (MockedConstruction<PauseMenuController> mockedController = mockConstruction(PauseMenuController.class,
                (mock, context) -> {
                    doNothing().when(mock).step(any(), any(), anyLong());
                });
             MockedConstruction<PauseMenuViewer> mockedViewer = mockConstruction(PauseMenuViewer.class)) {

            PauseMenuState newState = new PauseMenuState(model, parent, 3);

            // Verify that PauseMenuController was instantiated with the correct model and parent
            assertEquals(1, mockedController.constructed().size(), "PauseMenuController should be instantiated once");
            PauseMenuController instantiatedController = mockedController.constructed().get(0);
            verify(instantiatedController, never()).step(any(), any(), anyLong());

            // Verify that PauseMenuViewer was instantiated with the correct model
            assertEquals(1, mockedViewer.constructed().size(), "PauseMenuViewer should be instantiated once");
        }
    }
}
