package badIceCream.GUI;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;


public abstract class GUI {

    protected abstract Terminal createTerminal() throws IOException;

    protected Screen createScreen(Terminal terminal) throws IOException{
        final Screen screen;
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }

    public enum ACTION {UP, RIGHT, DOWN, LEFT, SPACE, NONE, SELECT, PAUSE}
}
