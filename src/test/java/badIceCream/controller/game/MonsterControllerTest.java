package badIceCream.controller.game;

import badIceCream.audio.AudioController;
import badIceCream.controller.game.monsters.RunnerMovementDisabled;
import badIceCream.controller.game.monsters.RunnerMovementEnabled;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MonsterControllerTest {

    @Mock
    private Arena arena;

    @Mock
    private StepMonsters initialStepMonsters;

    @Mock
    private Monster monster;

    @InjectMocks
    private MonsterController monsterController;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Initialize MonsterController with mocks
        monsterController = new MonsterController(arena, initialStepMonsters, monster);

        // Mock private fields
        setPrivateField(monsterController, "lastMovement", 0L);
        setPrivateField(monsterController, "lastChange", 0L);
        setPrivateField(monsterController, "step", initialStepMonsters);
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void constructor_initializesFieldsCorrectly() throws NoSuchFieldException, IllegalAccessException {
        // Given
        MonsterController controller = new MonsterController(arena, initialStepMonsters, monster);

        // Then
        assertThat(getPrivateField(controller, "arena")).isEqualTo(arena);
        assertThat(getPrivateField(controller, "step")).isEqualTo(initialStepMonsters);
        assertThat(getPrivateField(controller, "monster")).isEqualTo(monster);
        assertThat(getPrivateField(controller, "lastMovement")).isEqualTo(0L);
        assertThat(getPrivateField(controller, "lastChange")).isEqualTo(0L);
        assertThat(getPrivateField(controller, "runnerOn")).isEqualTo(false);
    }

    @Test
    public void step_monsterTypeNot3_delegatesToStep() throws IOException, NoSuchFieldException, IllegalAccessException {
        // Given
        when(monster.getType()).thenReturn(2); // Type is not 3
        long currentTime = 10000L;

        // Mock AudioController's static method
        try (MockedStatic<AudioController> mockedAudio = mockStatic(AudioController.class)) {
            // When
            monsterController.step(currentTime);

            // Then
            verify(initialStepMonsters, times(1)).step(monster, arena, currentTime, 0L);
            assertThat(getPrivateField(monsterController, "lastMovement")).isEqualTo(currentTime);
            assertThat(getPrivateField(monsterController, "runnerOn")).isEqualTo(false);
            mockedAudio.verifyNoInteractions();
        }
    }

    @Test
    public void step_monsterType3_timeBelowThreshold_delegatesToStep() throws IOException, NoSuchFieldException, IllegalAccessException {
        // Given
        when(monster.getType()).thenReturn(3);
        long currentTime = 6000L; // Assuming randomLong is between 5000 and 15000

        // Set lastChange to currentTime - 4000L (< minValue of 5000L)
        setPrivateField(monsterController, "lastChange", currentTime - 4000L);

        // Mock AudioController's static method
        try (MockedStatic<AudioController> mockedAudio = mockStatic(AudioController.class)) {
            // When
            monsterController.step(currentTime);

            // Then
            verify(initialStepMonsters, times(1)).step(monster, arena, currentTime, 0L);
            assertThat(getPrivateField(monsterController, "lastMovement")).isEqualTo(currentTime);
            assertThat(getPrivateField(monsterController, "runnerOn")).isEqualTo(false);
            mockedAudio.verifyNoInteractions();
        }
    }

    @Test
    public void step_underlyingStepThrowsIOException_propagatesException() throws IOException {
        // Given
        when(monster.getType()).thenReturn(2);
        long currentTime = 10000L;
        doThrow(new IOException("Test IOException")).when(initialStepMonsters)
                .step(monster, arena, currentTime, 0L);

        // When / Then
        org.junit.jupiter.api.Assertions.assertThrows(IOException.class, () -> {
            monsterController.step(currentTime);
        });
    }

    private Object getPrivateField(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }
}