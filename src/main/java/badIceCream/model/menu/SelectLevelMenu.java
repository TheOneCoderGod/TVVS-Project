package badIceCream.model.menu;

import java.util.Arrays;

public class SelectLevelMenu extends Menu{
    public SelectLevelMenu() {
        super(Arrays.asList("1", "2", "3", "4", "5"));
    }
    public boolean isSelectedLevel1() {
        return isSelected(0);
    }
    public boolean isSelectedLevel2() {
        return isSelected(1);
    }
    public boolean isSelectedLevel3() {
        return isSelected(2);
    }
    public boolean isSelectedLevel4() {
        return isSelected(3);
    }
    public boolean isSelectedLevel5() {
        return isSelected(4);
    }
}
