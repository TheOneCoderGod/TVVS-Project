package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import badIceCream.model.menu.MainMenu;
import badIceCream.model.menu.SelectLevelMenu;
import badIceCream.states.InstructionsMenuFirstPageState;
import badIceCream.states.SelectLevelMenuState;
import badIceCream.utils.Type;

import java.io.IOException;

public class MainMenuController extends MenuController<MainMenu> {
    public MainMenuController(MainMenu menu) {
        super(menu);
    }

    @Override
    public void step(Game game, GUI.ACTION action, long time) throws IOException {
        switch (action) {
            case UP:
                getModel().previousEntry();
                break;
            case DOWN:
                getModel().nextEntry();
                break;
            case SELECT:
                if (getModel().isSelectedExit()) {
                    game.setState(null, Type.nulo, 0,0);
                }
                if (getModel().isSelectedInstructions()) game.setState(new InstructionsMenuFirstPageState(new InstructionsMenuFirstPage(), game.getState().getLevel()), Type.nulo,0,0);
                if (getModel().isSelectedStart()) game.setState(new SelectLevelMenuState(new SelectLevelMenu(), game.getState().getLevel()), Type.nulo,0,0);
                // fall through

                default:
        }
    }
}