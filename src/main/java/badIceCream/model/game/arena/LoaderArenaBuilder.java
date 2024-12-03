package badIceCream.model.game.arena;

import badIceCream.model.game.elements.*;
import badIceCream.model.game.elements.fruits.*;
import badIceCream.model.game.elements.monsters.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoaderArenaBuilder extends ArenaBuilder {
    private final List<String> lines;
    private final int level;

    public LoaderArenaBuilder(int level) throws IOException {
        this.level = level;

        String rootPath = new File(System.getProperty("user.dir")).getPath();
        String mapLocation = rootPath + "/src/main/resources/levels/level"  + level + ".lvl";

        BufferedReader br = Files.newBufferedReader(Paths.get(mapLocation), Charset.defaultCharset());

        lines = readLines(br);
    }

    private List<String> readLines(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();
        for (String line; (line = br.readLine()) != null; )
            lines.add(line);
        return lines;
    }

    @Override
    protected int getLevel() {
        return this.level;
    }

    @Override
    protected int getWidth() {
        int width = 0;
        for (String line : lines) width = Math.max(width, line.length());
        return width;
    }

    @Override
    protected int getHeight() {
        return lines.size();
    }

    @Override
    protected List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++)
                if (line.charAt(x) == 'G') walls.add(new StoneWall(x, y));
                else if (line.charAt(x) == 'F') walls.add(new IceWall(x,y));
        }

        return walls;
    }

    @Override
    protected List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++)
                if (line.charAt(x) == 'Y') monsters.add(new DefaultMonster(x, y));
                else if (line.charAt(x) == 'J') monsters.add(new JumperMonster(x, y));
                else if (line.charAt(x) == 'V') monsters.add(new RunnerMonster(x, y));
                else if (line.charAt(x) == 'W') monsters.add(new WallBreakerMonster(x, y));
        }
        return monsters;
    }

    @Override
    protected IceCream createIceCream() {
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++)
                if (line.charAt(x) == 'Z') return new IceCream(x, y);
        }
        return null;
    }

    @Override
    protected List<Fruit> createFruits() {
        List<Fruit> fruits = new ArrayList<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++)
                if (line.charAt(x) == 'M') fruits.add(new BananaFruit(x,y));
                else if (line.charAt(x) == 'Q') fruits.add(new StrawberryFruit(x,y));
                else if (line.charAt(x) == 'K') fruits.add(new CherryFruit(x,y));
                else if (line.charAt(x) == 'O') fruits.add(new PineappleFruit(x,y));
                else if (line.charAt(x) == 'T') fruits.add(new AppleFruit(x,y));
        }
        return fruits;
    }

    @Override
    protected List<HotFloor> createHotFloors() {
        List<HotFloor> hotFloors = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++)
                if (line.charAt(x) == 'E') hotFloors.add(new HotFloor(x,y));
        }
        return hotFloors;
    }
}
