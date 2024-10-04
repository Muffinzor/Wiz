package wizardo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import wizardo.game.Screens.MainMenuScreen;

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

	}
	
	@Override
	public void dispose () {

	}
}
