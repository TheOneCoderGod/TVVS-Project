package badIceCream.controller.game.monsters;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import badIceCream.controller.game.MonsterController;
import badIceCream.controller.game.StepMonsters;
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
        MockitoAnnotations.openMocks(this);
        controller = new MonsterController(mockArena, mockStep, mockMonster);

        // Mock the getIceCream method to return a non-null IceCream object
        IceCream mockIceCream = mock(IceCream.class);
        when(mockArena.getIceCream()).thenReturn(mockIceCream);
    }

    @Test
    public void testStepWithMonsterNotType3() throws IOException {
        when(mockMonster.getType()).thenReturn(1);  // Set monster type to a non-3 value

        long currentTime = System.currentTimeMillis();
        controller.step(currentTime);  // Call step with a random time

        // Verify that the runner behavior is not triggered
        verify(mockStep, times(1)).step(mockMonster, mockArena, currentTime, 0);
        verify(mockMonster, never()).startRunning();
        verify(mockMonster, never()).stopRunning();
    }

    @Test
    public void testStepWithMonsterType3AndRunnerEnabled() throws IOException, NoSuchFieldException, IllegalAccessException {
        when(mockMonster.getType()).thenReturn(3);  // Set monster type to 3
        long currentTime = System.currentTimeMillis();

        // Use reflection to access the private field 'lastChange'
        Field lastChangeField = MonsterController.class.getDeclaredField("lastChange");
        lastChangeField.setAccessible(true);
        long initialLastChange = (long) lastChangeField.get(controller);  // Capture the initial lastChange

        try (MockedStatic<AudioController> mockedAudioController = mockStatic(AudioController.class)) {
            controller.step(currentTime);

            // Check that the runner was enabled and the correct sound was played
            verify(mockMonster).startRunning();
            mockedAudioController.verify(AudioController::playRunnerMonsterSound, times(1));
            assertNotEquals(initialLastChange, lastChangeField.get(controller)); // lastChange should have been updated

            // Ensure the step method was called
            verify(mockStep, times(1)).step(mockMonster, mockArena, currentTime, 0);
        }
    }

    @Test
    public void testStepWithMonsterType3AndRunnerDisabled() throws IOException, NoSuchFieldException, IllegalAccessException {
        when(mockMonster.getType()).thenReturn(3);  // Set monster type to 3

        // Use reflection to access the package-private field 'runnerOn'
        Field runnerOnField = MonsterController.class.getDeclaredField("runnerOn");
        runnerOnField.setAccessible(true);
        runnerOnField.set(controller, true); // Manually enable runner

        long currentTime = System.currentTimeMillis();
        long timeToDisableRunner = currentTime + 10000;  // Set time greater than the random interval

        controller.step(timeToDisableRunner);  // This should toggle the runner state

        // Check that the runner was disabled and the sound was not played
        verify(mockMonster).stopRunning();
        verify(mockStep, times(1)).step(mockMonster, mockArena, timeToDisableRunner, 0);
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
}