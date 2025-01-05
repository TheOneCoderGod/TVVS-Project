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
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WallBreakerMovementTest {

    @Mock
    private Monster monster;

    @Mock
    private Arena arena;

    @Mock
    private IceCream iceCream;

    @InjectMocks
    private WallBreakerMovement wallBreakerMovement;

    /**
     * Tests the step method when the time condition is met and a possible move is available with an ice wall present.
     */
    @Test
    public void step_ShouldMoveMonster_WhenTimeConditionMetAndPossibleMoveAvailableAndIceWallPresent() throws Exception {
        // Arrange
        long currentTime = 300;
        long lastMovement = 100;

        Position currentPosition = new Position(2, 2);
        Position targetPosition = new Position(2, 3); // Assume DOWN direction

        when(monster.getPosition()).thenReturn(currentPosition);
        when(arena.isEmptyNoStoneWall(targetPosition)).thenReturn(true);
        when(arena.isIceWall(targetPosition)).thenReturn(true);
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCream.getPosition()).thenReturn(targetPosition);
        when(iceCream.isStrawberryActive()).thenReturn(false);

        // Mock the construction of ShortestPathNextMove to return a mock with findShortestPath stubbed
        try (MockedConstruction<ShortestPathNextMove> mocked = mockConstruction(ShortestPathNextMove.class,
                (mock, context) -> when(mock.findShortestPath(monster, arena)).thenReturn(targetPosition))) {

            // Act
            wallBreakerMovement.step(monster, arena, currentTime, lastMovement);

            // Assert
            verify(monster, times(1)).setLastAction(GUI.ACTION.DOWN);
            verify(arena, times(1)).iceWallDestroyed(targetPosition);
            verify(monster, times(1)).setPosition(targetPosition);
            verify(iceCream, times(1)).changeAlive();
        }
    }

    /**
     * Tests the step method when the time condition is met and a possible move is available without an ice wall.
     */
    @Test
    public void step_ShouldMoveMonster_WhenTimeConditionMetAndPossibleMoveAvailableAndNoIceWall() throws Exception {
        // Arrange
        long currentTime = 250;
        long lastMovement = 50;

        Position currentPosition = new Position(1, 1);
        Position targetPosition = new Position(1, 2); // Assume DOWN direction

        when(monster.getPosition()).thenReturn(currentPosition);
        when(arena.isEmptyNoStoneWall(targetPosition)).thenReturn(true);
        when(arena.isIceWall(targetPosition)).thenReturn(false);
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCream.getPosition()).thenReturn(targetPosition);
        when(iceCream.isStrawberryActive()).thenReturn(false);

        // Mock the construction of ShortestPathNextMove to return a mock with findShortestPath stubbed
        try (MockedConstruction<ShortestPathNextMove> mocked = mockConstruction(ShortestPathNextMove.class,
                (mock, context) -> when(mock.findShortestPath(monster, arena)).thenReturn(targetPosition))) {

            // Act
            wallBreakerMovement.step(monster, arena, currentTime, lastMovement);

            // Assert
            verify(monster, times(1)).setLastAction(GUI.ACTION.DOWN);
            verify(arena, never()).iceWallDestroyed(any(Position.class));
            verify(monster, times(1)).setPosition(targetPosition);
            verify(iceCream, times(1)).changeAlive();
        }
    }

    /**
     * Tests the step method when the time condition is not met.
     */
    @Test
    public void step_ShouldNotMoveMonster_WhenTimeConditionNotMet() throws IOException {
        // Arrange
        long currentTime = 150;
        long lastMovement = 100;

        // Act
        wallBreakerMovement.step(monster, arena, currentTime, lastMovement);

        // Assert
        verifyNoInteractions(monster);
        verifyNoInteractions(arena);
    }

    /**
     * Tests the lastMove private method when the monster moves down.
     */
    @Test
    public void lastMove_ShouldReturnDOWN_WhenMovedDown() throws Exception {
        // Arrange
        Position previous = new Position(1, 1);
        Position after = new Position(1, 2);

        // Access private method using reflection
        Method lastMoveMethod = WallBreakerMovement.class.getDeclaredMethod("lastMove", Position.class, Position.class);
        lastMoveMethod.setAccessible(true);

        // Act
        GUI.ACTION action = (GUI.ACTION) lastMoveMethod.invoke(wallBreakerMovement, previous, after);

        // Assert
        assertThat(action).isEqualTo(GUI.ACTION.DOWN);
    }

    /**
     * Tests the lastMove private method when the monster moves left.
     */
    @Test
    public void lastMove_ShouldReturnLEFT_WhenMovedLeft() throws Exception {
        // Arrange
        Position previous = new Position(1, 1);
        Position after = new Position(0, 1);

        // Access private method using reflection
        Method lastMoveMethod = WallBreakerMovement.class.getDeclaredMethod("lastMove", Position.class, Position.class);
        lastMoveMethod.setAccessible(true);

        // Act
        GUI.ACTION action = (GUI.ACTION) lastMoveMethod.invoke(wallBreakerMovement, previous, after);

        // Assert
        assertThat(action).isEqualTo(GUI.ACTION.LEFT);
    }

    /**
     * Tests the lastMove private method when the monster moves right.
     */
    @Test
    public void lastMove_ShouldReturnRIGHT_WhenMovedRight() throws Exception {
        // Arrange
        Position previous = new Position(1, 1);
        Position after = new Position(2, 1);

        // Access private method using reflection
        Method lastMoveMethod = WallBreakerMovement.class.getDeclaredMethod("lastMove", Position.class, Position.class);
        lastMoveMethod.setAccessible(true);

        // Act
        GUI.ACTION action = (GUI.ACTION) lastMoveMethod.invoke(wallBreakerMovement, previous, after);

        // Assert
        assertThat(action).isEqualTo(GUI.ACTION.RIGHT);
    }

    /**
     * Tests the lastMove private method when the monster moves up.
     */
    @Test
    public void lastMove_ShouldReturnUP_WhenMovedUp() throws Exception {
        // Arrange
        Position previous = new Position(1, 2);
        Position after = new Position(1, 1);

        // Access private method using reflection
        Method lastMoveMethod = WallBreakerMovement.class.getDeclaredMethod("lastMove", Position.class, Position.class);
        lastMoveMethod.setAccessible(true);

        // Act
        GUI.ACTION action = (GUI.ACTION) lastMoveMethod.invoke(wallBreakerMovement, previous, after);

        // Assert
        assertThat(action).isEqualTo(GUI.ACTION.UP);
    }

    /**
     * Tests the getPossible private method when all directions are available.
     */
    @Test
    public void getPossible_ShouldReturnOneOfTheAvailablePositions_WhenAllDirectionsAreAvailable() throws Exception {
        // Arrange
        Position currentPosition = new Position(1, 1);
        Position down = new Position(1, 2);
        Position left = new Position(0, 1);
        Position up = new Position(1, 0);
        Position right = new Position(2, 1);

        when(monster.getPosition()).thenReturn(currentPosition);
        when(arena.isEmptyNoStoneWall(down)).thenReturn(true);
        when(arena.isEmptyNoStoneWall(left)).thenReturn(true);
        when(arena.isEmptyNoStoneWall(up)).thenReturn(true);
        when(arena.isEmptyNoStoneWall(right)).thenReturn(true);

        // Mock the construction of ShortestPathNextMove to return a mock with findShortestPath stubbed as null
        try (MockedConstruction<ShortestPathNextMove> mocked = mockConstruction(ShortestPathNextMove.class,
                (mock, context) -> when(mock.findShortestPath(monster, arena)).thenReturn(null))) {

            // Access private method using reflection
            Method getPossibleMethod = WallBreakerMovement.class.getDeclaredMethod("getPossible", Monster.class, Arena.class);
            getPossibleMethod.setAccessible(true);

            // Act
            Position result = (Position) getPossibleMethod.invoke(wallBreakerMovement, monster, arena);

            // Assert
            List<Position> possiblePositions = Arrays.asList(down, left, up, right);
            assertThat(possiblePositions).contains(result);
        }
    }

    /**
     * Tests the getPossible private method when some directions are unavailable.
     */
    @Test
    public void getPossible_ShouldReturnOneOfTheAvailablePositions_WhenSomeDirectionsAreUnavailable() throws Exception {
        // Arrange
        Position currentPosition = new Position(2, 2);
        Position down = new Position(2, 3);
        Position left = new Position(1, 2);
        Position up = new Position(2, 1);
        Position right = new Position(3, 2);

        when(monster.getPosition()).thenReturn(currentPosition);
        when(arena.isEmptyNoStoneWall(down)).thenReturn(false);
        when(arena.isEmptyNoStoneWall(left)).thenReturn(true);
        when(arena.isEmptyNoStoneWall(up)).thenReturn(false);
        when(arena.isEmptyNoStoneWall(right)).thenReturn(true);

        // Mock the construction of ShortestPathNextMove to return a mock with findShortestPath stubbed as null
        try (MockedConstruction<ShortestPathNextMove> mocked = mockConstruction(ShortestPathNextMove.class,
                (mock, context) -> when(mock.findShortestPath(monster, arena)).thenReturn(null))) {

            // Access private method using reflection
            Method getPossibleMethod = WallBreakerMovement.class.getDeclaredMethod("getPossible", Monster.class, Arena.class);
            getPossibleMethod.setAccessible(true);

            // Act
            Position result = (Position) getPossibleMethod.invoke(wallBreakerMovement, monster, arena);

            // Assert
            List<Position> possiblePositions = Arrays.asList(left, right);
            assertThat(possiblePositions).contains(result);
        }
    }

    /**
     * Tests the getPossible private method when no directions are available.
     */
    @Test
    public void getPossible_ShouldReturnNull_WhenNoDirectionsAreAvailable() throws Exception {
        // Arrange
        Position currentPosition = new Position(0, 0);
        Position down = new Position(0, 1);
        Position left = new Position(-1, 0); // Assuming out of bounds or invalid
        Position up = new Position(0, -1);   // Assuming out of bounds or invalid
        Position right = new Position(1, 0);

        when(monster.getPosition()).thenReturn(currentPosition);
        when(arena.isEmptyNoStoneWall(down)).thenReturn(false);
        when(arena.isEmptyNoStoneWall(left)).thenReturn(false);
        when(arena.isEmptyNoStoneWall(up)).thenReturn(false);
        when(arena.isEmptyNoStoneWall(right)).thenReturn(false);

        // Mock the construction of ShortestPathNextMove to return a mock with findShortestPath stubbed as null
        try (MockedConstruction<ShortestPathNextMove> mocked = mockConstruction(ShortestPathNextMove.class,
                (mock, context) -> when(mock.findShortestPath(monster, arena)).thenReturn(null))) {

            // Access private method using reflection
            Method getPossibleMethod = WallBreakerMovement.class.getDeclaredMethod("getPossible", Monster.class, Arena.class);
            getPossibleMethod.setAccessible(true);

            // Act
            Position result = (Position) getPossibleMethod.invoke(wallBreakerMovement, monster, arena);

            // Assert
            assertThat(result).isNull();
        }
    }
}