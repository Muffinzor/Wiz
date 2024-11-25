package wizardo.game.Screens.Battle;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import wizardo.game.Maps.MapGeneration.MapManager;
import wizardo.game.Monsters.*;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterProjectiles.MonsterProjectile;
import wizardo.game.Monsters.MonsterProjectiles.MonsterProjectileManager;
import wizardo.game.Player.Pawn;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Spells.SpellManager;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Wizardo;

import static wizardo.game.Spells.SpellBank.Frost_Spells.frostspells;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.*;

public class BattleScreen extends BaseScreen {

    boolean initialized;
    Sprite controllerTargetSprite;

    public MonsterProjectileManager monsterProjManager;

    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    MapManager mapManager;

    public BattleScreen(Wizardo game, String biome) {
        super(game);

        mainCamera.viewportWidth = Gdx.graphics.getWidth();
        mainCamera.viewportHeight = Gdx.graphics.getHeight();
        mainCamera.position.set(950, 950, 0);

        mainCamera.zoom = 1;

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
        monsterProjManager = new MonsterProjectileManager(this);

        cursorTexturePath = "Cursors/Battle_Cursor.png";
        controllerTargetSprite = new Sprite(new Texture("Cursors/Controller_Cursor.png"));

        for (int i = 0; i < 10; i++) {
            Vector2 random = SpellUtils.getRandomVectorInRadius(player.pawn.getPosition(), 15);
            Monster monster = new TEST_RANGED(this, random);
            monsterManager.addMonster(monster);
        }


    }

    @Override
    public void render(float delta) {

        if(!initialized) {
            player.spellbook.equippedSpells.add(frostspells[0]);
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
        monsterProjManager.update(delta);

        drawControllerTarget();
        displayManager.update(delta);
        lightManager.update(delta);
        updateCamera();  // must be after displayManager.update

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

    public void drawControllerTarget() {
        if (controllerActive) {
            // Get the player's world position (in meters)
            Vector2 playerWorldPosition = player.pawn.body.getPosition();

            // Convert the player's world position to screen space manually, taking the camera's position into account
            // Convert world position to screen position in pixels (scaled by PPM)
            float playerWorldX = playerWorldPosition.x * PPM;
            float playerWorldY = playerWorldPosition.y * PPM;

            // Apply the camera's world position (in pixels) to convert world position to screen space
            float playerScreenX = playerWorldX - (mainCamera.position.x);
            float playerScreenY = playerWorldY - (mainCamera.position.y);

            // Now, we need to handle the scale by PPM, which has already been factored into the camera position
            Vector2 playerScreenPosition = new Vector2(playerScreenX, playerScreenY);


            // Define the target vector based on joystick input and normalize
            Vector2 targetVector = player.pawn.targetVector;
            if(targetVector.isZero()) {
                targetVector.set(0,1);
            }
            float angle = targetVector.angleRad();

            // Define the cursor offset in screen pixels
            float aimRadiusPixels = 100f;  // Adjust for the desired cursor offset

            float cursorX = playerScreenPosition.x + aimRadiusPixels * (float)Math.cos(angle);
            float cursorY = playerScreenPosition.y + aimRadiusPixels * (float)Math.sin(angle);


            // Draw the cursor at the calculated position
            Sprite frame = displayManager.spriteRenderer.pool.getSprite();
            frame.set(controllerTargetSprite);
            frame.setCenter(cursorX + Gdx.graphics.getWidth()/2f, cursorY + Gdx.graphics.getHeight()/2f);
            frame.setRotation(targetVector.angleDeg() - 45);  // Optional: Adjust for angle
            displayManager.spriteRenderer.ui_sprites.add(frame);
        }
    }

}
