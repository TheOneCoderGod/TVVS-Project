package badIceCream.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PauseMenuTest {

    private PauseMenu menu;

    @BeforeEach
    void setUp() {
        menu = new PauseMenu();
    }

    @Test
    @DisplayName("Constructor has 2 entries: RESUME, MENU")
    void testConstructor() {
        assertEquals(2, menu.getNumberEntries());
        assertEquals("RESUME", menu.getEntry(0));
        assertEquals(" MENU", menu.getEntry(1));
    }

    @Test
    @DisplayName("isSelectedResume() and isSelectedMenu() checks")
    void testSelections() {
        // By default, currentEntry=0 => RESUME
        assertTrue(menu.isSelectedResume());
        assertFalse(menu.isSelectedMenu());

        // next => currentEntry=1 => MENU
        menu.nextEntry();
        assertFalse(menu.isSelectedResume());
        assertTrue(menu.isSelectedMenu());
    }

    @Test
    @DisplayName("nextEntry() / previousEntry() wrap with 2 entries")
    void testNavigation() {
        assertTrue(menu.isSelected(0));
        menu.nextEntry();
        assertTrue(menu.isSelected(1));
        menu.nextEntry();
        assertTrue(menu.isSelected(0));
    }
}
