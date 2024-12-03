package badIceCream.model.game.elements.monsters;

import badIceCream.GUI.GUI;
import badIceCream.model.game.elements.Element;

public abstract class Monster extends Element {
    protected boolean running = false;
    protected GUI.ACTION lastAction = GUI.ACTION.DOWN;

    public Monster(int x, int y) {
        super(x, y);
    }

    @Override
    abstract public int getType();

    public GUI.ACTION getLastAction() {return lastAction;}

    public void setLastAction(GUI.ACTION action) {
        lastAction = action;
    }

    public void startRunning() {}
    public void stopRunning() {}

    public boolean isRunning() {return running;}
}
