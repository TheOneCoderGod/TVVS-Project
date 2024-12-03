package badIceCream.model.game.elements;

public abstract class Wall extends Element{
    public Wall(int x, int y) {
        super(x, y);
    }
    @Override
    public abstract int getType();
}
