package badIceCream.controller.menu;

import badIceCream.controller.Controller;

public abstract class MenuController<T> extends Controller<T> {
    public MenuController(T model) {
        super(model);
    }

}
