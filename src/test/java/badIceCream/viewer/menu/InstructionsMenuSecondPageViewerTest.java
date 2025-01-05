package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InstructionsMenuSecondPageViewerTest {

    private InstructionsMenuSecondPage model;
    private InstructionsMenuSecondPageViewer viewer;
    private Graphics graphics;

    @BeforeEach
    public void setUp() {
        model = mock(InstructionsMenuSecondPage.class);
        graphics = mock(Graphics.class);
        viewer = new InstructionsMenuSecondPageViewer(model);
    }

    @Test
    @DisplayName("Check drawTitle, drawSnowflake, drawCharacters, all text lines, and buttons")
    public void testDrawElementsAllLines() throws IOException {
        viewer.drawElements(graphics);

        // Verify that drawCharacters() was called once
        verify(graphics, times(1)).drawCharacters();

        // Capture all drawText calls
        ArgumentCaptor<Position> posCaptor = ArgumentCaptor.forClass(Position.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> colorCaptor = ArgumentCaptor.forClass(String.class);

        verify(graphics, atLeastOnce()).drawText(posCaptor.capture(), textCaptor.capture(), colorCaptor.capture());

        List<Position> positions = posCaptor.getAllValues();
        List<String> texts = textCaptor.getAllValues();
        List<String> colors = colorCaptor.getAllValues();

        // Prepare a list of expected drawText calls
        List<DrawTextCall> expectedCalls = new ArrayList<>();

        // drawTitle calls
        expectedCalls.add(new DrawTextCall(new Position(35, 1), "           _____           _                   _   _                       ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(35, 2), "          |_   _|         | |                 | | (_)                      ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(35, 3), "            | |  _ __  ___| |_ _ __ _   _  ___| |_ _  ___  _ __  ___       ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(35, 4), "            | | | '_ \\/ __| __| '__| | | |/ __| __| |/ _ \\| '_ \\/ __|      ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(35, 5), "           _| |_| | | \\__ \\ |_| |  | |_| | (__| |_| | (_) | | | \\__ \";    ", "#f7dc6f"));
        expectedCalls.add(new DrawTextCall(new Position(35, 6), "          |_____|_| |_|___/\\__|_|   \\__,_|\\___|\\__|_|\\___/|_| |_|___/      ", "#f7dc6f"));

        // drawSnowflake calls at (15,25) to (15,32)
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
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(15, 25 + i), snowflakeLines[i], "#ffffff"));
        }

        // drawSnowflake calls at (5,1) to (5,8)
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(5, 1 + i), snowflakeLines[i], "#ffffff"));
        }

        // drawSnowflake calls at (120,7) to (120,14)
        for (int i = 0; i < snowflakeLines.length; i++) {
            expectedCalls.add(new DrawTextCall(new Position(120, 7 + i), snowflakeLines[i], "#ffffff"));
        }

        // Description text lines
        expectedCalls.add(new DrawTextCall(new Position(45, 15), "Default Monster: Just walks around the arena.", "#FFFFFF"));
        expectedCalls.add(new DrawTextCall(new Position(45, 18), "Jumper Monster: Has the ability to jump walls.", "#FFFFFF"));
        expectedCalls.add(new DrawTextCall(new Position(45, 21), "WallBreaker Monster: Has the ability to break ice walls.", "#FFFFFF"));
        expectedCalls.add(new DrawTextCall(new Position(45, 24), "Runner Monster Inactive: Acts like a default monster.", "#FFFFFF"));
        expectedCalls.add(new DrawTextCall(new Position(45, 27), "Default Monster Active: Has the ability to track Bad Ice Cream and run faster.", "#FFFFFF"));
        expectedCalls.add(new DrawTextCall(new Position(45, 30), "Hot Floor: Blocks the progression of ice walls.", "#FFFFFF"));

        // Button "Last Page"
        expectedCalls.add(new DrawTextCall(new Position(36, 40), "Last Page", "#FFFFFF"));
        expectedCalls.add(new DrawTextCall(new Position(30, 39), " ___", "#FFFFFF"));
        expectedCalls.add(new DrawTextCall(new Position(30, 40), "|<- |", "#FFFFFF"));
        expectedCalls.add(new DrawTextCall(new Position(30, 41), "|___| ", "#FFFFFF"));

        // Button "Main Menu"
        expectedCalls.add(new DrawTextCall(new Position(110, 40), "Main Menu", "#FFFFFF"));
        expectedCalls.add(new DrawTextCall(new Position(120, 39), " ___", "#FFFFFF"));
        expectedCalls.add(new DrawTextCall(new Position(120, 40), "|ESC|", "#FFFFFF"));
        expectedCalls.add(new DrawTextCall(new Position(120, 41), "|___| ", "#FFFFFF"));

        // Verify each expected call is present
        for (DrawTextCall expected : expectedCalls) {
            boolean found = false;
            for (int i = 0; i < texts.size(); i++) {
                if (expected.position.equals(positions.get(i)) &&
                        expected.text.equals(texts.get(i)) &&
                        expected.color.equalsIgnoreCase(colors.get(i).trim())) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Missing drawText call with Position: " + expected.position +
                    ", Text: \"" + expected.text + "\", Color: " + expected.color);
        }

        // Optionally, verify the total number of drawText calls matches expected
        assertEquals(expectedCalls.size(), texts.size(),
                "Number of drawText calls does not match expected. Expected: " +
                        expectedCalls.size() + ", Actual: " + texts.size());
    }

    /**
     * Helper class to represent an expected drawText call.
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
    }
}
