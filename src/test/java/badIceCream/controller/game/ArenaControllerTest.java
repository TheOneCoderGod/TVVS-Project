package badIceCream.controller.game;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.controller.game.MonsterController;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.fruits.Fruit;
import badIceCream.model.game.elements.monsters.Monster;
import badIceCream.model.menu.GameOverMenu;
import badIceCream.model.menu.LevelCompletedMenu;
import badIceCream.model.menu.PauseMenu;
import badIceCream.states.GameOverMenuState;
import badIceCream.states.GameState;
import badIceCream.states.LevelCompletedMenuState;
import badIceCream.states.PauseMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class ArenaControllerTest {

    @Mock
    private Arena arena;

    @Mock
    private IceCreamController iceCreamController;

    @Mock
    private IceCream iceCream;

    @Mock
    private Game game;

    @Mock
    private GameState gameState;

    @Mock
    private LevelCompletedMenuState levelCompletedMenuState;

    @Mock
    private GameOverMenuState gameOverMenuState;

    @Mock
    private PauseMenuState pauseMenuState;

    @Mock
    private PauseMenu pauseMenu;

    @Mock
    private LevelCompletedMenu levelCompletedMenu;

    @Mock
    private GameOverMenu gameOverMenu;

    @Mock
    private Monster mockMonster1;

    @Mock
    private Monster mockMonster2;

    private ArenaController arenaController;

    private List<MonsterController> monsterControllers;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        monsterControllers = new ArrayList<>();
        // ArenaController will be instantiated within each test for specific setups
    }

    /**
     * Tests the constructor to ensure MonsterControllers are added based on monster types.
     */
    @Test
    void constructor_ShouldAddMonsterControllersBasedOnMonsterTypes() {
        // Arrange
        when(arena.getMonsters()).thenReturn(List.of(mockMonster1, mockMonster2));

        when(mockMonster1.getType()).thenReturn(1); // DefaultMovement
        when(mockMonster2.getType()).thenReturn(4); // WallBreakerMovement

        // Act
        arenaController = new ArenaController(arena, iceCreamController, monsterControllers);

        // Assert
        assertThat(monsterControllers).hasSize(2);
        // Further detailed verification would require exposing MonsterController's movement type or behavior.
        // Alternatively, use spies or reflection if necessary.
    }

    /**
     * Tests the step method when getFruits is empty and first is true, generating new fruits.
     */
    @Test
    void step_ShouldGenerateNewFruits_WhenFruitsEmptyAndFirstIsTrue() throws IOException {
        // Arrange
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCreamController.eatFruit()).thenReturn(-1); // No fruit eaten
        when(arena.getFruits()).thenReturn(new ArrayList<>()); // Fruits empty
        when(arena.getLevel()).thenReturn(1); // Current level

        arenaController = new ArenaController(arena, iceCreamController, monsterControllers);

        // Act
        arenaController.step(game, GUI.ACTION.UP, 1000L);

        // Assert
        verify(arena, times(1)).generateNewFruits(1);
        // Ensure that 'first' is now false (cannot test directly)
        verify(game, never()).setState(any(LevelCompletedMenuState.class), any(Type.class), anyInt(), anyInt());
    }

    /**
     * Tests the step method when getFruits is empty and first is false, and level >= game state level.
     */
    @Test
    void step_ShouldIncreaseLevelAndSetLevelCompletedState_WhenFruitsEmptyAndFirstIsFalse_AndLevelGEGameStateLevel() throws IOException, NoSuchFieldException, IllegalAccessException {
        // Arrange
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCreamController.eatFruit()).thenReturn(-1); // No fruit eaten
        when(arena.getFruits()).thenReturn(new ArrayList<>()); // Fruits empty
        when(arena.getLevel()).thenReturn(2); // Current level
        when(game.getState()).thenReturn(gameState);
        when(gameState.getLevel()).thenReturn(1); // Game state level

        arenaController = new ArenaController(arena, iceCreamController, monsterControllers);

        // Set 'first' to false using reflection
        Field firstField = ArenaController.class.getDeclaredField("first");
        firstField.setAccessible(true);
        firstField.setBoolean(arenaController, false);

        // Act
        arenaController.step(game, GUI.ACTION.UP, 1000L);

        // Assert
        verify(gameState, times(1)).increaseLevel();
        verify(game, times(1)).setState(any(LevelCompletedMenuState.class), eq(Type.win), eq(140), eq(50));
    }

    /**
     * Tests the step method when getFruits is empty and first is false, and level < game state level.
     */
    @Test
    void step_ShouldSetLevelCompletedState_WhenFruitsEmptyAndFirstIsFalse_AndLevelLTGameStateLevel() throws IOException, NoSuchFieldException, IllegalAccessException {
        // Arrange
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCreamController.eatFruit()).thenReturn(-1); // No fruit eaten
        when(arena.getFruits()).thenReturn(new ArrayList<>()); // Fruits empty
        when(arena.getLevel()).thenReturn(1); // Current level
        when(game.getState()).thenReturn(gameState);
        when(gameState.getLevel()).thenReturn(2); // Game state level

        arenaController = new ArenaController(arena, iceCreamController, monsterControllers);

        // Set 'first' to false using reflection
        Field firstField = ArenaController.class.getDeclaredField("first");
        firstField.setAccessible(true);
        firstField.setBoolean(arenaController, false);

        // Act
        arenaController.step(game, GUI.ACTION.UP, 1000L);

        // Assert
        verify(gameState, never()).increaseLevel();
        verify(game, times(1)).setState(any(LevelCompletedMenuState.class), eq(Type.win), eq(140), eq(50));
    }


    /**
     * Tests the step method when action is PAUSE, setting pause menu state.
     */
    @Test
    void step_ShouldSetPauseMenuState_WhenActionIsPause() throws IOException {
        // Arrange
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCreamController.eatFruit()).thenReturn(-1); // No fruit eaten
        when(arena.getFruits()).thenReturn(List.of(mock(Fruit.class))); // Fruits not empty
        when(iceCream.getAlive()).thenReturn(true); // IceCream is alive
        when(game.getState()).thenReturn(gameState);
        when(gameState.getLevel()).thenReturn(1); // Game state level

        arenaController = new ArenaController(arena, iceCreamController, monsterControllers);

        // Act
        arenaController.step(game, GUI.ACTION.PAUSE, 1000L);

        // Assert
        verify(game, times(1)).setState(any(PauseMenuState.class), eq(Type.menu), eq(140), eq(50));
    }


    /**
     * Tests the stepMonsters method, ensuring step(time) is called on all MonsterControllers.
     */
    @Test
    void stepMonsters_ShouldCallStepOnAllMonsterControllers() throws IOException {
        // Arrange
        MonsterController mockMonsterController1 = mock(MonsterController.class);
        MonsterController mockMonsterController2 = mock(MonsterController.class);
        monsterControllers.add(mockMonsterController1);
        monsterControllers.add(mockMonsterController2);

        arenaController = new ArenaController(arena, iceCreamController, monsterControllers);

        long time = 1000L;

        // Act
        arenaController.stepMonsters(time);

        // Assert
        verify(mockMonsterController1, times(1)).step(time);
        verify(mockMonsterController2, times(1)).step(time);
    }

    /**
     * Tests the stepMonsters method with no MonsterControllers.
     */
    @Test
    void stepMonsters_ShouldNotThrow_WhenNoMonsterControllers() throws IOException {
        // Arrange
        arenaController = new ArenaController(arena, iceCreamController, monsterControllers); // Empty list

        long time = 1000L;

        // Act & Assert
        assertDoesNotThrow(() -> arenaController.stepMonsters(time));
    }


    /**
     * Tests the step method when game state is null, should not throw exception.
     */
    @Test
    void step_ShouldHandleNullGameState() throws IOException {
        // Arrange
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCreamController.eatFruit()).thenReturn(-1); // No fruit eaten
        when(arena.getFruits()).thenReturn(new ArrayList<>()); // Fruits empty
        when(arena.getLevel()).thenReturn(1); // Current level
        when(game.getState()).thenReturn(null); // Null game state

        arenaController = new ArenaController(arena, iceCreamController, monsterControllers);

        // Act & Assert
        assertDoesNotThrow(() -> arenaController.step(game, GUI.ACTION.UP, 1000L));

        // Verify that generateNewFruits is called
        verify(arena, times(1)).generateNewFruits(1);
        // Since game state is null, setState is not called
        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }

    /**
     * Tests the step method when setStrawberry is called but is already active.
     */
    @Test
    void step_ShouldNotThrow_WhenSetStrawberryCalledWhileAlreadyActive() throws IOException {
        // Arrange
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCreamController.eatFruit()).thenReturn(5); // Eat strawberry
        when(iceCream.isStrawberryActive()).thenReturn(true); // Already active

        long currentTime = 1000L;

        arenaController = new ArenaController(arena, iceCreamController, monsterControllers);

        // Act & Assert
        assertDoesNotThrow(() -> arenaController.step(game, GUI.ACTION.UP, currentTime));

        // Verify setStrawberry(true) is called, even if it's already true
        verify(iceCream, times(1)).setStrawberry(true);
    }
}