package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.MainMenu;
import badIceCream.model.menu.SelectLevelMenu;
import badIceCream.states.InstructionsMenuFirstPageState;
import badIceCream.states.SelectLevelMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class MainMenuControllerTest {

    private MainMenu menu;
    private MainMenuController controller;
    private Game game;

    @BeforeEach
    public void setUp() {
        // Mocks
        menu = mock(MainMenu.class);
        game = mock(Game.class);

        // By default, none of the menu selections is chosen
        when(menu.isSelectedExit()).thenReturn(false);
        when(menu.isSelectedInstructions()).thenReturn(false);
        when(menu.isSelectedStart()).thenReturn(false);

        // The controller under test
        controller = new MainMenuController(menu);
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
        verifyNoInteractions(game);
    }

    @Test
    @DisplayName("step(...) with DOWN should call nextEntry() on the menu")
    public void testStepDown() throws IOException {
        controller.step(game, GUI.ACTION.DOWN, 0L);

        verify(menu, times(1)).nextEntry();
        verify(menu, never()).previousEntry();
        verifyNoMoreInteractions(menu);
        verifyNoInteractions(game);
    }

    @Test
    @DisplayName("step(...) with SELECT + Exit selected should set game state to null")
    public void testStepSelectExit() throws IOException {
        when(menu.isSelectedExit()).thenReturn(true);

        controller.step(game, GUI.ACTION.SELECT, 0L);

        verify(game, times(1)).setState(null, Type.nulo, 0, 0);
    }

    @Test
    @DisplayName("step(...) with SELECT + Instructions selected should go to InstructionsMenuFirstPageState")
    public void testStepSelectInstructions() throws IOException {
        when(menu.isSelectedInstructions()).thenReturn(true);

        // Mock the old state for level retrieval
        var oldState = mock(SelectLevelMenuState.class);
        when(oldState.getLevel()).thenReturn(2);
        when(game.getState()).thenReturn(oldState);

        controller.step(game, GUI.ACTION.SELECT, 0L);

        // Capture the newly created InstructionsMenuFirstPageState
        ArgumentCaptor<InstructionsMenuFirstPageState> captor =
                ArgumentCaptor.forClass(InstructionsMenuFirstPageState.class);

        verify(game, times(1))
                .setState(captor.capture(), eq(Type.nulo), eq(0), eq(0));
        var newState = captor.getValue();
        assertNotNull(newState, "InstructionsMenuFirstPageState should not be null");
    }

    @Test
    @DisplayName("step(...) with SELECT + Start selected should go to SelectLevelMenuState")
    public void testStepSelectStart() throws IOException {
        when(menu.isSelectedStart()).thenReturn(true);

        // Mock the old state for level retrieval
        var oldState = mock(InstructionsMenuFirstPageState.class);
        when(oldState.getLevel()).thenReturn(5);
        when(game.getState()).thenReturn(oldState);

        controller.step(game, GUI.ACTION.SELECT, 0L);

        // Capture the newly created SelectLevelMenuState
        ArgumentCaptor<SelectLevelMenuState> captor =
                ArgumentCaptor.forClass(SelectLevelMenuState.class);

        verify(game, times(1))
                .setState(captor.capture(), eq(Type.nulo), eq(0), eq(0));

        var newState = captor.getValue();
        assertNotNull(newState, "SelectLevelMenuState should not be null");
    }

    @Test
    @DisplayName("step(...) with SELECT but no selection does nothing")
    public void testStepSelectNoSelection() throws IOException {
        // all false by default
        controller.step(game, GUI.ACTION.SELECT, 0L);

        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("step(...) with unrecognized action (e.g., LEFT) does nothing")
    public void testStepOtherAction() throws IOException {
        controller.step(game, GUI.ACTION.LEFT, 0L);

        // No calls to menu or game
        verify(menu, never()).previousEntry();
        verify(menu, never()).nextEntry();
        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }
}