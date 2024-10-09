package wizardo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.MainMenu.MainMenuScreen;

import java.util.Stack;

public class Wizardo extends Game {

	private Stack<BaseScreen> screenStack;
	public BaseScreen currentScreen;
	public static World world;
	
	@Override
	public void create () {
		BaseScreen.screenRatio = Gdx.graphics.getWidth()/1920f;
		screenStack = new Stack<>();
		setNewScreen(new MainMenuScreen(this));
	}


	/**
	 * Renders all the screens in the stack
	 */
	@Override
	public void render() {
		if (!screenStack.isEmpty()) {
			if (screenStack.size() > 1) {
				screenStack.get(screenStack.size() - 2).render(Gdx.graphics.getDeltaTime());
			}
			screenStack.peek().render(Gdx.graphics.getDeltaTime());
		}
	}
	
	@Override
	public void dispose () {
		while (!screenStack.isEmpty()) {
			screenStack.pop().dispose();
		}
		if (world != null) {
			world.dispose();
		}
	}

	/**
	 * Swap to new screen, puts current screen on top of stack
	 * @param newScreen screen to switch to
	 */
	public void setNewScreen(BaseScreen newScreen) {
		if (!screenStack.isEmpty()) {
			screenStack.peek().removeInputs();
			screenStack.peek().pause();
		}
		screenStack.push(newScreen);
		currentScreen = newScreen;
		setScreen(newScreen);
	}

	/**
	 * Pop the current screen off the stack and return to the previous one.
	 */
	public void setPreviousScreen() {
		if (!screenStack.isEmpty()) {
			screenStack.pop().dispose();
			if (!screenStack.isEmpty()) {
				BaseScreen previousScreen = screenStack.peek();
				currentScreen = previousScreen;
				setScreen(previousScreen);
				previousScreen.resume();
			}
		}
	}

	public static void createNewWorld() {
		if(world != null) {
			world.dispose();
		}
		world = new World(new Vector2(0,0), false);
	}



}
