package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.HotFloor;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.Wall;
import badIceCream.model.game.elements.fruits.Fruit;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link ArenaViewer}. We craft a scenario that
 * exercises as many branches as possible in drawElements(...).
 */
class ArenaViewerTest {

    private Arena arena;
    private FruitViewer fruitViewer;
    private MonsterViewer monsterViewer;
    private WallViewer wallViewer;
    private HotFloorViewer hotFloorViewer;
    private IceCreamViewer iceCreamViewer;
    private ArenaViewer viewer;
    private Graphics graphics;

    @BeforeEach
    void setUp() {
        // Mocks
        arena = mock(Arena.class);
        fruitViewer = mock(FruitViewer.class);
        monsterViewer = mock(MonsterViewer.class);
        wallViewer = mock(WallViewer.class);
        hotFloorViewer = mock(HotFloorViewer.class);
        iceCreamViewer = mock(IceCreamViewer.class);

        // The viewer under test
        viewer = new ArenaViewer(
                arena, fruitViewer, monsterViewer, wallViewer, hotFloorViewer, iceCreamViewer
        );

        graphics = mock(Graphics.class);
    }

    @Test
    @DisplayName("drawElements(...) covers fruits, monsters, walls, iceCream, hotFloors")
    void testDrawElements() throws IOException {
        // Setup lists returned by arena
        Fruit fruitMock = mock(Fruit.class);
        when(fruitMock.getPosition()).thenReturn(new Position(1,1));
        when(fruitMock.getType()).thenReturn(2);

        Monster monsterMock = mock(Monster.class);
        when(monsterMock.getPosition()).thenReturn(new Position(2,2));
        when(monsterMock.getType()).thenReturn(3);
        when(monsterMock.getLastAction()).thenReturn(badIceCream.GUI.GUI.ACTION.DOWN);
        when(monsterMock.isRunning()).thenReturn(true);

        Wall wall1 = mock(Wall.class); // StoneWall => type=2
        when(wall1.getPosition()).thenReturn(new Position(3,3));
        when(wall1.getType()).thenReturn(2);

        Wall wall2 = mock(Wall.class); // "IceWall" => type != 2 => we check fruit/monster logic
        when(wall2.getPosition()).thenReturn(new Position(4,4));
        when(wall2.getType()).thenReturn(1);

        HotFloor hotFloor1 = mock(HotFloor.class);
        when(hotFloor1.getPosition()).thenReturn(new Position(5,5));

        // Suppose fruit= -1 at most positions
        when(arena.isFruit(any())).thenReturn(-1);
        // But for wall2's position, let's say there's a fruit=1 => triggers branch
        when(arena.isFruit(eq(new Position(4,4)))).thenReturn(1);

        // Suppose there's a monster at (4,4) with type=2 => triggers monster-lives-on-wall branch
        Monster jumpMonster = mock(Monster.class);
        when(jumpMonster.getType()).thenReturn(2);
        when(jumpMonster.getLastAction()).thenReturn(badIceCream.GUI.GUI.ACTION.UP);
        when(arena.hasMonster(eq(new Position(4,4)))).thenReturn(jumpMonster);

        // For hotFloor
        // Suppose there's no monster at (5,5), but there is fruit=3 => triggers hotFloor fruit
        when(arena.isFruit(eq(new Position(5,5)))).thenReturn(3);

        // IceCream
        IceCream iceMock = mock(IceCream.class);
        when(iceMock.getPosition()).thenReturn(new Position(6,6));
        when(iceMock.getLastMovement()).thenReturn(badIceCream.GUI.GUI.ACTION.RIGHT);
        when(arena.getIceCream()).thenReturn(iceMock);

        // For hotFloor2: a new one that shares position with iceCream => triggers branch
        HotFloor hotFloor2 = mock(HotFloor.class);
        when(hotFloor2.getPosition()).thenReturn(new Position(6,6));

        // Return lists from arena
        when(arena.getFruits()).thenReturn(List.of(fruitMock));
        when(arena.getMonsters()).thenReturn(List.of(monsterMock));
        when(arena.getWalls()).thenReturn(List.of(wall1, wall2));
        when(arena.getHotFloors()).thenReturn(List.of(hotFloor1, hotFloor2));

        // Now call drawElements
        viewer.drawElements(graphics);

        // Verify fruitViewer drew the fruit
        verify(fruitViewer).draw(eq(fruitMock), eq(graphics), eq(2));

        // Verify monsterViewer drew the monster
        verify(monsterViewer).draw(eq(monsterMock), eq(graphics), eq(3));

        // For wall1 => type=2 => stoneWall
        verify(wallViewer).draw(eq(wall1), eq(graphics), eq(2));

        // For wall2 => type!=2 => we check fruit => 1 => triggers wallViewer with 3
        verify(wallViewer).draw(eq(wall2), eq(graphics), eq(3));

        // Also we see there's a monster=type2/UP => that replaces drawing with 9
        verify(wallViewer).draw(eq(wall2), eq(graphics), eq(9));

        // iceCream
        verify(iceCreamViewer).draw(eq(iceMock), eq(graphics), eq(1));

        // For hotFloor1 => fruit=3 => triggers hotFloorViewer with 23
        verify(hotFloorViewer).draw(eq(hotFloor1), eq(graphics), eq(23));

        // For hotFloor2 => same pos as iceCream => lastMovement=RIGHT => 27
        verify(hotFloorViewer).draw(eq(hotFloor2), eq(graphics), eq(27));
    }
}
