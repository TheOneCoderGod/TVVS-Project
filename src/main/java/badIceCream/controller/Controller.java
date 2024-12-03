package badIceCream.controller;

import badIceCream.GUI.GUI;
import badIceCream.Game;

import java.io.IOException;

public abstract class Controller<T> {
    private final T model;


    public Controller(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public abstract void step(Game game, GUI.ACTION action, long time) throws IOException;
    public void stepMonsters(long time) throws IOException {}
}