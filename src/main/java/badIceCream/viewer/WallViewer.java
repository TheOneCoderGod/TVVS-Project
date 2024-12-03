package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.Wall;


public class WallViewer implements ElementViewer<Wall> {
    @Override
    public void draw(Wall wall, Graphics gui, int type) {
        switch (type) {
            case 1, 3, 4, 5, 6, 7, 8, 9, 10, 11: gui.drawIceWall(wall.getPosition(), type);
                break;
            case 2: gui.drawStoneWall(wall.getPosition());
        }
    }

}

