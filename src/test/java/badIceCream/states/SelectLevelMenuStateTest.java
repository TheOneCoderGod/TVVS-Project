package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.SelectLevelMenuController;
import badIceCream.model.menu.SelectLevelMenu;
import badIceCream.viewer.menu.SelectLevelMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Improved test class for SelectLevelMenuState, covering all constructors and interactions.
 */
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

        state.step(game, graphics, 10L);

        verify(controller, times(1)).step(eq(game), any(), eq(10L));
        verify(viewer, times(1)).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(600L);
        verify(controller, times(1)).stepMonsters(600L);
    }

    @Test
    @DisplayName("Constructor without controller and viewer initializes them correctly")
    void testConstructorWithoutControllerAndViewer() throws IOException {
        try (MockedConstruction<SelectLevelMenuController> mockedController = mockConstruction(SelectLevelMenuController.class,
                (mock, context) -> {
                    doNothing().when(mock).step(any(), any(), anyLong());
                });
             MockedConstruction<SelectLevelMenuViewer> mockedViewer = mockConstruction(SelectLevelMenuViewer.class)) {

            SelectLevelMenuState newState = new SelectLevelMenuState(model, 2);

            // Verify that SelectLevelMenuController was instantiated with the correct model
            assertEquals(1, mockedController.constructed().size(), "SelectLevelMenuController should be instantiated once");
            SelectLevelMenuController instantiatedController = mockedController.constructed().get(0);
            verify(instantiatedController, never()).step(any(), any(), anyLong());

            // Verify that SelectLevelMenuViewer was instantiated with the correct model
            assertEquals(1, mockedViewer.constructed().size(), "SelectLevelMenuViewer should be instantiated once");
        }
    }
}
