package badIceCream.model.game.arena;

import badIceCream.GUI.GUI;
import badIceCream.audio.AudioController;
import badIceCream.model.Position;
import badIceCream.model.game.elements.*;
import badIceCream.model.game.elements.fruits.*;
import badIceCream.model.game.elements.monsters.Monster;

import java.util.List;
import java.util.Random;

public class Arena {
    private final int width;
    private final int height;
    private int level;
    private IceCream iceCream;
    private List<Monster> monsters;
    private List<Wall> walls;
    private List<Fruit> fruits;
    private List<HotFloor> hotFloors;

    public Arena(int width, int height){
        this.width = width;
        this.height = height;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public IceCream getIceCream() {
        return iceCream;
    }
    public List<Monster> getMonsters() {
        return monsters;
    }
    public List<Wall> getWalls() {
        return walls;
    }
    public List<Fruit> getFruits(){return fruits;}
    public void setIceCream(IceCream iceCream) {
        this.iceCream = iceCream;
    }

    public List<HotFloor> getHotFloors() {
        return hotFloors;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }
    public void setFruits(List<Fruit> fruits) {
        this.fruits = fruits;
    }
    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }
    public void setHotFloors(List<HotFloor> hotFloors) {
        this.hotFloors = hotFloors;
    }

    public boolean isEmpty(Position position) {
        for (Wall wall : walls)
            if (wall.getPosition().equals(position))
                return false;
        return true;
    }

    public synchronized boolean isEmptyMonsters(Position position) {
        for (Wall wall : walls)
            if (wall.getPosition().equals(position))
                return false;
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public synchronized boolean isEmptyNoStoneWall(Position position) {
        for (Wall wall : walls)
            if (wall instanceof StoneWall && wall.getPosition().equals(position))
                return false;
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmptySpawnFruit(Position position) {
        for (Wall wall : walls) {
            if (wall instanceof StoneWall && wall.getPosition().equals(position))
                return false;
        }

        for (Fruit fruit : fruits) {
            if (fruit.getPosition().equals(position)) {return false;}
        }
        return true;
    }

    public boolean isHotFloor(Position position) {
        for(HotFloor hotFloor : hotFloors) {
            if (position.equals(hotFloor.getPosition()))
                return true;
        }
        return false;
    }

    public void iceWallDestroyed(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                walls.remove(wall);
                return;
            }
        }
    }
    public boolean isIceWall(Position position) {
        for (Wall wall : walls) {
            if (wall instanceof IceWall && wall.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }
    public Monster hasMonster(Position position) {
        for (Monster monster : monsters)
            if (monster.getPosition().equals(position))
                return monster;
        return null;
    }

    public int isFruit(Position position) {
        for (Fruit fruit : fruits) {
            if (fruit.getPosition().equals(position))
                return fruit.getType();
        }
        return -1;
    }
    public int eatFruit(Position position) {
        for (Fruit f : fruits) {
            if (f.getPosition().equals(position)) {
                int type = f.getType();
                fruits.remove(f);
                return type;
            }
        }
        return -1;
    }

    public void powerIceCream(GUI.ACTION lastMovement) {
        switch (lastMovement) {
            case UP: {
                if (isIceWall(iceCream.getPosition().getUp())) {
                    destroyIceWall(0, -1);
                }
                else {
                    constroyIceWall(0, -1);
                }
                break;
            }
            case DOWN: {
                if (isIceWall(iceCream.getPosition().getDown())) {
                    destroyIceWall(0, 1);
                }
                else {
                    constroyIceWall(0, 1);
                }
                break;
            }
            case RIGHT: {
                if (isIceWall(iceCream.getPosition().getRight())) {
                    destroyIceWall(1, 0);
                }
                else {
                    constroyIceWall(1, 0);
                }
                break;
            }
            case LEFT: {
                if (isIceWall(iceCream.getPosition().getLeft())) {
                    destroyIceWall(-1, 0);
                }
                else {
                    constroyIceWall(-1, 0);
                }
                break;
            }
            default:
        }
    }

    private void destroyIceWall(int deltaX, int deltaY) {
        boolean first = true;
        Position pos = new Position(iceCream.getPosition().getX() + deltaX, iceCream.getPosition().getY() + deltaY);

        while (isIceWall(pos)) {
            if (first) {
                AudioController.playBreakWallSound();
                first = false;
            }
            iceWallDestroyed(pos);
            pos.setX(pos.getX() + deltaX);
            pos.setY(pos.getY() + deltaY);
        }
    }

    private void createIceWall(Position pos) {
        IceWall newIceWall = new IceWall(pos.getX(), pos.getY());
        walls.add(newIceWall);
    }

    private void constroyIceWall(int deltaX, int deltaY) {
        boolean first = true;
        Position pos = new Position(iceCream.getPosition().getX() + deltaX, iceCream.getPosition().getY() + deltaY);

        while (isEmptyMonsters(pos) && !isHotFloor(pos)) {
            if (first) {
                AudioController.playBuildWallSound();
                first = false;
            }
            createIceWall(pos);
            pos.setX(pos.getX() + deltaX);
            pos.setY(pos.getY() + deltaY);
        }
    }

    private Position generateRandomPosition() {
        Random rand = new Random();
        int upperWidth = width;
        int upperHeight = height;

        Position pos = new Position(rand.nextInt(upperWidth), rand.nextInt(upperHeight));
        while (!isEmptySpawnFruit(pos)) {
            pos.setX(rand.nextInt(upperWidth - 1));
            pos.setY(rand.nextInt(upperHeight - 1));
        }
        return pos;
    }
    public void generateNewFruits(int level) {
        switch (level) {
            case 1:
                for (int i = 0; i < 6; i++) {
                    Position nextPos = generateRandomPosition();
                    fruits.add(new AppleFruit(nextPos.getX(), nextPos.getY()));
                }
                break;

            case 2:
                for (int i = 0; i < 8; i++) {
                    Position nextPos = generateRandomPosition();
                    fruits.add(new CherryFruit(nextPos.getX(), nextPos.getY()));
                }
                break;

            case 3:
                for (int i = 0; i < 10; i++) {
                    Position nextPos = generateRandomPosition();
                    fruits.add(new PineappleFruit(nextPos.getX(), nextPos.getY()));
                }
                break;

            case 4:
                for (int i = 0; i < 12; i++) {
                    Position nextPos = generateRandomPosition();
                    fruits.add(new BananaFruit(nextPos.getX(), nextPos.getY()));
                }
                break;
            case 5:
                for (int i = 0; i < 14; i++) {
                    Position nextPos = generateRandomPosition();
                    fruits.add(new AppleFruit(nextPos.getX(), nextPos.getY()));
                }
                break;
        }
    }
}
