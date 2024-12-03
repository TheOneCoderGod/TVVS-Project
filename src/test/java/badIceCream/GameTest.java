package badIceCream;

import badIceCream.GUI.Graphics;
import badIceCream.audio.AudioController;
import badIceCream.states.State;
import badIceCream.utils.Type;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.awt.*;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameTest {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Game game;
    private State mockState;
    private Graphics mockGraphics;

    @Before
    public void setUp() throws FontFormatException, IOException {
        mockState = mock(State.class);
        mockGraphics = mock(Graphics.class);
        game = spy(new Game());
        doReturn(mockGraphics).when(game).getGraphicsForGame(any(Type.class), anyInt(), anyInt());
    }

    @Test(timeout = 1000)
    public void testGameInitialization() {
        assertNotNull(game.getGui());
        assertNotNull(game.getState());
    }

    @Test(timeout = 1000)
    public void testSetState() throws IOException {
        game.setState(mockState, Type.menu, WIDTH, HEIGHT);
        assertEquals(mockState, game.getState());
    }

    @Test(timeout = 1000)
    public void testHandleSound() throws IOException {
        try (MockedStatic<AudioController> mockedStatic = mockStatic(AudioController.class)) {
            game.setState(mockState, Type.menu, WIDTH, HEIGHT);
            mockedStatic.verify(AudioController::playMenuMusic);
        }
    }

    @Test(timeout = 1000)
    public void testHandleSoundWithDifferentTypes() throws IOException {
        try (MockedStatic<AudioController> mockedStatic = mockStatic(AudioController.class)) {
            game.setState(mockState, Type.win, WIDTH, HEIGHT);
            mockedStatic.verify(AudioController::playLevelCompleteMusic);

            game.setState(mockState, Type.gameOver, WIDTH, HEIGHT);
            mockedStatic.verify(AudioController::playGameOverMusic);

            game.setState(mockState, Type.game, WIDTH, HEIGHT);
            mockedStatic.verify(AudioController::playLevelMusic);
        }
    }

    @Test(timeout = 1000)
    public void testHandleSoundNulo() throws IOException {
        try (MockedStatic<AudioController> mockedStatic = mockStatic(AudioController.class)) {
            game.setState(mockState, Type.nulo, WIDTH, HEIGHT);
            mockedStatic.verifyNoInteractions(); // Ensure no audio actions for Type.nulo
        }
    }

    @Test(timeout = 1000)
    public void testGetGraphicsForGame() throws IOException {
        Graphics graphics = game.getGraphicsForGame(Type.menu, WIDTH, HEIGHT);
        assertNotNull(graphics);
    }

    @Test(timeout = 1000)
    public void testGetGraphicsForGameWithDifferentTypes() throws IOException {
        Graphics menuGraphics = game.getGraphicsForGame(Type.menu, WIDTH, HEIGHT);
        assertNotNull(menuGraphics);

        Graphics winGraphics = game.getGraphicsForGame(Type.win, WIDTH, HEIGHT);
        assertNotNull(winGraphics);

        Graphics gameOverGraphics = game.getGraphicsForGame(Type.gameOver, WIDTH, HEIGHT);
        assertNotNull(gameOverGraphics);

        Graphics gameGraphics = game.getGraphicsForGame(Type.game, WIDTH, HEIGHT);
        assertNotNull(gameGraphics);
    }

    @Test(timeout = 1000)
    public void testGetGraphicsForGameWithInvalidType() {
        try {
            Graphics graphics = game.getGraphicsForGame(null, WIDTH, HEIGHT);
            assertNull(graphics);  // Expect null for invalid type
        } catch (NullPointerException | IOException e) {
            // Handle the exception if necessary
            assertTrue(e.getMessage().contains("Cannot invoke"));
        }
    }

    @Test(timeout = 5000)
    public void testStart() throws IOException, InterruptedException {
        // Mock the state to avoid actual game loop execution
        doNothing().when(mockState).step(any(Game.class), any(Graphics.class), anyLong());
        doNothing().when(mockState).stepMonsters(anyLong());

        game.setState(mockState, Type.menu, WIDTH, HEIGHT);

        // Run the game in a separate thread and interrupt it after a short delay
        Thread gameThread = new Thread(() -> {
            try {
                game.start();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        gameThread.start();
        Thread.sleep(1000); // Let the game run for a short time
        gameThread.interrupt(); // Interrupt the game loop

        gameThread.join(1000); // Wait for the game thread to finish

        verify(mockState, atLeastOnce()).step(any(Game.class), any(Graphics.class), anyLong());
        verify(mockState, atLeastOnce()).stepMonsters(anyLong());
    }

    @Test(timeout = 5000)
    public void testStartWithDifferentStates() throws IOException, InterruptedException {
        State anotherMockState = mock(State.class);
        doNothing().when(anotherMockState).step(any(Game.class), any(Graphics.class), anyLong());
        doNothing().when(anotherMockState).stepMonsters(anyLong());

        game.setState(anotherMockState, Type.game, WIDTH, HEIGHT);

        // Run the game in a separate thread and interrupt it after a short delay
        Thread gameThread = new Thread(() -> {
            try {
                game.start();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        gameThread.start();
        Thread.sleep(1000); // Let the game run for a short time
        gameThread.interrupt(); // Interrupt the game loop

        gameThread.join(1000); // Wait for the game thread to finish

        verify(anotherMockState, atLeastOnce()).step(any(Game.class), any(Graphics.class), anyLong());
        verify(anotherMockState, atLeastOnce()).stepMonsters(anyLong());
    }

    @Test(timeout = 1000)
    public void testSetStateAndCloseGui() throws IOException {
        game.setState(mockState, Type.game, WIDTH, HEIGHT);
        game.setState(mockState, Type.menu, WIDTH, HEIGHT); // This should trigger the close method on the previous graphics

        verify(mockGraphics).close();  // Ensure close is called when changing state
    }

    @Test(timeout = 1000)
    public void testHandleSoundException() throws IOException {
        try (MockedStatic<AudioController> mockedStatic = mockStatic(AudioController.class)) {
            mockedStatic.when(AudioController::playMenuMusic).thenThrow(new RuntimeException("Audio failed"));

            // Ensure the mock setup is correct
            assertNotNull(mockState);
            assertNotNull(game);

            // Test with valid state
            try {
                game.setState(mockState, Type.menu, WIDTH, HEIGHT);
                fail("Expected RuntimeException to be thrown");
            } catch (RuntimeException e) {
                assertEquals("Audio failed", e.getMessage());
            }

            // Verify that the playMenuMusic method was called
            mockedStatic.verify(AudioController::playMenuMusic, times(1));
        }
    }

    @Test(timeout = 1000)
    public void testSetStateWithInvalidType() throws IOException {
        try {
            game.setState(mockState, null, WIDTH, HEIGHT);
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException e) {
            assertTrue(e.getMessage().contains("Cannot invoke"));
        }
    }

}