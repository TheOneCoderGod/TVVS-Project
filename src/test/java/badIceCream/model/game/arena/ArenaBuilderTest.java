package badIceCream.model.game.arena;

import badIceCream.model.game.elements.*;
import badIceCream.model.game.elements.fruits.Fruit;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
   Testing only the public createArena() method.
   No calls to private or reflection.
*/

class ArenaBuilderTest {

    @Test
    @DisplayName("createArena() sets fields properly using protected methods")
    void testCreateArena() {
        ArenaBuilder builder = new DummyArenaBuilder();
        Arena arena = builder.createArena();

        assertEquals(10, arena.getWidth());
        assertEquals(5, arena.getHeight());
        assertEquals(2, arena.getLevel());
        assertNotNull(arena.getIceCream());
        assertNotNull(arena.getMonsters());
        assertNotNull(arena.getWalls());
        assertNotNull(arena.getFruits());
        assertNotNull(arena.getHotFloors());
    }

    static class DummyArenaBuilder extends ArenaBuilder {
        @Override
        protected int getWidth() {return 10;}
        @Override
        protected int getHeight() {return 5;}
        @Override
        protected List<Wall> createWalls() {
            return List.of(mock(Wall.class));
        }
        @Override
        protected List<Monster> createMonsters() {
            return List.of(mock(Monster.class));
        }
        @Override
        protected IceCream createIceCream() {
            return mock(IceCream.class);
        }
        @Override
        protected List<Fruit> createFruits() {
            return List.of(mock(Fruit.class));
        }
        @Override
        protected List<HotFloor> createHotFloors() {
            return List.of(mock(HotFloor.class));
        }
        @Override
        protected int getLevel() {
            return 2;
        }
    }
}
