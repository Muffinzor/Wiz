package wizardo.game.Screens.Battle;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import wizardo.game.Display.DisplayManager;
import wizardo.game.Lighting.LightManager;
import wizardo.game.Maps.Hub.HubChunk;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapGeneration.MapManager;
import wizardo.game.Monsters.Monster;
import wizardo.game.Monsters.TEST_MONSTER;
import wizardo.game.Player.Pawn;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.createNewWorld;
import static wizardo.game.Wizardo.world;

public class BattleScreen extends BaseScreen {

    ArrayList<Monster> monsters = new ArrayList<>();

    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public Pawn playerPawn;

    TEST_MONSTER monster;
    TEST_MONSTER monster1;TEST_MONSTER monster2;

    MapManager mapManager;


    public BattleScreen(Wizardo game, String biome) {
        super(game);

        mainCamera.viewportWidth = Gdx.graphics.getWidth();
        mainCamera.viewportHeight = Gdx.graphics.getHeight();
        mainCamera.position.set(950, 950, 0);

        mainCamera.zoom = 1f;

        createNewWorld();
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.3f);

        playerPawn = new Pawn(this);
        playerPawn.createPawn(new Vector2(500,500));

        mapManager = new MapManager(biome, game, this);

        cursorTexturePath = "Cursors/Battle_Cursor.png";


        for (int i = 0; i < 60; i++) {
            Monster monster = new TEST_MONSTER(this, new Vector2(i * 5, i * 5));
            monsters.add(monster);
        }
    }

    @Override
    public void render(float delta) {
        if(paused) {
            delta = 0;
        }

        for(Monster monster : monsters) {
            monster.update(delta);
        }

        world.step(1 / 30f, 2, 1);

        mapManager.update(playerPawn.getBodyX() * PPM, playerPawn.getBodyY() * PPM);
        mapManager.render(delta);

        playerPawn.update(delta);
        updateCamera();

        displayManager.update(delta);
        lightManager.update(delta);

        Matrix4 debugMatrix = mainCamera.combined.cpy().scl(PPM);
        debugRenderer.render(world, debugMatrix);
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void pause() {
        paused = true;
        playerPawn.stop();
    }

    public void hide() {
        paused = true;
        playerPawn.stop();
    }

    @Override
    public void dispose() {

    }

    public void updateCamera() {
        float targetX = playerPawn.body.getPosition().x * PPM;
        float targetY = playerPawn.body.getPosition().y * PPM;

        // Lerp'd
        mainCamera.position.x += (targetX - mainCamera.position.x) * 0.03f;
        mainCamera.position.y += (targetY - mainCamera.position.y) * 0.03f;

        mainCamera.update();
    }
}
