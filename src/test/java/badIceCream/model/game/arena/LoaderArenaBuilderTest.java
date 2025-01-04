package badIceCream.model.game.arena;

import badIceCream.model.game.elements.*;
import badIceCream.model.game.elements.fruits.*;
import badIceCream.model.game.elements.monsters.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the LoaderArenaBuilder class.
 * Ensures correct parsing of level files and accurate construction of game elements.
 */
class LoaderArenaBuilderTest {

    @TempDir
    Path tempDir;

    private Path levelsDir;
    private String originalUserDir;

    @BeforeEach
    void setUp() throws IOException {
        // Store the original user.dir to restore it later
        originalUserDir = System.getProperty("user.dir");

        // Set up the directory structure: src/main/resources/levels/
        levelsDir = tempDir.resolve("src/main/resources/levels");
        Files.createDirectories(levelsDir);

        // Override the user.dir system property to the temp directory
        System.setProperty("user.dir", tempDir.toString());
    }

    @AfterEach
    void tearDown() {
        // Restore the original user.dir system property
        System.setProperty("user.dir", originalUserDir);
    }

    /**
     * Helper method to create a level file with the given level number and content.
     *
     * @param level The level number.
     * @param lines The content of the level file.
     * @throws IOException If an I/O error occurs.
     */
    private void createLevelFile(int level, List<String> lines) throws IOException {
        Path levelFile = levelsDir.resolve("level" + level + ".lvl");
        try (BufferedWriter writer = Files.newBufferedWriter(levelFile, StandardCharsets.UTF_8)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    /**
     * Test the constructor of LoaderArenaBuilder with a valid level file.
     * Ensures that the builder initializes correctly without throwing exceptions.
     */
    @Test
    @DisplayName("Constructor with valid level initializes correctly")
    void testConstructorWithValidLevel() throws IOException {
        int level = 1;
        List<String> levelData = List.of(
                "GGG",
                "GZG",
                "GGG"
        );
        createLevelFile(level, levelData);

        LoaderArenaBuilder builder = new LoaderArenaBuilder(level);

        assertNotNull(builder, "LoaderArenaBuilder instance should not be null");
        assertEquals(level, builder.getLevel(), "Level should be correctly set");
        assertEquals(3, builder.getWidth(), "Width should be calculated correctly");
        assertEquals(3, builder.getHeight(), "Height should be calculated correctly");
    }

    /**
     * Test the constructor of LoaderArenaBuilder with a missing level file.
     * Expects an IOException to be thrown.
     */
    @Test
    @DisplayName("Constructor with missing level file throws IOException")
    void testConstructorWithMissingLevelFile() {
        int level = 2;
        // Do not create the level file

        assertThrows(IOException.class, () -> new LoaderArenaBuilder(level), "Expected IOException when level file is missing");
    }

    /**
     * Test the creation of Wall objects (StoneWall and IceWall).
     * Verifies correct instantiation and counts based on the level file.
     */
    @Test
    @DisplayName("createWalls() correctly creates StoneWall and IceWall objects")
    void testCreateWalls() throws IOException {
        int level = 3;
        List<String> levelData = List.of(
                "GFG",
                "FGF",
                "GFG"
        );
        createLevelFile(level, levelData);

        LoaderArenaBuilder builder = new LoaderArenaBuilder(level);
        List<Wall> walls = builder.createWalls();

        // Expect G and F walls: G is StoneWall, F is IceWall
        assertEquals(9, walls.size(), "Expected 9 Wall objects");

        // Count the types of walls
        long stoneWallCount = walls.stream().filter(wall -> wall instanceof StoneWall).count();
        long iceWallCount = walls.stream().filter(wall -> wall instanceof IceWall).count();

        assertEquals(5, stoneWallCount, "Expected 5 StoneWall objects");
        assertEquals(4, iceWallCount, "Expected 4 IceWall objects");
    }

    /**
     * Test the creation of Monster objects (DefaultMonster, JumperMonster, RunnerMonster, WallBreakerMonster).
     * Verifies correct instantiation and counts based on the level file.
     */
    @Test
    @DisplayName("createMonsters() correctly creates various Monster objects")
    void testCreateMonsters() throws IOException {
        int level = 4;
        List<String> levelData = List.of(
                "YJVW",
                "....",
                "WVJY"
        );
        createLevelFile(level, levelData);

        LoaderArenaBuilder builder = new LoaderArenaBuilder(level);
        List<Monster> monsters = builder.createMonsters();

        // Y, J, V, W in first and last lines
        // First line: Y, J, V, W
        // Last line: W, V, J, Y
        assertEquals(8, monsters.size(), "Expected 8 Monster objects");

        // Count the types of monsters
        long defaultMonsterCount = monsters.stream().filter(monster -> monster instanceof DefaultMonster).count();
        long jumperMonsterCount = monsters.stream().filter(monster -> monster instanceof JumperMonster).count();
        long runnerMonsterCount = monsters.stream().filter(monster -> monster instanceof RunnerMonster).count();
        long wallBreakerMonsterCount = monsters.stream().filter(monster -> monster instanceof WallBreakerMonster).count();

        assertEquals(2, defaultMonsterCount, "Expected 2 DefaultMonster objects");
        assertEquals(2, jumperMonsterCount, "Expected 2 JumperMonster objects");
        assertEquals(2, runnerMonsterCount, "Expected 2 RunnerMonster objects");
        assertEquals(2, wallBreakerMonsterCount, "Expected 2 WallBreakerMonster objects");
    }

    /**
     * Test the creation of the IceCream object when present in the level file.
     * Verifies correct instantiation.
     */
    @Test
    @DisplayName("createIceCream() correctly creates IceCream object when present")
    void testCreateIceCreamPresent() throws IOException {
        int level = 5;
        List<String> levelData = List.of(
                "GZG",
                "GGG",
                "GGG"
        );
        createLevelFile(level, levelData);

        LoaderArenaBuilder builder = new LoaderArenaBuilder(level);
        IceCream iceCream = builder.createIceCream();

        assertNotNull(iceCream, "IceCream should be present");
    }

    /**
     * Test the creation of the IceCream object when absent in the level file.
     * Expects the method to return null.
     */
    @Test
    @DisplayName("createIceCream() returns null when IceCream is absent")
    void testCreateIceCreamAbsent() throws IOException {
        int level = 6;
        List<String> levelData = List.of(
                "GGG",
                "GGG",
                "GGG"
        );
        createLevelFile(level, levelData);

        LoaderArenaBuilder builder = new LoaderArenaBuilder(level);
        IceCream iceCream = builder.createIceCream();

        assertNull(iceCream, "IceCream should be absent");
    }

    /**
     * Test the creation of Fruit objects (BananaFruit, StrawberryFruit, CherryFruit, PineappleFruit, AppleFruit).
     * Verifies correct instantiation and counts based on the level file.
     */
    @Test
    @DisplayName("createFruits() correctly creates various Fruit objects")
    void testCreateFruits() throws IOException {
        int level = 7;
        List<String> levelData = List.of(
                "M.Q.K",
                "O.T.M",
                "Q.K.O",
                "T.M.Q",
                "K.O.T"
        );
        createLevelFile(level, levelData);

        LoaderArenaBuilder builder = new LoaderArenaBuilder(level);
        List<Fruit> fruits = builder.createFruits();

        // Each line has 3 fruits at positions 0, 2, 4
        // Total fruits: 15
        assertEquals(15, fruits.size(), "Expected 15 Fruit objects");

        // Count the types of fruits
        long bananaFruitCount = fruits.stream().filter(fruit -> fruit instanceof BananaFruit).count();
        long strawberryFruitCount = fruits.stream().filter(fruit -> fruit instanceof StrawberryFruit).count();
        long cherryFruitCount = fruits.stream().filter(fruit -> fruit instanceof CherryFruit).count();
        long pineappleFruitCount = fruits.stream().filter(fruit -> fruit instanceof PineappleFruit).count();
        long appleFruitCount = fruits.stream().filter(fruit -> fruit instanceof AppleFruit).count();

        assertEquals(3, bananaFruitCount, "Expected 3 BananaFruit objects");
        assertEquals(3, strawberryFruitCount, "Expected 3 StrawberryFruit objects");
        assertEquals(3, cherryFruitCount, "Expected 3 CherryFruit objects");
        assertEquals(3, pineappleFruitCount, "Expected 3 PineappleFruit objects");
        assertEquals(3, appleFruitCount, "Expected 3 AppleFruit objects");
    }

    /**
     * Test the creation of HotFloor objects.
     * Verifies correct instantiation and counts based on the level file.
     */
    @Test
    @DisplayName("createHotFloors() correctly creates HotFloor objects")
    void testCreateHotFloors() throws IOException {
        int level = 8;
        List<String> levelData = List.of(
                "E.E",
                ".E.",
                "E.E"
        );
        createLevelFile(level, levelData);

        LoaderArenaBuilder builder = new LoaderArenaBuilder(level);
        List<HotFloor> hotFloors = builder.createHotFloors();

        // Expected HotFloor positions: (0,0), (2,0), (1,1), (0,2), (2,2)
        assertEquals(5, hotFloors.size(), "Expected 5 HotFloor objects");
    }

    /**
     * Test the getWidth() and getHeight() methods with varying line lengths.
     * Ensures that the width is determined by the longest line and height by the number of lines.
     */
    @Test
    @DisplayName("getWidth() and getHeight() correctly identify arena dimensions")
    void testGetWidthAndHeightWithDifferentLineLengths() throws IOException {
        int level = 9;
        List<String> levelData = List.of(
                "G",
                "GGG",
                "GGGGG",
                "GGGGGGG"
        );
        createLevelFile(level, levelData);

        LoaderArenaBuilder builder = new LoaderArenaBuilder(level);
        assertEquals(7, builder.getWidth(), "Width should be the length of the longest line (7)");
        assertEquals(4, builder.getHeight(), "Height should be the number of lines (4)");
    }
}
