package badIceCream.controller.game.monsters;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.controller.game.IceCreamController;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IceCreamControllerTest {

    private IceCreamController controller;
    private Arena arena;
    private IceCream iceCream;
    private Game game;

    @Before
    public void setUp() {
        arena = mock(Arena.class);
        iceCream = mock(IceCream.class);
        game = mock(Game.class);

        when(arena.getIceCream()).thenReturn(iceCream);
        controller = new IceCreamController(arena);
    }

    @Test
    public void testMoveIceCreamLeft() {
        Position position = mock(Position.class);
        when(iceCream.getPosition()).thenReturn(position);
        when(position.getLeft()).thenReturn(position);
        when(arena.isEmpty(position)).thenReturn(true);

        controller.step(game, GUI.ACTION.LEFT, System.currentTimeMillis());

        verify(iceCream).setPosition(position);
        verify(iceCream).setLastMovement(GUI.ACTION.LEFT);
    }

    @Test
    public void testMoveIceCreamRight() {
        Position position = mock(Position.class);
        when(iceCream.getPosition()).thenReturn(position);
        when(position.getRight()).thenReturn(position);
        when(arena.isEmpty(position)).thenReturn(true);

        controller.step(game, GUI.ACTION.RIGHT, System.currentTimeMillis());

        verify(iceCream).setPosition(position);
        verify(iceCream).setLastMovement(GUI.ACTION.RIGHT);
    }

    @Test
    public void testMoveIceCreamUp() {
        Position position = mock(Position.class);
        when(iceCream.getPosition()).thenReturn(position);
        when(position.getUp()).thenReturn(position);
        when(arena.isEmpty(position)).thenReturn(true);

        controller.step(game, GUI.ACTION.UP, System.currentTimeMillis());

        verify(iceCream).setPosition(position);
        verify(iceCream).setLastMovement(GUI.ACTION.UP);
    }

    @Test
    public void testMoveIceCreamDown() {
        Position position = mock(Position.class);
        when(iceCream.getPosition()).thenReturn(position);
        when(position.getDown()).thenReturn(position);
        when(arena.isEmpty(position)).thenReturn(true);

        controller.step(game, GUI.ACTION.DOWN, System.currentTimeMillis());

        verify(iceCream).setPosition(position);
        verify(iceCream).setLastMovement(GUI.ACTION.DOWN);
    }

    @Test
    public void testEatFruit() {
        Position position = mock(Position.class);
        when(iceCream.getPosition()).thenReturn(position);
        when(arena.eatFruit(position)).thenReturn(1);

        int result = controller.eatFruit();

        assertEquals(1, result);
    }

    @Test
    public void testPowerIceCream() {
        controller.powerIceCream();
        verify(arena).powerIceCream(any(GUI.ACTION.class));
    }

    @Test
    public void testStepWithSpaceAction() {
        controller.step(game, GUI.ACTION.SPACE, System.currentTimeMillis());
        verify(arena).powerIceCream(any(GUI.ACTION.class));
    }


    @Test
    public void testMoveIceCreamLeftWhenNotEmpty() {
        Position position = mock(Position.class);
        when(iceCream.getPosition()).thenReturn(position);
        when(position.getLeft()).thenReturn(position);
        when(arena.isEmpty(position)).thenReturn(false);

        controller.step(game, GUI.ACTION.LEFT, System.currentTimeMillis());

        verify(iceCream, never()).setPosition(position);
        verify(iceCream).setLastMovement(GUI.ACTION.LEFT);
    }

    @Test
    public void testMoveIceCreamWhenStrawberryActive() {
        Position position = mock(Position.class);
        when(iceCream.getPosition()).thenReturn(position);
        when(position.getLeft()).thenReturn(position);
        when(arena.isEmpty(position)).thenReturn(true);
        when(iceCream.isStrawberryActive()).thenReturn(true);

        controller.step(game, GUI.ACTION.LEFT, System.currentTimeMillis());

        verify(iceCream).setPosition(position);
        verify(iceCream).setLastMovement(GUI.ACTION.LEFT);
    }

    @Test
    public void testStepWithInvalidAction() {
        controller.step(game, null, System.currentTimeMillis());
        verify(iceCream, never()).setPosition(any(Position.class));
        verify(arena, never()).powerIceCream(any(GUI.ACTION.class));
    }
}