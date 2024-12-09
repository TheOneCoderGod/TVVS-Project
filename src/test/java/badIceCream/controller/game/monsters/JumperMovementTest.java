package badIceCream.controller.game.monsters;

import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JumperMovementTest {

    private JumperMovement jumperMovement;

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
        jumperMovement = new JumperMovement();
    }

    // Utility method to invoke private method using reflection
    private Object invokePrivateMethod(String methodName, Object... args) throws Exception {
        Method method = JumperMovement.class.getDeclaredMethod(methodName, Monster.class, Arena.class);
        method.setAccessible(true);
        return method.invoke(jumperMovement, args);
    }

    @Test
    void testGetPossible_ValidMove() throws Exception {
        when(monster.getPosition()).thenReturn(position);
        when(position.getDown()).thenReturn(down);
        when(position.getLeft()).thenReturn(left);
        when(position.getUp()).thenReturn(up);
        when(position.getRight()).thenReturn(right);

        when(arena.isEmptyNoStoneWall(down)).thenReturn(true);
        when(arena.isEmptyNoStoneWall(left)).thenReturn(false);
        when(arena.isEmptyNoStoneWall(up)).thenReturn(true);
        when(arena.isEmptyNoStoneWall(right)).thenReturn(false);

        Position result = (Position) invokePrivateMethod("getPossible", monster, arena);

        assertTrue(result == down || result == up, "Expected down or up, but got " + result);
    }

    @Test
    void testGetPossible_NoValidMove() throws Exception {
        when(monster.getPosition()).thenReturn(position);
        when(position.getDown()).thenReturn(down);
        when(position.getLeft()).thenReturn(left);
        when(position.getUp()).thenReturn(up);
        when(position.getRight()).thenReturn(right);

        when(arena.isEmptyNoStoneWall(down)).thenReturn(false);
        when(arena.isEmptyNoStoneWall(left)).thenReturn(false);
        when(arena.isEmptyNoStoneWall(up)).thenReturn(false);
        when(arena.isEmptyNoStoneWall(right)).thenReturn(false);

        Position result = (Position) invokePrivateMethod("getPossible", monster, arena);

        assertNull(result, "Expected null, but got " + result);
    }

    @Test
    void testStepMovement_NoMovement() throws IOException {
        long time = 100;
        long lastMovement = 0;

        when(monster.getPosition()).thenReturn(position);

        jumperMovement.step(monster, arena, time, lastMovement);

        verify(monster, times(0)).setPosition(any(Position.class));
    }

    @Test
    void testStepMovement_WithMovement() throws IOException {
        long time = 300;
        long lastMovement = 0;

        when(monster.getPosition()).thenReturn(position);
        when(position.getDown()).thenReturn(down);
        when(position.getLeft()).thenReturn(left);
        when(position.getUp()).thenReturn(up);
        when(position.getRight()).thenReturn(right);

        when(arena.isEmptyNoStoneWall(down)).thenReturn(true);
        when(arena.isEmptyNoStoneWall(left)).thenReturn(false);
        when(arena.isEmptyNoStoneWall(up)).thenReturn(true);
        when(arena.isEmptyNoStoneWall(right)).thenReturn(false);
        when(arena.getIceCream()).thenReturn(iceCream); // Ensure getIceCream() returns a non-null IceCream object
        when(iceCream.getPosition()).thenReturn(position); // Ensure getPosition() returns a non-null Position object

        jumperMovement.step(monster, arena, time, lastMovement);

        verify(monster, times(1)).setPosition(any(Position.class));
    }
    @Test
    void testMoveMonster() {
        when(monster.getPosition()).thenReturn(position);
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCream.isStrawberryActive()).thenReturn(false);
        when(iceCream.getPosition()).thenReturn(position);

        jumperMovement.moveMonster(monster, position, arena);

        verify(monster, times(1)).setPosition(position);
        verify(iceCream, times(1)).changeAlive();
    }
}