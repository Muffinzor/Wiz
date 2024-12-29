package wizardo.game.Screens.Hub;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import wizardo.game.Maps.Hub.HubChunk;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Player.Pawn;
import wizardo.game.Player.Player;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.createNewWorld;
import static wizardo.game.Wizardo.world;

public class HubScreen extends BaseScreen {

    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public MapChunk chunk;
    public Pawn playerPawn;

    public HubScreen(Wizardo game) {
        super(game);
        chunk = new HubChunk("Maps/TEST/Map_HUB.tmx", 0, 0, game, this);

        mainCamera.viewportWidth = Gdx.graphics.getWidth();
        mainCamera.viewportHeight = Gdx.graphics.getHeight();
        mainCamera.position.set(1000, 1000, 0);

        mainCamera.zoom = 1f;

        createNewWorld();
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.3f);
        lightManager.rayHandler = rayHandler;

        playerPawn = new Pawn(this);
        playerPawn.createPawn(new Vector2(1000f/PPM,1000f/PPM));
        Wizardo.player = new Player(playerPawn);

        cursorTexturePath = "Cursors/Battle_Cursor.png";
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(paused) {
            delta = 0;
        }

        world.step(1 / 30f, 2, 1);
        updateCamera();

        chunk.render(delta);

        playerPawn.update(delta);

        displayManager.update(delta);
        lightManager.update(delta);

        Matrix4 debugMatrix = mainCamera.combined.cpy().scl(PPM);
        debugRenderer.render(world, debugMatrix);
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

        float cameraX = Math.max(mainCamera.viewportWidth / 2, Math.min(x, 2048 - mainCamera.viewportWidth / 2));
        float cameraY = Math.max(mainCamera.viewportHeight / 2, Math.min(y, 2048 - mainCamera.viewportHeight / 2));

        // Lerp'd
        mainCamera.position.x += (cameraX - mainCamera.position.x) * 0.03f;
        mainCamera.position.y += (cameraY - mainCamera.position.y) * 0.03f;

        mainCamera.update();
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void dispose() {
        removeInputs();
        batch.dispose();
        chunk.dispose();
    }


}
