package wizardo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import wizardo.game.Screens.MainMenu.MainMenuScreen;

import java.util.Stack;

public class Wizardo extends Game {

	private Screen previousScreen;
	
	@Override
	public void create () {
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		previousScreen.dispose();
	}

	/**
	 * Swap to new screen, holds reference to screen it leaves
	 * @param previousScreen "this" as in the screen actually active
	 * @param newScreen screen to switch to
	 */
	public void setNewScreen(Screen previousScreen, Screen newScreen) {
		this.previousScreen = previousScreen;
		setScreen(newScreen);
	}

	/**
	 * Goes back to screen held in reference
	 */
	public void setPreviousScreen() {
		if (null != previousScreen) {
			setScreen(previousScreen);
		}
	}
}
