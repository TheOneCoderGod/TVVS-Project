package badIceCream.model.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @Test
    @DisplayName("Menu constructor sets entries, nextEntry() and previousEntry() wrap around")
    void testMenuBasic() {
        Menu menu = new Menu(List.of("Option1", "Option2", "Option3"));
        assertEquals(3, menu.getNumberEntries());
        assertEquals("Option1", menu.getEntry(0));
        assertEquals("Option2", menu.getEntry(1));
        assertEquals("Option3", menu.getEntry(2));

        // Start at currentEntry=0
        assertTrue(menu.isSelected(0));
        assertFalse(menu.isSelected(1));

        // next => 1
        menu.nextEntry();
        assertTrue(menu.isSelected(1));

        // next => 2
        menu.nextEntry();
        assertTrue(menu.isSelected(2));

        // next => wraps to 0
        menu.nextEntry();
        assertTrue(menu.isSelected(0));

        // prev => wraps to 2
        menu.previousEntry();
        assertTrue(menu.isSelected(2));
    }

    @Test
    @DisplayName("Menu isSelected(i) returns true only if currentEntry == i")
    void testIsSelected() {
        Menu menu = new Menu(List.of("A","B"));
        assertTrue(menu.isSelected(0));
        assertFalse(menu.isSelected(1));

        menu.nextEntry();
        assertFalse(menu.isSelected(0));
        assertTrue(menu.isSelected(1));
    }
}
