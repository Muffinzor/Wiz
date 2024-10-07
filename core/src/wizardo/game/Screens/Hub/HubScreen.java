package wizardo.game.Screens.Hub;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import wizardo.game.Screens.Hub.Controls.KeyboardListener_HUB;
import wizardo.game.Screens.Hub.Controls.MouseListener_HUB;
import wizardo.game.Screens.Hub.Controls.ControllerListener_HUB;
import wizardo.game.Display.SpriteRenderer;
import wizardo.game.Player.Pawn;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.createNewWorld;
import static wizardo.game.Wizardo.world;

public class HubScreen extends BaseScreen {

    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    public SpriteRenderer spriteRenderer = new SpriteRenderer();

    private TiledMap map;
    private TiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private Pawn playerPawn;

    public HubScreen(Wizardo game) {
        super(game);
        loadTiledMap("Maps/StartingChunk.tmx");

        createNewWorld();
        playerPawn = new Pawn();
        playerPawn.createPawn(new Vector2(500,500));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
    }

    @Override
    public void render(float delta) {

        if(paused) {
            delta = 0;
        }

        world.step(1 / 30f, 2, 1);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        playerPawn.update(delta);

        spriteRenderer.renderAll();

        Matrix4 debugMatrix = camera.combined.cpy().scl(PPM);
        debugRenderer.render(world, debugMatrix);
    }

    private void setInputs() {
        mouseAdapter = new MouseListener_HUB(this);
        keyboardProcessor = new KeyboardListener_HUB(playerPawn, this);
        controllerAdapter = new ControllerListener_HUB(playerPawn, this);

        inputMultiplexer.clear();
        for (Controller controller : Controllers.getControllers()) {
            controller.addListener(controllerAdapter);
        }
        inputMultiplexer.addProcessor(mouseAdapter);
        inputMultiplexer.addProcessor(keyboardProcessor);
    }

    @Override
    public void show() {
        paused = false;
        setInputs();
    }

    @Override
    public void pause() {
        paused = true;
        playerPawn.stop();
    }

    @Override
    public void hide() {
        paused = true;
        playerPawn.stop();
        removeInputs();
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void dispose() {
        removeInputs();
        batch.dispose();
    }

    public void loadTiledMap(String pathToFile) {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load(pathToFile);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }


}
