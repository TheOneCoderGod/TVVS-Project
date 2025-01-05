package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.arena.LoaderArenaBuilder;
import badIceCream.model.menu.GameOverMenu;
import badIceCream.model.menu.MainMenu;
import badIceCream.states.GameState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class GameOverMenuControllerTest {

    private GameOverMenu menu;
    private GameOverMenuController controller;
    private Game game;

    @BeforeEach
    public void setUp() {
        menu = mock(GameOverMenu.class);
        game = mock(Game.class);

        // Default: no option selected
        when(menu.isSelectedQuitToMainMenu()).thenReturn(false);
        when(menu.isSelectedPlayAgain()).thenReturn(false);

        // Controller uses the single-arg constructor
        controller = new GameOverMenuController(menu);
    }

    @Test
    @DisplayName("Constructor should properly instantiate the controller")
    public void testConstructor() {
        assertNotNull(controller, "Controller should be instantiated");
    }

    @Test
    @DisplayName("step(...) with UP should call previousEntry() on the menu")
    public void testStepUp() throws IOException {
        controller.step(game, GUI.ACTION.UP, 0L);

        verify(menu, times(1)).previousEntry();
        verify(menu, never()).nextEntry();
        verifyNoMoreInteractions(menu);
    }

    @Test
    @DisplayName("step(...) with DOWN should call nextEntry() on the menu")
    public void testStepDown() throws IOException {
        controller.step(game, GUI.ACTION.DOWN, 0L);

        verify(menu, times(1)).nextEntry();
        verify(menu, never()).previousEntry();
        verifyNoMoreInteractions(menu);
    }

    @Test
    @DisplayName("step(...) with SELECT + Quit to MainMenu selected should go to MainMenuState")
    public void testStepSelectQuitToMainMenu() throws IOException {
        // Force the menu to "Quit"
        when(menu.isSelectedQuitToMainMenu()).thenReturn(true);

        // Mock the old state
        MainMenuState oldState = mock(MainMenuState.class);
        when(oldState.getLevel()).thenReturn(2);
        when(game.getState()).thenReturn(oldState);

        // Execute the SELECT action
        controller.step(game, GUI.ACTION.SELECT, 0L);

        // Capture MainMenuState in the call to game.setState(...)
        ArgumentCaptor<MainMenuState> captor = ArgumentCaptor.forClass(MainMenuState.class);
        verify(game, times(1)).setState(captor.capture(), eq(Type.menu), eq(140), eq(50));

        MainMenuState newState = captor.getValue();
        assertNotNull(newState, "Should create a non-null MainMenuState");
    }

    @Test
    @DisplayName("step(...) with SELECT + Play Again selected should create new Arena and GameState")
    public void testStepSelectPlayAgain() throws IOException {
        // Force the menu to "Play Again"
        when(menu.isSelectedPlayAgain()).thenReturn(true);

        // Mock the old state
        MainMenuState oldState = mock(MainMenuState.class);
        when(oldState.getLevel()).thenReturn(3);
        when(game.getState()).thenReturn(oldState);

        // We'll mock construction of LoaderArenaBuilder
        Arena arenaMock = mock(Arena.class);
        when(arenaMock.getWidth()).thenReturn(15);
        when(arenaMock.getHeight()).thenReturn(10);

        // Use try-with-resources to control the scope of mocked construction
        try (MockedConstruction<LoaderArenaBuilder> mockedBuilder =
                     mockConstruction(LoaderArenaBuilder.class, (builderMock, context) -> {
                         // Whenever createArena() is called, return our mock Arena
                         when(builderMock.createArena()).thenReturn(arenaMock);
                     })) {

            // Execute the SELECT action
            controller.step(game, GUI.ACTION.SELECT, 0L);

            // Verify that the new LoaderArenaBuilder(...) was constructed once
            assertEquals(1, mockedBuilder.constructed().size(),
                    "LoaderArenaBuilder should have been constructed exactly once");

            // Verify that createArena() was called on that builder
            LoaderArenaBuilder constructedBuilder = mockedBuilder.constructed().get(0);
            verify(constructedBuilder, times(1)).createArena();
        }

        // Now verify game.setState(...) was called with the new Arena
        ArgumentCaptor<GameState> captor = ArgumentCaptor.forClass(GameState.class);
        verify(game, times(1))
                .setState(captor.capture(), eq(Type.game), eq(15), eq(10));

        GameState newState = captor.getValue();
        assertNotNull(newState, "A new GameState should be created");
    }

    @Test
    @DisplayName("step(...) with a non-handled action (like LEFT) should do nothing")
    public void testStepDefault() throws IOException {
        // No special selection
        controller.step(game, GUI.ACTION.LEFT, 0L);

        // Verify we do not call next/previous entry or setState
        verify(menu, never()).nextEntry();
        verify(menu, never()).previousEntry();
        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }
}