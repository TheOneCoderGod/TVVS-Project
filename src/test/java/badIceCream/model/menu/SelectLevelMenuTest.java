package badIceCream.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectLevelMenuTest {

    private SelectLevelMenu menu;

    @BeforeEach
    void setUp() {
        menu = new SelectLevelMenu();
    }

    @Test
    @DisplayName("Constructor has 5 entries: 1,2,3,4,5")
    void testConstructor() {
        assertEquals(5, menu.getNumberEntries());
        assertEquals("1", menu.getEntry(0));
        assertEquals("2", menu.getEntry(1));
        assertEquals("3", menu.getEntry(2));
        assertEquals("4", menu.getEntry(3));
        assertEquals("5", menu.getEntry(4));
    }

    @Test
    @DisplayName("isSelectedLevel1..5 checks")
    void testSelections() {
        // default => currentEntry=0 => level1
        assertTrue(menu.isSelectedLevel1());
        assertFalse(menu.isSelectedLevel2());
        assertFalse(menu.isSelectedLevel3());
        assertFalse(menu.isSelectedLevel4());
        assertFalse(menu.isSelectedLevel5());

        // move to next => index=1 => level2
        menu.nextEntry();
        assertFalse(menu.isSelectedLevel1());
        assertTrue(menu.isSelectedLevel2());

        // next => index=2 => level3
        menu.nextEntry();
        assertTrue(menu.isSelectedLevel3());
        assertFalse(menu.isSelectedLevel4());
        // next => index=3 => level4
        menu.nextEntry();
        assertTrue(menu.isSelectedLevel4());
        // next => index=4 => level5
        menu.nextEntry();
        assertTrue(menu.isSelectedLevel5());
    }

    @Test
    @DisplayName("Wrapping around 5 entries with next/previous")
    void testEntryWrap() {
        assertTrue(menu.isSelected(0));
        menu.nextEntry(); // 1
        menu.nextEntry(); // 2
        menu.nextEntry(); // 3
        menu.nextEntry(); // 4
        assertTrue(menu.isSelected(4));

        // next => wrap to 0
        menu.nextEntry();
        assertTrue(menu.isSelected(0));

        // previous => wrap back to 4
        menu.previousEntry();
        assertTrue(menu.isSelected(4));
    }
}
