package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import badIceCream.model.menu.MainMenu;
import badIceCream.states.InstructionsMenuSecondPageState;
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

class InstructionsMenuFirstPageControllerTest {

    private InstructionsMenuFirstPage menu;
    private InstructionsMenuFirstPageController controller;
    private Game game;

    @BeforeEach
    void setUp() {
        // Create mocks
        menu = mock(InstructionsMenuFirstPage.class);
        game = mock(Game.class);

        // Instantiate the controller with the mock menu
        controller = new InstructionsMenuFirstPageController(menu);
    }

    @Test
    @DisplayName("Constructor should properly instantiate")
    void testConstructor() {
        assertNotNull(controller, "Controller should be instantiated");
    }

    @Test
    @DisplayName("step(...) with PAUSE action should go to MainMenuState")
    void testStepPauseAction() throws IOException {
        // Mock the game’s current state to return some level, e.g. 2
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
    @DisplayName("step(...) with RIGHT action should go to InstructionsMenuSecondPageState")
    void testStepRightAction() throws IOException {
        // Mock the game’s current state to return some level, e.g. 5
        MainMenuState oldState = mock(MainMenuState.class);
        when(oldState.getLevel()).thenReturn(5);
        when(game.getState()).thenReturn(oldState);

        // Invoke the step method with RIGHT
        controller.step(game, GUI.ACTION.RIGHT, 0L);

        // Capture the call to setState(...)
        ArgumentCaptor<InstructionsMenuSecondPageState> stateCaptor =
                ArgumentCaptor.forClass(InstructionsMenuSecondPageState.class);
        verify(game, times(1))
                .setState(stateCaptor.capture(), eq(Type.nulo), eq(0), eq(0));

        InstructionsMenuSecondPageState newState = stateCaptor.getValue();
        assertNotNull(newState, "InstructionsMenuSecondPageState should not be null");
    }

    @Test
    @DisplayName("step(...) with any other action should do nothing")
    void testStepOtherAction() throws IOException {
        // Try an action that isn't PAUSE or RIGHT, e.g. LEFT
        controller.step(game, GUI.ACTION.LEFT, 0L);

        // Verify that game.setState was never called
        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }
}
