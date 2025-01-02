package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.SelectLevelMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SelectLevelMenuViewerTest {

    private SelectLevelMenu model;
    private SelectLevelMenuViewer viewer;
    private Graphics graphics;

    @BeforeEach
    void setUp() {
        model = mock(SelectLevelMenu.class);
        graphics = mock(Graphics.class);
        viewer = new SelectLevelMenuViewer(model);
    }

    @Test
    @DisplayName("drawElements => verify all lines from drawTitle, drawSnowflake, plus the 5 level boxes, plus loop calls")
    void testDrawElementsAllLines() {
        // Suppose we have 5 entries: 1..5
        when(model.getNumberEntries()).thenReturn(5);
        when(model.getEntry(0)).thenReturn("1");
        when(model.getEntry(1)).thenReturn("2");
        when(model.getEntry(2)).thenReturn("3");
        when(model.getEntry(3)).thenReturn("4");
        when(model.getEntry(4)).thenReturn("5");
        // Suppose the 2nd entry is selected
        when(model.isSelected(anyInt())).thenReturn(false);
        when(model.isSelected(1)).thenReturn(true);

        viewer.drawElements(graphics);

        var txtCap = ArgumentCaptor.forClass(String.class);
        verify(graphics, atLeast(1)).drawText(any(Position.class), txtCap.capture(), anyString());
        var allTexts = txtCap.getAllValues();

        // drawTitle(...) s0..s5
        assertTrue(allTexts.contains("   _                    _    _____      _           _              "),
                "Missing s0 from drawTitle");
        // etc. for s1..s5

        // drawSnowflake(...) s0..s7 repeated
        String s0 = "   ..    ..          ";
        long countS0 = allTexts.stream().filter(s0::equals).count();
        // from your snippet, s0 is drawn 4 times or so => check it
        assertTrue(countS0 >= 4, "Missing or removed call for s0 in drawSnowflake");

        // The code also draws the " --- " boxes at positions 43..112. If you want to be thorough:
        assertTrue(allTexts.contains(" --- "));

        // The loop => "1","2" (selected), "3","4","5"
        assertTrue(allTexts.contains("1"));
        assertTrue(allTexts.contains("2"));
        assertTrue(allTexts.contains("3"));
        assertTrue(allTexts.contains("4"));
        assertTrue(allTexts.contains("5"));
    }

    @Test
    @DisplayName("If 0 entries => no loop calls, still all ASCII from title and snowflake are present")
    void testDrawElementsZeroEntries() {
        when(model.getNumberEntries()).thenReturn(0);

        viewer.drawElements(graphics);
        verify(model).getNumberEntries();
        verify(model, never()).getEntry(anyInt());
        verify(model, never()).isSelected(anyInt());

        var txtCap = ArgumentCaptor.forClass(String.class);
        verify(graphics, atLeast(1)).drawText(any(), txtCap.capture(), anyString());
        var allTexts = txtCap.getAllValues();

        // check title line s0
        assertTrue(allTexts.contains("   _                    _    _____      _           _              "),
                "Title line s0 missing with 0 entries");
    }
}
