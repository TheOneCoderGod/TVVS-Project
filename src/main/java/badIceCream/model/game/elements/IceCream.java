package badIceCream.model.game.elements;

import badIceCream.GUI.GUI;

public class IceCream extends Element {
    private boolean alive;
    private GUI.ACTION lastMovement = GUI.ACTION.DOWN;
    private boolean strawberry;
    public IceCream(int x, int y) {
        super(x, y);
        alive = true;
        strawberry = false;
    }

    @Override
    public int getType() {
        return 1;
    }

    public boolean getAlive() {
        return alive;
    }

    public synchronized void changeAlive() {
        this.alive = !this.alive;
    }

    public boolean isStrawberryActive() {
        return this.strawberry;
    }

    public void setStrawberry(boolean bool) {
        this.strawberry = bool;
    }

    public void setLastMovement(GUI.ACTION lastMovement) {
        this.lastMovement = lastMovement;
    }
    public GUI.ACTION getLastMovement() {
        return this.lastMovement;
    }
}
