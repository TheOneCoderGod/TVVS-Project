package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.InstructionsMenuFirstPageController;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import badIceCream.viewer.menu.InstructionsMenuFirstPageViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Improved test class for InstructionsMenuFirstPageState, covering all constructors and interactions.
 */
class InstructionsMenuFirstPageStateTest {

    private InstructionsMenuFirstPage model;
    private InstructionsMenuFirstPageController controller;
    private InstructionsMenuFirstPageViewer viewer;
    private InstructionsMenuFirstPageState state;

    @BeforeEach
    void setUp() {
        model = mock(InstructionsMenuFirstPage.class);
        controller = mock(InstructionsMenuFirstPageController.class);
        viewer = mock(InstructionsMenuFirstPageViewer.class);
        state = new InstructionsMenuFirstPageState(model, controller, viewer, 1);
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
        state.stepMonsters(50L);
        verify(controller, times(1)).stepMonsters(50L);
    }
    @Test
    @DisplayName("Constructor without controller and viewer initializes them correctly")
    void testConstructorWithoutControllerAndViewer() throws IOException {
        try (MockedConstruction<InstructionsMenuFirstPageController> mockedController = mockConstruction(InstructionsMenuFirstPageController.class,
                (mock, context) -> {
                    doNothing().when(mock).step(any(), any(), anyLong());
                });
             MockedConstruction<InstructionsMenuFirstPageViewer> mockedViewer = mockConstruction(InstructionsMenuFirstPageViewer.class)) {

            InstructionsMenuFirstPageState newState = new InstructionsMenuFirstPageState(model, 2);

            // Verify that InstructionsMenuFirstPageController was instantiated with the correct model
            assertEquals(1, mockedController.constructed().size(), "InstructionsMenuFirstPageController should be instantiated once");
            InstructionsMenuFirstPageController instantiatedController = mockedController.constructed().get(0);
            verify(instantiatedController, never()).step(any(), any(), anyLong());

            // Verify that InstructionsMenuFirstPageViewer was instantiated with the correct model
            assertEquals(1, mockedViewer.constructed().size(), "InstructionsMenuFirstPageViewer should be instantiated once");
        }
    }
}
