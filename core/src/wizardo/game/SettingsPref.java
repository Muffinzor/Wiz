package wizardo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SettingsPref {

    private static final String PREFERENCES_NAME = "GameSettings";  // Preferences file name
    private static final String MUSIC_KEY = "music_volume";
    private static final String SOUND_KEY = "sound_volume";
    private static final float DEFAULT_VOLUME = 1f;
    private static final String DMG_TEXT_ON = "dmg_text_on";

    private static Preferences prefs;

    private static Preferences getPrefs() {
        if (prefs == null) {
            prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        }
        return prefs;
    }

    public static void saveSettings() {
        saveVolume();
        saveDmgTextSettings();
    }

    // Save the current volume setting to preferences
    public static void saveVolume() {
        getPrefs().putFloat(SOUND_KEY, GameSettings.sound_volume);
        getPrefs().flush();  // ensure data is written to disk
    }

    public static void saveDmgTextSettings() {
        getPrefs().putBoolean(DMG_TEXT_ON, GameSettings.dmg_text_on);
        getPrefs().flush();  // ensure data is written to disk
    }

    // Load the volume setting from preferences, returning the default if not set
    public static void loadVolume() {
        GameSettings.sound_volume = getPrefs().getFloat(SOUND_KEY, DEFAULT_VOLUME);
    }

    public static void loadDmgTxt() {
        GameSettings.dmg_text_on = getPrefs().getBoolean(DMG_TEXT_ON, true);
    }

    public static void loadSettings() {
        loadVolume();
        loadDmgTxt();
    }
}
