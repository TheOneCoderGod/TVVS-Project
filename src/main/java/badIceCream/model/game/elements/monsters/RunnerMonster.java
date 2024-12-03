package badIceCream.model.game.elements.monsters;


public class RunnerMonster extends Monster {
    public RunnerMonster(int x, int y) {
        super(x, y);
    }
    @Override
    public void startRunning() {this.running = true;}
    @Override
    public void stopRunning() {this.running = false;}
    @Override
    public int getType() {return 3;}
}
