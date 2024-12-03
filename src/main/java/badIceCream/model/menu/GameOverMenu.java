package badIceCream.model.menu;

import java.util.Arrays;

public class GameOverMenu extends Menu {
    public GameOverMenu() {
        super(Arrays.asList("PLAY AGAIN", "   MENU"));
    }
    public boolean isSelectedPlayAgain() {
        return isSelected(0);
    }
    public boolean isSelectedQuitToMainMenu() {
        return isSelected(1);
    }
}