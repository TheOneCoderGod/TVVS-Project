package badIceCream.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest {

    private MainMenu menu;

    @BeforeEach
    void setUp() {
        menu = new MainMenu();
    }

    @Test
    @DisplayName("Constructor should have 3 entries: START, INSTRUCTIONS, EXIT")
    void testConstructor() {
        assertEquals(3, menu.getNumberEntries());
        assertEquals("   START", menu.getEntry(0));
        assertEquals("INSTRUCTIONS", menu.getEntry(1));
        assertEquals("    EXIT", menu.getEntry(2));
    }

    @Test
    @DisplayName("isSelectedExit(), isSelectedInstructions(), isSelectedStart() checks")
    void testSelections() {
        // By default, currentEntry=0 => START
        assertTrue(menu.isSelectedStart());
        assertFalse(menu.isSelectedInstructions());
        assertFalse(menu.isSelectedExit());

        // next => currentEntry=1 => INSTRUCTIONS
        menu.nextEntry();
        assertFalse(menu.isSelectedStart());
        assertTrue(menu.isSelectedInstructions());
        assertFalse(menu.isSelectedExit());

        // next => currentEntry=2 => EXIT
        menu.nextEntry();
        assertFalse(menu.isSelectedStart());
        assertFalse(menu.isSelectedInstructions());
        assertTrue(menu.isSelectedExit());
    }

    @Test
    @DisplayName("nextEntry() and previousEntry() wrap around 3 entries")
    void testWrapAround() {
        // Start at index 0
        assertTrue(menu.isSelected(0));

        menu.nextEntry();  // 1
        assertTrue(menu.isSelected(1));

        menu.nextEntry();  // 2
        assertTrue(menu.isSelected(2));

        menu.nextEntry();  // wraps back to 0
        assertTrue(menu.isSelected(0));

        menu.previousEntry(); // wraps back to 2
        assertTrue(menu.isSelected(2));
    }
}
