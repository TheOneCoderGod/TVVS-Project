package badIceCream.states;

import badIceCream.controller.menu.SelectLevelMenuController;
import badIceCream.model.menu.SelectLevelMenu;
import badIceCream.viewer.menu.SelectLevelMenuViewer;

public class SelectLevelMenuState extends MenuState<SelectLevelMenu> {
    public SelectLevelMenuState(SelectLevelMenu model, int level) {
        super(model, new SelectLevelMenuController(model), new SelectLevelMenuViewer(model), level);
    }
    public SelectLevelMenuState(SelectLevelMenu model, SelectLevelMenuController controller, SelectLevelMenuViewer viewer, int level) {
        super(model, controller, viewer, level);
    }
}
