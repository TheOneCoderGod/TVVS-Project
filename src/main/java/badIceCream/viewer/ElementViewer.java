package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.Element;

public interface ElementViewer<T extends Element> {
    void draw(T element, Graphics gui, int type);

}
