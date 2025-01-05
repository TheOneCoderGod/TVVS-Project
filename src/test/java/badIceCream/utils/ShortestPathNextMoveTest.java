package badIceCream.utils;

import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * We'll test the public method findShortestPath(...).
 * The private manhattanDistance(...) method is tested via black-box approach.
 */
public class ShortestPathNextMoveTest {

    private ShortestPathNextMove sp;
    private Arena arena;
    private Monster monster;

    @BeforeEach
    public void setUp() {
        sp = new ShortestPathNextMove();
        arena = mock(Arena.class);
        monster = mock(Monster.class);
    }

    @Test
    @DisplayName("Returns null if monster's position == ice cream position")
    public void testSamePosition() {
        Position samePos = new Position(5,5);
        when(monster.getPosition()).thenReturn(samePos);

        // mock Arena's getIceCream().getPosition() to same
        var iceCream = mock(badIceCream.model.game.elements.IceCream.class);
        when(iceCream.getPosition()).thenReturn(samePos);
        when(arena.getIceCream()).thenReturn(iceCream);

        assertNull(sp.findShortestPath(monster, arena));
    }

    @Test
    @DisplayName("Returns null if no path found (arena not isEmptyMonsters => no expansions)")
    public void testNoPathFound() {
        Position monsterPos = new Position(2,2);
        Position icePos = new Position(10,10);

        when(monster.getPosition()).thenReturn(monsterPos);
        var iceCream = mock(badIceCream.model.game.elements.IceCream.class);
        when(iceCream.getPosition()).thenReturn(icePos);
        when(arena.getIceCream()).thenReturn(iceCream);

        // every call to isEmptyMonsters => false => no expansions => no path
        when(arena.isEmptyMonsters(any())).thenReturn(false);

        assertNull(sp.findShortestPath(monster, arena));
    }

    @Test
    @DisplayName("Returns next move if a short path is available")
    public void testShortPath() {
        Position monsterPos = new Position(5,5);
        Position icePos = new Position(5,7);
        when(monster.getPosition()).thenReturn(monsterPos);

        var iceCream = mock(badIceCream.model.game.elements.IceCream.class);
        when(iceCream.getPosition()).thenReturn(icePos);
        when(arena.getIceCream()).thenReturn(iceCream);

        // We'll allow moves to be empty except for final
        // e.g. isEmptyMonsters(...) => true to let path expand
        when(arena.isEmptyMonsters(any())).thenReturn(true);

        // The path from (5,5) to (5,7) is down => (5,6) => (5,7)
        // The returned position should be (5,6), which is the first step
        Position result = sp.findShortestPath(monster, arena);
        // path: monsterPos => (5,6) => (5,7)
        // we want the immediate next step => (5,6)
        assertNotNull(result);
        assertEquals(new Position(5,6), result);
    }
}
