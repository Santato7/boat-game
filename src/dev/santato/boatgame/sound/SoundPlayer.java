package dev.santato.boatgame.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
    private static Clip backgroundMusicClip;

    public static void playBackgroundMusic(String path) {
        try {
            URL soundURL = SoundPlayer.class.getResource(path);
            if (soundURL == null) return;

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioIn);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);

            FloatControl volumeControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            float currentVolume = volumeControl.getValue();
            volumeControl.setValue(currentVolume - 20.0f);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.stop();
        }
    }

    public static void playSound(String path) {
        try {
            URL soundURL = SoundPlayer.class.getResource(path);
            if (soundURL == null) return;

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
