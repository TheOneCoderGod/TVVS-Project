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

public class PauseMenuViewerTest {

    private PauseMenu model;
    private PauseMenuViewer viewer;
    private Graphics graphics;

    @BeforeEach
    public void setUp() {
        model = mock(PauseMenu.class);
        graphics = mock(Graphics.class);
        viewer = new PauseMenuViewer(model);
    }

    @Test
    @DisplayName("All ASCII lines from drawTitle, drawSnowflake, drawPauseSymbol, plus loop for entries")
    public void testDrawElementsAllLines() {
        when(model.getNumberEntries()).thenReturn(2);
        when(model.getEntry(0)).thenReturn("RESUME");
        when(model.getEntry(1)).thenReturn("  MENU");
        when(model.isSelected(0)).thenReturn(true);
        when(model.isSelected(1)).thenReturn(false);

        viewer.drawElements(graphics);

        ArgumentCaptor<Position> positionCaptor = ArgumentCaptor.forClass(Position.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> colorCaptor = ArgumentCaptor.forClass(String.class);

        verify(graphics, atLeast(1)).drawText(positionCaptor.capture(), textCaptor.capture(), colorCaptor.capture());

        var allPositions = positionCaptor.getAllValues();
        var allTexts = textCaptor.getAllValues();
        var allColors = colorCaptor.getAllValues();

        // Verify drawTitle(...) lines s0..s5
        assertTrue(allTexts.contains("  _____                                            "), "Missing s0 from drawTitle");
        assertTrue(allTexts.contains(" |_|      \\__,_|  \\__,_| |___/  \\___|          "), "Missing s5 from drawTitle");

        // Verify drawPauseSymbol(...) lines s1..s7
        assertTrue(allTexts.contains(" __    _           "), "Missing s1 from drawPauseSymbol");
        assertTrue(allTexts.contains("|__|  |_/             "), "Missing s7 from drawPauseSymbol");

        // Verify drawSnowflake(...) lines s0..s7 repeated
        assertTrue(allTexts.contains("   ..    ..          "), "Missing s0 from drawSnowflake");

        // Verify loop entries
        assertTrue(allTexts.contains("RESUME"), "Missing entry RESUME");
        assertTrue(allTexts.contains("  MENU"), "Missing entry MENU");

        // Verify positions and colors for a few key elements
        Position expectedPosition1 = new Position(51, 3);
        Position actualPosition1 = allPositions.get(allTexts.indexOf("  _____                                            "));
        assertEquals(expectedPosition1.getX(), actualPosition1.getX());
        assertEquals(expectedPosition1.getY(), actualPosition1.getY());
        assertEquals("#f7dc6f", allColors.get(allTexts.indexOf("  _____                                            ")).trim());

        Position expectedPosition2 = new Position(66, 10);
        Position actualPosition2 = allPositions.get(allTexts.indexOf(" __    _           "));
        assertEquals(expectedPosition2.getX(), actualPosition2.getX());
        assertEquals(expectedPosition2.getY(), actualPosition2.getY());
        assertEquals("#b05fa3", allColors.get(allTexts.indexOf(" __    _           ")).trim());

        Position expectedPosition3 = new Position(68, 21);
        Position actualPosition3 = allPositions.get(allTexts.indexOf("RESUME"));
        assertEquals(expectedPosition3.getX(), actualPosition3.getX());
        assertEquals(expectedPosition3.getY(), actualPosition3.getY());
        assertEquals("#D1D100", allColors.get(allTexts.indexOf("RESUME")).trim());

        Position expectedPosition4 = new Position(68, 24); // Updated expected y value
        Position actualPosition4 = allPositions.get(allTexts.indexOf("  MENU"));
        assertEquals(expectedPosition4.getX(), actualPosition4.getX());
        assertEquals(expectedPosition4.getY(), actualPosition4.getY());
        assertEquals("#FFFFFF", allColors.get(allTexts.indexOf("  MENU")).trim());
    }
}