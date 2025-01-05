package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import java.util.List;
import java.util.ArrayList;
import badIceCream.model.Position;
import badIceCream.model.menu.LevelCompletedMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LevelCompletedMenuViewerTest {

    private LevelCompletedMenu model;
    private LevelCompletedMenuViewer viewer;
    private Graphics graphics;

    @BeforeEach
    public  void setUp() {
        model = mock(LevelCompletedMenu.class);
        graphics = mock(Graphics.class);
        viewer = new LevelCompletedMenuViewer(model);
    }

    @Test
    @DisplayName("Check drawTitle, drawSnowflake, and all text lines with entries")
    public void testDrawElementsAllLines() {
        // Setup model with 2 entries
        when(model.getNumberEntries()).thenReturn(2);
        when(model.getEntry(0)).thenReturn("NEXT LEVEL");
        when(model.getEntry(1)).thenReturn("   MENU");
        when(model.isSelected(0)).thenReturn(true);
        when(model.isSelected(1)).thenReturn(false);

        viewer.drawElements(graphics);

        // Capture all drawText calls
        ArgumentCaptor<Position> posCaptor = ArgumentCaptor.forClass(Position.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> colorCaptor = ArgumentCaptor.forClass(String.class);

        verify(graphics, atLeastOnce()).drawText(posCaptor.capture(), textCaptor.capture(), colorCaptor.capture());

        List<Position> positions = posCaptor.getAllValues();
        List<String> texts = textCaptor.getAllValues();
        List<String> colors = colorCaptor.getAllValues();

        // Print captured values for debugging
        for (int i = 0; i < positions.size(); i++) {
            System.out.println("Position: (" + positions.get(i).getX() + ", " + positions.get(i).getY() + "), " +
                    "Text: \"" + texts.get(i) + "\", Color: " + colors.get(i));
        }

        // Prepare a list of expected drawText calls
        List<DrawTextCall> expectedCalls = new ArrayList<>();

        // drawTitle calls (lines s0 to s7)
        expectedCalls.add(new DrawTextCall(new Position(24, 2), "  _                              _      _____                               _          _  ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 3), " | |                            | |    / ____|                             | |        | | ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 4), " | |        ___  __   __   ___  | |   | |        ___    _ __ ___    _ __   | |   ___  | |_    ___  ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 5), " | |       / _ \\ \\ \\ / /  / _ \\ | |   | |       / _ \\  | '_ ` _ \\  | '_ \\  | |  / _ \\ | __|  / _ \\ ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 6), " | |____  |  __/  \\ V /  |  __/ | |   | |____  | (_) | | | | | | | | |_) | | | |  __/ | |_  |  __/ ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 7), " |______|  \\___|   \\_/    \\___| |_|    \\_____|  \\___/  |_| |_| |_| | .__/  |_|  \\___|  \\__|  \\___| ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 8), "                                                                   | | ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 9), "                                                                   |_|   ", "#f7dc6f"));

        // drawSnowflake calls at various positions with different colors
        String[] snowflakeLines = {
                "   ..    ..          ",
                "   '\\    /'         ",
                "     \\\\//          ",
                "_.__\\\\\\///__._    ",
                " '  ///\\\\\\  '     ",
                "     //\\\\          ",
                "   ./    \\.         ",
                "   ''    ''          "
        };

        // Snowflake set 1 at (15,25) with color #f70d09
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(15, 25 + i), snowflakeLines[i], "#f70d09"));
        }

        // Snowflake set 2 at (5,1) with color #8bf117
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(5, 1 + i), snowflakeLines[i], "#8bf117"));
        }

        // Snowflake set 3 at (70,33) with color #56b6f4
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(70, 33 + i), snowflakeLines[i], "#56b6f4"));
        }

        // Snowflake set 4 at (100,20) with color #fc9a02
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(100, 20 + i), snowflakeLines[i], "#fc9a02"));
        }

        // Snowflake set 5 at (120,11) with color #ff53f7
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(120, 11 + i), snowflakeLines[i], "#ff53f7"));
        }

        // Entries
        expectedCalls.add(new DrawTextCall(new Position(65, 17), "NEXT LEVEL", "#D1D100")); // Selected entry
        expectedCalls.add(new DrawTextCall(new Position(65, 21), "   MENU", "#FFFFFF")); // Non-selected entry

        // Verify each expected call is present
        for (DrawTextCall expected : expectedCalls) {
            boolean found = false;
            for (int i = 0; i < texts.size(); i++) {
                if (expected.matches(positions.get(i), texts.get(i), colors.get(i))) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Missing drawText call with Position: (" + expected.position.getX() + ", " + expected.position.getY() + "), " +
                    "Text: \"" + expected.text + "\", Color: " + expected.color);
        }

        // Verify the total number of drawText calls matches expected
        assertEquals(expectedCalls.size(), texts.size(),
                "Number of drawText calls does not match expected. Expected: " +
                        expectedCalls.size() + ", Actual: " + texts.size());
    }

    @Test
    @DisplayName("0 entries => still prints ASCII, no loop calls")
    public void testDrawElementsZeroEntries() {
        // Setup model with 0 entries
        when(model.getNumberEntries()).thenReturn(0);

        viewer.drawElements(graphics);

        // Verify that getNumberEntries is called once
        verify(model, times(1)).getNumberEntries();
        // Verify that getEntry and isSelected are never called
        verify(model, never()).getEntry(anyInt());
        verify(model, never()).isSelected(anyInt());

        // Capture all drawText calls
        ArgumentCaptor<Position> posCaptor = ArgumentCaptor.forClass(Position.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> colorCaptor = ArgumentCaptor.forClass(String.class);

        verify(graphics, atLeastOnce()).drawText(posCaptor.capture(), textCaptor.capture(), colorCaptor.capture());

        List<Position> positions = posCaptor.getAllValues();
        List<String> texts = textCaptor.getAllValues();
        List<String> colors = colorCaptor.getAllValues();

        // Prepare a list of expected drawText calls without entries
        List<DrawTextCall> expectedCalls = new ArrayList<>();

        // drawTitle calls (lines s0 to s7)
        expectedCalls.add(new DrawTextCall(new Position(24, 2), "  _                              _      _____                               _          _  ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 3), " | |                            | |    / ____|                             | |        | | ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 4), " | |        ___  __   __   ___  | |   | |        ___    _ __ ___    _ __   | |   ___  | |_    ___  ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 5), " | |       / _ \\ \\ \\ / /  / _ \\ | |   | |       / _ \\  | '_ ` _ \\  | '_ \\  | |  / _ \\ | __|  / _ \\ ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 6), " | |____  |  __/  \\ V /  |  __/ | |   | |____  | (_) | | | | | | | | |_) | | | |  __/ | |_  |  __/ ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 7), " |______|  \\___|   \\_/    \\___| |_|    \\_____|  \\___/  |_| |_| |_| | .__/  |_|  \\___|  \\__|  \\___| ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 8), "                                                                   | | ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(24, 9), "                                                                   |_|   ", "#f7dc6f"));

        // drawSnowflake calls at various positions with different colors
        String[] snowflakeLines = {
                "   ..    ..          ",
                "   '\\    /'         ",
                "     \\\\//          ",
                "_.__\\\\\\///__._    ",
                " '  ///\\\\\\  '     ",
                "     //\\\\          ",
                "   ./    \\.         ",
                "   ''    ''          "
        };

        // Snowflake set 1 at (15,25) with color #f70d09
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(15, 25 + i), snowflakeLines[i], "#f70d09"));
        }

        // Snowflake set 2 at (5,1) with color #8bf117
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(5, 1 + i), snowflakeLines[i], "#8bf117"));
        }

        // Snowflake set 3 at (70,33) with color #56b6f4
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(70, 33 + i), snowflakeLines[i], "#56b6f4"));
        }

        // Snowflake set 4 at (100,20) with color #fc9a02
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(100, 20 + i), snowflakeLines[i], "#fc9a02"));
        }

        // Snowflake set 5 at (120,11) with color #ff53f7
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(120, 11 + i), snowflakeLines[i], "#ff53f7"));
        }

        // Verify each expected call is present
        for (DrawTextCall expected : expectedCalls) {
            boolean found = false;
            for (int i = 0; i < texts.size(); i++) {
                if (expected.matches(positions.get(i), texts.get(i), colors.get(i))) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Missing drawText call with Position: (" + expected.position.getX() + ", " + expected.position.getY() + "), " +
                    "Text: \"" + expected.text + "\", Color: " + expected.color);
        }

        // Verify the total number of drawText calls matches expected
        assertEquals(expectedCalls.size(), texts.size(),
                "Number of drawText calls does not match expected. Expected: " +
                        expectedCalls.size() + ", Actual: " + texts.size());
    }

    /**
     * Helper class to represent an expected drawText call.
     * Compares positions based on x and y coordinates.
     */
    private static class DrawTextCall {
        Position position;
        String text;
        String color;

        DrawTextCall(Position position, String text, String color) {
            this.position = position;
            this.text = text;
            this.color = color;
        }

        /**
         * Checks if the actual call matches the expected call.
         * Compares positions based on x and y coordinates.
         *
         * @param actualPosition The actual Position object.
         * @param actualText     The actual text.
         * @param actualColor    The actual color.
         * @return True if all parameters match, false otherwise.
         */
        boolean matches(Position actualPosition, String actualText, String actualColor) {
            return this.position.getX() == actualPosition.getX() &&
                    this.position.getY() == actualPosition.getY() &&
                    this.text.equals(actualText) &&
                    this.color.equalsIgnoreCase(actualColor.trim());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof DrawTextCall)) return false;
            DrawTextCall other = (DrawTextCall) obj;
            return this.position.equals(other.position) &&
                    this.text.equals(other.text) &&
                    this.color.equalsIgnoreCase(other.color);
        }

        @Override
        public int hashCode() {
            return position.hashCode() + text.hashCode() + color.toLowerCase().hashCode();
        }
    }
}