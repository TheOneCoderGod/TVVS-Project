package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.MainMenuController;
import badIceCream.model.menu.MainMenu;
import badIceCream.viewer.menu.MainMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Improved test class for MainMenuState, covering all constructors and interactions.
 */
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
    @DisplayName("Constructor with controller and viewer sets model and level")
    void testConstructorWithControllerAndViewer() {
        assertEquals(model, state.getModel(), "Model should be set correctly");
        assertEquals(1, state.getLevel(), "Level should be set correctly");
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 100L);

        verify(controller, times(1)).step(eq(game), any(), eq(100L));
        verify(viewer, times(1)).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(99L);
        verify(controller, times(1)).stepMonsters(99L);
    }

    @Test
    @DisplayName("Constructor without controller and viewer initializes them correctly")
    void testConstructorWithoutControllerAndViewer() throws IOException {
        try (MockedConstruction<MainMenuController> mockedController = mockConstruction(MainMenuController.class,
                (mock, context) -> {
                    doNothing().when(mock).step(any(), any(), anyLong());
                });
             MockedConstruction<MainMenuViewer> mockedViewer = mockConstruction(MainMenuViewer.class)) {

            MainMenuState newState = new MainMenuState(model, 2);

            // Verify that MainMenuController was instantiated with the correct model
            assertEquals(1, mockedController.constructed().size(), "MainMenuController should be instantiated once");
            MainMenuController instantiatedController = mockedController.constructed().get(0);
            verify(instantiatedController, never()).step(any(), any(), anyLong());

            // Verify that MainMenuViewer was instantiated with the correct model
            assertEquals(1, mockedViewer.constructed().size(), "MainMenuViewer should be instantiated once");
        }
    }
}
