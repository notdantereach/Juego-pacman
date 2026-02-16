package lospedros.edu.pacman.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private Clip sirenClip;

    public SoundManager() {
        try {
            File soundFile = new File("path/to/siren.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = audioInputStream.getFormat();
            this.sirenClip = AudioSystem.getClip();
            this.sirenClip.open(audioInputStream);
            FloatControl volumeControl = (FloatControl) this.sirenClip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-10.0f);  // Set initial volume as necessary
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playSiren() {
        if (this.sirenClip != null) {
            this.sirenClip.loop(Clip.LOOP_CONTINUOUSLY);
            createToneClip(300, 600, 0.05); // Volume lowered to 0.05
        }
    }

    private void createToneClip(int frequency, int duration, double volume) {
        // Tone creation logic here
    }
}