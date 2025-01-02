package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.PauseMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PauseMenuViewerTest {

    private PauseMenu model;
    private PauseMenuViewer viewer;
    private Graphics graphics;

    @BeforeEach
    void setUp() {
        model = mock(PauseMenu.class);
        graphics = mock(Graphics.class);
        viewer = new PauseMenuViewer(model);
    }

    @Test
    @DisplayName("All ASCII lines from drawTitle, drawSnowflake, drawPauseSymbol, plus loop for entries")
    void testDrawElementsAllLines() {
        when(model.getNumberEntries()).thenReturn(2);
        when(model.getEntry(0)).thenReturn("RESUME");
        when(model.getEntry(1)).thenReturn("  MENU");
        when(model.isSelected(0)).thenReturn(true);
        when(model.isSelected(1)).thenReturn(false);

        viewer.drawElements(graphics);

        var txtCap = ArgumentCaptor.forClass(String.class);
        verify(graphics, atLeast(1)).drawText(any(Position.class), txtCap.capture(), anyString());
        var allTexts = txtCap.getAllValues();

        // drawTitle(...) lines s0..s5
        assertTrue(allTexts.contains("  _____                                            "),
                "Missing s0 from drawTitle");
        // etc.

        // drawPauseSymbol(...) lines => s1..s7
        assertTrue(allTexts.contains(" __    _           "),
                "Missing s1 from pause symbol");
        assertTrue(allTexts.contains("|__|  |_/             "),
                "Missing last line from pause symbol");

        // drawSnowflake(...) lines => s0..s7 repeated
        assertTrue(allTexts.contains("   ..    ..          "));

        // loop => "RESUME" => #D1D100, "  MENU" => #FFFFFF
        assertTrue(allTexts.contains("RESUME"));
        assertTrue(allTexts.contains("  MENU"));
    }
}
