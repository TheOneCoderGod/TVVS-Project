package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Comprehensive test class for InstructionsMenuFirstPageViewer.
 * Ensures all drawText calls are made correctly to achieve full mutation coverage.
 */
class InstructionsMenuFirstPageViewerTest {

    private InstructionsMenuFirstPage model;
    private InstructionsMenuFirstPageViewer viewer;
    private Graphics graphics;

    @BeforeEach
    void setUp() {
        // Mock the model and graphics
        model = mock(InstructionsMenuFirstPage.class);
        graphics = mock(Graphics.class);
        viewer = new InstructionsMenuFirstPageViewer(model);
    }

    @Test
    @DisplayName("drawElements => verifies all drawText calls in drawTitle, drawSnowflake, and additional texts")
    void testDrawElementsAllLines() {
        // Execute the method under test
        viewer.drawElements(graphics);

        // Capture all drawText calls
        ArgumentCaptor<Position> posCaptor = ArgumentCaptor.forClass(Position.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> colorCaptor = ArgumentCaptor.forClass(String.class);

        verify(graphics, atLeast(1)).drawText(posCaptor.capture(), textCaptor.capture(), colorCaptor.capture());

        // Retrieve all captured arguments
        List<Position> allPositions = posCaptor.getAllValues();
        List<String> allTexts = textCaptor.getAllValues();
        List<String> allColors = colorCaptor.getAllValues();

        // ********** VERIFY drawTitle(...) LINES **********
        // Expected drawText calls from drawTitle(gui)
        assertAll("drawTitle checks",
                () -> assertTrue(allTexts.contains("           _____           _                   _   _                       "),
                        "Missing s0 from drawTitle"),
                () -> assertTrue(allTexts.contains("          |_   _|         | |                 | | (_)                      "),
                        "Missing s1 from drawTitle"),
                () -> assertTrue(allTexts.contains("            | |  _ __  ___| |_ _ __ _   _  ___| |_ _  ___  _ __  ___       "),
                        "Missing s2 from drawTitle"),
                () -> assertTrue(allTexts.contains("            | | | '_ \\/ __| __| '__| | | |/ __| __| |/ _ \\| '_ \\/ __|      "),
                        "Missing s3 from drawTitle"),
                () -> assertTrue(allTexts.contains("           _| |_| | | \\__ \\ |_| |  | |_| | (__| |_| | (_) | | | \\__ \";    "),
                        "Missing s4 from drawTitle"),
                () -> assertTrue(allTexts.contains("          |_____|_| |_|___/\\__|_|   \\__,_|\\___|\\__|_|\\___/|_| |_|___/      "),
                        "Missing s5 from drawTitle")
        );

        // ********** VERIFY drawSnowflake(...) LINES **********
        // Define snowflake strings
        String s0 = "   ..    ..          ";
        String s1 = "   '\\    /'         ";
        String s2 = "     \\\\//          ";
        String s3 = "_.__\\\\\\///__._    ";
        String s4 = " '  ///\\\\\\  '     ";
        String s5 = "     //\\\\          ";
        String s6 = "   ./    \\.         ";
        String s7 = "   ''    ''          ";

        // Expected number of times each snowflake line is drawn
        // From the code:
        // - (15,25) to (15,32)
        // - (5,1) to (5,8)
        // - (120,7) to (120,14)
        // Total: 3 sets of 8 lines each = 24 drawText calls for snowflake lines
        // Therefore, each snowflake line should appear 3 times

        // ********** VERIFY drawSnowflake(...) LINES **********
        assertAll("drawSnowflake checks",
                () -> assertEquals(3, allTexts.stream().filter(s0::equals).count(),
                        String.format("Snowflake line '%s' should be drawn 3 times.", s0)),
                () -> assertEquals(3, allTexts.stream().filter(s1::equals).count(),
                        String.format("Snowflake line '%s' should be drawn 3 times.", s1)),
                () -> assertEquals(3, allTexts.stream().filter(s2::equals).count(),
                        String.format("Snowflake line '%s' should be drawn 3 times.", s2)),
                () -> assertEquals(3, allTexts.stream().filter(s3::equals).count(),
                        String.format("Snowflake line '%s' should be drawn 3 times.", s3)),
                () -> assertEquals(3, allTexts.stream().filter(s4::equals).count(),
                        String.format("Snowflake line '%s' should be drawn 3 times.", s4)),
                () -> assertEquals(3, allTexts.stream().filter(s5::equals).count(),
                        String.format("Snowflake line '%s' should be drawn 3 times.", s5)),
                () -> assertEquals(3, allTexts.stream().filter(s6::equals).count(),
                        String.format("Snowflake line '%s' should be drawn 3 times.", s6)),
                () -> assertEquals(3, allTexts.stream().filter(s7::equals).count(),
                        String.format("Snowflake line '%s' should be drawn 3 times.", s7))
        );

        // ********** VERIFY ADDITIONAL TEXT LINES **********
        // Define additional expected texts and their colors
        List<AbstractMap.SimpleEntry<String, String>> additionalTextsWithColors = List.of(
                new AbstractMap.SimpleEntry<>("The goal of the game is to collect all fruits without being caught by the monsters", "#f76fe0"),
                new AbstractMap.SimpleEntry<>("Movements", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("Build/Break Ice", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("Pause", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("       ___          ", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("      | ^ |         ", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("      |_|_|         ", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("  ___  ___  ___     ", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>(" | <-|| | ||-> |    ", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>(" |___||_v_||___|    ", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>(" _________________ ", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("|      SPACE      |", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("|_________________|", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>(" _____", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("| ESC |", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("|_____|", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("Next Page", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>(" ___", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("| ->|", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("|___|", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("Main Menu", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>(" ___", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("|ESC|", "#FFFFFF"),
                new AbstractMap.SimpleEntry<>("|___| ", "#FFFFFF")
        );

        // Verify each additional text is drawn once with the correct color
        for (AbstractMap.SimpleEntry<String, String> entry : additionalTextsWithColors) {
            String expectedText = entry.getKey();
            String expectedColor = entry.getValue();

            // Find if the text with the correct color exists
            boolean found = false;
            for (int i = 0; i < allTexts.size(); i++) {
                if (allTexts.get(i).equals(expectedText) && allColors.get(i).equals(expectedColor)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, String.format("Missing text '%s' with color '%s'.", expectedText, expectedColor));
        }
    }
}
