package badIceCream.controller.game.monsters;


import badIceCream.GUI.GUI;
import badIceCream.controller.game.StepMonsters;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.monsters.Monster;

import java.io.IOException;
import java.util.List;

public class RunnerMovementDisabled implements StepMonsters {
    @Override
    public void step(Monster monster, Arena arena, long time, long lastMovement) throws IOException {
        if (time - lastMovement >= 150) {
            Position pos = getPossible(monster, arena);
            if (pos != null) {
                monster.setLastAction(lastMove(monster.getPosition(), pos));
                moveMonster(monster, pos, arena);
            }
        }
    }

    private Position getPossible(Monster monster, Arena arena) {
        List<Position> options = new java.util.ArrayList<>(List.of(new Position[]{monster.getPosition().getDown(), monster.getPosition().getLeft(), monster.getPosition().getUp(), monster.getPosition().getRight()}));

        options.removeIf(pos -> !arena.isEmptyMonsters(pos));

        if (options.isEmpty()) return null;

        java.util.Random random = new java.util.Random();
        int randomIndex = random.nextInt(options.size());

        return options.get(randomIndex);
    }
    @Override
    public void moveMonster(Monster monster, Position position, Arena arena) {
        monster.setPosition(position);
        if (!arena.getIceCream().isStrawberryActive() && arena.getIceCream().getPosition().equals(position))
            arena.getIceCream().changeAlive();

    }

    private GUI.ACTION lastMove(Position previous, Position after) {
        if (previous.getY() == after.getY()) {
            if (previous.getX() > after.getX()) {
                return GUI.ACTION.LEFT;
            }
            else return GUI.ACTION.RIGHT;
        }

        if (previous.getX() == after.getX()) {
            if (previous.getY() > after.getY()) {
                return GUI.ACTION.UP;
            }
        }
        return GUI.ACTION.DOWN;
    }
}
