package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import badIceCream.viewer.Viewer;

import java.io.IOException;

public class InstructionsMenuSecondPageViewer extends Viewer<InstructionsMenuSecondPage> {

    public InstructionsMenuSecondPageViewer(InstructionsMenuSecondPage menu) {super(menu);}

    void drawTitle(Graphics gui) {
        String s0 = "           _____           _                   _   _                       ";
        String s1 = "          |_   _|         | |                 | | (_)                      " ;
        String s2 = "            | |  _ __  ___| |_ _ __ _   _  ___| |_ _  ___  _ __  ___       ";
        String s3 = "            | | | '_ \\/ __| __| '__| | | |/ __| __| |/ _ \\| '_ \\/ __|      ";
        String s4 = "           _| |_| | | \\__ \\ |_| |  | |_| | (__| |_| | (_) | | | \\__ \";    ";
        String s5 = "          |_____|_| |_|___/\\__|_|   \\__,_|\\___|\\__|_|\\___/|_| |_|___/      ";





        gui.drawText(new Position(35, 1), s0, "  #f7dc6f  ");
        gui.drawText(new Position(35, 2), s1, "  #f7dc6f  ");
        gui.drawText(new Position(35, 3), s2, "  #f7dc6f  ");
        gui.drawText(new Position(35, 4), s3, "  #f7dc6f  ");
        gui.drawText(new Position(35, 5), s4, "  #f7dc6f  ");
        gui.drawText(new Position(35, 6), s5, "  #f7dc6f  ");
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
    public void drawElements(Graphics gui) throws IOException {
        drawTitle(gui);
        drawSnowflake(gui);
        gui.drawCharacters();

        gui.drawText(new Position(45, 15), "Default Monster: Just walks around the arena.", "#FFFFFF");
        gui.drawText(new Position(45, 18), "Jumper Monster: Has the ability to jump walls.", "#FFFFFF");
        gui.drawText(new Position(45, 21), "WallBreaker Monster: Has the ability to break ice walls.", "#FFFFFF");
        gui.drawText(new Position(45, 24), "Runner Monster Inactive: Acts like a default monster.", "#FFFFFF");
        gui.drawText(new Position(45, 27), "Default Monster Active: Has the ability to track Bad Ice Cream and run faster.", "#FFFFFF");
        gui.drawText(new Position(45, 30), "Hot Floor: Blocks the progression of ice walls.", "#FFFFFF");


        gui.drawText(new Position(36, 40), "Last Page", "#FFFFFF");
        gui.drawText(new Position(30, 39), " ___", "#FFFFFF");
        gui.drawText(new Position(30, 40), "|<- |", "#FFFFFF");
        gui.drawText(new Position(30, 41), "|___| ", "#FFFFFF");

        gui.drawText(new Position(110, 40), "Main Menu", "#FFFFFF");
        gui.drawText(new Position(120, 39), " ___", "#FFFFFF");
        gui.drawText(new Position(120, 40), "|ESC|", "#FFFFFF");
        gui.drawText(new Position(120, 41), "|___| ", "#FFFFFF");
    }
}
