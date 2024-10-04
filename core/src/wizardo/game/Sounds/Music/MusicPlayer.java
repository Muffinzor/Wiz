package wizardo.game.Sounds.Music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {

    private static MusicPlayer musicPlayer;

    private Music currentMusic;
    private String currentMusicFilePath;

    public static MusicPlayer getMusicPlayer() {
        if (musicPlayer == null) {
            musicPlayer = new MusicPlayer();
        }
        return musicPlayer;
    }

    public void playMusic(String filePath, boolean looping) {

        //If music to be played and music playing are the same
        if (currentMusicFilePath != null && currentMusicFilePath.equals(filePath)) {
            return;
        }

        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }

        // Load and start the new music
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        currentMusicFilePath = filePath;
        currentMusic.setLooping(looping);
        currentMusic.play();
    }

    public void pauseMusic() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.pause();
        }
    }

    public void resumeMusic() {
        if (currentMusic != null && !currentMusic.isPlaying()) {
            currentMusic.play();
        }
    }

    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
    }

    public void dispose() {
        if (currentMusic != null) {
            currentMusic.dispose();
            currentMusic = null;
            currentMusicFilePath = null;
        }
    }
}
