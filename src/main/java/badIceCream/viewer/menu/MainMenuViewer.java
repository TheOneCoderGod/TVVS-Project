package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.MainMenu;
import badIceCream.viewer.Viewer;

public class MainMenuViewer extends Viewer<MainMenu> {
    public MainMenuViewer(MainMenu menu){
        super(menu);
    }

    void drawTitle(Graphics gui) {
        String s0 = "                        ....                .                                     ";
        String s1 = "                       |  . \\              | |                                    ";
        String s2 = "                       | |.) |   .. .    ..| |                                    ";
        String s3 = "                       |  . <   / .` |  / .` |                                    ";
        String s4 = "                       | |.) | | (.| | | (.| |                                    ";
        String s5 = "                       |...../  \\..,.|  \\..,.|                                    ";
        String s6 = "                                                                                   ";
        String s7 = "                                                                                   ";
        String s8 = "  .....                            .....                                           ";
        String s9 = " |.   .|                          / ....|                                          ";
        String s10= "   | |     ...    ...    ......  | |       . ..    ...    .. .   . .. ...        ";
        String s11= "   | |    / ..|  / . \\  |......| | |      | '..|  / . \\  / .` | | '. ` . \\      ";
        String s12= "  .| |.  | (..  |  ../           | |....  | |    |  ../ | (.| | | | | | | |      ";
        String s13= " |.....|  \\...|  \\...|            \\.....| |.|     \\...|  \\..,.| |.| |.| |.|      ";

        gui.drawText(new Position(35, 1), s0, "  #f7dc6f  ");
        gui.drawText(new Position(35, 2), s1, "  #f7dc6f  ");
        gui.drawText(new Position(35, 3), s2, "  #f7dc6f  ");
        gui.drawText(new Position(35, 4), s3, "  #f7dc6f  ");
        gui.drawText(new Position(35, 5), s4, "  #f7dc6f  ");
        gui.drawText(new Position(35, 6), s5, "  #f7dc6f  ");
        gui.drawText(new Position(35, 7), s6, " #bb8fce ");
        gui.drawText(new Position(35, 8), s7, " #bb8fce ");
        gui.drawText(new Position(35, 9), s8, " #bb8fce ");
        gui.drawText(new Position(35, 10), s9, " #bb8fce ");
        gui.drawText(new Position(35, 11), s10, " #bb8fce ");
        gui.drawText(new Position(35, 12), s11, " #bb8fce ");
        gui.drawText(new Position(35, 13), s12, " #bb8fce ");
        gui.drawText(new Position(35, 14), s13, " #bb8fce ");
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

        int heigth = 20;

        for(int i = 0; i < getModel().getNumberEntries(); i++){
            gui.drawText(new Position(63, heigth + i), getModel().getEntry(i), getModel().isSelected(i) ? "#D1D100" : "#FFFFFF" );
            heigth += 3;
        }
    }
}
