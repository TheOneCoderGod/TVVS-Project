package badIceCream.states;

import badIceCream.GUI.GUI;
import badIceCream.GUI.Graphics;
import badIceCream.Game;
import badIceCream.controller.Controller;
import badIceCream.viewer.Viewer;

import java.io.IOException;

public abstract class State<T> {
    private final T model;
    private final Controller<T> controller;
    private final Viewer<T> viewer;
    private int level;

    public State(T model, Controller<T> controller, Viewer<T> viewer, int level) {
        this.model = model;
        this.viewer = viewer;
        this.controller = controller;
        this.level = level;
    }

    public T getModel() {
        return model;
    }

    public synchronized void step(Game game, Graphics graphics, long time) throws IOException {
        GUI.ACTION action = graphics.getNextAction();
        controller.step(game, action, time);
        viewer.draw(graphics);
    }

    public void stepMonsters(long time) throws IOException {
        controller.stepMonsters(time);
    }

    public int getLevel() {
        return level;
    }

    public void increaseLevel() {
        if (this.level < 5) {
            this.level++;
        }
    }
}