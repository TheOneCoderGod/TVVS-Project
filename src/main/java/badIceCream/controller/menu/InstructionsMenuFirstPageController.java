package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import badIceCream.model.menu.MainMenu;
import badIceCream.states.InstructionsMenuSecondPageState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;

import java.io.IOException;

public class InstructionsMenuFirstPageController extends MenuController<InstructionsMenuFirstPage> {
    public InstructionsMenuFirstPageController(InstructionsMenuFirstPage menu) {
        super(menu);
    }
    @Override
    public void step(Game game, GUI.ACTION action, long time) throws IOException {
        if (action == GUI.ACTION.PAUSE) {
            game.setState(new MainMenuState(new MainMenu(), game.getState().getLevel()), Type.nulo, 0,0);
        }
        else if (action == GUI.ACTION.RIGHT) {
            game.setState(new InstructionsMenuSecondPageState(new InstructionsMenuSecondPage(), game.getState().getLevel()), Type.nulo, 0,0);
        }
    }
}
