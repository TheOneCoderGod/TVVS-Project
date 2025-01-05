package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import badIceCream.model.menu.MainMenu;
import badIceCream.states.InstructionsMenuFirstPageState;
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

public class InstructionsMenuSecondPageControllerTest {

    private InstructionsMenuSecondPage menu;
    private InstructionsMenuSecondPageController controller;
    private Game game;

    @BeforeEach
    public void setUp() {
        // Create mocks
        menu = mock(InstructionsMenuSecondPage.class);
        game = mock(Game.class);

        // Instantiate the controller with the mock menu
        controller = new InstructionsMenuSecondPageController(menu);
    }

    @Test
    @DisplayName("Constructor should properly instantiate")
    public void testConstructor() {
        assertNotNull(controller, "Controller should be instantiated");
    }

    @Test
    @DisplayName("step(...) with PAUSE action should go to MainMenuState")
    public void testStepPauseAction() throws IOException {
        // Mock the game’s current state to return some level, e.g., 2
        MainMenuState oldState = mock(MainMenuState.class);
        when(oldState.getLevel()).thenReturn(2);
        when(game.getState()).thenReturn(oldState);

        // Invoke the step method with PAUSE
        controller.step(game, GUI.ACTION.PAUSE, 0L);

        // Capture the call to setState(...)
        ArgumentCaptor<MainMenuState> stateCaptor = ArgumentCaptor.forClass(MainMenuState.class);
        verify(game, times(1))
                .setState(stateCaptor.capture(), eq(Type.nulo), eq(0), eq(0));

        // Verify we got the MainMenuState
        MainMenuState newState = stateCaptor.getValue();
        assertNotNull(newState, "MainMenuState should not be null");
    }

    @Test
    @DisplayName("step(...) with LEFT action should go to InstructionsMenuFirstPageState")
    public void testStepLeftAction() throws IOException {
        // Mock the game’s current state to return some level, e.g., 5
        MainMenuState oldState = mock(MainMenuState.class);
        when(oldState.getLevel()).thenReturn(5);
        when(game.getState()).thenReturn(oldState);

        // Invoke the step method with LEFT
        controller.step(game, GUI.ACTION.LEFT, 0L);

        // Capture the call to setState(...)
        ArgumentCaptor<InstructionsMenuFirstPageState> stateCaptor =
                ArgumentCaptor.forClass(InstructionsMenuFirstPageState.class);
        verify(game, times(1))
                .setState(stateCaptor.capture(), eq(Type.nulo), eq(0), eq(0));

        InstructionsMenuFirstPageState newState = stateCaptor.getValue();
        assertNotNull(newState, "InstructionsMenuFirstPageState should not be null");
    }

    @Test
    @DisplayName("step(...) with an unhandled action (like RIGHT) should do nothing")
    public void testStepOtherAction() throws IOException {
        // Use an action not handled by the if statements
        controller.step(game, GUI.ACTION.RIGHT, 0L);

        // Ensure we do not call setState
        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }
}