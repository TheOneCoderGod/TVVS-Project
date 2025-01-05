package badIceCream.GUI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class GameGraphicsTest {

    private GameGraphics gameGraphics;

    @BeforeEach
    public void setUp() {
        // We'll instantiate with some sample width/height
        gameGraphics = new GameGraphics(80, 24);
    }

    @Test
    @DisplayName("Constructor properly sets width and height (no crash)")
    public void testConstructor() {
        assertNotNull(gameGraphics, "GameGraphics should be instantiated");
        // If you had getters for width/height, you could assert them here.
    }

    @Nested
    @DisplayName("createTerminal() tests")
    public class CreateTerminalTests {

        @Test
        @DisplayName("createTerminal() - success scenario with no NullPointerException")
        public void testCreateTerminalSuccess() throws Exception {
            // We'll mock static calls to Font.createFont and also intercept DefaultTerminalFactory
            // Key point: we set the defaultAnswer(...) to RETURNS_SELF to allow method chaining
            try (MockedStatic<Font> fontStaticMock = mockStatic(Font.class);
                 MockedConstruction<DefaultTerminalFactory> defaultFactoryMocked =
                         mockConstruction(
                                 DefaultTerminalFactory.class,
                                 withSettings().defaultAnswer(Answers.RETURNS_SELF), // <- important
                                 (mockFactory, context) -> {
                                     // We'll stub createTerminal() to return a mock Terminal
                                     Terminal terminalMock = mock(Terminal.class);
                                     when(mockFactory.createTerminal()).thenReturn(terminalMock);
                                 }
                         )
            ) {
                // Stub Font.createFont(...) to return a normal Font
                Font dummyFont = new Font("Monospaced", Font.PLAIN, 14);
                fontStaticMock.when(() -> Font.createFont(eq(Font.TRUETYPE_FONT), any(File.class)))
                        .thenReturn(dummyFont);

                // Now call createTerminal()
                Terminal terminal = gameGraphics.createTerminal();
                assertNotNull(terminal, "Terminal should not be null on successful creation");

                // Verify the default factory was constructed
                // and that we set the initial size, then the font configuration
                DefaultTerminalFactory constructedFactory = defaultFactoryMocked.constructed().get(0);

                verify(constructedFactory).setInitialTerminalSize(new TerminalSize(80, 24));
                verify(constructedFactory).setTerminalEmulatorFontConfiguration(any(AWTTerminalFontConfiguration.class));
                verify(constructedFactory).createTerminal();
            }
        }

        @Test
        @DisplayName("createTerminal() - FontFormatException => wraps in IOException")
        public void testCreateTerminalFontFormatException() throws Exception {
            try (MockedStatic<Font> fontStaticMock = mockStatic(Font.class)) {
                // Simulate a FontFormatException
                fontStaticMock.when(() -> Font.createFont(eq(Font.TRUETYPE_FONT), any(File.class)))
                        .thenThrow(new FontFormatException("Invalid font file"));

                // We expect an IOException
                assertThrows(IOException.class, () -> gameGraphics.createTerminal(),
                        "Should throw IOException if FontFormatException occurs");
            }
        }

        @Test
        @DisplayName("createTerminal() - IOException reading file => rethrows IOException")
        public void testCreateTerminalIOException() throws Exception {
            try (MockedStatic<Font> fontStaticMock = mockStatic(Font.class)) {
                // Simulate an IOException when reading the font file
                fontStaticMock.when(() -> Font.createFont(eq(Font.TRUETYPE_FONT), any(File.class)))
                        .thenThrow(new IOException("Cannot read file"));

                // We expect an IOException
                assertThrows(IOException.class, () -> gameGraphics.createTerminal(),
                        "Should throw IOException if file cannot be read");
            }
        }
    }
}