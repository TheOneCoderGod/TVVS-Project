package badIceCream.controller.game;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;


public class IceCreamController extends GameController {

        private final IceCream iceCream;
        private GUI.ACTION lastMovement = GUI.ACTION.DOWN;
        long lastTime = 0;
        public IceCreamController(Arena arena) {
            super(arena);
            this.iceCream = arena.getIceCream();
        }

        private void moveIceCreamLeft() {
            moveIceCream(getModel().getIceCream().getPosition().getLeft(), GUI.ACTION.LEFT, System.currentTimeMillis());
        }

        private void moveIceCreamRight() {
            moveIceCream(getModel().getIceCream().getPosition().getRight(), GUI.ACTION.RIGHT, System.currentTimeMillis());
        }

        private void moveIceCreamUp() {
            moveIceCream(getModel().getIceCream().getPosition().getUp(), GUI.ACTION.UP, System.currentTimeMillis());
        }

        private void moveIceCreamDown() {
            moveIceCream(getModel().getIceCream().getPosition().getDown(), GUI.ACTION.DOWN, System.currentTimeMillis());
        }

        public int eatFruit() {
            return getModel().eatFruit(getModel().getIceCream().getPosition());
        }

        private void moveIceCream(Position position, GUI.ACTION last, long time) {

            if (getModel().isEmpty(position) && time - lastTime >= 15) {
                iceCream.setPosition(position);

                if (!iceCream.isStrawberryActive() && getModel().hasMonster(position) != null) iceCream.changeAlive();
            }
            iceCream.setLastMovement(last);
            lastMovement = last;
        }

        public void powerIceCream() {
            getModel().powerIceCream(lastMovement);
        }

        @Override
        public void step(Game game, GUI.ACTION action, long time) {
            if (action == GUI.ACTION.UP) moveIceCreamUp();
            if (action == GUI.ACTION.RIGHT) moveIceCreamRight();
            if (action == GUI.ACTION.DOWN) moveIceCreamDown();
            if (action == GUI.ACTION.LEFT) moveIceCreamLeft();
            if (action == GUI.ACTION.SPACE) powerIceCream();
        }
}

