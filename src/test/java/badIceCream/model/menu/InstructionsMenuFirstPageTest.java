package badIceCream.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstructionsMenuFirstPageTest {

    private InstructionsMenuFirstPage menu;

    @BeforeEach
    void setUp() {
        menu = new InstructionsMenuFirstPage();
    }

    @Test
    @DisplayName("Constructor has only 1 entry: Quit to Main Menu")
    void testConstructor() {
        assertEquals(1, menu.getNumberEntries());
        assertEquals("Quit to Main Menu", menu.getEntry(0));
    }

    @Test
    @DisplayName("nextEntry() / previousEntry() do not change selection because there's only 1 entry")
    void testSingleEntry() {
        // There's only one item, so next/previous won't change anything.
        assertTrue(menu.isSelected(0));
        menu.nextEntry();
        assertTrue(menu.isSelected(0));
        menu.previousEntry();
        assertTrue(menu.isSelected(0));
    }
}
