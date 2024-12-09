package badIceCream.controller.game.monsters;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import badIceCream.controller.game.MonsterController;
import badIceCream.controller.game.StepMonsters;
import badIceCream.model.Position;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import badIceCream.audio.AudioController;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.monsters.Monster;
import badIceCream.model.game.elements.IceCream;

import java.io.IOException;
import java.lang.reflect.Field;

public class MonsterControllerTest {

    @Mock private Arena mockArena;
    @Mock private Monster mockMonster;
    @Mock private StepMonsters mockStep;

    private MonsterController controller;

    @Before
    public void setup() {
        // Add the Mockito Java agent
        System.setProperty("org.mockito.inline.mock-maker", "true");

        MockitoAnnotations.openMocks(this);
        controller = new MonsterController(mockArena, mockStep, mockMonster);

        // Mock the getIceCream method to return a non-null IceCream object
        IceCream mockIceCream = mock(IceCream.class);
        when(mockArena.getIceCream()).thenReturn(mockIceCream);

        // Mock the getPosition method to return a non-null Position object for IceCream
        Position mockIceCreamPosition = mock(Position.class);
        when(mockIceCream.getPosition()).thenReturn(mockIceCreamPosition);

        // Mock the getPosition method to return a non-null Position object for Monster
        Position mockMonsterPosition = mock(Position.class);
        when(mockMonster.getPosition()).thenReturn(mockMonsterPosition);

        // Mock the getDown, getUp, getLeft, and getRight methods to return non-null Position objects
        when(mockMonsterPosition.getDown()).thenReturn(mock(Position.class));
        when(mockMonsterPosition.getUp()).thenReturn(mock(Position.class));
        when(mockMonsterPosition.getLeft()).thenReturn(mock(Position.class));
        when(mockMonsterPosition.getRight()).thenReturn(mock(Position.class));
    }

    @Test
    public void testStepWithMonsterNotType3() throws IOException {
        when(mockMonster.getType()).thenReturn(1);  // Set monster type to a non-3 value

        long currentTime = System.currentTimeMillis();
        controller.step(currentTime);  // Call step with a random time

        // Verify that the step method is invoked and runner methods are not triggered
        verify(mockStep, times(1)).step(mockMonster, mockArena, currentTime, 0);
        verify(mockMonster, never()).startRunning();
        verify(mockMonster, never()).stopRunning();
    }

    @Test
    public void testStepWithMonsterType3AndInterval() throws IOException, NoSuchFieldException, IllegalAccessException {
        when(mockMonster.getType()).thenReturn(3);

        long currentTime = System.currentTimeMillis();

        controller.step(currentTime);  // First step

        // Use reflection to access the private field 'lastChange'
        Field lastChangeField = MonsterController.class.getDeclaredField("lastChange");
        lastChangeField.setAccessible(true);

        // Ensure lastChange is set
        assertEquals(currentTime, lastChangeField.get(controller));
    }

        @Test
    public void testStepMonsters() throws IOException {
        // Create a mock implementation of StepMonsters
        StepMonsters mockStepMonsters = mock(StepMonsters.class);

        // Create a MonsterController with the mock StepMonsters
        MonsterController controller = new MonsterController(mockArena, mockStepMonsters, mockMonster);

        long currentTime = System.currentTimeMillis();
        controller.step(currentTime);

        // Verify that the step method of StepMonsters is called
        verify(mockStepMonsters, times(1)).step(mockMonster, mockArena, currentTime, 0);
    }
}