package wizardo.game.Screens.Battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import wizardo.game.Resources.PlayerResources;

import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Screens.BaseScreen.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class BattleUI {

    BattleScreen screen;

    public Label goldLabel;

    public BattleUI(BattleScreen screen) {
        this.screen = screen;
        goldPanel();
    }

    public void update() {
        drawPlayerHP();
        drawControllerTarget();
    }

    public void drawPlayerHP() {
        Sprite frame1 = screen.getSprite();
        frame1.set(PlayerResources.shieldEffect);
        frame1.setOrigin(0, 0);
        frame1.setPosition(80 * xRatio, 65 * yRatio);

        float shieldPercentage = player.stats.shield / player.stats.maxShield;

        float originalWidth = frame1.getRegionWidth();
        float renderWidth = originalWidth * shieldPercentage;
        frame1.setRegion(frame1.getRegionX(), frame1.getRegionY(), (int) renderWidth, frame1.getRegionHeight());

        frame1.setSize(renderWidth * xRatio, frame1.getHeight() * yRatio);
        screen.displayManager.spriteRenderer.ui_sprites.add(frame1);

        Sprite frame2 = screen.getSprite();
        frame2.set(PlayerResources.shieldBar);
        frame2.setScale(xRatio, yRatio);
        frame2.setOrigin(0,0);
        frame2.setPosition(50 * xRatio, 45 * yRatio);
        screen.displayManager.spriteRenderer.ui_sprites.add(frame2);

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
            float playerScreenX = playerWorldX - (screen.mainCamera.position.x);
            float playerScreenY = playerWorldY - (screen.mainCamera.position.y);

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
            Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
            frame.set(screen.controllerTargetSprite);
            frame.setCenter(cursorX + Gdx.graphics.getWidth()/2f, cursorY + Gdx.graphics.getHeight()/2f);
            frame.setRotation(targetVector.angleDeg() - 45);  // Optional: Adjust for angle
            screen.displayManager.spriteRenderer.ui_sprites.add(frame);
        }
    }
    public void goldPanel() {
        goldLabel = new Label("" + player.inventory.gold + "g", inventorySkin, "Gear_Name", Color.YELLOW);
        goldLabel.setPosition(630 * xRatio,55 * yRatio);
        screen.stage.addActor(goldLabel);
    }

    public void updateGoldPanel() {
        goldLabel.setText("" + player.inventory.gold + "g");
    }

    public void resize() {
        goldLabel.setPosition(630 * xRatio,55 * yRatio);
    }


}
