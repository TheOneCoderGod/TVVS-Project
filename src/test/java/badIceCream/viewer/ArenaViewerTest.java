package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.HotFloor;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.Wall;
import badIceCream.model.game.elements.fruits.Fruit;
import badIceCream.model.game.elements.monsters.Monster;
import badIceCream.GUI.GUI.ACTION;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

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
        arena = mock(Arena.class);
        fruitViewer = mock(FruitViewer.class);
        monsterViewer = mock(MonsterViewer.class);
        wallViewer = mock(WallViewer.class);
        hotFloorViewer = mock(HotFloorViewer.class);
        iceCreamViewer = mock(IceCreamViewer.class);

        viewer = new ArenaViewer(arena, fruitViewer, monsterViewer, wallViewer, hotFloorViewer, iceCreamViewer);
        graphics = mock(Graphics.class);
    }

    private Position pos(int x, int y) {
        return new Position(x, y);
    }



    @Test
    @DisplayName("Draw walls with different fruit cases")
    void testDrawElements_WallFruitCases() throws IOException {
        Wall wall = mock(Wall.class);
        Position wallPos = pos(10, 10);
        when(wall.getPosition()).thenReturn(wallPos);
        when(wall.getType()).thenReturn(1);

        when(arena.getWalls()).thenReturn(List.of(wall));
        when(arena.isFruit(wallPos)).thenReturn(2);
        when(arena.hasMonster(wallPos)).thenReturn(null);

        viewer.drawElements(graphics);

        verify(wallViewer).draw(wall, graphics, 4);
    }

    @Test
    @DisplayName("Draw walls with monster on wall")
    void testDrawElements_WallWithMonster() throws IOException {
        Wall wall = mock(Wall.class);
        Position wallPos = pos(11, 11);
        when(wall.getPosition()).thenReturn(wallPos);
        when(wall.getType()).thenReturn(1);

        Monster monster = mock(Monster.class);
        when(monster.getType()).thenReturn(2);
        when(monster.getLastAction()).thenReturn(ACTION.LEFT);

        when(arena.getWalls()).thenReturn(List.of(wall));
        when(arena.isFruit(wallPos)).thenReturn(-1);
        when(arena.hasMonster(wallPos)).thenReturn(monster);

        viewer.drawElements(graphics);

        verify(wallViewer).draw(wall, graphics, 1);
        verify(wallViewer).draw(wall, graphics, 11);
    }

    @Test
    @DisplayName("Draw hotFloor with monster type 1")
    void testDrawElements_HotFloorWithMonsterType1() throws IOException {
        HotFloor hotFloor = mock(HotFloor.class);
        Position pos = pos(12, 12);
        when(hotFloor.getPosition()).thenReturn(pos);

        Monster monster = mock(Monster.class);
        when(monster.getType()).thenReturn(1);
        when(monster.getLastAction()).thenReturn(ACTION.UP);
        when(monster.isRunning()).thenReturn(false);

        when(arena.getHotFloors()).thenReturn(List.of(hotFloor));
        when(arena.isFruit(pos)).thenReturn(-1);
        when(arena.hasMonster(pos)).thenReturn(monster);

        viewer.drawElements(graphics);

        verify(hotFloorViewer).draw(hotFloor, graphics, 1);
    }

    @Test
    @DisplayName("Draw hotFloor with iceCream on same position")
    void testDrawElements_HotFloorWithIceCream() throws IOException {
        HotFloor hotFloor = mock(HotFloor.class);
        Position pos = pos(13, 13);
        when(hotFloor.getPosition()).thenReturn(pos);

        IceCream iceCream = mock(IceCream.class);
        when(iceCream.getPosition()).thenReturn(pos);
        when(iceCream.getLastMovement()).thenReturn(ACTION.LEFT);

        when(arena.getHotFloors()).thenReturn(List.of(hotFloor));
        when(arena.isFruit(pos)).thenReturn(-1);
        when(arena.hasMonster(pos)).thenReturn(null);
        when(arena.getIceCream()).thenReturn(iceCream);

        viewer.drawElements(graphics);

        verify(hotFloorViewer).draw(hotFloor, graphics, 28);
    }

    @Test
    @DisplayName("Draw hotFloor with no conditions")
    void testDrawElements_HotFloorDefault() throws IOException {
        HotFloor hotFloor = mock(HotFloor.class);
        Position pos = pos(14, 14);
        when(hotFloor.getPosition()).thenReturn(pos);

        IceCream iceCream = mock(IceCream.class);
        when(iceCream.getPosition()).thenReturn(pos(15, 15));

        when(arena.getHotFloors()).thenReturn(List.of(hotFloor));
        when(arena.isFruit(pos)).thenReturn(-1);
        when(arena.hasMonster(pos)).thenReturn(null);
        when(arena.getIceCream()).thenReturn(iceCream);

        viewer.drawElements(graphics);

        verify(hotFloorViewer).draw(hotFloor, graphics, 0);
    }

    @Test
    @DisplayName("Draw iceCream")
    void testDrawElements_IceCream() throws IOException {
        IceCream iceCream = mock(IceCream.class);
        when(iceCream.getPosition()).thenReturn(pos(16, 16));
        when(iceCream.getLastMovement()).thenReturn(ACTION.DOWN);

        when(arena.getIceCream()).thenReturn(iceCream);
        when(arena.getWalls()).thenReturn(List.of());
        when(arena.getFruits()).thenReturn(List.of());
        when(arena.getMonsters()).thenReturn(List.of());
        when(arena.getHotFloors()).thenReturn(List.of());

        viewer.drawElements(graphics);

        verify(iceCreamViewer).draw(iceCream, graphics, 1);
    }
}
