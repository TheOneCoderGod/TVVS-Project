package badIceCream.viewer;

import badIceCream.GUI.Graphics;

import java.io.IOException;

public abstract class Viewer<T> {
    protected final T model;
    public Viewer(T model){
        this.model = model;
    }
    public T getModel(){
        return model;
    }
    public synchronized void draw(Graphics gui) throws IOException{
        gui.clear();
        drawElements(gui);
        gui.refresh();
    }
    protected abstract void drawElements(Graphics gui) throws IOException;

}
