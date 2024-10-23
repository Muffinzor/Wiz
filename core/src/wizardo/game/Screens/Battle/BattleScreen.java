package wizardo.game.Screens.Battle;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import wizardo.game.Maps.MapGeneration.MapManager;
import wizardo.game.Monsters.Monster;
import wizardo.game.Monsters.MonsterManager;
import wizardo.game.Monsters.TEST_MONSTER;
import wizardo.game.Player.Pawn;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Spells.SpellManager;
import wizardo.game.Wizardo;

import static wizardo.game.Spells.SpellBank.FrostLightning_Spells.frostliteSpells;
import static wizardo.game.Spells.SpellBank.Frost_Spells.frostspells;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class BattleScreen extends BaseScreen {

    boolean initialized;

    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    MapManager mapManager;

    public BattleScreen(Wizardo game, String biome) {
        super(game);

        mainCamera.viewportWidth = Gdx.graphics.getWidth();
        mainCamera.viewportHeight = Gdx.graphics.getHeight();
        mainCamera.position.set(950, 950, 0);

        mainCamera.zoom = 1f;

        createNewWorld();
        rayHandler = new RayHandler(world);
        rayHandler.setCulling(false);
        rayHandler.setAmbientLight(0.3f);
        lightManager.rayHandler = rayHandler;

        Pawn playerPawn = new Pawn(this);
        playerPawn.createPawn(new Vector2(1000f/PPM,1000f/PPM));
        player.pawn = playerPawn;


        mapManager = new MapManager(biome, game, this);

        monsterManager = new MonsterManager(this);
        spellManager = new SpellManager(this);

        cursorTexturePath = "Cursors/Battle_Cursor.png";

        for (int i = 0; i < 200; i++) {
            Monster monster = new TEST_MONSTER(this, new Vector2((float) (Math.random() * 4000/32), (float) (Math.random() * 4000/32)));
            monsterManager.addMonster(monster);
        }

    }

    @Override
    public void render(float delta) {
        if(!initialized) {
            player.spellbook.equippedSpells.add(frostspells[6]);
            initialized = true;
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(paused) {
            delta = 0;
        } else {
            world.step(1 / 30f, 6, 2);
        }
        mapManager.update(player.pawn.getPosition().x * PPM, player.pawn.getPosition().y * PPM);
        mapManager.render(delta);

        player.pawn.update(delta);
        spellManager.update(delta);
        monsterManager.update(delta);
        updateCamera();

        displayManager.update(delta);
        lightManager.update(delta);

        Matrix4 debugMatrix = mainCamera.combined.cpy().scl(PPM);
        //debugRenderer.render(world, debugMatrix);
    }

    @Override
    public void resume() {
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
        float targetX = player.pawn.body.getPosition().x * PPM;
        float targetY = player.pawn.body.getPosition().y * PPM;

        // Lerp'd
        mainCamera.position.x += (targetX - mainCamera.position.x) * 0.03f;
        mainCamera.position.y += (targetY - mainCamera.position.y) * 0.03f;

        mainCamera.update();
    }
}
