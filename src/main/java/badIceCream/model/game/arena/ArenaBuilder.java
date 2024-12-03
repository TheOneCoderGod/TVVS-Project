package badIceCream.model.game.arena;

import badIceCream.model.game.elements.HotFloor;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.Wall;
import badIceCream.model.game.elements.fruits.Fruit;
import badIceCream.model.game.elements.monsters.Monster;

import java.util.List;

public abstract class ArenaBuilder {
    public Arena createArena() {
    Arena arena = new Arena(getWidth(), getHeight());

    arena.setIceCream(createIceCream());
    arena.setMonsters(createMonsters());
    arena.setWalls(createWalls());
    arena.setFruits(createFruits());
    arena.setHotFloors(createHotFloors());
    arena.setLevel(getLevel());

    return arena;
}

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract List<Wall> createWalls();

    protected abstract List<Monster> createMonsters();

    protected abstract IceCream createIceCream();
    protected abstract List<Fruit> createFruits();
    protected abstract List<HotFloor> createHotFloors();

    protected abstract int getLevel();
}
