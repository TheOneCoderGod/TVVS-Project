package badIceCream.model.game.elements;

public class IceWall extends Wall{
    public IceWall(int x, int y) {
        super(x, y);
    }
    @Override
    public int getType() {return 1;}
}
