package badIceCream.model.game.elements.fruits;

import badIceCream.model.game.elements.Element;

public abstract class Fruit extends Element {
    public Fruit(int x, int y) {
        super(x, y);
    }
    @Override
    abstract public int getType();
}
