package wizardo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import wizardo.game.Player.Player;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.LoadingScreen.LoadingScreen;
import wizardo.game.Utils.Contacts.WizContactListener;

import java.util.Stack;

import static wizardo.game.SettingsPref.*;

public class Wizardo extends Game {

	private Stack<BaseScreen> screenStack;
	public BaseScreen currentScreen;
	public static World world;
	public static Player player;
	public static WizContactListener contactListener;
	public static AssetManager assetManager;
	public OrthographicCamera mainCamera;
	public OrthographicCamera uiCamera;
	public OrthographicCamera mapCamera;
	
	@Override
	public void create () {
		BaseScreen.screenRatio = Gdx.graphics.getWidth()/1920f;
		screenStack = new Stack<>();
		mainCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mapCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		assetManager = new AssetManager();
		contactListener = new WizContactListener();
		loadSettings();
		addNewScreen(new LoadingScreen(this));
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
	public void addNewScreen(BaseScreen newScreen) {
		if (!screenStack.isEmpty()) {
			screenStack.peek().removeInputs();
			screenStack.peek().pause();
		}
		screenStack.push(newScreen);
		currentScreen = newScreen;
		setScreen(newScreen);
	}

	/**
	 * Swap to a new screen without adding the origin to the screen stack
	 * @param newScreen
	 */
	public void skipToScreen(BaseScreen newScreen) {
		if (!screenStack.isEmpty()) {
			screenStack.pop().dispose();
		}
		currentScreen.removeInputs();
		currentScreen = newScreen;
		screenStack.push(newScreen);
		setScreen(newScreen);
	}

	/**
	 * Resets the stack with only the new screen
	 * @param newScreen
	 */
	public void freshScreen(BaseScreen newScreen) {
		for(BaseScreen screen : screenStack) {
			screen.dispose();
		}

		screenStack.clear();
		currentScreen.removeInputs();
		currentScreen = newScreen;
		screenStack.push(newScreen);
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
		world.setContactListener(contactListener);
	}

	public static void exit() {
		saveSettings();
		Gdx.app.exit();
	}

}
