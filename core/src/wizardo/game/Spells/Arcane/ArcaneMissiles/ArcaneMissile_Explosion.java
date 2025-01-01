package wizardo.game.Spells.Arcane.ArcaneMissiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Toon;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class ArcaneMissile_Explosion extends ArcaneMissile_Spell {

    Animation<Sprite> anim;

    Body body;
    RoundLight light;

    float rotation;
    boolean flipX;
    boolean flipY;

    public ArcaneMissile_Explosion(Vector2 targetPosition) {

        this.targetPosition = new Vector2(targetPosition);

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

        radius = 24 + 4 * player.spellbook.overheat_lvl;

        baseDmg = 35;
    }

    public void update(float delta) {

        if(!initialized) {
            pickAnim();
            createBody();
            createLight();
            flamejets();
            initialized = true;
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2f) {
            body.setActive(false);
            light.dimKill(0.025f);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.remove(this);
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }


    public void pickAnim() {
        anim = ExplosionAnims_Toon.getExplosionAnim(anim_element);
        switch (anim_element) {
            case FIRE -> {
                red = 0.8f;
                green = 0.25f;
            }
            case ARCANE -> {
                red = 0.6f;
                blue = 0.9f;
            }
        }
    }
    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        float lightRadius = 75 + 5f * player.spellbook.overheat_lvl;
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, lightRadius, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);

        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void flamejets() {
        if(flamejet) {
            float procRate = 0.65f - 0.05f * player.spellbook.flamejet_lvl;
            if(Math.random() >= procRate) {
                int quantity = 3;
                for (int i = 0; i < quantity; i++) {
                    Flamejet_Spell flame = new Flamejet_Spell();
                    flame.setElements(this);
                    flame.originBody = body;
                    flame.spawnPosition = new Vector2(body.getPosition());
                    flame.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                    screen.spellManager.add(flame);
                }
            }
        }
    }

    @Override
    public int getDmg() {
        return baseDmg + player.spellbook.overheat_lvl * 25;
    }
}
