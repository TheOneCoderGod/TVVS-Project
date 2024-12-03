package badIceCream.viewer;


import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.HotFloor;

public class HotFloorViewer implements ElementViewer<HotFloor> {
    @Override
    public void draw(HotFloor hotFloor, Graphics gui, int type) {
        gui.drawHotFloor(hotFloor.getPosition(), type);
    }

}
