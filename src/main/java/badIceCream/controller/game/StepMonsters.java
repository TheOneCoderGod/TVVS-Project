package badIceCream.controller.game;

import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.monsters.Monster;

import java.io.IOException;

public interface StepMonsters {
    void step(Monster monster, Arena arena, long time, long lastMovement) throws IOException;
    void moveMonster(Monster monster, Position position, Arena arena);
}
