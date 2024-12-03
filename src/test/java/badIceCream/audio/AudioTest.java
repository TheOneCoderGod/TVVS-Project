package badIceCream.audio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AudioTest {

    private Clip mockClip;
    private Audio audio;

    @BeforeEach
    public void setUp() {
        mockClip = mock(Clip.class);
        audio = new Audio(mockClip);
    }

    @Test
    public void testLoadMusicFileNotFound() {
        assertThrows(FileNotFoundException.class, () -> {
            Audio.loadMusic("D:/School/Masters/1st Year/1st Semester/TVVS/Project/project-bad-ice-cream/src/main/resources/Music/nonexistent.wav");
        });
    }

    @Test
    public void testPlay() {
        audio.play();
        verify(mockClip, times(1)).setMicrosecondPosition(0);
        verify(mockClip, times(1)).start();
        verify(mockClip, times(1)).loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Test
    public void testPlayOnce() {
        audio.playOnce();
        verify(mockClip, times(1)).setMicrosecondPosition(0);
        verify(mockClip, times(1)).start();
    }

    @Test
    public void testStop() {
        audio.stop();
        verify(mockClip, times(1)).stop();
    }
}