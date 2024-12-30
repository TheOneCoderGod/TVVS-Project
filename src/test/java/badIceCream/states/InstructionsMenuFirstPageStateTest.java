package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.menu.InstructionsMenuFirstPageController;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import badIceCream.viewer.menu.InstructionsMenuFirstPageViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    @DisplayName("Constructor sets model and level")
    void testConstructor() {
        assertEquals(model, state.getModel());
        assertEquals(1, state.getLevel());
    }

    @Test
    @DisplayName("step(...) calls controller.step(...) and viewer.draw(...)")
    void testStep() throws IOException {
        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);

        state.step(game, graphics, 100L);

        verify(controller).step(eq(game), any(), eq(100L));
        verify(viewer).draw(graphics);
    }

    @Test
    @DisplayName("stepMonsters(...) calls controller.stepMonsters(...)")
    void testStepMonsters() throws IOException {
        state.stepMonsters(50L);
        verify(controller).stepMonsters(50L);
    }
}
