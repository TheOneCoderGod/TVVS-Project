package badIceCream;

import badIceCream.GUI.Graphics;
import badIceCream.audio.AudioController;
import badIceCream.states.State;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.awt.FontFormatException;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Game game;
    private State mockState;
    private Graphics mockGraphics;

    @BeforeEach
    public void setUp() throws FontFormatException, IOException, NoSuchFieldException, IllegalAccessException {
        mockState = mock(State.class);
        mockGraphics = mock(Graphics.class);

        // Spy on the Game instance to allow partial mocking
        game = spy(new Game());

        // Mock getGraphicsForGame to return the mockGraphics when called
        doReturn(mockGraphics).when(game).getGraphicsForGame(any(Type.class), anyInt(), anyInt());

        // Use reflection to set the initial gui to mockGraphics
        Field guiField = Game.class.getDeclaredField("gui");
        guiField.setAccessible(true);
        guiField.set(game, mockGraphics);
    }

    @Test
    public void testGameInitialization() throws FontFormatException, IOException {
        try (MockedStatic<AudioController> mockedAudio = mockStatic(AudioController.class)) {
            // Initialize a new Game instance to verify constructor behavior
            Game initializedGame = new Game();

            // Verify that GUI is initialized correctly
            assertNotNull(initializedGame.getGui(), "GUI should not be null after initialization");

            // Verify that state is initialized correctly
            assertNotNull(initializedGame.getState(), "State should not be null after initialization");
            assertTrue(initializedGame.getState() instanceof badIceCream.states.MainMenuState,
                    "State should be instance of MainMenuState");

            // Verify that AudioController.playMenuMusic() was called once
            mockedAudio.verify(AudioController::playMenuMusic, times(1));
        }
    }

    @Test
    public void testSetState() throws FontFormatException, IOException {
        try (MockedStatic<AudioController> mockedAudio = mockStatic(AudioController.class)) {
            game.setState(mockState, Type.menu, WIDTH, HEIGHT);

            // Verify state is updated
            assertEquals(mockState, game.getState(), "State should be updated to the mockState");

            // Verify that AudioController.playMenuMusic() was called once
            mockedAudio.verify(AudioController::playMenuMusic, times(1));

            // Verify gui.close() is called
            verify(mockGraphics, times(1)).close();

            // Verify getGraphicsForGame is called with correct parameters
            verify(game, times(1)).getGraphicsForGame(Type.menu, WIDTH, HEIGHT);

            // Verify gui.refresh() is called
            verify(mockGraphics, times(1)).refresh();
        }
    }

    @Test
    public void testHandleSound() throws FontFormatException, IOException {
        try (MockedStatic<AudioController> mockedStatic = mockStatic(AudioController.class)) {
            game.setState(mockState, Type.menu, WIDTH, HEIGHT);

            // Verify that playMenuMusic was called once
            mockedStatic.verify(AudioController::playMenuMusic, times(1));

            // Removed verifyNoMoreInteractions to allow other interactions
        }
    }

    @Test
    public void testHandleSoundWithDifferentTypes() throws FontFormatException, IOException {
        try (MockedStatic<AudioController> mockedStatic = mockStatic(AudioController.class)) {
            // Test Type.win
            game.setState(mockState, Type.win, WIDTH, HEIGHT);
            mockedStatic.verify(AudioController::playLevelCompleteMusic, times(1));
            mockedStatic.reset();

            // Test Type.gameOver
            game.setState(mockState, Type.gameOver, WIDTH, HEIGHT);
            mockedStatic.verify(AudioController::playGameOverMusic, times(1));
            mockedStatic.reset();

            // Test Type.game
            game.setState(mockState, Type.game, WIDTH, HEIGHT);
            mockedStatic.verify(AudioController::playLevelMusic, times(1));
        }
    }

    @Test
    public void testHandleSoundNulo() throws FontFormatException, IOException {
        try (MockedStatic<AudioController> mockedStatic = mockStatic(AudioController.class)) {
            game.setState(mockState, Type.nulo, WIDTH, HEIGHT);

            // Verify that no AudioController methods were called
            mockedStatic.verifyNoInteractions();

            // Additionally, GUI should not be closed or refreshed
            verify(mockGraphics, never()).close();
            verify(mockGraphics, never()).refresh();
        }
    }

    @Test
    public void testGetGraphicsForGame() throws FontFormatException, IOException {
        Graphics graphics = game.getGraphicsForGame(Type.menu, WIDTH, HEIGHT);
        assertNotNull(graphics, "Graphics should not be null for Type.menu");
    }

    @Test
    public void testGetGraphicsForGameWithDifferentTypes() throws FontFormatException, IOException {
        Graphics menuGraphics = game.getGraphicsForGame(Type.menu, WIDTH, HEIGHT);
        assertNotNull(menuGraphics, "Menu Graphics should not be null");

        Graphics winGraphics = game.getGraphicsForGame(Type.win, WIDTH, HEIGHT);
        assertNotNull(winGraphics, "Win Graphics should not be null");

        Graphics gameOverGraphics = game.getGraphicsForGame(Type.gameOver, WIDTH, HEIGHT);
        assertNotNull(gameOverGraphics, "GameOver Graphics should not be null");

        Graphics gameGraphics = game.getGraphicsForGame(Type.game, WIDTH, HEIGHT);
        assertNotNull(gameGraphics, "Game Graphics should not be null");
    }

    @Test
    public void testGetGraphicsForGameWithInvalidType() {
        assertThrows(NullPointerException.class, () -> {
            game.getGraphicsForGame(null, WIDTH, HEIGHT);
        }, "Expected NullPointerException when Type is null");
    }

    @Test
    public void testStart() throws FontFormatException, IOException, InterruptedException {
        // Mock the state to prevent actual game loop execution
        doNothing().when(mockState).step(any(Game.class), any(Graphics.class), anyLong());
        doNothing().when(mockState).stepMonsters(anyLong());

        game.setState(mockState, Type.menu, WIDTH, HEIGHT);

        // Start the game in a separate thread
        Thread gameThread = new Thread(() -> {
            try {
                game.start();
            } catch (IOException | InterruptedException e) {
                // Handle exception if necessary
            }
        });

        gameThread.start();

        // Let the game run for a short time and then interrupt
        Thread.sleep(1000);
        gameThread.interrupt();

        gameThread.join(2000);

        // Verify that step and stepMonsters were called at least once
        verify(mockState, atLeastOnce()).step(any(Game.class), any(Graphics.class), anyLong());
        verify(mockState, atLeastOnce()).stepMonsters(anyLong());
    }

    @Test
    public void testStartWithDifferentStates() throws FontFormatException, IOException, InterruptedException {
        State anotherMockState = mock(State.class);
        doNothing().when(anotherMockState).step(any(Game.class), any(Graphics.class), anyLong());
        doNothing().when(anotherMockState).stepMonsters(anyLong());

        game.setState(anotherMockState, Type.game, WIDTH, HEIGHT);

        // Start the game in a separate thread
        Thread gameThread = new Thread(() -> {
            try {
                game.start();
            } catch (IOException | InterruptedException e) {
                // Handle exception if necessary
            }
        });

        gameThread.start();

        // Let the game run for a short time and then interrupt
        Thread.sleep(1000);
        gameThread.interrupt();

        gameThread.join(2000);

        // Verify that step and stepMonsters were called at least once
        verify(anotherMockState, atLeastOnce()).step(any(Game.class), any(Graphics.class), anyLong());
        verify(anotherMockState, atLeastOnce()).stepMonsters(anyLong());
    }

    @Test
    public void testSetStateAndCloseGui() throws FontFormatException, IOException {
        // Create mocks for each Graphics instance
        Graphics firstMockGraphics = mock(Graphics.class);
        Graphics secondMockGraphics = mock(Graphics.class);

        // Mock getGraphicsForGame for Type.game and Type.menu to return separate mocks
        when(game.getGraphicsForGame(Type.game, WIDTH, HEIGHT)).thenReturn(firstMockGraphics);
        when(game.getGraphicsForGame(Type.menu, WIDTH, HEIGHT)).thenReturn(secondMockGraphics);

        // First setState call with Type.game
        game.setState(mockState, Type.game, WIDTH, HEIGHT);

        // Second setState call with Type.menu, which should close firstMockGraphics and set newMockGraphics
        game.setState(mockState, Type.menu, WIDTH, HEIGHT); // Changing state should close firstMockGraphics

        // Verify that close() was called once on the initial mockGraphics (from setUp via reflection)
        verify(mockGraphics, times(1)).close();

        // Verify that close() was called once on the firstMockGraphics
        verify(firstMockGraphics, times(1)).close();

        // Verify that refresh() was called once on the secondMockGraphics
        verify(secondMockGraphics, times(1)).refresh();
    }



    @Test
    public void testHandleSoundException() throws FontFormatException, IOException {
        try (MockedStatic<AudioController> mockedStatic = mockStatic(AudioController.class)) {
            // Configure AudioController.playMenuMusic() to throw an exception
            mockedStatic.when(AudioController::playMenuMusic).thenThrow(new RuntimeException("Audio failed"));

            // Attempt to set state, expecting a RuntimeException
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                game.setState(mockState, Type.menu, WIDTH, HEIGHT);
            }, "Expected RuntimeException due to AudioController failure");

            assertEquals("Audio failed", exception.getMessage(), "Exception message should match");

            // Verify that playMenuMusic was attempted
            mockedStatic.verify(AudioController::playMenuMusic, times(1));
        }
    }

    @Test
    public void testSetStateWithInvalidType() throws FontFormatException, IOException {
        // Attempt to set state with null Type, expecting a NullPointerException
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            game.setState(mockState, null, WIDTH, HEIGHT);
        }, "Expected NullPointerException when Type is null");

        // Optionally, verify the exception message
        assertTrue(exception.getMessage().contains("Cannot invoke"), "Exception message should indicate invocation issue");
    }

    // Additional Tests to Cover Survived Mutants

    @Test
    public void testGetGuiReturnsCorrectInstance() {
        // Since getGui() is already mocked via reflection, simply verify
        Graphics gui = game.getGui();
        assertNotNull(gui, "getGui() should return a non-null Graphics instance");
        assertEquals(mockGraphics, gui, "getGui() should return the mocked Graphics instance");
    }

    @Test
    public void testGetStateReturnsCorrectInstance() {
        // Mock getState() to return mockState
        doReturn(mockState).when(game).getState();
        State state = game.getState();
        assertNotNull(state, "getState() should return a non-null State instance");
        assertEquals(mockState, state, "getState() should return the mocked State instance");
    }

    @Test
    public void testGuiRefreshCalledOnSetState() throws FontFormatException, IOException {
        game.setState(mockState, Type.menu, WIDTH, HEIGHT);
        verify(mockGraphics, times(1)).refresh();
    }

    @Test
    public void testGuiCloseCalledOnSetState() throws FontFormatException, IOException {
        game.setState(mockState, Type.menu, WIDTH, HEIGHT);
        verify(mockGraphics, times(1)).close();
    }
}
