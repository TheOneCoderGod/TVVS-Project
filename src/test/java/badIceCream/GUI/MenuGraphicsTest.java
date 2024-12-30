package badIceCream.GUI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MenuGraphicsTest {

    private MenuGraphics menuGraphics;

    @BeforeEach
    void setUp() {
        menuGraphics = new MenuGraphics(40, 10);
    }

    @Test
    @DisplayName("createTerminal() success - ensures no exceptions and returns a Terminal")
    void testCreateTerminalSuccess() throws Exception {
        try (MockedStatic<Font> fontStatic = Mockito.mockStatic(Font.class);
             MockedConstruction<DefaultTerminalFactory> factoryConstruction =
                     mockConstruction(DefaultTerminalFactory.class,
                             (factoryMock, context) -> {
                                 when(factoryMock.setInitialTerminalSize(any(TerminalSize.class)))
                                         .thenReturn(factoryMock);
                                 when(factoryMock.setTerminalEmulatorFontConfiguration(any(AWTTerminalFontConfiguration.class)))
                                         .thenReturn(factoryMock);
                                 Terminal terminalMock = mock(Terminal.class);
                                 when(factoryMock.createTerminal()).thenReturn(terminalMock);
                             })) {

            Font dummyFont = new Font("Monospaced", Font.PLAIN, 14);
            fontStatic.when(() -> Font.createFont(eq(Font.TRUETYPE_FONT), any(File.class)))
                    .thenReturn(dummyFont);

            Terminal terminal = menuGraphics.createTerminal();
            assertNotNull(terminal);
        }
    }

    @Test
    @DisplayName("createTerminal() with FontFormatException should throw IOException")
    void testCreateTerminalFontFormatException() throws Exception {
        try (MockedStatic<Font> fontStatic = Mockito.mockStatic(Font.class)) {
            fontStatic.when(() -> Font.createFont(eq(Font.TRUETYPE_FONT), any(File.class)))
                    .thenThrow(new FontFormatException("Invalid font"));

            assertThrows(IOException.class, () -> menuGraphics.createTerminal());
        }
    }

    @Test
    @DisplayName("createTerminal() with IOException reading file should rethrow IOException")
    void testCreateTerminalIOException() throws Exception {
        try (MockedStatic<Font> fontStatic = Mockito.mockStatic(Font.class)) {
            fontStatic.when(() -> Font.createFont(eq(Font.TRUETYPE_FONT), any(File.class)))
                    .thenThrow(new IOException("File not found"));

            assertThrows(IOException.class, () -> menuGraphics.createTerminal());
        }
    }
}
