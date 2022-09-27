
import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class MusicPlayer {
    private AudioInputStream audioInputStream;
    private Clip clip;
    private ArrayList<MusicFile> music;
    private Scanner reader;

    public MusicPlayer() throws FileNotFoundException {
        this.music = new ArrayList<>();
        this.reader = new Scanner(new File("src/main/resources/Music"));
        loadMusicFiles();
    }


    public void loadMusic(int choice) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(music.get(choice).toFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    public void loadMusicFiles() {
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] array = line.split(",");
            music.add(new MusicFile(array[0], Integer.parseInt(array[1]), Boolean.parseBoolean(array[2]), Path.of(array[3])));
        }
    }

    public int getNumOfMusic(boolean isPremium) {
        if (isPremium) {
            return music.size();
        }
        int counter = 0;
        for (MusicFile file: music) {
            if (!file.isPremium()) {
                counter++;
            }
        }
        return counter;
    }

    public void play() {
       clip.start();
    }

    public int getMusicPrice(int choice) {
        return music.get(choice).getPrice();
    }

    public void stop() {
        clip.setMicrosecondPosition(0);
        clip.stop();
    }

    public void printMusic(boolean isPremium) {
        int index = 1;
            for (MusicFile musicFile: music) {
                if (isPremium) {
                System.out.println("(" + index + ") " + musicFile);
                }
                else {
                    if (!musicFile.isPremium()) {
                        System.out.println("(" + index + ") " + musicFile);
                    }
                }
                index++;
        }
    }
}
