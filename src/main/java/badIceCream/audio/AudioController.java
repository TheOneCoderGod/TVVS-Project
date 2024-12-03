package badIceCream.audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class AudioController {
    private static Audio breakWallSound, buildWallSound, gameOverMusic, levelCompleteMusic, levelMusic, menuMusic, runnerMonsterSound;

    public static synchronized void playLevelMusic() {
        if (levelMusic == null) {
            try {
                levelMusic = new Audio(Audio.loadMusic("LevelMusic.wav"));
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        levelMusic.play();
    }
    public static synchronized void playMenuMusic() {
        if (menuMusic == null) {
            try {
                menuMusic = new Audio(Audio.loadMusic("MainMenuMusic.wav"));
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        menuMusic.play();
    }
    public static synchronized void playLevelCompleteMusic() {
        if (levelCompleteMusic == null) {
            try {
                levelCompleteMusic = new Audio(Audio.loadMusic("LevelCompleteMenuSound.wav"));
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        levelCompleteMusic.playOnce();
    }
    public static synchronized void playGameOverMusic() {
        if (gameOverMusic == null) {
            try {
                gameOverMusic = new Audio(Audio.loadMusic("GameOverMenuSound.wav"));
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        gameOverMusic.playOnce();
    }
    public static synchronized void playBuildWallSound() {
        if (buildWallSound == null) {
            try {
                buildWallSound = new Audio(Audio.loadMusic("BuildWallSound.wav"));
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        buildWallSound.playOnce();
    }
    public static synchronized void playBreakWallSound() {
        if (breakWallSound == null) {
            try {
                breakWallSound = new Audio(Audio.loadMusic("BreakWallSound.wav"));
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        breakWallSound.playOnce();
    }
    public static synchronized void playRunnerMonsterSound() {
        if (runnerMonsterSound == null) {
            try {
                runnerMonsterSound = new Audio(Audio.loadMusic("RunnerMonsterSound.wav"));
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        runnerMonsterSound.playOnce();
    }
    public static synchronized void stopLevelMusic() {
        if (levelMusic != null) {
            levelMusic.stop();
        }
    }
    public static synchronized void stopMenuMusic() {
        if (menuMusic != null) {
            menuMusic.stop();
        }
    }
    public static synchronized void stopGameOverMusic() {
        if (gameOverMusic != null) {
            gameOverMusic.stop();
        }
    }
    public static synchronized void stopLevelCompleteMusic() {
        if (levelCompleteMusic != null) {
            levelCompleteMusic.stop();
        }
    }
}
