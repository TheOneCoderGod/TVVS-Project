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
public class RunnerMovementEnabledTest {

    @Mock
    private Monster monster;

    @Mock
    private Arena arena;

    @Mock
    private IceCream iceCream;

    @InjectMocks
    private RunnerMovementEnabled runnerMovementEnabled;

    @Test
    public void step_ShouldMoveMonster_WhenTimeConditionMetAndShortestPathAvailable() throws Exception {
        long currentTime = 10;
        long lastMovement = 4;

        Position currentPosition = new Position(1, 1);
        Position nextMove = new Position(1, 2);

        // Mock the behavior of monster and arena
        when(monster.getPosition()).thenReturn(currentPosition);
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCream.getPosition()).thenReturn(nextMove);

        // Mock the construction of ShortestPathNextMove and its method
        try (MockedConstruction<ShortestPathNextMove> mocked = mockConstruction(ShortestPathNextMove.class,
                (mock, context) -> when(mock.findShortestPath(monster, arena)).thenReturn(nextMove))) {

            // Call the step method
            runnerMovementEnabled.step(monster, arena, currentTime, lastMovement);

            // Verify that the monster's position and last action were updated
            verify(monster, times(1)).setLastAction(any(GUI.ACTION.class));
            verify(monster, times(1)).setPosition(nextMove);
        }
    }

    @Test
    public void step_ShouldNotMoveMonster_WhenTimeConditionNotMet() throws IOException {
        long currentTime = 4;
        long lastMovement = 0;

        // Call the step method
        runnerMovementEnabled.step(monster, arena, currentTime, lastMovement);

        // Verify that there were no interactions with monster and arena
        verifyNoInteractions(monster);
        verifyNoInteractions(arena);
    }

    // Change access to public and keep the necessary comments
    public GUI.ACTION lastMove(Position previous, Position after) throws Exception {
        // Use reflection to invoke the private lastMove method
        Method lastMoveMethod = RunnerMovementEnabled.class.getDeclaredMethod("lastMove", Position.class, Position.class);
        lastMoveMethod.setAccessible(true);
        return (GUI.ACTION) lastMoveMethod.invoke(runnerMovementEnabled, previous, after);
    }

    @Test
    public void lastMove_ShouldReturnDOWN_WhenMovedDown() throws Exception {
        Position previous = new Position(1, 1);
        Position after = new Position(1, 2);

        // Call the lastMove method and verify the result
        GUI.ACTION action = lastMove(previous, after);
        assertThat(action).isEqualTo(GUI.ACTION.DOWN);
    }

    @Test
    public void lastMove_ShouldReturnLEFT_WhenMovedLeft() throws Exception {
        Position previous = new Position(1, 1);
        Position after = new Position(0, 1);

        // Call the lastMove method and verify the result
        GUI.ACTION action = lastMove(previous, after);
        assertThat(action).isEqualTo(GUI.ACTION.LEFT);
    }

    @Test
    public void lastMove_ShouldReturnRIGHT_WhenMovedRight() throws Exception {
        Position previous = new Position(1, 1);
        Position after = new Position(2, 1);

        // Call the lastMove method and verify the result
        GUI.ACTION action = lastMove(previous, after);
        assertThat(action).isEqualTo(GUI.ACTION.RIGHT);
    }

    @Test
    public void lastMove_ShouldReturnUP_WhenMovedUp() throws Exception {
        Position previous = new Position(1, 2);
        Position after = new Position(1, 1);

        // Call the lastMove method and verify the result
        GUI.ACTION action = lastMove(previous, after);
        assertThat(action).isEqualTo(GUI.ACTION.UP);
    }
}