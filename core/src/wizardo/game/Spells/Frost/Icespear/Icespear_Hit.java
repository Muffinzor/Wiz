package wizardo.game.Spells.Frost.Icespear;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Screens.Battle.BattleScreen;

import static wizardo.game.Resources.SpellAnims.IcespearAnims.icespear_hit_anim_frost;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;

public class Icespear_Hit extends Icespear_Spell {

    float rotation;
    RoundLight light;

    public Icespear_Hit(Vector2 targetPosition, float rotation) {
        this.targetPosition = new Vector2(targetPosition);
        this.rotation = rotation;

        anim = icespear_hit_anim_frost;
        createLight();

        screen = currentScreen;
    }

    public void update(float delta) {

        drawFrame();

        stateTime += delta;

        if(stateTime >= anim.getAnimationDuration()) {
            currentScreen.spellManager.toRemove(this);
        }

    }

    public void createLight() {
        light = currentScreen.lightManager.pool.getLight();
        light.setLight(0,0,0.5f,1, 25, targetPosition);
        light.toLightManager();
        light.dimKill(0.05f);    }

    public void drawFrame() {

        Sprite frame = currentScreen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.rotate(rotation - 45 + 180);
        currentScreen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

}
