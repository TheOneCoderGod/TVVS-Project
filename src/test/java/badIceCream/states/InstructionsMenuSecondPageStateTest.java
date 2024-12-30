package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.InstructionsMenuSecondPageController;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import badIceCream.viewer.menu.InstructionsMenuSecondPageViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    @DisplayName("Constructor sets model, level")
    void testConstructor() {
        assertEquals(model, state.getModel());
        assertEquals(2, state.getLevel());
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 999L);

        verify(controller).step(eq(game), any(), eq(999L));
        verify(viewer).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(123L);
        verify(controller).stepMonsters(123L);
    }
}
