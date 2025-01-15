package wizardo.game.Screens.Battle;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Maps.Chest;
import wizardo.game.Maps.MapGeneration.MapManager;
import wizardo.game.Monsters.*;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterActions.MonsterSpellManager;
import wizardo.game.Monsters.MonsterTypes.MawDemon.MawDemon;
import wizardo.game.Player.Pawn;
import wizardo.game.Player.Player;
import wizardo.game.Resources.ScreenResources.LevelUpResources;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Items.Drop.DropManager;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;
import wizardo.game.Screens.MainMenu.MainMenuScreen;
import wizardo.game.Spells.SpellManager;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Wizardo;

import static wizardo.game.GameSettings.debug_camera;
import static wizardo.game.Spells.SpellBank.Frost_Spells.frostspells;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class BattleScreen extends BaseScreen {

    FPSLogger logger = new FPSLogger();

    public float stateTime;
    Sprite controllerTargetSprite;

    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public MapManager mapManager;
    public MonsterSpawner monsterSpawner;
    public MonsterSpellManager monsterSpellManager;
    public DropManager dropManager;

    public final BattleUI battleUI;

    int COUNTER = 0;

    public BattleScreen(Wizardo game, String biome) {
        super(game);

        stage = new Stage(new ScreenViewport(uiCamera));

        mainCamera.viewportWidth = Gdx.graphics.getWidth();
        mainCamera.viewportHeight = Gdx.graphics.getHeight();
        mainCamera.zoom = 4f;

        createNewWorld();
        rayHandler = new RayHandler(world);
        rayHandler.setCulling(false);
        rayHandler.setAmbientLight(0.45f);
        lightManager.rayHandler = rayHandler;

        Pawn playerPawn = new Pawn(this);
        playerPawn.createPawn(new Vector2(1000f/PPM,1000f/PPM));
        player.pawn = playerPawn;
        player.screen = this;

        mapManager = new MapManager(biome, game, this);
        monsterSpawner = new MonsterSpawner(this);

        monsterManager = new MonsterManager(this);
        spellManager = new SpellManager(this);
        player.spellManager = spellManager;
        monsterSpellManager = new MonsterSpellManager(this);
        this.dropManager = Wizardo.dropManager;

        battleUI = new BattleUI(this);
        cursorTexturePath = "Cursors/Battle_Cursor.png";
        controllerTargetSprite = new Sprite(new Texture("Cursors/Controller_Cursor.png"));

    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(paused) {
            delta = 0;
        } else {
            world.step(1 / 30f, 6, 2);
        }
        mapManager.update(player.pawn.getPosition().x * PPM, player.pawn.getPosition().y * PPM);
        mapManager.render(delta);
        monsterSpawner.update(delta);

        player.pawn.update(delta);
        spellManager.update(delta);
        monsterManager.update(delta);
        monsterSpellManager.update(delta);
        dropManager.update(delta);

        battleUI.update();
        displayManager.update(delta);
        lightManager.update(delta);
        updateCamera();  // must be after displayManager.update

        player.update(delta);

        stage.act(delta);
        stage.draw();

        if(player.stats.shield <= 0) {
            game.freshScreen(new MainMenuScreen(game));
        }

        if(debug_camera) {
            Matrix4 debugMatrix = mainCamera.combined.cpy().scl(PPM);
            debugRenderer.render(world, debugMatrix);
            logger.log();
            COUNTER++;
            if(COUNTER > 60) {
                COUNTER = 0;
                System.out.println("MONSTER POOL: " +  monsterSpawner.bodyPool.getSize());
                System.out.println("LIGHT POOL: " +  lightManager.pool.getPoolSize());
            }
        }


    }

    @Override
    public void resume() {
        paused = false;
    }

    public void show() {
        super.show();
        paused = false;
    }

    @Override
    public void pause() {
        paused = true;
        player.pawn.stop();
    }

    public void hide() {
        paused = true;
        player.pawn.stop();
    }

    @Override
    public void dispose() {

    }

    public void updateCamera() {
        int targetX = Math.round(player.pawn.body.getPosition().x * PPM);
        int targetY = Math.round(player.pawn.body.getPosition().y * PPM);

        // Lerp'd
        mainCamera.position.x += (targetX - mainCamera.position.x) * 0.05f;
        mainCamera.position.y += (targetY - mainCamera.position.y) * 0.05f;

        mainCamera.update();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        battleUI.resize();
    }

}
