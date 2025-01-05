package badIceCream.controller.game;

import badIceCream.controller.game.GameController;
import badIceCream.model.game.arena.Arena;
import badIceCream.Game;
import badIceCream.GUI.GUI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class GameControllerTest {

    private Arena mockArena;
    private GameController gameController;

    @Before
    public void setUp() {
        mockArena = mock(Arena.class);
        gameController = new GameController(mockArena) {
            @Override
            public void step(Game game, GUI.ACTION action, long time) {
                // Implement the abstract method
            }
        };
    }

    @Test
    public void testGameControllerInitialization() {
        assertNotNull(gameController);
        assertEquals(mockArena, gameController.getModel());
    }
}