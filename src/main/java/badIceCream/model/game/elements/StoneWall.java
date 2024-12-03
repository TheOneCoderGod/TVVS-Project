package badIceCream.model.game.elements;

public class StoneWall extends Wall{
    public StoneWall(int x, int y) {
        super(x, y);
    }

    @Override
    public int getType() {return 2;}
}
