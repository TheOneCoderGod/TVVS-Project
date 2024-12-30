package badIceCream.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOverMenuTest {

    private GameOverMenu menu;

    @BeforeEach
    void setUp() {
        menu = new GameOverMenu();
    }

    @Test
    @DisplayName("Constructor should have 2 entries: PLAY AGAIN, MENU")
    void testConstructor() {
        assertEquals(2, menu.getNumberEntries());
        assertEquals("PLAY AGAIN", menu.getEntry(0));
        assertEquals("   MENU", menu.getEntry(1));
    }

    @Test
    @DisplayName("isSelectedPlayAgain() and isSelectedQuitToMainMenu() checks")
    void testSelections() {
        // By default, currentEntry = 0
        assertTrue(menu.isSelectedPlayAgain());
        assertFalse(menu.isSelectedQuitToMainMenu());

        // Move to next entry => currentEntry = 1
        menu.nextEntry();
        assertFalse(menu.isSelectedPlayAgain());
        assertTrue(menu.isSelectedQuitToMainMenu());
    }

    @Test
    @DisplayName("nextEntry() and previousEntry() wrap around")
    void testNextPreviousEntry() {
        // Initially at index 0
        assertTrue(menu.isSelected(0));

        // next -> index 1
        menu.nextEntry();
        assertTrue(menu.isSelected(1));

        // next -> back to 0
        menu.nextEntry();
        assertTrue(menu.isSelected(0));

        // prev -> back to 1
        menu.previousEntry();
        assertTrue(menu.isSelected(1));
    }
}
