package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.GameOverMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameOverMenuViewerTest {

    private GameOverMenu model;
    private GameOverMenuViewer viewer;
    private Graphics graphics;

    @BeforeEach
    void setUp() {
        model = mock(GameOverMenu.class);
        graphics = mock(Graphics.class);
        viewer = new GameOverMenuViewer(model);
    }

    @Test
    @DisplayName("All ASCII lines from drawTitle(...) and drawSnowflake(...), plus loop over entries, are required")
    public void testDrawElementsAllLines() {
        // Suppose we have 2 entries for the loop
        when(model.getNumberEntries()).thenReturn(2);
        when(model.getEntry(0)).thenReturn("PLAY AGAIN");
        when(model.getEntry(1)).thenReturn("   MENU");
        when(model.isSelected(0)).thenReturn(true);
        when(model.isSelected(1)).thenReturn(false);

        viewer.drawElements(graphics);

        // Capture all calls
        ArgumentCaptor<String> textCap = ArgumentCaptor.forClass(String.class);
        verify(graphics, atLeast(1)).drawText(any(Position.class), textCap.capture(), anyString());
        var allTexts = textCap.getAllValues();

        // ********** VERIFY drawTitle(...) LINES **********
        assertTrue(allTexts.contains("  _____                                 ____                            "),
                "Missing s0 from drawTitle");
        assertTrue(allTexts.contains(" / ____|                               / __ \\                           "),
                "Missing s1 from drawTitle");
        assertTrue(allTexts.contains("| |  __    __ _   _ __ ___     ___    | |  | | __   __   ___   _ __    "),
                "Missing s2 from drawTitle");
        assertTrue(allTexts.contains("| | |_ |  / _` | | '_ ` _ \\   / _ \\   | |  | | \\ \\ / /  / _ \\ | '__|   "),
                "Missing s3 from drawTitle");
        assertTrue(allTexts.contains("| |__| | | (_| | | | | | | | |  __/   | |__| |  \\ V /  |  __/ | |      "),
                "Missing s4 from drawTitle");
        assertTrue(allTexts.contains(" \\_____|  \\__,_| |_| |_| |_|  \\___|    \\____/    \\_/    \\___| |_|       "),
                "Missing s5 from drawTitle");

        // ********** VERIFY drawSnowflake(...) LINES **********
        // We'll check the core 8 lines s0..s7 repeated in multiple sets
        String s0 = "   ..    ..          ";
        String s1 = "   '\\    /'         ";
        String s2 = "     \\\\//          ";
        String s3 = "_.__\\\\\\///__._    ";
        String s4 = " '  ///\\\\\\  '     ";
        String s5 = "     //\\\\          ";
        String s6 = "   ./    \\.         ";
        String s7 = "   ''    ''          ";

        // count how many times s0 should appear => you do it at positions (15,25), (5,1), (70,33), (100,20), (120,7) => 5 times
        assertEquals(5, allTexts.stream().filter(s0::equals).count(), "drawSnowflake => missing or removed call for s0");
        // Repeat for s1..s7:
        assertEquals(5, allTexts.stream().filter(s1::equals).count(), "Missing s1 lines");
        assertEquals(5, allTexts.stream().filter(s2::equals).count(), "Missing s2 lines");
        assertEquals(5, allTexts.stream().filter(s3::equals).count(), "Missing s3 lines");
        assertEquals(5, allTexts.stream().filter(s4::equals).count(), "Missing s4 lines");
        assertEquals(5, allTexts.stream().filter(s5::equals).count(), "Missing s5 lines");
        assertEquals(5, allTexts.stream().filter(s6::equals).count(), "Missing s6 lines");
        assertEquals(5, allTexts.stream().filter(s7::equals).count(), "Missing s7 lines");

        // ********** VERIFY the loop calls for entries **********
        assertTrue(allTexts.contains("PLAY AGAIN"), "Missing first entry");
        assertTrue(allTexts.contains("   MENU"), "Missing second entry");
    }

    @Test
    @DisplayName("If 0 entries => must still have drawTitle/drawSnowflake lines but no loop calls for entries")
    public void testDrawElementsZeroEntries() {
        when(model.getNumberEntries()).thenReturn(0);

        viewer.drawElements(graphics);
        verify(model).getNumberEntries();
        verify(model, never()).getEntry(anyInt());
        verify(model, never()).isSelected(anyInt());

        // check we still have the ASCII lines
        ArgumentCaptor<String> textCap = ArgumentCaptor.forClass(String.class);
        verify(graphics, atLeast(1)).drawText(any(), textCap.capture(), anyString());
        var allTexts = textCap.getAllValues();

        // Just check one line from drawTitle to confirm
        assertTrue(allTexts.contains("  _____                                 ____                            "),
                "Title line s0 missing with 0 entries");
        // No "PLAY AGAIN" or "   MENU"
        assertFalse(allTexts.contains("PLAY AGAIN"));
        assertFalse(allTexts.contains("   MENU"));
    }
}
