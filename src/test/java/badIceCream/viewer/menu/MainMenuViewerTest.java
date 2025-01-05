package badIceCream.viewer.menu;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.MainMenu;
import badIceCream.viewer.menu.MainMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MainMenuViewerTest {

    @Mock
    private Graphics mockGraphics;

    @Mock
    private MainMenu mockMenu;

    @InjectMocks
    private MainMenuViewer viewer;

    @Captor
    private ArgumentCaptor<Position> positionCaptor;

    @Captor
    private ArgumentCaptor<String> textCaptor;

    @Captor
    private ArgumentCaptor<String> colorCaptor;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test to ensure test isolation
        reset(mockGraphics, mockMenu);
    }

    @Nested
    @DisplayName("Draw Elements Tests")
    public class DrawElementsTests {

        @Test
        @DisplayName("Should draw all menu entries with correct text and colors")
        void testDrawElementsWithEntries() {
            // Arrange
            when(mockMenu.getNumberEntries()).thenReturn(3);
            when(mockMenu.getEntry(0)).thenReturn("Start Game");
            when(mockMenu.getEntry(1)).thenReturn("Options");
            when(mockMenu.getEntry(2)).thenReturn("Exit");
            when(mockMenu.isSelected(anyInt())).thenReturn(false);
            when(mockMenu.isSelected(eq(1))).thenReturn(true); // "Options" is selected

            // Act
            viewer.drawElements(mockGraphics);

            // Assert
            // Capture all drawText calls
            verify(mockGraphics, atLeast(1)).drawText(positionCaptor.capture(), textCaptor.capture(), colorCaptor.capture());

            List<Position> capturedPositions = positionCaptor.getAllValues();
            List<String> capturedTexts = textCaptor.getAllValues();
            List<String> capturedColors = colorCaptor.getAllValues();

            // Verify that menu entries are drawn with correct text and colors
            assertThat(capturedTexts).contains("Start Game", "Options", "Exit");

            // Filter the captured colors to only include the colors of the menu entries
            List<String> entryColors = capturedColors.subList(capturedColors.size() - 3, capturedColors.size());
            assertThat(entryColors).containsExactly(
                    "#FFFFFF", // Start Game
                    "#D1D100", // Options
                    "#FFFFFF"  // Exit
            );

            // Filter the captured positions to only include the positions of the menu entries
            List<Position> entryPositions = capturedPositions.subList(capturedPositions.size() - 3, capturedPositions.size());
            assertThat(entryPositions).containsExactly(
                    new Position(63, 20),
                    new Position(63, 24),
                    new Position(63, 28)
            );
        }

        @Test
        @DisplayName("Should handle zero menu entries without errors")
        public void testDrawElementsZeroEntries() {
            // Arrange
            when(mockMenu.getNumberEntries()).thenReturn(0);

            // Act
            viewer.drawElements(mockGraphics);

            // Assert
            // Verify that no menu entry drawText calls are made
            verify(mockGraphics, never()).drawText(argThat(pos -> pos.getX() == 63), anyString(), anyString());

            // However, title and snowflakes should still be drawn
            // Assuming title has 14 drawText calls and snowflakes have 32 drawText calls
            verify(mockGraphics, atLeast(46)).drawText(any(Position.class), anyString(), anyString());
        }
    }
}
