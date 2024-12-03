package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.LevelCompletedMenu;
import badIceCream.viewer.Viewer;

public class LevelCompletedMenuViewer extends Viewer<LevelCompletedMenu> {
    public LevelCompletedMenuViewer(LevelCompletedMenu menu) {
        super(menu);
    }

    void drawTitle(Graphics gui) {
        String s0 = "  _                              _      _____                               _          _  ";
        String s1 = " | |                            | |    / ____|                             | |        | | ";
        String s2 = " | |        ___  __   __   ___  | |   | |        ___    _ __ ___    _ __   | |   ___  | |_    ___  ";
        String s3 = " | |       / _ \\ \\ \\ / /  / _ \\ | |   | |       / _ \\  | '_ ` _ \\  | '_ \\  | |  / _ \\ | __|  / _ \\ ";
        String s4 = " | |____  |  __/  \\ V /  |  __/ | |   | |____  | (_) | | | | | | | | |_) | | | |  __/ | |_  |  __/ ";
        String s5 = " |______|  \\___|   \\_/    \\___| |_|    \\_____|  \\___/  |_| |_| |_| | .__/  |_|  \\___|  \\__|  \\___| ";
        String s6 = "                                                                   | | ";
        String s7 = "                                                                   |_|   ";



        gui.drawText(new Position(24, 2), s0, "  #f7dc6f  ");
        gui.drawText(new Position(24, 3), s1, "  #f7dc6f  ");
        gui.drawText(new Position(24, 4), s2, "  #f7dc6f  ");
        gui.drawText(new Position(24, 5), s3, "  #f7dc6f  ");
        gui.drawText(new Position(24, 6), s4, "  #f7dc6f  ");
        gui.drawText(new Position(24, 7), s5, "  #f7dc6f  ");
        gui.drawText(new Position(24, 8), s6, " #f7dc6f ");
        gui.drawText(new Position(24, 9), s7, " #f7dc6f ");
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

        gui.drawText(new Position(15, 25), s0, " #f70d09  ");
        gui.drawText(new Position(15, 26), s1, " #f70d09  ");
        gui.drawText(new Position(15, 27), s2, " #f70d09  ");
        gui.drawText(new Position(15, 28), s3, " #f70d09  ");
        gui.drawText(new Position(15, 29), s4, " #f70d09  ");
        gui.drawText(new Position(15, 30), s5, " #f70d09  ");
        gui.drawText(new Position(15, 31), s6, " #f70d09 ");
        gui.drawText(new Position(15, 32), s7, " #f70d09 ");

        gui.drawText(new Position(5, 1), s0, "  #8bf117  ");
        gui.drawText(new Position(5, 2), s1, "  #8bf117  ");
        gui.drawText(new Position(5, 3), s2, "  #8bf117  ");
        gui.drawText(new Position(5, 4), s3, "  #8bf117  ");
        gui.drawText(new Position(5, 5), s4, "  #8bf117  ");
        gui.drawText(new Position(5, 6), s5, "  #8bf117  ");
        gui.drawText(new Position(5, 7), s6, "  #8bf117 ");
        gui.drawText(new Position(5, 8), s7, "  #8bf117 ");

        gui.drawText(new Position(70, 33), s0, "  #56b6f4  ");
        gui.drawText(new Position(70, 34), s1, "  #56b6f4  ");
        gui.drawText(new Position(70, 35), s2, "  #56b6f4  ");
        gui.drawText(new Position(70, 36), s3, "  #56b6f4  ");
        gui.drawText(new Position(70, 37), s4, "  #56b6f4  ");
        gui.drawText(new Position(70, 38), s5, "  #56b6f4  ");
        gui.drawText(new Position(70, 39), s6, " #56b6f4 ");
        gui.drawText(new Position(70, 40), s7, " #56b6f4 ");

        gui.drawText(new Position(100, 20), s0, "  #fc9a02  ");
        gui.drawText(new Position(100, 21), s1, "  #fc9a02  ");
        gui.drawText(new Position(100, 22), s2, "  #fc9a02  ");
        gui.drawText(new Position(100, 23), s3, "  #fc9a02  ");
        gui.drawText(new Position(100, 24), s4, "  #fc9a02  ");
        gui.drawText(new Position(100, 25), s5, "  #fc9a02  ");
        gui.drawText(new Position(100, 26), s6, " #fc9a02 ");
        gui.drawText(new Position(100, 27), s7, " #fc9a02 ");

        gui.drawText(new Position(120, 11), s0, "  #ff53f7  ");
        gui.drawText(new Position(120, 12), s1, "  #ff53f7  ");
        gui.drawText(new Position(120, 13), s2, "  #ff53f7  ");
        gui.drawText(new Position(120, 14), s3, "  #ff53f7  ");
        gui.drawText(new Position(120, 15), s4, "  #ff53f7  ");
        gui.drawText(new Position(120, 16), s5, "  #ff53f7  ");
        gui.drawText(new Position(120, 17), s6, " #ff53f7 ");
        gui.drawText(new Position(120, 18), s7, " #ff53f7 ");
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
