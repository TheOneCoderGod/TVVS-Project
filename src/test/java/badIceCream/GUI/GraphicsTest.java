package badIceCream.GUI;

import badIceCream.model.Position;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Comprehensive test class for the Graphics class, addressing all mutations.
 */
class GraphicsTest {

    private GUI gui;
    private Screen screen;
    private TextGraphics textGraphics;
    private Graphics graphics;

    @BeforeEach
    void setUp() throws IOException {
        // Mocks
        gui = mock(GUI.class);
        screen = mock(Screen.class);
        textGraphics = mock(TextGraphics.class);

        // GUI: createTerminal() => mock Terminal
        Terminal terminal = mock(Terminal.class);
        when(gui.createTerminal()).thenReturn(terminal);

        // GUI: createScreen(Terminal) => mock Screen
        when(gui.createScreen(terminal)).thenReturn(screen);

        // screen.newTextGraphics() => mock TextGraphics
        when(screen.newTextGraphics()).thenReturn(textGraphics);

        // Instantiate the Graphics object
        graphics = new Graphics(gui);
    }

    @Test
    @DisplayName("Constructor should instantiate and not be null")
    void testConstructor() {
        assertNotNull(graphics, "Graphics should be instantiated");
        assertEquals(gui, graphics.getGui(), "GUI should be set correctly in constructor");
    }

    @Test
    @DisplayName("setGui() and getGui() should update/return the GUI instance")
    void testSetGetGui() {
        GUI newGui = mock(GUI.class);
        graphics.setGui(newGui);
        assertEquals(newGui, graphics.getGui(), "getGui() should return the newly set GUI");
    }

    // -------------------------------------------------------------------
    // drawIceCream(...) tests
    // -------------------------------------------------------------------
    @Nested
    @DisplayName("drawIceCream(...) tests")
    class DrawIceCreamTests {

        @Test
        @DisplayName("UP + strawberry=true => char='7', color='#48DEFF'")
        void testDrawIceCreamUpStrawberry() {
            Position position = new Position(10, 5);

            graphics.drawIceCream(position, GUI.ACTION.UP, true);

            verifyCharacterDrawn(10, 5, '7', "#48DEFF");
        }

        @Test
        @DisplayName("LEFT + strawberry=false => char=':', color='#FFFFFF'")
        void testDrawIceCreamLeftNoStrawberry() {
            Position position = new Position(4, 8);

            graphics.drawIceCream(position, GUI.ACTION.LEFT, false);

            verifyCharacterDrawn(4, 8, ':', "#FFFFFF");
        }

        @Test
        @DisplayName("RIGHT + strawberry=true => char='9', color='#48DEFF'")
        void testDrawIceCreamRightStrawberry() {
            Position position = new Position(7, 12);

            graphics.drawIceCream(position, GUI.ACTION.RIGHT, true);

            verifyCharacterDrawn(7, 12, '9', "#48DEFF");
        }

        @Test
        @DisplayName("Default action + strawberry=false => char='8', color='#FFFFFF'")
        void testDrawIceCreamDefaultNoStrawberry() {
            Position position = new Position(2, 3);

            graphics.drawIceCream(position, GUI.ACTION.DOWN, false);

            verifyCharacterDrawn(2, 3, '8', "#FFFFFF");
        }
    }

    // -------------------------------------------------------------------
    // drawStoneWall(...) test
    // -------------------------------------------------------------------
    @Test
    @DisplayName("drawStoneWall => char='G', color='#696969'")
    void testDrawStoneWall() {
        Position position = new Position(0, 0);
        graphics.drawStoneWall(position);

        verifyCharacterDrawn(0, 0, 'G', "#696969");
    }

    // -------------------------------------------------------------------
    // drawIceWall(...) tests
    // -------------------------------------------------------------------
    @Nested
    @DisplayName("drawIceWall(...) tests")
    class DrawIceWallTests {

        @Test
        @DisplayName("type=1 => char='F', color='#87CEFA'")
        void testDrawIceWallType1() {
            Position pos = new Position(5, 5);
            graphics.drawIceWall(pos, 1);

            verifyCharacterDrawn(5, 5, 'F', "#87CEFA");
        }

        @Test
        @DisplayName("type=9 => char='l', color='#87CEFA'")
        void testDrawIceWallType9() {
            Position pos = new Position(6, 10);
            graphics.drawIceWall(pos, 9);

            verifyCharacterDrawn(6, 10, 'l', "#87CEFA");
        }

        @Test
        @DisplayName("type=15 => no drawing (invalid type)")
        void testDrawIceWallType15() {
            Position pos = new Position(8, 14);
            graphics.drawIceWall(pos, 15);

            // Verify that no drawing is done for invalid type
            verify(textGraphics, never()).setForegroundColor(any());
            verify(textGraphics, never()).putString(anyInt(), anyInt(), anyString());
        }

        @Test
        @DisplayName("type=28 => no drawing (invalid type)")
        void testDrawIceWallType28() {
            Position pos = new Position(9, 16);
            graphics.drawIceWall(pos, 28);

            // Verify that no drawing is done for invalid type
            verify(textGraphics, never()).setForegroundColor(any());
            verify(textGraphics, never()).putString(anyInt(), anyInt(), anyString());
        }

        @Test
        @DisplayName("Invalid type => no drawing")
        void testDrawIceWallInvalidType() {
            Position pos = new Position(1, 1);
            graphics.drawIceWall(pos, 0);

            // no calls to putString
            verify(textGraphics, never()).setForegroundColor(any());
            verify(textGraphics, never()).putString(anyInt(), anyInt(), anyString());
        }
    }

    // -------------------------------------------------------------------
    // drawDefaultMonster(...) test
    // -------------------------------------------------------------------
    @Test
    @DisplayName("drawDefaultMonster => appropriate char and color based on action")
    void testDrawDefaultMonster() {
        Position position = new Position(10, 10);
        graphics.drawDefaultMonster(position, GUI.ACTION.UP);

        verifyCharacterDrawn(10, 10, '4', "#00FF00");
    }

    // -------------------------------------------------------------------
    // drawJumperMonster(...) test
    // -------------------------------------------------------------------
    @Test
    @DisplayName("drawJumperMonster => appropriate char and color based on action")
    void testDrawJumperMonster() {
        Position position = new Position(5, 5);
        graphics.drawJumperMonster(position, GUI.ACTION.LEFT);

        verifyCharacterDrawn(5, 5, 'y', "#FF3333");
    }

    // -------------------------------------------------------------------
    // drawRunnerMonster(...) tests
    // -------------------------------------------------------------------
    @Nested
    @DisplayName("drawRunnerMonster(...) tests")
    class DrawRunnerMonsterTests {

        @Test
        @DisplayName("runner=true, action=UP => char='3', color='#FF0000'")
        void testDrawRunnerMonsterRunnerUp() {
            Position position = new Position(7, 7);
            graphics.drawRunnerMonster(position, GUI.ACTION.UP, true);

            verifyCharacterDrawn(7, 7, '3', "#FF0000");
        }

        @Test
        @DisplayName("runner=false, action=LEFT => char='W', color='#FFFF66'")
        void testDrawRunnerMonsterNonRunnerLeft() {
            Position position = new Position(8, 8);
            graphics.drawRunnerMonster(position, GUI.ACTION.LEFT, false);

            verifyCharacterDrawn(8, 8, 'W', "#FFFF66");
        }

        @Test
        @DisplayName("runner=true, action=RIGHT => char='}', color='#FF0000'")
        void testDrawRunnerMonsterRunnerRight() {
            Position position = new Position(9, 9);
            graphics.drawRunnerMonster(position, GUI.ACTION.RIGHT, true);

            verifyCharacterDrawn(9, 9, '}', "#FF0000");
        }

        @Test
        @DisplayName("runner=false, default action => char='V', color='#FFFF66'")
        void testDrawRunnerMonsterNonRunnerDefault() {
            Position position = new Position(10, 10);
            graphics.drawRunnerMonster(position, GUI.ACTION.DOWN, false);

            verifyCharacterDrawn(10, 10, 'V', "#FFFF66");
        }
    }

    // -------------------------------------------------------------------
    // drawWallBreakerMonster(...) test
    // -------------------------------------------------------------------
    @Test
    @DisplayName("drawWallBreakerMonster => appropriate char and color based on action")
    void testDrawWallBreakerMonster() {
        Position position = new Position(3, 3);
        graphics.drawWallBreakerMonster(position, GUI.ACTION.SELECT);

        verifyCharacterDrawn(3, 3, 'U', "#FF99FF");
    }

    // -------------------------------------------------------------------
    // drawAppleFruit(...) test
    // -------------------------------------------------------------------
    @Test
    @DisplayName("drawAppleFruit => char=']', color='#FF0000'")
    void testDrawAppleFruit() {
        Position position = new Position(4, 4);
        graphics.drawAppleFruit(position);

        verifyCharacterDrawn(4, 4, ']', "#FF0000");
    }

    // -------------------------------------------------------------------
    // drawBananaFruit(...) test
    // -------------------------------------------------------------------
    @Test
    @DisplayName("drawBananaFruit => char='a', color='#FFFF00'")
    void testDrawBananaFruit() {
        Position position = new Position(5, 5);
        graphics.drawBananaFruit(position);

        verifyCharacterDrawn(5, 5, 'a', "#FFFF00");
    }

    // -------------------------------------------------------------------
    // drawPineappleFruit(...) test
    // -------------------------------------------------------------------
    @Test
    @DisplayName("drawPineappleFruit => char='^', color='#FFFF66'")
    void testDrawPineappleFruit() {
        Position position = new Position(6, 6);
        graphics.drawPineappleFruit(position);

        verifyCharacterDrawn(6, 6, '^', "#FFFF66");
    }

    // -------------------------------------------------------------------
    // drawCherryFruit(...) test
    // -------------------------------------------------------------------
    @Test
    @DisplayName("drawCherryFruit => char='\\', color='#FF0000'")
    void testDrawCherryFruit() {
        Position position = new Position(7, 7);
        graphics.drawCherryFruit(position);

        verifyCharacterDrawn(7, 7, '\\', "#FF0000");
    }

    // -------------------------------------------------------------------
    // drawStrawberryFruit(...) test
    // -------------------------------------------------------------------
    @Test
    @DisplayName("drawStrawberryFruit => char='_', color='#FF0000'")
    void testDrawStrawberryFruit() {
        Position position = new Position(8, 8);
        graphics.drawStrawberryFruit(position);

        verifyCharacterDrawn(8, 8, '_', "#FF0000");
    }

    // -------------------------------------------------------------------
    // drawHotFloor(...) tests
    // -------------------------------------------------------------------
    @Nested
    @DisplayName("drawHotFloor(...) tests")
    class DrawHotFloorTests {

        @Test
        @DisplayName("type=1 => char='w', color='#e14750'")
        void testDrawHotFloorType1() {
            Position position = new Position(9, 9);
            graphics.drawHotFloor(position, 1);

            verifyCharacterDrawn(9, 9, 'w', "#e14750");
        }

        @Test
        @DisplayName("type=10 => char='+', color='#e14750'")
        void testDrawHotFloorType10() {
            Position position = new Position(10, 10);
            graphics.drawHotFloor(position, 10);

            verifyCharacterDrawn(10, 10, '+', "#e14750");
        }

        @Test
        @DisplayName("type=28 => char='>', color='#e14750'")
        void testDrawHotFloorType28() {
            Position position = new Position(11, 11);
            graphics.drawHotFloor(position, 28);

            verifyCharacterDrawn(11, 11, '>', "#e14750");
        }

        @Test
        @DisplayName("Invalid type => char='b', color='#e14750'")
        void testDrawHotFloorInvalidType() {
            Position position = new Position(12, 12);
            graphics.drawHotFloor(position, 99);

            verifyCharacterDrawn(12, 12, 'b', "#e14750");
        }
    }

    // -------------------------------------------------------------------
    // drawCharacters() test
    // -------------------------------------------------------------------
    @Test
    @DisplayName("drawCharacters() draws 6 special chars in green at specific positions")
    void testDrawCharacters() {
        /*
         * drawCharacters() calls:
         *   drawCharacter(33, 15, 'Ê', "#00FF00")
         *   drawCharacter(33, 18, 'À', "#00FF00")
         *   drawCharacter(33, 21, 'Á', "#00FF00")
         *   drawCharacter(33, 24, 'È', "#00FF00")
         *   drawCharacter(33, 27, 'É', "#00FF00")
         *   drawCharacter(33, 30, 'Í', "#00FF00")
         */

        graphics.drawCharacters();

        // 1) The code calls setForegroundColor("#00FF00") 6 times
        verify(textGraphics, times(6))
                .setForegroundColor(TextColor.Factory.fromString("#00FF00"));

        // 2) The code calls putString(...) 6 times with different positions/characters
        // Let's capture them all
        ArgumentCaptor<Integer> xCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> yCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> strCaptor = ArgumentCaptor.forClass(String.class);

        verify(textGraphics, times(6))
                .putString(xCaptor.capture(), yCaptor.capture(), strCaptor.capture());

        List<Integer> xs = xCaptor.getAllValues();
        List<Integer> ys = yCaptor.getAllValues();
        List<String> strs = strCaptor.getAllValues();

        assertEquals(6, xs.size(), "Should draw 6 characters (X positions)");
        assertEquals(6, ys.size(), "Should draw 6 characters (Y positions)");
        assertEquals(6, strs.size(), "Should draw 6 strings total");

        // Check each call
        assertEquals(33, xs.get(0));
        assertEquals(15, ys.get(0));
        assertEquals("Ê", strs.get(0));

        assertEquals(33, xs.get(1));
        assertEquals(18, ys.get(1));
        assertEquals("À", strs.get(1));

        assertEquals(33, xs.get(2));
        assertEquals(21, ys.get(2));
        assertEquals("Á", strs.get(2));

        assertEquals(33, xs.get(3));
        assertEquals(24, ys.get(3));
        assertEquals("È", strs.get(3));

        assertEquals(33, xs.get(4));
        assertEquals(27, ys.get(4));
        assertEquals("É", strs.get(4));

        assertEquals(33, xs.get(5));
        assertEquals(30, ys.get(5));
        assertEquals("Í", strs.get(5));
    }

    // -------------------------------------------------------------------
    // clear(), refresh(), close() tests
    // -------------------------------------------------------------------
    @Test
    @DisplayName("clear() should call screen.clear()")
    void testClear() {
        graphics.clear();
        verify(screen).clear();
    }

    @Test
    @DisplayName("refresh() should call screen.refresh()")
    void testRefresh() throws IOException {
        graphics.refresh();
        verify(screen).refresh();
    }

    @Test
    @DisplayName("close() should call screen.close()")
    void testClose() throws IOException {
        graphics.close();
        verify(screen).close();
    }

    // -------------------------------------------------------------------
    // drawText(...) test
    // -------------------------------------------------------------------
    @Test
    @DisplayName("drawText() should draw text at specified position with color")
    void testDrawText() {
        Position position = new Position(20, 20);
        String text = "Hello";
        String color = "#123456";

        graphics.drawText(position, text, color);

        verify(textGraphics).setForegroundColor(TextColor.Factory.fromString(color));
        verify(textGraphics).putString(20, 20, text);
    }

    // -------------------------------------------------------------------
    // getNextAction() tests
    // -------------------------------------------------------------------
    @Nested
    @DisplayName("getNextAction() tests")
    class GetNextActionTests {
        @Test
        @DisplayName("Null KeyStroke => ACTION.NONE")
        void testNullKeyStroke() throws IOException {
            when(screen.pollInput()).thenReturn(null);

            GUI.ACTION action = graphics.getNextAction();
            assertEquals(GUI.ACTION.NONE, action);
        }

        @Test
        @DisplayName("ArrowDown => ACTION.DOWN")
        void testArrowDown() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.ArrowDown);
            when(screen.pollInput()).thenReturn(ks);

            GUI.ACTION action = graphics.getNextAction();
            assertEquals(GUI.ACTION.DOWN, action);
        }

        @Test
        @DisplayName("ArrowUp => ACTION.UP")
        void testArrowUp() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.ArrowUp);
            when(screen.pollInput()).thenReturn(ks);

            GUI.ACTION action = graphics.getNextAction();
            assertEquals(GUI.ACTION.UP, action);
        }

        @Test
        @DisplayName("ArrowRight => ACTION.RIGHT")
        void testArrowRight() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.ArrowRight);
            when(screen.pollInput()).thenReturn(ks);

            GUI.ACTION action = graphics.getNextAction();
            assertEquals(GUI.ACTION.RIGHT, action);
        }

        @Test
        @DisplayName("ArrowLeft => ACTION.LEFT")
        void testArrowLeft() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.ArrowLeft);
            when(screen.pollInput()).thenReturn(ks);

            GUI.ACTION action = graphics.getNextAction();
            assertEquals(GUI.ACTION.LEFT, action);
        }

        @Test
        @DisplayName("KeyType.Enter => ACTION.SELECT")
        void testEnter() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.Enter);
            when(screen.pollInput()).thenReturn(ks);

            GUI.ACTION action = graphics.getNextAction();
            assertEquals(GUI.ACTION.SELECT, action);
        }

        @Test
        @DisplayName("KeyType.Escape => ACTION.PAUSE")
        void testEscape() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.Escape);
            when(screen.pollInput()).thenReturn(ks);

            GUI.ACTION action = graphics.getNextAction();
            assertEquals(GUI.ACTION.PAUSE, action);
        }

        @Test
        @DisplayName("Space char => ACTION.SPACE")
        void testSpaceChar() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.Character);
            when(ks.getCharacter()).thenReturn(' ');
            when(screen.pollInput()).thenReturn(ks);

            GUI.ACTION action = graphics.getNextAction();
            assertEquals(GUI.ACTION.SPACE, action);
        }

        @Test
        @DisplayName("Any other char => ACTION.NONE")
        void testAnyOtherChar() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.Character);
            when(ks.getCharacter()).thenReturn('X');  // e.g., 'X'
            when(screen.pollInput()).thenReturn(ks);

            GUI.ACTION action = graphics.getNextAction();
            assertEquals(GUI.ACTION.NONE, action);
        }
    }

    // -------------------------------------------------------------------
    // Utility method to verify a single drawCharacter(...) call
    // -------------------------------------------------------------------
    private void verifyCharacterDrawn(int x, int y, char c, String color) {
        // We expect:
        //   textGraphics.setForegroundColor(TextColor.Factory.fromString(color));
        //   textGraphics.putString(x, y, String.valueOf(c));

        verify(textGraphics, times(1))
                .setForegroundColor(TextColor.Factory.fromString(color));
        verify(textGraphics, times(1))
                .putString(x, y, String.valueOf(c));

        // After we verify once, we might reset the mock if we plan multiple calls:
        // reset(textGraphics);
    }
}
