package badIceCream.model.menu;

import java.util.Arrays;

public class PauseMenu extends Menu {
    public PauseMenu() {
        super(Arrays.asList("RESUME", " MENU"));
    }
    public boolean isSelectedMenu() {
        return isSelected(1);
    }
    public boolean isSelectedResume() {
        return isSelected(0);
    }
}
