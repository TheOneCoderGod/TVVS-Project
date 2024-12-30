package badIceCream.model.game.arena;

import badIceCream.model.game.elements.*;
import badIceCream.model.game.elements.fruits.*;
import badIceCream.model.game.elements.monsters.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/*
   Testing only public constructor and public createArena().
   No direct calls to private readLines(...).
*/

class LoaderArenaBuilderTest {

    @BeforeEach
    void setup() {}

    @Test
    @DisplayName("Constructor with valid level does not throw (no real file read tested here)")
    void testConstructor() throws IOException {
        try (MockedConstruction<BufferedReader> c = Mockito.mockConstruction(BufferedReader.class)) {
            LoaderArenaBuilder builder = new LoaderArenaBuilder(1);
            assertNotNull(builder);
        }
    }

    @Test
    @DisplayName("createArena() with stubbed lines => correct objects in Arena")
    void testCreateArena() throws IOException {
        LoaderArenaBuilder builder = Mockito.spy(new LoaderArenaBuilder(2));

        // We'll just override getWidth/getHeight so we don't rely on file lines
        Mockito.doReturn(5).when(builder).getWidth();
        Mockito.doReturn(3).when(builder).getHeight();

        Mockito.doReturn(List.of(new StoneWall(0,0))).when(builder).createWalls();
        Mockito.doReturn(List.of(new DefaultMonster(1,1))).when(builder).createMonsters();
        Mockito.doReturn(new IceCream(2,2)).when(builder).createIceCream();
        Mockito.doReturn(List.of(new BananaFruit(3,0))).when(builder).createFruits();
        Mockito.doReturn(List.of(new HotFloor(4,2))).when(builder).createHotFloors();

        Arena arena = builder.createArena();
        assertNotNull(arena);
        assertEquals(2, arena.getLevel());
        assertEquals(5, arena.getWidth());
        assertEquals(3, arena.getHeight());
        assertEquals(1, arena.getWalls().size());
        assertEquals(1, arena.getMonsters().size());
        assertEquals(1, arena.getFruits().size());
        assertEquals(1, arena.getHotFloors().size());
        assertNotNull(arena.getIceCream());
    }
}
