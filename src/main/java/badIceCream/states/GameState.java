package badIceCream.states;

import badIceCream.controller.game.ArenaController;
import badIceCream.controller.game.IceCreamController;
import badIceCream.model.game.arena.Arena;
import badIceCream.viewer.*;

import java.util.ArrayList;

public class GameState extends State<Arena> {
    public GameState(Arena arena, int level) {
        super(arena, new ArenaController(arena, new IceCreamController(arena), new ArrayList<>()), new ArenaViewer(arena, new FruitViewer(), new MonsterViewer(), new WallViewer(), new HotFloorViewer(), new IceCreamViewer()), level);
    }
    public GameState(Arena arena, ArenaController controller, ArenaViewer viewer , int level){
        super(arena, controller, viewer, level);
    }
}