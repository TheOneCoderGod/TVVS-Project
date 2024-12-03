package badIceCream.controller;

import badIceCream.GUI.GUI;
import badIceCream.Game;

import java.io.IOException;

public class TestController extends Controller<Object> {

    public TestController(Object model) {
        super(model);
    }

    @Override
    public void step(Game game, GUI.ACTION action, long time) throws IOException {
        // Implementation for testing
    }

    @Override
    public void stepMonsters(long time) throws IOException {
        // Implementation for testing
    }
}