package badIceCream.GUI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GUITest {

    private GUI gui;

    @BeforeEach
    void setUp() {
        gui = new GUI() {
            @Override
            protected Terminal createTerminal() throws IOException {
                return mock(Terminal.class);
            }
        };
    }

    @Test
    @DisplayName("createScreen(...) should return a TerminalScreen with non-null size")
    void testCreateScreen() throws IOException {
        Terminal terminalMock = mock(Terminal.class);
        // Stub getTerminalSize() so it doesn't return null
        when(terminalMock.getTerminalSize()).thenReturn(new TerminalSize(80, 24));

        Screen screen = gui.createScreen(terminalMock);
        assertNotNull(screen);
        assertTrue(screen instanceof TerminalScreen);
    }

    @Test
    @DisplayName("createTerminal() in anonymous GUI returns a mock Terminal")
    void testCreateTerminal() throws IOException {
        Terminal terminal = gui.createTerminal();
        assertNotNull(terminal);
    }
}
