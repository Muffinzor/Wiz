package wizardo.game.Account;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

public class SaveManager {

    private static final String SAVE_PATH = "player_progress.json";

    public static AccountProgress loadProgress() {
        FileHandle file = Gdx.files.local(SAVE_PATH);
        if (file.exists()) {
            Json json = new Json();
            return json.fromJson(AccountProgress.class, file.readString());
        } else {
            return new AccountProgress();
        }
    }

    public static void saveProgress(AccountProgress progress) {
        Json json = new Json();
        json.setElementType(AccountProgress.class, "unlockedPerks", Boolean.class);
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);

        String progressJson = json.prettyPrint(progress);

        FileHandle file = Gdx.files.local(SAVE_PATH);
        file.writeString(progressJson, false);
    }
}
