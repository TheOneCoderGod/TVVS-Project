package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.menu.MainMenu;
import badIceCream.model.menu.PauseMenu;
import badIceCream.states.GameState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class PauseMenuControllerTest {

    private PauseMenu menu;
    private GameState parentState;
    private PauseMenuController controller;
    private Game game;

    @BeforeEach
    public void setUp() {
        // Mocks
        menu = mock(PauseMenu.class);
        parentState = mock(GameState.class);
        game = mock(Game.class);

        // By default, no resume/menu selection
        when(menu.isSelectedResume()).thenReturn(false);
        when(menu.isSelectedMenu()).thenReturn(false);

        // Mock parent's getModel() so we can retrieve width/height
        // Typically, getModel() returns an Arena (or some similar class with getWidth()/getHeight())
        Arena arenaMock = mock(Arena.class);
        when(parentState.getModel()).thenReturn(arenaMock);

        // Suppose the arena has certain width/height
        when(arenaMock.getWidth()).thenReturn(31);
        when(arenaMock.getHeight()).thenReturn(15);

        // Instantiate the controller under test
        controller = new PauseMenuController(menu, parentState);
    }

    @Test
    @DisplayName("Constructor should properly instantiate")
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
    @DisplayName("step(...) with SELECT + Resume selected => restore parent game state")
    public void testStepSelectResume() throws IOException {
        // Indicate the resume option was selected
        when(menu.isSelectedResume()).thenReturn(true);

        // Execute the SELECT action
        controller.step(game, GUI.ACTION.SELECT, 0L);

        // Verify setState(...) was called with the existing parent state
        // and the parent's arena dimensions (31x15)
        verify(game, times(1))
                .setState(eq(parentState), eq(Type.game), eq(31), eq(15));
    }

    @Test
    @DisplayName("step(...) with SELECT + Menu selected => go to MainMenuState")
    public void testStepSelectMenu() throws IOException {
        // Indicate the "Menu" option was selected
        when(menu.isSelectedMenu()).thenReturn(true);

        // Mock the old state so we can retrieve a level
        GameState oldState = mock(GameState.class);
        when(oldState.getLevel()).thenReturn(2);
        when(game.getState()).thenReturn(oldState);

        // Execute the SELECT action
        controller.step(game, GUI.ACTION.SELECT, 0L);

        // Capture the new MainMenuState
        ArgumentCaptor<MainMenuState> captor = ArgumentCaptor.forClass(MainMenuState.class);
        verify(game, times(1))
                .setState(captor.capture(), eq(Type.nulo), eq(0), eq(0));

        MainMenuState newState = captor.getValue();
        assertNotNull(newState, "MainMenuState should not be null");
    }

    @Test
    @DisplayName("step(...) with SELECT but neither option => do nothing")
    public void testStepSelectNoOption() throws IOException {
        // By default, isSelectedResume() and isSelectedMenu() are false
        controller.step(game, GUI.ACTION.SELECT, 0L);

        // No setState calls
        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("step(...) with an unhandled action (LEFT, RIGHT, etc.) does nothing")
    public void testStepDefaultAction() throws IOException {
        controller.step(game, GUI.ACTION.LEFT, 0L);

        // No previous/next entry calls
        verify(menu, never()).previousEntry();
        verify(menu, never()).nextEntry();

        // No setState calls
        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }
}