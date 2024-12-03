package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.IceCream;

public class IceCreamViewer implements ElementViewer<IceCream>{
    @Override
    public void draw(IceCream ice, Graphics gui, int type){
        gui.drawIceCream(ice.getPosition(), ice.getLastMovement(), ice.isStrawberryActive());
    }

}
