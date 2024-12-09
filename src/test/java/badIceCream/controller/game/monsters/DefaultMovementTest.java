package badIceCream.controller.game.monsters;

import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DefaultMovementTest {

    private DefaultMovement defaultMovement;

    @Mock
    private Monster monster;
    @Mock
    private Arena arena;
    @Mock
    private Position position;
    @Mock
    private Position down;
    @Mock
    private Position left;
    @Mock
    private Position up;
    @Mock
    private Position right;
    @Mock
    private IceCream iceCream;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        defaultMovement = new DefaultMovement();
    }

    @Test
    void testGetPossible_ValidMove() throws Exception {
        // Setup mock behavior for the Position objects
        when(monster.getPosition()).thenReturn(position);
        when(position.getDown()).thenReturn(down);
        when(position.getLeft()).thenReturn(left);
        when(position.getUp()).thenReturn(up);
        when(position.getRight()).thenReturn(right);

        // Setup mock behavior for Arena methods
        when(arena.isEmptyMonsters(down)).thenReturn(true);
        when(arena.isEmptyMonsters(left)).thenReturn(false);
        when(arena.isEmptyMonsters(up)).thenReturn(true);
        when(arena.isEmptyMonsters(right)).thenReturn(false);

        // Ensure positions are not null
        assertNotNull(down, "Down position is null");
        assertNotNull(left, "Left position is null");
        assertNotNull(up, "Up position is null");
        assertNotNull(right, "Right position is null");

        // Use reflection to invoke the private getPossible method
        Method getPossibleMethod = DefaultMovement.class.getDeclaredMethod("getPossible", Monster.class, Arena.class);
        getPossibleMethod.setAccessible(true);
        Position result = (Position) getPossibleMethod.invoke(defaultMovement, monster, arena);

        // Check if the result is either down or up (since left and right are blocked)
        assertTrue(result == down || result == up, "Expected down or up, but got " + result);
    }

    @Test
    void testGetPossible_NoValidMove() throws Exception {
        // Setup mock behavior for the Position objects
        when(monster.getPosition()).thenReturn(position);
        when(position.getDown()).thenReturn(down);
        when(position.getLeft()).thenReturn(left);
        when(position.getUp()).thenReturn(up);
        when(position.getRight()).thenReturn(right);

        // Setup mock behavior for Arena methods to block all directions
        when(arena.isEmptyMonsters(down)).thenReturn(false);
        when(arena.isEmptyMonsters(left)).thenReturn(false);
        when(arena.isEmptyMonsters(up)).thenReturn(false);
        when(arena.isEmptyMonsters(right)).thenReturn(false);

        // Ensure positions are not null
        assertNotNull(down, "Down position is null");
        assertNotNull(left, "Left position is null");
        assertNotNull(up, "Up position is null");
        assertNotNull(right, "Right position is null");

        // Use reflection to invoke the private getPossible method
        Method getPossibleMethod = DefaultMovement.class.getDeclaredMethod("getPossible", Monster.class, Arena.class);
        getPossibleMethod.setAccessible(true);
        Position result = (Position) getPossibleMethod.invoke(defaultMovement, monster, arena);

        // Assert that the result is null as no valid move exists
        assertNull(result, "Expected null, but got " + result);
    }

    @Test
    void testStepMovement_NoMovement() throws Exception {
        long time = 100;
        long lastMovement = 0;

        // Setup mock behavior for the Position and Arena
        when(monster.getPosition()).thenReturn(position);

        // Use reflection to invoke the private step method
        Method stepMethod = DefaultMovement.class.getDeclaredMethod("step", Monster.class, Arena.class, long.class, long.class);
        stepMethod.setAccessible(true);
        stepMethod.invoke(defaultMovement, monster, arena, time, lastMovement);

        // Verify that the monster's position was not updated
        verify(monster, times(0)).setPosition(any(Position.class));
    }


    @Test
    void testMoveMonster() {
        // Setup mock behavior for the Position and Arena
        when(monster.getPosition()).thenReturn(position);
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCream.isStrawberryActive()).thenReturn(false);
        when(iceCream.getPosition()).thenReturn(position);

        // Ensure ice cream and position are not null
        assertNotNull(iceCream, "IceCream is null");
        assertNotNull(position, "Position is null");

        // Invoke the moveMonster method
        defaultMovement.moveMonster(monster, position, arena);

        // Verify that the monster's position was updated
        verify(monster, times(1)).setPosition(position);

        // Verify that the ice cream's alive status was changed
        verify(iceCream, times(1)).changeAlive();
    }
}
