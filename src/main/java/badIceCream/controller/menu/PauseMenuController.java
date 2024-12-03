package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.MainMenu;
import badIceCream.model.menu.PauseMenu;
import badIceCream.states.GameState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;

import java.io.IOException;

public class PauseMenuController extends MenuController<PauseMenu> {
    private final GameState parent;

    public PauseMenuController(PauseMenu menu, GameState parent) {
        super(menu);
        this.parent = parent;
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
                if (getModel().isSelectedResume()) {
                    game.setState(parent, Type.game, parent.getModel().getWidth(), parent.getModel().getHeight());
                }
                if (getModel().isSelectedMenu()) game.setState(new MainMenuState(new MainMenu(), game.getState().getLevel()), Type.nulo, 0, 0);
                // fall through
            default:
        }
    }
}
