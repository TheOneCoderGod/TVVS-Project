package badIceCream.GUI;

import badIceCream.model.Position;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;


public class Graphics {
    private GUI gui;
    private final Screen screen;

    public Graphics(GUI gui) throws IOException {
        this.gui = gui;
        screen = gui.createScreen(gui.createTerminal());
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public GUI getGui(){
        return gui;
    }

    public void drawIceCream(Position position, GUI.ACTION action, boolean strawberry) {
        char c;
        switch (action) {
            case UP: {c = '7'; break;}
            case LEFT: {c = ':'; break;}
            case RIGHT: {c = '9'; break;}
            default: c = '8';
        }
        String color = strawberry ? "#48DEFF" : "#FFFFFF";
        drawCharacter(position.getX(), position.getY(), c , color);
    }
    public void drawStoneWall(Position position) {
        drawCharacter(position.getX(), position.getY(), 'G', "#696969");
    }
    public void drawIceWall(Position position, int type){
        switch (type) {
            case 1: drawCharacter(position.getX(), position.getY(), 'F', "#87CEFA");
            break;
            case 3: drawCharacter(position.getX(), position.getY(), 'f', "#87CEFA");
            break;
            case 4: drawCharacter(position.getX(), position.getY(), 'h', "#87CEFA");
            break;
            case 5: drawCharacter(position.getX(), position.getY(), 'g', "#87CEFA");
            break;
            case 6: drawCharacter(position.getX(), position.getY(), 'i', "#87CEFA");
            break;
            case 7: drawCharacter(position.getX(), position.getY(), 'e', "#87CEFA");
            break;
            case 8: drawCharacter(position.getX(), position.getY(), 'k', "#87CEFA");
            break;
            case 9: drawCharacter(position.getX(), position.getY(), 'l', "#87CEFA");
            break;
            case 10: drawCharacter(position.getX(), position.getY(), 'n', "#87CEFA");
            break;
            case 11: drawCharacter(position.getX(), position.getY(), 'm', "#87CEFA");
            break;
        }
    }

    public void drawDefaultMonster(Position position, GUI.ACTION action){
        char c;
        switch (action) {
            case UP: { c = '4'; break;}
            case LEFT: {c = '~'; break;}
            case RIGHT: {c = 'È'; break;}
            default: c = 'Y';

        }
        drawCharacter(position.getX(), position.getY(), c, "#00FF00");
    }

    public void drawJumperMonster(Position position, GUI.ACTION action) {
        char c;
        switch (action) {
            case UP: { c = '/'; break;}
            case LEFT: {c = 'y'; break;}
            case RIGHT: {c = 'è'; break;}
            default: c = 'T';

        }
        drawCharacter(position.getX(), position.getY(), c, "#FF3333");
    }

    public void drawRunnerMonster(Position position, GUI.ACTION action, boolean runner) {
        char c;
        switch (action) {
            case UP: { c = runner ? '3' : '1'; break;}
            case LEFT: {c = runner ? 'X' : 'W'; break;}
            case RIGHT: {c = runner ? '}' : '2'; break;}
            default: c = runner ? '|' : 'V';

        }
        String color = runner ? "#FF0000" : "#FFFF66";
        drawCharacter(position.getX(), position.getY(), c, color);
    }

    public void drawWallBreakerMonster(Position position, GUI.ACTION action) {
        char c;
        switch (action) {
            case UP: { c = '0'; break;}
            case LEFT: {c = 'é'; break;}
            case RIGHT: {c = 'z'; break;}
            default: c = 'U';

        }
        drawCharacter(position.getX(), position.getY(), c, "#FF99FF");
    }


    public void drawAppleFruit(Position position) {
        drawCharacter(position.getX(), position.getY(), ']', "#FF0000");
    }


    public void drawBananaFruit(Position position) {
        drawCharacter(position.getX(), position.getY(), 'a', "#FFFF00");
    }


    public void drawPineappleFruit(Position position) {
        drawCharacter(position.getX(), position.getY(), '^', "#FFFF66");

    }

    public void drawCherryFruit(Position position) {
        drawCharacter(position.getX(), position.getY(), '\\', "#FF0000");
    }


    public void drawStrawberryFruit(Position position) {
        drawCharacter(position.getX(), position.getY(), '_', "#FF0000");
    }


    public void drawHotFloor(Position position, int type) {
        switch (type) {
            case 1: drawCharacter(position.getX(), position.getY(), 'w', "#e14750");
                break;
            case 2: drawCharacter(position.getX(), position.getY(), 'd', "#e14750");
                break;
            case 3: drawCharacter(position.getX(), position.getY(), 'c', "#e14750");
                break;
            case 4: drawCharacter(position.getX(), position.getY(), 'x', "#e14750");
                break;
            case 5: drawCharacter(position.getX(), position.getY(), '%', "#e14750");
                break;
            case 6: drawCharacter(position.getX(), position.getY(), '(', "#e14750");
                break;
            case 7: drawCharacter(position.getX(), position.getY(), '\'', "#e14750");
                break;
            case 8: drawCharacter(position.getX(), position.getY(), '&', "#e14750");
                break;
            case 9: drawCharacter(position.getX(), position.getY(), ')', "#e14750");
                break;
            case 10: drawCharacter(position.getX(), position.getY(), '+', "#e14750");
                break;
            case 11: drawCharacter(position.getX(), position.getY(), ',', "#e14750");
                break;
            case 12: drawCharacter(position.getX(), position.getY(), '*', "#e14750");
                break;
            case 13: drawCharacter(position.getX(), position.getY(), '-', "#e14750");
                break;
            case 14: drawCharacter(position.getX(), position.getY(), '.', "#e14750");
                break;
            case 15: drawCharacter(position.getX(), position.getY(), 'S', "#e14750");
                break;
            case 16: drawCharacter(position.getX(), position.getY(), 'R', "#e14750");
                break;
            case 17: drawCharacter(position.getX(), position.getY(), '!', "#e14750");
                break;
            case 18: drawCharacter(position.getX(), position.getY(), '#', "#e14750");
                break;
            case 19: drawCharacter(position.getX(), position.getY(), '"', "#e14750");
                break;
            case 20: drawCharacter(position.getX(), position.getY(), '$', "#e14750");
                break;
            case 21: drawCharacter(position.getX(), position.getY(), 'C', "#e14750");
                break;
            case 22: drawCharacter(position.getX(), position.getY(), '@', "#e14750");
                break;
            case 23: drawCharacter(position.getX(), position.getY(), 'D', "#e14750");
                break;
            case 24: drawCharacter(position.getX(), position.getY(), 'B', "#e14750");
                break;
            case 25: drawCharacter(position.getX(), position.getY(), 'A', "#e14750");
                break;
            case 26: drawCharacter(position.getX(), position.getY(), ';', "#e14750");
                break;
            case 27: drawCharacter(position.getX(), position.getY(), '=', "#e14750");
                break;
            case 28: drawCharacter(position.getX(), position.getY(), '>', "#e14750");
                break;
            case 29: drawCharacter(position.getX(), position.getY(), '<', "#e14750");
                break;
            default: drawCharacter(position.getX(), position.getY(), 'b', "#e14750");
        }
    }

    public void drawCharacters() {
        drawCharacter(33, 15, 'Ê', "#00FF00");
        drawCharacter(33, 18, 'À', "#00FF00");
        drawCharacter(33, 21, 'Á', "#00FF00");
        drawCharacter(33, 24, 'È', "#00FF00");
        drawCharacter(33, 27, 'É', "#00FF00");
        drawCharacter(33, 30, 'Í', "#00FF00");
    }


    public void clear() {screen.clear();}
    public void refresh() throws IOException {screen.refresh();}
    public void close() throws IOException {
        screen.close();
    }
    void drawCharacter(int a, int b, char c, String color) {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.Factory.fromString(color));
        textGraphics.putString(a, b, "" + c);
    }

    public void drawText(Position position, String text, String color){
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.Factory.fromString(color));
        textGraphics.putString(position.getX(), position.getY(), text);
    }

    public GUI.ACTION getNextAction() throws IOException {
        KeyStroke keyStroke = screen.pollInput();
        if(keyStroke == null) return GUI.ACTION.NONE;
        if(keyStroke.getKeyType() == KeyType.ArrowDown) return GUI.ACTION.DOWN;
        if(keyStroke.getKeyType() == KeyType.ArrowUp) return GUI.ACTION.UP;
        if(keyStroke.getKeyType() == KeyType.ArrowRight) return GUI.ACTION.RIGHT;
        if(keyStroke.getKeyType() == KeyType.ArrowLeft) return GUI.ACTION.LEFT;
        if(keyStroke.getKeyType() == KeyType.Enter) return GUI.ACTION.SELECT;
        if(keyStroke.getKeyType() == KeyType.Escape) return GUI.ACTION.PAUSE;
        if(keyStroke.getCharacter() == ' ') return GUI.ACTION.SPACE;

        return GUI.ACTION.NONE;
    }
}
