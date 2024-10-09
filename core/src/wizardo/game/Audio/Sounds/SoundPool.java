package wizardo.game.Audio.Sounds;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayDeque;

public class SoundPool {

    private final ArrayDeque<SoundEffect> array;

    public SoundPool() {
        this.array = new ArrayDeque<>();
    }

    public Sound getSoundObject() {
        if(array.isEmpty()) {
            return new SoundEffect();
        } else {
            return array.removeLast();
        }
    }
}
