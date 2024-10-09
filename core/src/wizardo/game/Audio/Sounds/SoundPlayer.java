package wizardo.game.Audio.Sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer implements Disposable {

    private static SoundPlayer soundPlayer;

    private Map<String, Sound> soundMap;

    private SoundPlayer() {
        soundMap = new HashMap<>();
    }

    public static SoundPlayer getSoundPlayer() {
        if (soundPlayer == null) {
            soundPlayer = new SoundPlayer();
        }
        return soundPlayer;
    }

    public void playSound(String filePath, float volume){
        Sound sound = getSound(filePath);
        sound.play(volume);
    }

    public Sound getSound(String filePath) {
        Sound sound = soundMap.get(filePath);
        if (sound == null) {
            sound = Gdx.audio.newSound(Gdx.files.internal(filePath));
            soundMap.put(filePath, sound);
        }
        return sound;
    }

    @Override
    public void dispose() {

    }
}
