package badIceCream.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Audio {
    private final Clip sound;

    public Audio(Clip sound) {
        this.sound = sound;
    }

    public synchronized static Clip loadMusic(String sound) throws LineUnavailableException, UnsupportedAudioFileException, IOException {

        try{
            String rootPath = new File(System.getProperty("user.dir")).getPath();
            String soundPath = rootPath + "/src/main/resources/Music/" + sound;
            File soundFile = new File(soundPath);
            AudioInputStream input = AudioSystem.getAudioInputStream(soundFile);
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(input);

            FloatControl volumeController = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeController.setValue(-20.0f);


            return soundClip;
        } catch (Exception e) {
            throw new FileNotFoundException("Couldn't get the sound!");
        }
    }

    synchronized public void play() {
        if (sound != null) {
            sound.setMicrosecondPosition(0);
            sound.start();
            sound.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    synchronized public void playOnce() {
        if (sound != null) {
            sound.setMicrosecondPosition(0);
            sound.start();
        }
    }

    synchronized public void stop() {
        if (sound != null) sound.stop();
    }
}
