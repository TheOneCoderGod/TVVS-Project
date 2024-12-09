package badIceCream.controller.game.monsters;

import badIceCream.GUI.GUI;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.monsters.Monster;
import badIceCream.utils.ShortestPathNextMove;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class RunnerMovementEnabledTest {

    @Mock
    private Monster monster;

    @Mock
    private Arena arena;

    @Mock
    private IceCream iceCream;

    @InjectMocks
    private RunnerMovementEnabled runnerMovementEnabled;

    /**
     * Tests the step method when the time condition is met and a shortest path is available.
     * Mocks the ShortestPathNextMove constructor to return a mock with findShortestPath stubbed.
     */
    @Test
    void step_ShouldMoveMonster_WhenTimeConditionMetAndShortestPathAvailable() throws Exception {
        // Arrange
        long currentTime = 10;
        long lastMovement = 4;

        // Create real Position instances
        Position currentPosition = new Position(1, 1);
        Position nextMove = new Position(1, 2);

        // Stub monster and arena methods
        when(monster.getPosition()).thenReturn(currentPosition);
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCream.getPosition()).thenReturn(nextMove); // Prevent NPE

        // Mock the construction of ShortestPathNextMove
        try (MockedConstruction<ShortestPathNextMove> mocked = mockConstruction(ShortestPathNextMove.class,
                (mock, context) -> when(mock.findShortestPath(monster, arena)).thenReturn(nextMove))) {

            // Act
            runnerMovementEnabled.step(monster, arena, currentTime, lastMovement);

            // Assert
            verify(monster, times(1)).setLastAction(any(GUI.ACTION.class));
            verify(monster, times(1)).setPosition(nextMove);
        }
    }

    /**
     * Tests the step method when the time condition is not met.
     */
    @Test
    void step_ShouldNotMoveMonster_WhenTimeConditionNotMet() throws IOException {
        // Arrange
        long currentTime = 4;
        long lastMovement = 0;

        // Act
        runnerMovementEnabled.step(monster, arena, currentTime, lastMovement);

        // Assert
        verifyNoInteractions(monster);
        verifyNoInteractions(arena);
    }


    /**
     * Tests the lastMove private method when the monster moves down.
     * Uses reflection to access the private method.
     */
    @Test
    void lastMove_ShouldReturnDOWN_WhenMovedDown() throws Exception {
        // Arrange
        Position previous = new Position(1, 1);
        Position after = new Position(1, 2);

        // Access private method using reflection
        Method lastMoveMethod = RunnerMovementEnabled.class.getDeclaredMethod("lastMove", Position.class, Position.class);
        lastMoveMethod.setAccessible(true);

        // Act
        GUI.ACTION action = (GUI.ACTION) lastMoveMethod.invoke(runnerMovementEnabled, previous, after);

        // Assert
        assertThat(action).isEqualTo(GUI.ACTION.DOWN);
    }

    /**
     * Tests the lastMove private method when the monster moves left.
     * Uses reflection to access the private method.
     */
    @Test
    void lastMove_ShouldReturnLEFT_WhenMovedLeft() throws Exception {
        // Arrange
        Position previous = new Position(1, 1);
        Position after = new Position(0, 1);

        // Access private method using reflection
        Method lastMoveMethod = RunnerMovementEnabled.class.getDeclaredMethod("lastMove", Position.class, Position.class);
        lastMoveMethod.setAccessible(true);

        // Act
        GUI.ACTION action = (GUI.ACTION) lastMoveMethod.invoke(runnerMovementEnabled, previous, after);

        // Assert
        assertThat(action).isEqualTo(GUI.ACTION.LEFT);
    }

    /**
     * Tests the lastMove private method when the monster moves right.
     * Uses reflection to access the private method.
     */
    @Test
    void lastMove_ShouldReturnRIGHT_WhenMovedRight() throws Exception {
        // Arrange
        Position previous = new Position(1, 1);
        Position after = new Position(2, 1);

        // Access private method using reflection
        Method lastMoveMethod = RunnerMovementEnabled.class.getDeclaredMethod("lastMove", Position.class, Position.class);
        lastMoveMethod.setAccessible(true);

        // Act
        GUI.ACTION action = (GUI.ACTION) lastMoveMethod.invoke(runnerMovementEnabled, previous, after);

        // Assert
        assertThat(action).isEqualTo(GUI.ACTION.RIGHT);
    }

    /**
     * Tests the lastMove private method when the monster moves up.
     * Uses reflection to access the private method.
     */
    @Test
    void lastMove_ShouldReturnUP_WhenMovedUp() throws Exception {
        // Arrange
        Position previous = new Position(1, 2);
        Position after = new Position(1, 1);

        // Access private method using reflection
        Method lastMoveMethod = RunnerMovementEnabled.class.getDeclaredMethod("lastMove", Position.class, Position.class);
        lastMoveMethod.setAccessible(true);

        // Act
        GUI.ACTION action = (GUI.ACTION) lastMoveMethod.invoke(runnerMovementEnabled, previous, after);

        // Assert
        assertThat(action).isEqualTo(GUI.ACTION.UP);
    }
}
