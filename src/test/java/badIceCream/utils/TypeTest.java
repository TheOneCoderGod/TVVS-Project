package badIceCream.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    @DisplayName("Type enum has expected values")
    void testEnumValues() {
        Type[] values = Type.values();
        assertEquals(5, values.length);
        assertTrue(contains(values, Type.menu));
        assertTrue(contains(values, Type.game));
        assertTrue(contains(values, Type.nulo));
        assertTrue(contains(values, Type.gameOver));
        assertTrue(contains(values, Type.win));
    }

    @Test
    @DisplayName("valueOf(...) returns correct enum constant")
    void testValueOf() {
        assertEquals(Type.menu, Type.valueOf("menu"));
        assertEquals(Type.game, Type.valueOf("game"));
        assertEquals(Type.nulo, Type.valueOf("nulo"));
        assertEquals(Type.gameOver, Type.valueOf("gameOver"));
        assertEquals(Type.win, Type.valueOf("win"));
    }

    private boolean contains(Type[] values, Type expected) {
        for (Type t : values) {
            if (t == expected) return true;
        }
        return false;
    }
}
