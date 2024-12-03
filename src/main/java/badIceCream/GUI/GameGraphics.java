package badIceCream.GUI;


import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameGraphics extends GUI {
    int width, height;
    public GameGraphics(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected Terminal createTerminal() throws IOException {
        try {
            String rootPath = new File(System.getProperty("user.dir")).getPath();
            String mapLocation = rootPath + "/src/main/resources/FontForge/Untitled1.otf";
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(mapLocation));
            font = font.deriveFont(Font.PLAIN, 35);
            AWTTerminalFontConfiguration cfg = new SwingTerminalFontConfiguration(true, AWTTerminalFontConfiguration.BoldMode.NOTHING, font);

            return new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(width, height)).setTerminalEmulatorFontConfiguration(cfg).createTerminal();
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            throw new IOException("Error creating terminal with custom font.", e);
        }
    }
}
