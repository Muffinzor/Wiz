package wizardo.game.Account;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;

import java.io.Serializable;

public class AccountProgress {

    public static AccountProgress loaded;

    public int monster_souls = 0;
    public int max_levelup_choices = 4;
    public int max_equipped_spells = 3;

    public ObjectMap<String, Boolean> unlockedPerks = new ObjectMap<>();

    public AccountProgress() {}

    public static void load() {
        loaded = SaveManager.loadProgress();
        if (loaded == null) {
            loaded = new AccountProgress();
        }
    }

    public static void save() {
        FileHandle file = Gdx.files.local("player_progress.json");
        if (file.exists()) {
            System.out.println("Save file successfully written at: " + file.file().getAbsolutePath());
        } else {
            System.out.println("Save file NOT found!");
        }
        SaveManager.saveProgress(loaded);
    }

}
