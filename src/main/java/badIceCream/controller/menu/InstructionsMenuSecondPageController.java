package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import badIceCream.model.menu.MainMenu;
import badIceCream.states.InstructionsMenuFirstPageState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;

import java.io.IOException;

public class InstructionsMenuSecondPageController extends MenuController<InstructionsMenuSecondPage> {

    public InstructionsMenuSecondPageController(InstructionsMenuSecondPage menu) {super(menu);}

    @Override
    public void step(Game game, GUI.ACTION action, long time) throws IOException {
        if (action == GUI.ACTION.PAUSE) {
            game.setState(new MainMenuState(new MainMenu(), game.getState().getLevel()), Type.nulo, 0, 0);
        }
        else if (action == GUI.ACTION.LEFT) {
            game.setState(new InstructionsMenuFirstPageState(new InstructionsMenuFirstPage(), game.getState().getLevel()), Type.nulo, 0 ,0);
        }
    }
}
