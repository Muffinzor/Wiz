package wizardo.game.Spells.Frost.Frostbolt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import wizardo.game.Audio.Sounds.SoundPlayer;
import wizardo.game.Display.Text.FloatingDamage;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Resources.Skins.mainMenuSkin;
import static wizardo.game.Resources.SpellAnims.FrostboltAnims.frostbolt_anim_frost;
import static wizardo.game.Resources.SpellAnims.FrostboltAnims.frostbolt_explosion_anim_frost;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.world;

public class Frostbolt_Explosion extends Frostbolt_Spell{

    Animation<Sprite> anim = frostbolt_explosion_anim_frost;

    Body body;
    RoundLight light;

    float rotation;
    boolean flipX;
    boolean flipY;

    int soundType;

    public Frostbolt_Explosion(Vector2 targetPosition) {
        super();
        this.targetPosition = targetPosition;
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

        soundType = MathUtils.random(1,3);
        soundPath = "Sounds/Spells/IceExplosion" + soundType + ".wav";

        screen = currentScreen;
    }

    public void update(float delta) {
        stateTime += delta;

        drawFrame();

        if(!initialized) {
            createBody();
            createLight();
            playSound(body.getPosition());
            initialized = true;
        }

        if(stateTime >= 0.25f) {
            if(body != null) {
                world.destroyBody(body);
                body = null;
            }
        }

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.toRemove(this);
        }
    }


    public void drawFrame() {

        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.rotate(rotation);
        frame.flip(flipX, flipY);
        screen.centerSort(frame, targetPosition.y);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    public void handleCollision(Monster monster) {
        monster.hp -= dmg;

        /**
        FloatingDamage text = screen.displayManager.textManager.pool.getDmgText();
        text.setAll("25", monster.body.getPosition().scl(PPM), mainMenuSkin.getFont("DamageNumbers"), Color.WHITE);
        screen.displayManager.textManager.addDmgText(text);
         */

    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 35);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0,0,0.8f,1,100, body.getPosition());
        light.toLightManager();
        light.dimKill(0.02f);
    }

    public void dispose() {

    }
}
