package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.InstructionsMenuSecondPageController;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import badIceCream.viewer.menu.InstructionsMenuSecondPageViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Improved test class for InstructionsMenuSecondPageState, covering all constructors and interactions.
 */
class InstructionsMenuSecondPageStateTest {

    private InstructionsMenuSecondPage model;
    private InstructionsMenuSecondPageController controller;
    private InstructionsMenuSecondPageViewer viewer;
    private InstructionsMenuSecondPageState state;

    @BeforeEach
    void setUp() {
        model = mock(InstructionsMenuSecondPage.class);
        controller = mock(InstructionsMenuSecondPageController.class);
        viewer = mock(InstructionsMenuSecondPageViewer.class);
        state = new InstructionsMenuSecondPageState(model, controller, viewer, 2);
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

        state.step(game, graphics, 999L);

        verify(controller, times(1)).step(eq(game), any(), eq(999L));
        verify(viewer, times(1)).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(123L);
        verify(controller, times(1)).stepMonsters(123L);
    }

    @Test
    @DisplayName("Constructor without controller and viewer initializes them correctly")
    void testConstructorWithoutControllerAndViewer() throws IOException {
        try (MockedConstruction<InstructionsMenuSecondPageController> mockedController = mockConstruction(InstructionsMenuSecondPageController.class,
                (mock, context) -> {
                    doNothing().when(mock).step(any(), any(), anyLong());
                });
             MockedConstruction<InstructionsMenuSecondPageViewer> mockedViewer = mockConstruction(InstructionsMenuSecondPageViewer.class)) {

            InstructionsMenuSecondPageState newState = new InstructionsMenuSecondPageState(model, 3);

            // Verify that InstructionsMenuSecondPageController was instantiated with the correct model
            assertEquals(1, mockedController.constructed().size(), "InstructionsMenuSecondPageController should be instantiated once");
            InstructionsMenuSecondPageController instantiatedController = mockedController.constructed().get(0);
            verify(instantiatedController, never()).step(any(), any(), anyLong());

            // Verify that InstructionsMenuSecondPageViewer was instantiated with the correct model
            assertEquals(1, mockedViewer.constructed().size(), "InstructionsMenuSecondPageViewer should be instantiated once");
        }
    }
}
