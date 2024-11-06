package wizardo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SettingsPref {

    private static final String PREFERENCES_NAME = "GameSettings";  // Preferences file name
    private static final String MUSIC_KEY = "music_volume";
    private static final String SOUND_KEY = "sound_volume";
    private static final float DEFAULT_VOLUME = 1f;
    private static final boolean DMG_TEXT_ON = true;

    private static Preferences prefs;

    private static Preferences getPrefs() {
        if (prefs == null) {
            prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        }
        return prefs;
    }

    // Save the current volume setting to preferences
    public static void saveVolume() {
        getPrefs().putFloat(SOUND_KEY, GameSettings.sound_volume);
        getPrefs().flush();  // ensure data is written to disk
    }

    // Load the volume setting from preferences, returning the default if not set
    public static void loadVolume() {
        GameSettings.sound_volume = getPrefs().getFloat(SOUND_KEY, DEFAULT_VOLUME);
    }
}
