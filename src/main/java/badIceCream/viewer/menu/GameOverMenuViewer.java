package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.GameOverMenu;
import badIceCream.viewer.Viewer;

public class GameOverMenuViewer extends Viewer<GameOverMenu> {
    public GameOverMenuViewer(GameOverMenu menu) {
        super(menu);
    }

    void drawTitle(Graphics gui) {
        String s0 = "  _____                                 ____                            ";
        String s1 = " / ____|                               / __ \\                           ";
        String s2 = "| |  __    __ _   _ __ ___     ___    | |  | | __   __   ___   _ __    ";
        String s3 = "| | |_ |  / _` | | '_ ` _ \\   / _ \\   | |  | | \\ \\ / /  / _ \\ | '__|   ";
        String s4 = "| |__| | | (_| | | | | | | | |  __/   | |__| |  \\ V /  |  __/ | |      ";
        String s5 = " \\_____|  \\__,_| |_| |_| |_|  \\___|    \\____/    \\_/    \\___| |_|       ";


        gui.drawText(new Position(37, 4), s0, "#f6160f");
        gui.drawText(new Position(37, 5), s1, "#f6160f");
        gui.drawText(new Position(37, 6), s2, "#f6160f");
        gui.drawText(new Position(37, 7), s3, "#f6160f");
        gui.drawText(new Position(37, 8), s4, "#f6160f");
        gui.drawText(new Position(37, 9), s5, "#f6160f");
    }

    void drawSnowflake(Graphics gui) {
        String s0 = "   ..    ..          ";
        String s1 = "   '\\    /'         ";
        String s2 = "     \\\\//          ";
        String s3 = "_.__\\\\\\///__._    ";
        String s4 = " '  ///\\\\\\  '     ";
        String s5 = "     //\\\\          ";
        String s6 = "   ./    \\.         ";
        String s7 = "   ''    ''          ";

        gui.drawText(new Position(15, 25), s0, "#ffffff");
        gui.drawText(new Position(15, 26), s1, "#ffffff");
        gui.drawText(new Position(15, 27), s2, "#ffffff");
        gui.drawText(new Position(15, 28), s3, "#ffffff");
        gui.drawText(new Position(15, 29), s4, "#ffffff");
        gui.drawText(new Position(15, 30), s5, "#ffffff");
        gui.drawText(new Position(15, 31), s6, "#ffffff");
        gui.drawText(new Position(15, 32), s7, "#ffffff");

        gui.drawText(new Position(5, 1), s0, "  #ffffff  ");
        gui.drawText(new Position(5, 2), s1, "  #ffffff  ");
        gui.drawText(new Position(5, 3), s2, "  #ffffff  ");
        gui.drawText(new Position(5, 4), s3, "  #ffffff  ");
        gui.drawText(new Position(5, 5), s4, "  #ffffff  ");
        gui.drawText(new Position(5, 6), s5, "  #ffffff  ");
        gui.drawText(new Position(5, 7), s6, " #ffffff ");
        gui.drawText(new Position(5, 8), s7, " #ffffff ");

        gui.drawText(new Position(70, 33), s0, "  #ffffff  ");
        gui.drawText(new Position(70, 34), s1, "  #ffffff  ");
        gui.drawText(new Position(70, 35), s2, "  #ffffff  ");
        gui.drawText(new Position(70, 36), s3, "  #ffffff  ");
        gui.drawText(new Position(70, 37), s4, "  #ffffff  ");
        gui.drawText(new Position(70, 38), s5, "  #ffffff  ");
        gui.drawText(new Position(70, 39), s6, " #ffffff ");
        gui.drawText(new Position(70, 40), s7, " #ffffff ");

        gui.drawText(new Position(100, 20), s0, "  #ffffff  ");
        gui.drawText(new Position(100, 21), s1, "  #ffffff  ");
        gui.drawText(new Position(100, 22), s2, "  #ffffff  ");
        gui.drawText(new Position(100, 23), s3, "  #ffffff  ");
        gui.drawText(new Position(100, 24), s4, "  #ffffff  ");
        gui.drawText(new Position(100, 25), s5, "  #ffffff  ");
        gui.drawText(new Position(100, 26), s6, " #ffffff ");
        gui.drawText(new Position(100, 27), s7, " #ffffff ");

        gui.drawText(new Position(120, 7), s0, "  #ffffff  ");
        gui.drawText(new Position(120, 8), s1, "  #ffffff  ");
        gui.drawText(new Position(120, 9), s2, "  #ffffff  ");
        gui.drawText(new Position(120, 10), s3, "  #ffffff  ");
        gui.drawText(new Position(120, 11), s4, "  #ffffff  ");
        gui.drawText(new Position(120, 12), s5, "  #ffffff  ");
        gui.drawText(new Position(120, 13), s6, " #ffffff ");
        gui.drawText(new Position(120, 14), s7, " #ffffff ");
    }

    @Override
    public void drawElements(Graphics gui) {
        drawTitle(gui);
        drawSnowflake(gui);

        int height = 17;

        for(int i = 0; i < getModel().getNumberEntries(); i++) {
            gui.drawText(new Position(65, height + i), getModel().getEntry(i), getModel().isSelected(i) ? "#D1D100" : "#FFFFFF" );
            height += 3;
        }
    }
}
