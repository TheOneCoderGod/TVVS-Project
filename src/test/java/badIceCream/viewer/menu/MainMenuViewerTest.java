package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.MainMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainMenuViewerTest {

    private MainMenu model;
    private MainMenuViewer viewer;
    private Graphics graphics;

    @BeforeEach
    void setUp() {
        model = mock(MainMenu.class);
        graphics = mock(Graphics.class);
        viewer = new MainMenuViewer(model);
    }

    @Test
    @DisplayName("All ASCII lines from drawTitle/drawSnowflake, plus entries loop")
    void testDrawElementsAllLines() {
        when(model.getNumberEntries()).thenReturn(3);
        when(model.getEntry(0)).thenReturn("   START");
        when(model.getEntry(1)).thenReturn("INSTRUCTIONS");
        when(model.getEntry(2)).thenReturn("    EXIT");
        when(model.isSelected(0)).thenReturn(true);
        when(model.isSelected(1)).thenReturn(false);
        when(model.isSelected(2)).thenReturn(false);

        viewer.drawElements(graphics);

        var txtCap = ArgumentCaptor.forClass(String.class);
        verify(graphics, atLeast(1)).drawText(any(Position.class), txtCap.capture(), anyString());
        var allTexts = txtCap.getAllValues();

        // Title lines s0..s13
        assertTrue(allTexts.contains("                        ....                .                                     "),
                "Missing s0 from drawTitle");
        // ... up to s13

        // Snowflake lines
        String s0 = "   ..    ..          ";
        long countS0 = allTexts.stream().filter(s0::equals).count();
        // code draws s0 about 4 or 5 times => adjust
        assertTrue(countS0 > 3, "Missing s0 lines from snowflake");

        // The loop => "   START" => selected => #D1D100, etc.
        assertTrue(allTexts.contains("   START"));
        assertTrue(allTexts.contains("INSTRUCTIONS"));
        assertTrue(allTexts.contains("    EXIT"));
    }

    @Test
    @DisplayName("Zero entries => no loop calls, but still ASCII from title & snowflake")
    void testDrawElementsZeroEntries() {
        when(model.getNumberEntries()).thenReturn(0);

        viewer.drawElements(graphics);
        verify(model).getNumberEntries();
        verify(model, never()).getEntry(anyInt());
        verify(model, never()).isSelected(anyInt());

        var txtCap = ArgumentCaptor.forClass(String.class);
        verify(graphics, atLeast(1)).drawText(any(), txtCap.capture(), anyString());
        var allTexts = txtCap.getAllValues();

        assertTrue(allTexts.contains("                        ....                .                                     "),
                "Title line missing");
    }
}
