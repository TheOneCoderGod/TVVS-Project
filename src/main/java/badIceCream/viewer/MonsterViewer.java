package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.monsters.Monster;

public class MonsterViewer implements ElementViewer<Monster>{
    @Override
    public void draw(Monster monster, Graphics gui, int type){
        switch (type) {
            case 1: gui.drawDefaultMonster(monster.getPosition(), monster.getLastAction());
                break;
            case 2: gui.drawJumperMonster(monster.getPosition(), monster.getLastAction());
                break;
            case 3: gui.drawRunnerMonster(monster.getPosition(), monster.getLastAction(), monster.isRunning());
                break;
            case 4: gui.drawWallBreakerMonster(monster.getPosition(), monster.getLastAction());
                break;
        }
    }
}
