package badIceCream.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InstructionsMenuSecondPageTest {

    private InstructionsMenuSecondPage menu;

    @BeforeEach
    public  void setUp() {
        menu = new InstructionsMenuSecondPage();
    }

    @Test
    @DisplayName("Constructor has only 1 entry: Quit to Main Menu")
    public void testConstructor() {
        assertEquals(1, menu.getNumberEntries());
        assertEquals("Quit to Main Menu", menu.getEntry(0));
    }

    @Test
    @DisplayName("Single entry means selection never changes")
    public void testSingleEntry() {
        assertTrue(menu.isSelected(0));
        menu.nextEntry();
        assertTrue(menu.isSelected(0));
        menu.previousEntry();
        assertTrue(menu.isSelected(0));
    }
}
