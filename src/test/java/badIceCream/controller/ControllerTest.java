package badIceCream.controller;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.FontFormatException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private TestController controller;
    private Object model;

    @BeforeEach
    public void setUp() {
        model = new Object();
        controller = new TestController(model);
    }

    @Test
    public void testGetModel() {
        assertEquals(model, controller.getModel());
    }

    @Test
    public void testStep() throws FontFormatException, IOException {
        Game game = new Game();
        GUI.ACTION action = GUI.ACTION.UP;
        long time = System.currentTimeMillis();

        assertDoesNotThrow(() -> controller.step(game, action, time));
    }

    @Test
    public void testStepMonsters() throws IOException {
        long time = System.currentTimeMillis();

        assertDoesNotThrow(() -> controller.stepMonsters(time));
    }
}