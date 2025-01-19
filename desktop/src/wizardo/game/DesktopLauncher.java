package wizardo.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import wizardo.game.Wizardo;

import static wizardo.game.SettingsPref.loadVolume;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		try {
			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setForegroundFPS(60);
			config.setTitle("Wizardo");

			if(isSteamDeck()) {
				//config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
				config.setWindowedMode(1280, 800);
			} else {
				//config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
				config.setWindowedMode(1280, 800);
			}

			new Lwjgl3Application(new Wizardo(), config);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static boolean isSteamDeck() {
		// Check the environment variables or system properties
		String osName = System.getProperty("os.name").toLowerCase();
		String steamDeckEnv = System.getenv("STEAM_DECK");
		return osName.contains("linux") && "1".equals(steamDeckEnv);
	}
}
