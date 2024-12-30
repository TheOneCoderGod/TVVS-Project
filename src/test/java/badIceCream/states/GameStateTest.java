package badIceCream.states;

import badIceCream.Game;
import badIceCream.GUI.Graphics;
import badIceCream.controller.game.ArenaController;
import badIceCream.model.game.arena.Arena;
import badIceCream.viewer.ArenaViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    @DisplayName("Constructor sets model, controller, viewer, and level")
    void testConstructor() {
        assertEquals(model, state.getModel());
        assertEquals(3, state.getLevel());
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
        verify(controller).stepMonsters(200L);
    }

    @Test
    @DisplayName("increaseLevel() up to 5")
    void testIncreaseLevel() {
        assertEquals(3, state.getLevel());
        state.increaseLevel();
        assertEquals(4, state.getLevel());
        state.increaseLevel();
        assertEquals(5, state.getLevel());
        // Additional calls won't exceed 5
        state.increaseLevel();
        assertEquals(5, state.getLevel());
    }
}
