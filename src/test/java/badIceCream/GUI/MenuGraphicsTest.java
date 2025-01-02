package badIceCream.GUI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MenuGraphicsTest {

    @Nested
    @DisplayName("createTerminal() Tests")
    class CreateTerminalTests {

        @Test
        @DisplayName("createTerminal() - Success Scenario: Returns a Terminal Without Exceptions")
        void testCreateTerminalSuccess() throws Exception {
            // Mock Font.createFont and DefaultTerminalFactory constructor
            try (MockedStatic<Font> fontStaticMock = Mockito.mockStatic(Font.class);
                 MockedConstruction<DefaultTerminalFactory> factoryConstruction =
                         mockConstruction(DefaultTerminalFactory.class,
                                 withSettings().defaultAnswer(Answers.RETURNS_SELF),
                                 (factoryMock, context) -> {
                                     // Stub method chaining on DefaultTerminalFactory
                                     when(factoryMock.setInitialTerminalSize(any(TerminalSize.class)))
                                             .thenReturn(factoryMock);
                                     when(factoryMock.setTerminalEmulatorFontConfiguration(any(SwingTerminalFontConfiguration.class)))
                                             .thenReturn(factoryMock);
                                     // Stub createTerminal() to return a mock Terminal
                                     Terminal terminalMock = mock(Terminal.class);
                                     when(factoryMock.createTerminal()).thenReturn(terminalMock);
                                 })) {

                // Stub Font.createFont(...) to return a dummy Font
                Font dummyFont = new Font("Monospaced", Font.PLAIN, 14);
                fontStaticMock.when(() -> Font.createFont(eq(Font.TRUETYPE_FONT), any(File.class)))
                        .thenReturn(dummyFont);

                // Instantiate MenuGraphics after mocks are set up
                MenuGraphics menuGraphics = new MenuGraphics(80, 24);
                Terminal terminal = menuGraphics.createTerminal();

                // Assertions
                assertNotNull(terminal, "Terminal should not be null on successful creation");

                // Verify that DefaultTerminalFactory was constructed once
                assertEquals(1, factoryConstruction.constructed().size(),
                        "DefaultTerminalFactory should be constructed once");

                // Retrieve the constructed factory mock
                DefaultTerminalFactory constructedFactory = factoryConstruction.constructed().get(0);
                assertNotNull(constructedFactory, "DefaultTerminalFactory mock should not be null");

                // Verify method calls on the factory
                verify(constructedFactory).setInitialTerminalSize(new TerminalSize(80, 24));
                verify(constructedFactory).setTerminalEmulatorFontConfiguration(any(SwingTerminalFontConfiguration.class));
                verify(constructedFactory).createTerminal();
            }
        }

        @Test
        @DisplayName("createTerminal() - FontFormatException: Should Wrap and Throw IOException")
        void testCreateTerminalFontFormatException() throws Exception {
            // Mock Font.createFont to throw FontFormatException
            try (MockedStatic<Font> fontStaticMock = Mockito.mockStatic(Font.class)) {
                fontStaticMock.when(() -> Font.createFont(eq(Font.TRUETYPE_FONT), any(File.class)))
                        .thenThrow(new FontFormatException("Invalid font file"));

                // Instantiate MenuGraphics after mocks are set up
                MenuGraphics menuGraphics = new MenuGraphics(80, 24);

                // Assert that IOException is thrown with FontFormatException as the cause
                IOException exception = assertThrows(IOException.class, () -> menuGraphics.createTerminal(),
                        "Should throw IOException if FontFormatException occurs");

                // Verify the exception message
                assertEquals("Error creating terminal with custom font.", exception.getMessage(),
                        "Exception message should match the wrapped IOException message");

                // Verify the cause of the IOException
                assertTrue(exception.getCause() instanceof FontFormatException,
                        "Cause should be FontFormatException");
                assertEquals("Invalid font file", exception.getCause().getMessage(),
                        "Cause message should match the simulated FontFormatException message");
            }
        }

        @Test
        @DisplayName("createTerminal() - IOException Reading File: Should Rethrow IOException")
        void testCreateTerminalIOException() throws Exception {
            // Mock Font.createFont to throw IOException
            try (MockedStatic<Font> fontStaticMock = Mockito.mockStatic(Font.class)) {
                fontStaticMock.when(() -> Font.createFont(eq(Font.TRUETYPE_FONT), any(File.class)))
                        .thenThrow(new IOException("Cannot read file"));

                // Instantiate MenuGraphics after mocks are set up
                MenuGraphics menuGraphics = new MenuGraphics(80, 24);

                // Assert that IOException is thrown
                IOException exception = assertThrows(IOException.class, () -> menuGraphics.createTerminal(),
                        "Should throw IOException if file cannot be read");

                // Verify the exception message
                assertEquals("Error creating terminal with custom font.", exception.getMessage(),
                        "Exception message should match the wrapped IOException message");

                // Verify the cause of the IOException
                assertTrue(exception.getCause() instanceof IOException,
                        "Cause should be IOException");
                assertEquals("Cannot read file", exception.getCause().getMessage(),
                        "Cause message should match the simulated IOException message");
            }
        }
    }
}
