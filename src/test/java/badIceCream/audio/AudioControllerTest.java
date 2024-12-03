package badIceCream.audio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AudioControllerTest {

    private AudioController audioController;
    private Audio mockAudio;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Initialize AudioController and mock Audio object
        audioController = new AudioController();
        mockAudio = mock(Audio.class);

        // Using reflection to access private fields of AudioController
        setPrivateField("levelMusic", mockAudio);
        setPrivateField("menuMusic", mockAudio);
        setPrivateField("levelCompleteMusic", mockAudio);
        setPrivateField("gameOverMusic", mockAudio);
        setPrivateField("buildWallSound", mockAudio);
        setPrivateField("breakWallSound", mockAudio);
        setPrivateField("runnerMonsterSound", mockAudio);
    }

    private void setPrivateField(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        // Access the private fields using reflection
        Field field = AudioController.class.getDeclaredField(fieldName);
        field.setAccessible(true);  // Make the field accessible
        field.set(audioController, value);  // Set the field to the mock object
    }

    @Test
    public void testPlayLevelMusic() {
        AudioController.playLevelMusic();
        verify(mockAudio, times(1)).play();
    }

    @Test
    public void testStopLevelMusic() {
        AudioController.playLevelMusic();
        AudioController.stopLevelMusic();
        verify(mockAudio, times(1)).stop();
    }

    @Test
    public void testPlayMenuMusic() {
        AudioController.playMenuMusic();
        verify(mockAudio, times(1)).play();
    }

    @Test
    public void testStopMenuMusic() {
        AudioController.playMenuMusic();
        AudioController.stopMenuMusic();
        verify(mockAudio, times(1)).stop();
    }

    @Test
    public void testPlayLevelCompleteMusic() {
        AudioController.playLevelCompleteMusic();
        verify(mockAudio, times(1)).playOnce();
    }

    @Test
    public void testPlayGameOverMusic() {
        AudioController.playGameOverMusic();
        verify(mockAudio, times(1)).playOnce();
    }

    @Test
    public void testPlayBuildWallSound() {
        AudioController.playBuildWallSound();
        verify(mockAudio, times(1)).playOnce();
    }

    @Test
    public void testPlayBreakWallSound() {
        AudioController.playBreakWallSound();
        verify(mockAudio, times(1)).playOnce();
    }

    @Test
    public void testPlayRunnerMonsterSound() {
        AudioController.playRunnerMonsterSound();
        verify(mockAudio, times(1)).playOnce();
    }
}
