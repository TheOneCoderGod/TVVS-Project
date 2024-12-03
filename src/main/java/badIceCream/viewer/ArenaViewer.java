package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.HotFloor;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.Wall;
import badIceCream.model.game.elements.fruits.Fruit;
import badIceCream.model.game.elements.monsters.Monster;

import java.util.ArrayList;
import java.util.List;

public class ArenaViewer extends Viewer<Arena> {
    private final FruitViewer fruitViewer;
    private final MonsterViewer monsterViewer;
    private final WallViewer wallViewer;
    private  final HotFloorViewer hotFloorViewer;
    private  final IceCreamViewer iceCreamViewer;
    public ArenaViewer(Arena arena, FruitViewer fruitViewer, MonsterViewer monsterViewer, WallViewer wallViewer, HotFloorViewer hotFloorViewer, IceCreamViewer iceCreamViewer){
        super(arena);
        this.fruitViewer = fruitViewer;
        this.monsterViewer = monsterViewer;
        this.wallViewer = wallViewer;
        this.hotFloorViewer = hotFloorViewer;
        this.iceCreamViewer = iceCreamViewer;
    }
    @Override
    public synchronized void drawElements(Graphics gui) {
        List<Wall> walls = new ArrayList<>(model.getWalls());

        for (Fruit fruit : model.getFruits()){
            fruitViewer.draw(fruit, gui, fruit.getType());
        }

        for(Monster monster : model.getMonsters()){
            monsterViewer.draw(monster, gui, monster.getType());
        }

        for (Wall wall : walls) {
            int type = wall.getType();
            if (type == 2) {
                wallViewer.draw(wall, gui, type);
            }
            else {
                int fruit = model.isFruit(wall.getPosition());
                switch (fruit) {
                    case 1: wallViewer.draw(wall, gui, 3);
                        break;
                    case 2: wallViewer.draw(wall, gui, 4);
                        break;
                    case 3: wallViewer.draw(wall, gui, 5);
                        break;
                    case 4: wallViewer.draw(wall, gui, 6);
                        break;
                    case 5: wallViewer.draw(wall, gui, 7);
                        break;
                    default: wallViewer.draw(wall, gui, 1);
                }

                Monster monster = model.hasMonster(wall.getPosition());
                if (monster != null) {
                    if (monster.getType() == 2) {
                        switch (monster.getLastAction()) {
                            case UP: wallViewer.draw(wall, gui, 9);
                                break;
                            case RIGHT: wallViewer.draw(wall, gui, 10);
                                break;
                            case LEFT: wallViewer.draw(wall, gui, 11);
                                break;
                            default: wallViewer.draw(wall, gui, 8);
                        }
                    }
                }
            }
        }

        iceCreamViewer.draw(getModel().getIceCream(), gui, 1);

        for(HotFloor hotFloor : model.getHotFloors()){
            int fruit = model.isFruit(hotFloor.getPosition());
            IceCream iceCream = model.getIceCream();

            Monster monster = model.hasMonster(hotFloor.getPosition());
            if (monster != null) {
                switch (monster.getType()) {
                    case 1: switch (monster.getLastAction()) {
                        case UP: hotFloorViewer.draw(hotFloor, gui, 1);
                            break;
                        case RIGHT: hotFloorViewer.draw(hotFloor, gui, 2);
                            break;
                        case LEFT: hotFloorViewer.draw(hotFloor, gui, 3);
                            break;
                        default: hotFloorViewer.draw(hotFloor, gui, 4);
                    }
                        break;
                    case 2: switch (monster.getLastAction()) {
                        case UP: hotFloorViewer.draw(hotFloor, gui, 5);
                            break;
                        case RIGHT: hotFloorViewer.draw(hotFloor, gui, 6);
                            break;
                        case LEFT: hotFloorViewer.draw(hotFloor, gui, 7);
                            break;
                        default: hotFloorViewer.draw(hotFloor, gui, 8);
                    }
                        break;
                    case 3: {
                        if (!monster.isRunning()) {
                            switch (monster.getLastAction()) {
                                case UP:
                                    hotFloorViewer.draw(hotFloor, gui, 9);
                                    break;
                                case RIGHT:
                                    hotFloorViewer.draw(hotFloor, gui, 10);
                                    break;
                                case LEFT:
                                    hotFloorViewer.draw(hotFloor, gui, 11);
                                    break;
                                default:
                                    hotFloorViewer.draw(hotFloor, gui, 12);
                            }
                        }
                        else {
                            switch (monster.getLastAction()) {
                                case UP:
                                    hotFloorViewer.draw(hotFloor, gui, 13);
                                    break;
                                case RIGHT:
                                    hotFloorViewer.draw(hotFloor, gui, 14);
                                    break;
                                case LEFT:
                                    hotFloorViewer.draw(hotFloor, gui, 15);
                                    break;
                                default:
                                    hotFloorViewer.draw(hotFloor, gui, 16);
                            }
                        }
                    }
                    break;
                    case 4: switch (monster.getLastAction()) {
                        case UP: hotFloorViewer.draw(hotFloor, gui, 17);
                            break;
                        case RIGHT: hotFloorViewer.draw(hotFloor, gui, 18);
                            break;
                        case LEFT: hotFloorViewer.draw(hotFloor, gui, 19);
                            break;
                        default: hotFloorViewer.draw(hotFloor, gui, 20);
                    }
                }
            }
            else if (fruit != -1) {
                switch (fruit) {
                    case 1: hotFloorViewer.draw(hotFloor, gui, 21);
                        break;
                    case 2: hotFloorViewer.draw(hotFloor, gui, 22);
                        break;
                    case 3: hotFloorViewer.draw(hotFloor, gui, 23);
                        break;
                    case 4: hotFloorViewer.draw(hotFloor, gui, 24);
                        break;
                    case 5: hotFloorViewer.draw(hotFloor, gui, 25);
                        break;
                }
            }
            else if (iceCream.getPosition().equals(hotFloor.getPosition())) {
                switch (iceCream.getLastMovement()) {
                    case UP: hotFloorViewer.draw(hotFloor, gui, 26);
                        break;
                    case RIGHT: hotFloorViewer.draw(hotFloor, gui, 27);
                        break;
                    case LEFT: hotFloorViewer.draw(hotFloor, gui, 28);
                        break;
                    default: hotFloorViewer.draw(hotFloor, gui, 29);
                }
            }
            else {hotFloorViewer.draw(hotFloor, gui, 0);}
        }
    }
}
