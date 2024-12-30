package badIceCream.GUI;

import badIceCream.GUI.GUI.ACTION;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Comprehensive test class for the Graphics class, including a FIX for "TooManyActualInvocations."
 */
class GraphicsTest {

    private GUI gui;
    private Screen screen;
    private TextGraphics textGraphics;
    private Terminal terminal;
    private Graphics graphics;

    @BeforeEach
    void setUp() throws IOException {
        // Mocks
        gui = mock(GUI.class);
        screen = mock(Screen.class);
        textGraphics = mock(TextGraphics.class);
        terminal = mock(Terminal.class);

        // GUI: createTerminal() => mock Terminal
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

            graphics.drawIceCream(position, ACTION.UP, true);

            verifyCharacterDrawn(10, 5, '7', "#48DEFF");
        }

        @Test
        @DisplayName("RIGHT + strawberry=false => char='9', color='#FFFFFF'")
        void testDrawIceCreamRightNoStrawberry() {
            Position position = new Position(3, 7);

            graphics.drawIceCream(position, ACTION.RIGHT, false);

            verifyCharacterDrawn(3, 7, '9', "#FFFFFF");
        }

        @Test
        @DisplayName("Default (e.g. DOWN) + strawberry => char='8', color='#48DEFF'")
        void testDrawIceCreamDefaultStrawberry() {
            Position position = new Position(2, 2);

            // 'default' means not UP/LEFT/RIGHT => e.g. DOWN
            graphics.drawIceCream(position, ACTION.DOWN, true);

            verifyCharacterDrawn(2, 2, '8', "#48DEFF");
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
        @DisplayName("type=0 => no drawing (switch doesn't match case)")
        void testDrawIceWallInvalidType() {
            Position pos = new Position(1, 1);
            graphics.drawIceWall(pos, 0);

            // no calls to putString
            verify(textGraphics, never()).putString(anyInt(), anyInt(), anyString());
        }
    }

    // -------------------------------------------------------------------
    // drawCharacters() test => FIX for TooManyActualInvocations
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
    // getNextAction() tests
    // -------------------------------------------------------------------
    @Nested
    @DisplayName("getNextAction() tests")
    class GetNextActionTests {
        @Test
        @DisplayName("Null KeyStroke => ACTION.NONE")
        void testNullKeyStroke() throws IOException {
            when(screen.pollInput()).thenReturn(null);

            ACTION action = graphics.getNextAction();
            assertEquals(ACTION.NONE, action);
        }

        @Test
        @DisplayName("ArrowDown => ACTION.DOWN")
        void testArrowDown() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.ArrowDown);
            when(screen.pollInput()).thenReturn(ks);

            ACTION action = graphics.getNextAction();
            assertEquals(ACTION.DOWN, action);
        }

        @Test
        @DisplayName("ArrowUp => ACTION.UP")
        void testArrowUp() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.ArrowUp);
            when(screen.pollInput()).thenReturn(ks);

            ACTION action = graphics.getNextAction();
            assertEquals(ACTION.UP, action);
        }

        @Test
        @DisplayName("ArrowRight => ACTION.RIGHT")
        void testArrowRight() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.ArrowRight);
            when(screen.pollInput()).thenReturn(ks);

            ACTION action = graphics.getNextAction();
            assertEquals(ACTION.RIGHT, action);
        }

        @Test
        @DisplayName("ArrowLeft => ACTION.LEFT")
        void testArrowLeft() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.ArrowLeft);
            when(screen.pollInput()).thenReturn(ks);

            ACTION action = graphics.getNextAction();
            assertEquals(ACTION.LEFT, action);
        }

        @Test
        @DisplayName("KeyType.Enter => ACTION.SELECT")
        void testEnter() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.Enter);
            when(screen.pollInput()).thenReturn(ks);

            ACTION action = graphics.getNextAction();
            assertEquals(ACTION.SELECT, action);
        }

        @Test
        @DisplayName("KeyType.Escape => ACTION.PAUSE")
        void testEscape() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.Escape);
            when(screen.pollInput()).thenReturn(ks);

            ACTION action = graphics.getNextAction();
            assertEquals(ACTION.PAUSE, action);
        }

        @Test
        @DisplayName("Space char => ACTION.SPACE")
        void testSpaceChar() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.Character);
            when(ks.getCharacter()).thenReturn(' ');
            when(screen.pollInput()).thenReturn(ks);

            ACTION action = graphics.getNextAction();
            assertEquals(ACTION.SPACE, action);
        }

        @Test
        @DisplayName("Any other char => ACTION.NONE")
        void testAnyOtherChar() throws IOException {
            KeyStroke ks = mock(KeyStroke.class);
            when(ks.getKeyType()).thenReturn(KeyType.Character);
            when(ks.getCharacter()).thenReturn('X');  // e.g., 'X'
            when(screen.pollInput()).thenReturn(ks);

            ACTION action = graphics.getNextAction();
            assertEquals(ACTION.NONE, action);
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
