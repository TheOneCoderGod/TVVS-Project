package badIceCream.utils;

import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    private Node node1;
    private Node node2;
    private Node node3;

    @BeforeEach
    public void setUp() {
        // We don't have private classes in Node, so we just test the public API
        Position pos1 = new Position(2, 2);
        Position pos2 = new Position(3, 3);

        node1 = new Node(pos1, 2, 5, null);  // total cost+heur=7
        node2 = new Node(pos2, 3, 2, node1); // total = 5
        node3 = new Node(pos2, 4, 1, node1); // total = 5
    }

    @Test
    @DisplayName("compareTo() returns negative, zero, or positive based on cost+heuristic")
    public void testCompareTo() {
        // node1 = total=7, node2=node3 = total=5
        // node2 ~ node3 => both 5
        assertTrue(node2.compareTo(node1) < 0, "node2 < node1 because 5 < 7");
        assertEquals(0, node2.compareTo(node3), "node2 == node3 because both total=5");
        assertTrue(node1.compareTo(node2) > 0, "node1 > node2 because 7 > 5");
    }

    @Test
    @DisplayName("Constructor stores position, cost, heuristic, parent")
    public void testConstructor() {
        assertEquals(2, node1.position.getX());
        assertEquals(2, node1.position.getY());
        assertEquals(2, node1.cost);
        assertEquals(5, node1.heuristic);
        assertNull(node1.parent);

        assertEquals(node1, node2.parent, "node2 has node1 as parent");
    }
}
