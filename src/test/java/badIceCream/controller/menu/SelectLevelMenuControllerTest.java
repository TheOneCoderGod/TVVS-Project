package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.arena.LoaderArenaBuilder;
import badIceCream.model.menu.SelectLevelMenu;
import badIceCream.states.GameState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class SelectLevelMenuControllerTest {

    private SelectLevelMenu menu;
    private SelectLevelMenuController controller;
    private Game game;

    @BeforeEach
    public void setUp() {
        menu = mock(SelectLevelMenu.class);
        game = mock(Game.class);

        // By default, no level is selected
        when(menu.isSelectedLevel1()).thenReturn(false);
        when(menu.isSelectedLevel2()).thenReturn(false);
        when(menu.isSelectedLevel3()).thenReturn(false);
        when(menu.isSelectedLevel4()).thenReturn(false);
        when(menu.isSelectedLevel5()).thenReturn(false);

        controller = new SelectLevelMenuController(menu);
    }

    @Test
    @DisplayName("Constructor should properly instantiate the controller")
    public void testConstructor() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("step(...) with LEFT should call previousEntry()")
    public void testStepLeft() throws IOException {
        controller.step(game, GUI.ACTION.LEFT, 0L);
        verify(menu, times(1)).previousEntry();
        verify(menu, never()).nextEntry();
        verifyNoMoreInteractions(menu);
    }

    @Test
    @DisplayName("step(...) with RIGHT should call nextEntry()")
    public void testStepRight() throws IOException {
        controller.step(game, GUI.ACTION.RIGHT, 0L);
        verify(menu, times(1)).nextEntry();
        verify(menu, never()).previousEntry();
        verifyNoMoreInteractions(menu);
    }

    @Test
    @DisplayName("step(...) with SELECT but no level selected => do nothing")
    public void testStepSelectNoLevel() throws IOException {
        // All isSelectedLevelX return false by default
        controller.step(game, GUI.ACTION.SELECT, 0L);

        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("step(...) with SELECT + Level1 => always load level 1 and set GameState")
    public void testStepSelectLevel1() throws IOException {
        when(menu.isSelectedLevel1()).thenReturn(true);

        // Mock current game state level (but it doesn't matter for level1)
        GameState oldState = mock(GameState.class);
        when(oldState.getLevel()).thenReturn(3); // or 0, doesn't matter
        when(game.getState()).thenReturn(oldState);

        // Mock the arena
        Arena arenaMock = mock(Arena.class);
        when(arenaMock.getWidth()).thenReturn(10);
        when(arenaMock.getHeight()).thenReturn(7);

        // Mock the builder
        try (MockedConstruction<LoaderArenaBuilder> construction =
                     mockConstruction(LoaderArenaBuilder.class, (builderMock, context) -> {
                         when(builderMock.createArena()).thenReturn(arenaMock);
                     })) {

            // Trigger SELECT
            controller.step(game, GUI.ACTION.SELECT, 0L);

            // Verify 1 instance of LoaderArenaBuilder was created with argument (1)
            assertEquals(1, construction.constructed().size());
            // Confirm createArena() was called
            verify(construction.constructed().get(0), times(1)).createArena();
        }

        // Verify setState with Type.game, width=10, height=7
        ArgumentCaptor<GameState> stateCaptor = ArgumentCaptor.forClass(GameState.class);
        verify(game, times(1))
                .setState(stateCaptor.capture(), eq(Type.game), eq(10), eq(7));

        GameState newState = stateCaptor.getValue();
        assertNotNull(newState, "Should create a new GameState for level1");
    }

    @Test
    @DisplayName("step(...) with SELECT + Level2 => only if oldState.getLevel() >= 2")
    public void testStepSelectLevel2() throws IOException {
        when(menu.isSelectedLevel2()).thenReturn(true);

        // If the player's oldState level is 2 or more => loads arena
        GameState oldState = mock(GameState.class);
        when(oldState.getLevel()).thenReturn(2);
        when(game.getState()).thenReturn(oldState);

        Arena arenaMock = mock(Arena.class);
        when(arenaMock.getWidth()).thenReturn(12);
        when(arenaMock.getHeight()).thenReturn(9);

        try (MockedConstruction<LoaderArenaBuilder> construction =
                     mockConstruction(LoaderArenaBuilder.class, (builderMock, context) -> {
                         when(builderMock.createArena()).thenReturn(arenaMock);
                     })) {

            // Trigger SELECT
            controller.step(game, GUI.ACTION.SELECT, 0L);
        }

        // Verify setState was called with (arenaMock)
        verify(game, times(1))
                .setState(any(GameState.class), eq(Type.game), eq(12), eq(9));
    }

    @Test
    @DisplayName("step(...) with SELECT + Level2 => does nothing if oldState.getLevel() < 2")
    public void testStepSelectLevel2NotAllowed() throws IOException {
        when(menu.isSelectedLevel2()).thenReturn(true);

        // oldState.getLevel() = 1 => not allowed
        GameState oldState = mock(GameState.class);
        when(oldState.getLevel()).thenReturn(1);
        when(game.getState()).thenReturn(oldState);

        // Trigger SELECT
        controller.step(game, GUI.ACTION.SELECT, 0L);

        // No setState call
        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("step(...) with SELECT + Level3 => only if oldState.getLevel() >= 3")
    public void testStepSelectLevel3Allowed() throws IOException {
        when(menu.isSelectedLevel3()).thenReturn(true);

        GameState oldState = mock(GameState.class);
        when(oldState.getLevel()).thenReturn(3);
        when(game.getState()).thenReturn(oldState);

        Arena arenaMock = mock(Arena.class);
        when(arenaMock.getWidth()).thenReturn(13);
        when(arenaMock.getHeight()).thenReturn(7);

        try (MockedConstruction<LoaderArenaBuilder> construction =
                     mockConstruction(LoaderArenaBuilder.class, (builderMock, context) -> {
                         when(builderMock.createArena()).thenReturn(arenaMock);
                     })) {

            // Trigger SELECT
            controller.step(game, GUI.ACTION.SELECT, 0L);
        }

        verify(game, times(1))
                .setState(any(GameState.class), eq(Type.game), eq(13), eq(7));
    }

    @Test
    @DisplayName("step(...) with SELECT + Level3 => does nothing if oldState.getLevel() < 3")
    public void testStepSelectLevel3NotAllowed() throws IOException {
        when(menu.isSelectedLevel3()).thenReturn(true);

        GameState oldState = mock(GameState.class);
        when(oldState.getLevel()).thenReturn(2);
        when(game.getState()).thenReturn(oldState);

        controller.step(game, GUI.ACTION.SELECT, 0L);

        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }

    // Similarly for level 4
    @Test
    @DisplayName("step(...) with SELECT + Level4 => only if oldState.getLevel() >= 4")
    public void testStepSelectLevel4Allowed() throws IOException {
        when(menu.isSelectedLevel4()).thenReturn(true);

        GameState oldState = mock(GameState.class);
        when(oldState.getLevel()).thenReturn(4);
        when(game.getState()).thenReturn(oldState);

        Arena arenaMock = mock(Arena.class);
        when(arenaMock.getWidth()).thenReturn(14);
        when(arenaMock.getHeight()).thenReturn(7);

        try (MockedConstruction<LoaderArenaBuilder> construction =
                     mockConstruction(LoaderArenaBuilder.class, (builderMock, context) -> {
                         when(builderMock.createArena()).thenReturn(arenaMock);
                     })) {

            controller.step(game, GUI.ACTION.SELECT, 0L);
        }

        verify(game, times(1))
                .setState(any(GameState.class), eq(Type.game), eq(14), eq(7));
    }

    @Test
    @DisplayName("step(...) with SELECT + Level4 => does nothing if oldState.getLevel() < 4")
    public void testStepSelectLevel4NotAllowed() throws IOException {
        when(menu.isSelectedLevel4()).thenReturn(true);

        GameState oldState = mock(GameState.class);
        when(oldState.getLevel()).thenReturn(3);
        when(game.getState()).thenReturn(oldState);

        controller.step(game, GUI.ACTION.SELECT, 0L);

        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }

    // And for level 5
    @Test
    @DisplayName("step(...) with SELECT + Level5 => only if oldState.getLevel() >= 5")
    public void testStepSelectLevel5Allowed() throws IOException {
        when(menu.isSelectedLevel5()).thenReturn(true);

        GameState oldState = mock(GameState.class);
        when(oldState.getLevel()).thenReturn(5);
        when(game.getState()).thenReturn(oldState);

        Arena arenaMock = mock(Arena.class);
        when(arenaMock.getWidth()).thenReturn(15);
        when(arenaMock.getHeight()).thenReturn(8);

        try (MockedConstruction<LoaderArenaBuilder> construction =
                     mockConstruction(LoaderArenaBuilder.class, (builderMock, context) -> {
                         when(builderMock.createArena()).thenReturn(arenaMock);
                     })) {

            controller.step(game, GUI.ACTION.SELECT, 0L);
        }

        verify(game, times(1))
                .setState(any(GameState.class), eq(Type.game), eq(15), eq(8));
    }

    @Test
    @DisplayName("step(...) with SELECT + Level5 => does nothing if oldState.getLevel() < 5")
    public void testStepSelectLevel5NotAllowed() throws IOException {
        when(menu.isSelectedLevel5()).thenReturn(true);

        GameState oldState = mock(GameState.class);
        when(oldState.getLevel()).thenReturn(4);
        when(game.getState()).thenReturn(oldState);

        controller.step(game, GUI.ACTION.SELECT, 0L);

        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("step(...) with an unhandled action (UP, DOWN, etc.) => does nothing")
    public void testStepOtherAction() throws IOException {
        // E.g., the controller doesn't handle UP or DOWN, so it should do nothing
        controller.step(game, GUI.ACTION.UP, 0L);

        verify(menu, never()).previousEntry();
        verify(menu, never()).nextEntry();
        verify(game, never()).setState(any(), any(), anyInt(), anyInt());
    }
}