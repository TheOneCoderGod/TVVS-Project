package badIceCream.controller.game.monsters;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.controller.game.ArenaController;
import badIceCream.controller.game.IceCreamController;
import badIceCream.controller.game.MonsterController;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.menu.GameOverMenu;
import badIceCream.model.menu.LevelCompletedMenu;
import badIceCream.states.GameState;
import badIceCream.states.GameOverMenuState;
import badIceCream.states.LevelCompletedMenuState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class ArenaControllerTest {

    private ArenaController arenaController;
    private IceCreamController iceCreamController;
    private List<MonsterController> monsterControllers;
    private Arena arena;
    private Game game;
    private IceCream mockIceCream;

    @BeforeEach
    public void setUp() {
        // Mock dependencies
        arena = mock(Arena.class);
        iceCreamController = mock(IceCreamController.class);
        monsterControllers = new ArrayList<>();
        game = mock(Game.class);
        mockIceCream = mock(IceCream.class);

        // Ensure arena.getIceCream() does not return null
        when(arena.getIceCream()).thenReturn(mockIceCream);

        // Initialize the ArenaController
        arenaController = new ArenaController(arena, iceCreamController, monsterControllers);
    }

    @Test
    public void testStep() throws IOException {
        // Mock behavior
        when(iceCreamController.eatFruit()).thenReturn(-1); // Simulate no fruit eaten
        when(arena.getFruits()).thenReturn(new ArrayList<>()); // Simulate no fruits available
        when(mockIceCream.isStrawberryActive()).thenReturn(true); // Mock IceCream behavior
        when(game.getState()).thenReturn(mock(GameState.class)); // Mock GameState

        // Simulate action and time
        GUI.ACTION action = GUI.ACTION.UP;
        long time = System.currentTimeMillis();

        // Ensure no exception is thrown
        assertDoesNotThrow(() -> arenaController.step(game, action, time));
    }

    @Test
    public void testStepMonsters() throws IOException {
        // Add a mock MonsterController to the list
        MonsterController monsterController = mock(MonsterController.class);
        monsterControllers.add(monsterController);

        long time = System.currentTimeMillis();

        // Call stepMonsters and ensure no exceptions
        assertDoesNotThrow(() -> arenaController.stepMonsters(time));

        // Verify that step was called on the MonsterController
        verify(monsterController, times(1)).step(time);
    }

    @Test
    public void testStepWithNoMonsters() throws IOException {
        // Simulate action and time
        GUI.ACTION action = GUI.ACTION.LEFT;
        long time = System.currentTimeMillis();

        // Ensure no exceptions are thrown with an empty monsters list
        assertDoesNotThrow(() -> arenaController.step(game, action, time));
    }

    @Test
    public void testGameOverState() {
        // Mock GameOverMenu and state
        GameOverMenu gameOverMenu = mock(GameOverMenu.class);
        when(game.getState()).thenReturn(mock(GameOverMenuState.class));

        // Simulate action and time
        GUI.ACTION action = GUI.ACTION.DOWN;
        long time = 1000L;

        // Ensure no exceptions are thrown
        assertDoesNotThrow(() -> arenaController.step(game, action, time));
    }

    @Test
    public void testLevelCompletedState() {
        // Mock LevelCompletedMenu and state
        LevelCompletedMenu levelCompletedMenu = mock(LevelCompletedMenu.class);
        when(game.getState()).thenReturn(mock(LevelCompletedMenuState.class));

        // Simulate action and time
        GUI.ACTION action = GUI.ACTION.UP;
        long time = 1000L;

        // Ensure no exceptions are thrown
        assertDoesNotThrow(() -> arenaController.step(game, action, time));
    }
}
