package badIceCream.model.game.elements.monsters;

public class WallBreakerMonster extends Monster {
    public WallBreakerMonster(int x, int y) {
        super(x, y);
    }
    @Override
    public int getType() {return 4;}
}
