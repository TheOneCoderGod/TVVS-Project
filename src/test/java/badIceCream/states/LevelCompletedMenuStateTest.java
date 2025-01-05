package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.LevelCompletedMenuController;
import badIceCream.model.menu.LevelCompletedMenu;
import badIceCream.viewer.menu.LevelCompletedMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Improved test class for LevelCompletedMenuState, covering all constructors and interactions.
 */
public class LevelCompletedMenuStateTest {

    private LevelCompletedMenu model;
    private LevelCompletedMenuController controller;
    private LevelCompletedMenuViewer viewer;
    private LevelCompletedMenuState state;

    @BeforeEach
    public void setUp() {
        model = mock(LevelCompletedMenu.class);
        controller = mock(LevelCompletedMenuController.class);
        viewer = mock(LevelCompletedMenuViewer.class);
        state = new LevelCompletedMenuState(model, controller, viewer, 4);
    }

    @Test
    @DisplayName("Constructor with controller and viewer sets model and level")
    public void testConstructorWithControllerAndViewer() {
        assertEquals(model, state.getModel(), "Model should be set correctly");
        assertEquals(4, state.getLevel(), "Level should be set correctly");
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    public void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 11L);

        verify(controller, times(1)).step(eq(game), any(), eq(11L));
        verify(viewer, times(1)).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    public void testStepMonsters() throws IOException {
        state.stepMonsters(200L);
        verify(controller, times(1)).stepMonsters(200L);
    }
    @Test
    @DisplayName("Constructor without controller and viewer initializes them correctly")
    public void testConstructorWithoutControllerAndViewer() throws IOException {
        try (MockedConstruction<LevelCompletedMenuController> mockedController = mockConstruction(LevelCompletedMenuController.class,
                (mock, context) -> {
                    doNothing().when(mock).step(any(), any(), anyLong());
                });
             MockedConstruction<LevelCompletedMenuViewer> mockedViewer = mockConstruction(LevelCompletedMenuViewer.class)) {

            LevelCompletedMenuState newState = new LevelCompletedMenuState(model, 5);

            // Verify that LevelCompletedMenuController was instantiated with the correct model
            assertEquals(1, mockedController.constructed().size(), "LevelCompletedMenuController should be instantiated once");
            LevelCompletedMenuController instantiatedController = mockedController.constructed().get(0);
            verify(instantiatedController, never()).step(any(), any(), anyLong());

            // Verify that LevelCompletedMenuViewer was instantiated with the correct model
            assertEquals(1, mockedViewer.constructed().size(), "LevelCompletedMenuViewer should be instantiated once");
        }
    }
}
