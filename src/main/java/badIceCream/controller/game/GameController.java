package badIceCream.controller.game;

import badIceCream.controller.Controller;
import badIceCream.model.game.arena.Arena;

public abstract class GameController extends Controller<Arena> {
    public GameController(Arena arena) {
        super(arena);
    }
}
