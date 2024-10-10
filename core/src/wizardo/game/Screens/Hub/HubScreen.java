package wizardo.game.Screens.Hub;

import box2dLight.RayHandler;
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
import wizardo.game.Display.DisplayManager;
import wizardo.game.Screens.Hub.Controls.KeyboardMouseListener_HUB;
import wizardo.game.Screens.Hub.Controls.ControllerListener_HUB;
import wizardo.game.Player.Pawn;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.createNewWorld;
import static wizardo.game.Wizardo.world;

public class HubScreen extends BaseScreen {

    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    private TiledMap map;
    private TiledMapRenderer mapRenderer;

    private Pawn playerPawn;

    public HubScreen(Wizardo game) {
        super(game);
        loadTiledMap("Maps/StartingChunk.tmx");

        camera = new OrthographicCamera();
        camera.viewportWidth = Gdx.graphics.getWidth();
        camera.viewportHeight = Gdx.graphics.getHeight();
        camera.position.set(1000, 1000, 0);

        camera.zoom = 1f;

        displayManager = new DisplayManager(this);

        createNewWorld();
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.3f);

        playerPawn = new Pawn(this);
        playerPawn.createPawn(new Vector2(1000,1000));

        cursorTexturePath = "Cursors/Battle_Cursor.png";
    }

    @Override
    public void render(float delta) {

        if(paused) {
            delta = 0;
        }

        world.step(1 / 30f, 2, 1);
        updateCamera();

        mapRenderer.setView(camera);
        mapRenderer.render();

        playerPawn.update(delta);

        displayManager.update(delta);

        Matrix4 debugMatrix = camera.combined.cpy().scl(PPM);
        debugRenderer.render(world, debugMatrix);


    }

    private void setInputs() {
        keyboardProcessor = new KeyboardMouseListener_HUB(playerPawn, this);
        controllerAdapter = new ControllerListener_HUB(playerPawn, this);

        inputMultiplexer.clear();
        for (Controller controller : Controllers.getControllers()) {
            controller.addListener(controllerAdapter);
        }
        inputMultiplexer.addProcessor(keyboardProcessor);
    }

    @Override
    public void show() {
        paused = false;
        setInputs();
        setCursorTexture();
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

    public void updateCamera() {

        float x = playerPawn.getBodyX() * PPM;
        float y = playerPawn.getBodyY() * PPM;

        float cameraX = Math.max(camera.viewportWidth / 2, Math.min(x, 2560 - camera.viewportWidth / 2));
        float cameraY = Math.max(camera.viewportHeight / 2, Math.min(y, 2560 - camera.viewportHeight / 2));

        camera.position.x += (cameraX - camera.position.x) * 0.03f;
        camera.position.y += (cameraY - camera.position.y) * 0.03f;

        camera.update();
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

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(playerPawn.getBodyX() * PPM, playerPawn.getBodyY() * PPM, 0);
        camera.update();
    }


}
