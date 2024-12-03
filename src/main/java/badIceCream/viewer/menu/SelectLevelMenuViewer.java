package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.SelectLevelMenu;
import badIceCream.viewer.Viewer;

public class SelectLevelMenuViewer extends Viewer<SelectLevelMenu> {
    public SelectLevelMenuViewer(SelectLevelMenu menu) {
        super(menu);
    }

    void drawTitle(Graphics gui) {
        String s0 = "   _                    _    _____      _           _              ";
        String s1 = "  | |                  | |  / ____|    | |         | |             ";
        String s2 = "  | |     _____   _____| | | (___   ___| | ___  ___| |_            ";
        String s3 = "  | |    / _ \\ \\ / / _ \\ |  \\___ \\ / _ \\ |/ _ \\/ __| __|    ";
        String s4 = "  | |___|  __/\\ V /  __/ |  ____) |  __/ |  __/ (__| |_           ";
        String s5 = "  |______\\___| \\_/ \\___|_| |_____/ \\___|_|\\___|\\___|\\__|    ";





        gui.drawText(new Position(41, 1), s0, "  #f7dc6f  ");
        gui.drawText(new Position(41, 2), s1, "  #f7dc6f  ");
        gui.drawText(new Position(41, 3), s2, "  #f7dc6f  ");
        gui.drawText(new Position(41, 4), s3, "  #f7dc6f  ");
        gui.drawText(new Position(41, 5), s4, "  #f7dc6f  ");
        gui.drawText(new Position(41, 6), s5, "  #f7dc6f  ");
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

        gui.drawText(new Position(65, 33), s0, "  #ffffff  ");
        gui.drawText(new Position(65, 34), s1, "  #ffffff  ");
        gui.drawText(new Position(65, 35), s2, "  #ffffff  ");
        gui.drawText(new Position(65, 36), s3, "  #ffffff  ");
        gui.drawText(new Position(65, 37), s4, "  #ffffff  ");
        gui.drawText(new Position(65, 38), s5, "  #ffffff  ");
        gui.drawText(new Position(65, 39), s6, " #ffffff ");
        gui.drawText(new Position(65, 40), s7, " #ffffff ");

        gui.drawText(new Position(100, 26), s0, "  #ffffff  ");
        gui.drawText(new Position(100, 27), s1, "  #ffffff  ");
        gui.drawText(new Position(100, 28), s2, "  #ffffff  ");
        gui.drawText(new Position(100, 29), s3, "  #ffffff  ");
        gui.drawText(new Position(100, 30), s4, "  #ffffff  ");
        gui.drawText(new Position(100, 31), s5, "  #ffffff  ");
        gui.drawText(new Position(100, 32), s6, " #ffffff ");
        gui.drawText(new Position(100, 33), s7, " #ffffff ");

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

        gui.drawText(new Position(43, 17), " --- ", "#f76fe0");
        gui.drawText(new Position(43, 18), "|   |", "#f76fe0");
        gui.drawText(new Position(43, 19), " ---", "#f76fe0");

        gui.drawText(new Position(55, 17), " --- ", "#f76fe0");
        gui.drawText(new Position(55, 18), "|   |", "#f76fe0");
        gui.drawText(new Position(55, 19), " ---", "#f76fe0");

        gui.drawText(new Position(67, 17), " --- ", "#f76fe0");
        gui.drawText(new Position(67, 18), "|   |", "#f76fe0");
        gui.drawText(new Position(67, 19), " ---", "#f76fe0");

        gui.drawText(new Position(79, 17), " --- ", "#f76fe0");
        gui.drawText(new Position(79, 18), "|   |", "#f76fe0");
        gui.drawText(new Position(79, 19), " ---", "#f76fe0");

        gui.drawText(new Position(91, 17), " --- ", "#f76fe0");
        gui.drawText(new Position(91, 18), "|   |", "#f76fe0");
        gui.drawText(new Position(91, 19), " ---", "#f76fe0");

        int width = 45;

        for(int i = 0; i < getModel().getNumberEntries(); i++) {
            gui.drawText(new Position(width + i, 18), getModel().getEntry(i), getModel().isSelected(i) ? "#D1D100" : "#FFFFFF" );
            width += 11;
        }
    }
}
