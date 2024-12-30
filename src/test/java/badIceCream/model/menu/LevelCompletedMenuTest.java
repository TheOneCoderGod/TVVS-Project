package badIceCream.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelCompletedMenuTest {

    private LevelCompletedMenu menu;

    @BeforeEach
    void setUp() {
        menu = new LevelCompletedMenu();
    }

    @Test
    @DisplayName("Constructor with 2 entries: NEXT LEVEL, MENU")
    void testConstructor() {
        assertEquals(2, menu.getNumberEntries());
        assertEquals("NEXT LEVEL", menu.getEntry(0));
        assertEquals("   MENU", menu.getEntry(1));
    }

    @Test
    @DisplayName("isSelectedNextLevel() and isSelectedQuitToMainMenu() checks")
    void testSelections() {
        // By default, currentEntry=0 => nextLevel
        assertTrue(menu.isSelectedNextLevel());
        assertFalse(menu.isSelectedQuitToMainMenu());

        // next => currentEntry=1 => menu
        menu.nextEntry();
        assertFalse(menu.isSelectedNextLevel());
        assertTrue(menu.isSelectedQuitToMainMenu());
    }

    @Test
    @DisplayName("nextEntry() / previousEntry() wrap around properly")
    void testEntryNavigation() {
        assertTrue(menu.isSelected(0));

        menu.nextEntry();
        assertTrue(menu.isSelected(1));

        menu.nextEntry();
        assertTrue(menu.isSelected(0));

        menu.previousEntry();
        assertTrue(menu.isSelected(1));
    }
}
