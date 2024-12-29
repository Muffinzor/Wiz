package wizardo.game.Spells.Unique.ThundergodBolt;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Toon;
import wizardo.game.Resources.SpellAnims.LightningBoltAnims;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Spells.Unique.ThundergodBolt.ThundergodBolt_Projectile;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Resources.SpellAnims.FrostboltAnims.*;
import static wizardo.game.Spells.SpellUtils.Spell_Element.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class ThundergodBolt_Explosion extends ThundergodBolt_Spell {

    Body body;
    RoundLight light;

    float frameScale = 1;
    float rotation;
    boolean flipX;
    boolean flipY;


    public ThundergodBolt_Explosion(Vector2 position) {
        this.targetPosition = new Vector2(position);
    }

    public void update(float delta) {
        if(!initialized) {
            initialize();
            pickAnim();
            createBody();
            createLight();
            initialized = true;
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2) {
            body.setActive(false);
            light.dimKill(0.022f);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.toRemove(this);
            world.destroyBody(body);
        }
    }

    public void initialize() {
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
    }


    public void drawFrame() {

        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.rotate(rotation);
        frame.setScale(frameScale);
        frame.flip(flipX, flipY);

        screen.centerSort(frame, targetPosition.y * PPM - 15);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);

    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 30);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,100, body.getPosition());
        light.toLightManager();

    }

    public void pickAnim() {
        anim = LightningBoltAnims.getExplosionAnim();
        red = 0.5f;
        green = 0.35f;
        blue = 0.2f;
    }


    public void dispose() {

    }
}
