package badIceCream.model.game.arena;

import badIceCream.GUI.GUI;
import badIceCream.audio.AudioController;
import badIceCream.model.Position;
import badIceCream.model.game.elements.*;
import badIceCream.model.game.elements.fruits.*;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests the public methods in Arena, ensuring we avoid infinite loops
 * by controlling or limiting chain-building/destroying scenarios.
 */
public class ArenaTest {

    private Arena arena;
    private List<Wall> walls;
    private List<Monster> monsters;
    private List<Fruit> fruits;
    private List<HotFloor> hotFloors;
    private IceCream iceCream;

    @BeforeEach
    public void setUp() {
        arena = new Arena(50, 20);
        walls = new ArrayList<>();
        monsters = new ArrayList<>();
        fruits = new ArrayList<>();
        hotFloors = new ArrayList<>();

        iceCream = mock(IceCream.class);

        arena.setWalls(walls);
        arena.setMonsters(monsters);
        arena.setFruits(fruits);
        arena.setHotFloors(hotFloors);
        arena.setIceCream(iceCream);
    }

    @Test
    @DisplayName("Constructor sets width/height properly")
    public void testConstructor() {
        assertEquals(50, arena.getWidth());
        assertEquals(20, arena.getHeight());
    }

    @Test
    @DisplayName("Level setters/getters")
    public void testLevel() {
        arena.setLevel(3);
        assertEquals(3, arena.getLevel());
    }

    @Test
    @DisplayName("isEmpty(...) returns false if position has a wall, true otherwise")
    public void testIsEmpty() {
        walls.add(new StoneWall(10,10));
        assertFalse(arena.isEmpty(new Position(10,10)));
        assertTrue(arena.isEmpty(new Position(9,9)));
    }

    @Test
    @DisplayName("isEmptyMonsters(...) returns false if wall/monster at position, true otherwise")
    public void testIsEmptyMonsters() {
        walls.add(new StoneWall(5,5));
        assertFalse(arena.isEmptyMonsters(new Position(5,5)));
        assertTrue(arena.isEmptyMonsters(new Position(2,2)));
    }

    @Test
    @DisplayName("isEmptyNoStoneWall(...) returns false if StoneWall/monster at position, true otherwise")
    public void testIsEmptyNoStoneWall() {
        walls.add(new StoneWall(3,3));
        assertFalse(arena.isEmptyNoStoneWall(new Position(3,3)));
        assertTrue(arena.isEmptyNoStoneWall(new Position(4,4)));
    }

    @Test
    @DisplayName("isEmptySpawnFruit(...) returns false if StoneWall or Fruit at position, true otherwise")
    public void testIsEmptySpawnFruit() {
        walls.add(new StoneWall(1,1));
        assertFalse(arena.isEmptySpawnFruit(new Position(1,1)));
        assertTrue(arena.isEmptySpawnFruit(new Position(2,2)));
    }

    @Test
    @DisplayName("isHotFloor(...) returns true if HotFloor at position, false otherwise")
    public void testIsHotFloor() {
        hotFloors.add(new HotFloor(5,5));
        assertTrue(arena.isHotFloor(new Position(5,5)));
        assertFalse(arena.isHotFloor(new Position(1,1)));
    }

    @Test
    @DisplayName("iceWallDestroyed(...) removes the wall at position")
    public void testIceWallDestroyed() {
        IceWall i = new IceWall(2,2);
        walls.add(i);
        arena.iceWallDestroyed(new Position(2,2));
        assertFalse(walls.contains(i));
    }

    @Test
    @DisplayName("isIceWall(...) checks if an IceWall is at a position")
    public void testIsIceWall() {
        IceWall i = new IceWall(3,3);
        walls.add(i);
        assertTrue(arena.isIceWall(new Position(3,3)));
        assertFalse(arena.isIceWall(new Position(4,4)));
    }

    @Test
    @DisplayName("hasMonster(...) returns monster at position, else null")
    public void testHasMonster() {
        Monster m = mock(Monster.class);
        when(m.getPosition()).thenReturn(new Position(2,2));
        monsters.add(m);

        assertEquals(m, arena.hasMonster(new Position(2,2)));
        assertNull(arena.hasMonster(new Position(9,9)));
    }

    @Test
    @DisplayName("isFruit(...) returns fruit type if found, else -1")
    public void testIsFruit() {
        AppleFruit f = new AppleFruit(0,0);
        fruits.add(f);
        assertEquals(1, arena.isFruit(new Position(0,0)));
        assertEquals(-1, arena.isFruit(new Position(9,9)));
    }

    @Test
    @DisplayName("eatFruit(...) removes the fruit and returns its type, else -1")
    public void testEatFruit() {
        BananaFruit b = new BananaFruit(3,3);
        fruits.add(b);
        assertEquals(2, arena.eatFruit(new Position(3,3)));
        assertFalse(fruits.contains(b));
        assertEquals(-1, arena.eatFruit(new Position(3,3)));
    }

    @Test
    @DisplayName("powerIceCream(...) destroys or builds walls (no infinite loop by limiting scenario)")
    public void testPowerIceCream() {
        // We'll spy on the real arena to limit chain building or destruction.
        Arena spyArena = spy(arena);
        Position icePos = new Position(5,5);
        when(iceCream.getPosition()).thenReturn(icePos);

        // 1) Setup a short chain of IceWalls (2 in a row)
        IceWall w1 = new IceWall(5,4);
        IceWall w2 = new IceWall(5,3);
        walls.add(w1);
        walls.add(w2);

        // Destroy scenario: calls breakWall sound once, removes both walls
        try (MockedStatic<AudioController> audio = mockStatic(AudioController.class)) {
            spyArena.powerIceCream(GUI.ACTION.UP);
            audio.verify(() -> AudioController.playBreakWallSound(), times(1));
        }
        assertFalse(walls.contains(w1));
        assertFalse(walls.contains(w2));

        // 2) Setup a minimal path for building (limit indefinite loop)
        // We'll mock "isEmptyMonsters" to return true only a couple times
        doReturn(true, true, false) // after 2 expansions, stops
                .when(spyArena).isEmptyMonsters(any(Position.class));
        doReturn(false) // ensure no hotFloor
                .when(spyArena).isHotFloor(any(Position.class));

        // Build scenario: calls buildWall sound once, creates 2 new IceWalls
        try (MockedStatic<AudioController> audio = mockStatic(AudioController.class)) {
            spyArena.powerIceCream(GUI.ACTION.DOWN);
            audio.verify(() -> AudioController.playBuildWallSound(), times(1));
        }

        // 2 new walls should be added
        long newWallsCount = walls.stream().filter(w -> w instanceof IceWall).count();
        assertEquals(2, newWallsCount);
    }

    @Test
    @DisplayName("generateNewFruits(...) adds correct number of fruits per level")
    public void testGenerateNewFruits() {
        Arena spyArena = spy(arena);
        doReturn(true).when(spyArena).isEmptySpawnFruit(any());

        spyArena.generateNewFruits(1);
        assertEquals(6, fruits.size());
        fruits.clear();

        spyArena.generateNewFruits(2);
        assertEquals(8, fruits.size());
        fruits.clear();

        spyArena.generateNewFruits(3);
        assertEquals(10, fruits.size());
        fruits.clear();

        spyArena.generateNewFruits(4);
        assertEquals(12, fruits.size());
        fruits.clear();

        spyArena.generateNewFruits(5);
        assertEquals(14, fruits.size());
    }
}