package wizardo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import wizardo.game.Screens.MainMenu.MainMenuScreen;

import java.util.Stack;

public class Wizardo extends Game {

	private Stack<Screen> screenStack;
	
	@Override
	public void create () {
		screenStack = new Stack<>();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
