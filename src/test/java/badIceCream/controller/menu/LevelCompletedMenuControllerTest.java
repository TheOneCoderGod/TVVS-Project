package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.LevelCompletedMenu;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.arena.LoaderArenaBuilder;
import badIceCream.states.GameState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LevelCompletedMenuControllerTest {

    private LevelCompletedMenu menu;
    private LevelCompletedMenuController controller;
    private Game game;

    @BeforeEach
    public void setUp() {
        menu = mock(LevelCompletedMenu.class);
        game = mock(Game.class);

        // Default menu selections off
        when(menu.isSelectedNextLevel()).thenReturn(false);
        when(menu.isSelectedQuitToMainMenu()).thenReturn(false);

        controller = new LevelCompletedMenuController(menu);
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
    @DisplayName("step(...) with SELECT + NextLevel selected should create a new Arena and go to GameState")
    public void testStepSelectNextLevel() throws IOException {
        // Force 'Next Level' selection
        when(menu.isSelectedNextLevel()).thenReturn(true);

        // Mock the old state
        MainMenuState oldState = mock(MainMenuState.class);
        when(oldState.getLevel()).thenReturn(3);
        when(game.getState()).thenReturn(oldState);

        // We'll mock construction of LoaderArenaBuilder
        Arena arenaMock = mock(Arena.class);
        when(arenaMock.getWidth()).thenReturn(20);
        when(arenaMock.getHeight()).thenReturn(10);

        try (MockedConstruction<LoaderArenaBuilder> mockedBuilder =
                     mockConstruction(LoaderArenaBuilder.class, (builderMock, context) -> {
                         when(builderMock.createArena()).thenReturn(arenaMock);
                     })) {

            // Run the SELECT action
            controller.step(game, GUI.ACTION.SELECT, 0L);

            // Confirm the builder was constructed once
            assertEquals(1, mockedBuilder.constructed().size(),
                    "LoaderArenaBuilder should be constructed exactly once");
            LoaderArenaBuilder constructedBuilder = mockedBuilder.constructed().get(0);

            // Verify createArena() was called
            verify(constructedBuilder, times(1)).createArena();
        }

        // Verify we set the new GameState with the arena's width/height
        ArgumentCaptor<GameState> stateCaptor = ArgumentCaptor.forClass(GameState.class);
        verify(game, times(1)).setState(stateCaptor.capture(), eq(Type.game), eq(20), eq(10));

        GameState newState = stateCaptor.getValue();
        assertNotNull(newState, "Should create a non-null GameState");
    }

    @Test
    @DisplayName("step(...) with SELECT + QuitToMainMenu selected should go to MainMenuState")
    public void testStepSelectQuitToMainMenu() throws IOException {
        // Force 'Quit to Main Menu'
        when(menu.isSelectedQuitToMainMenu()).thenReturn(true);

        // Mock the old state
        MainMenuState oldState = mock(MainMenuState.class);
        when(oldState.getLevel()).thenReturn(4);
        when(game.getState()).thenReturn(oldState);

        // Run the SELECT action
        controller.step(game, GUI.ACTION.SELECT, 0L);

        // Capture the MainMenuState set on game
        ArgumentCaptor<MainMenuState> stateCaptor = ArgumentCaptor.forClass(MainMenuState.class);
        verify(game, times(1))
                .setState(stateCaptor.capture(), eq(Type.menu), eq(140), eq(50));

        MainMenuState newState = stateCaptor.getValue();
        assertNotNull(newState, "Should create a non-null MainMenuState");
    }

    @Test
    @DisplayName("step(...) with SELECT but neither nextLevel nor quitToMainMenu selected does nothing")
    public void testStepSelectNoSelection() throws IOException {
        // Both are false by default
        controller.step(game, GUI.ACTION.SELECT, 0L);

        // No setState() should occur
        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("step(...) with another action (e.g., LEFT) should do nothing")
    public void testStepDefault() throws IOException {
        controller.step(game, GUI.ACTION.LEFT, 0L);

        verify(menu, never()).previousEntry();
        verify(menu, never()).nextEntry();
        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }
}