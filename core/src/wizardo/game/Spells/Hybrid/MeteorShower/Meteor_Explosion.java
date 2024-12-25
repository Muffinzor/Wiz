package wizardo.game.Spells.Hybrid.MeteorShower;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.FireballAnims;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Display.DisplayUtils.getSprite;
import static wizardo.game.Spells.SpellUtils.Spell_Element.LIGHTNING;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Meteor_Explosion extends MeteorShower_Spell {

    Body body;
    RoundLight light;
    float frameScale;
    int rotation;
    boolean flipX;
    boolean flipY;

    float explosionRadius;

    public Meteor_Explosion(Vector2 targetPosition) {
        this.targetPosition = new Vector2(targetPosition);

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            createBody();
            createLight();
            shootProjs();
            initialized = true;
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2f) {
            body.setActive(false);
            light.dimKill(0.012f);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void drawFrame() {
        Sprite frame = getSprite(screen);
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);

        frame.flip(flipX, flipY);
        frame.setRotation(rotation);

        if(frameScale != 1) {
            frame.setScale(frameScale);
        }

        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, explosionRadius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 170, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void pickAnim() {
        anim = FireballAnims.getAnim(anim_element);
        switch(anim_element) {
            case LIGHTNING -> {
                red = 0.75f;
                green = 0.25f;
                frameScale = 0.65f;
                explosionRadius = 50;
            }
            case FIRE -> {
                red = 0.85f;
                green = 0.25f;
                frameScale = 0.65f;
                explosionRadius = 70;
            }
            case ARCANE -> {
                red = 0.55f;
                blue = 0.85f;
                frameScale = 0.7f;
                explosionRadius = 70;
            }
        }
    }

    public void shootProjs() {
        if(nested_spell != null) {
            float procRate = getProcRate();

            if(Math.random() >= procRate) {

                int quantity = getQuantity();
                for (int i = 0; i < quantity; i++) {
                    Spell proj = nested_spell.clone();
                    proj.setElements(this);
                    proj.spawnPosition = new Vector2(body.getPosition());
                    proj.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                    proj.originBody = body;
                    screen.spellManager.toAdd(proj);
                }
            }
        }
    }

    public float getProcRate() {
        float procRate = 1;
        if(nested_spell instanceof ChargedBolts_Spell) {
            procRate = 0.8f - 0.025f * player.spellbook.chargedbolt_lvl;
        }
        if(nested_spell instanceof Flamejet_Spell) {
            procRate = 0.8f - 0.05f * player.spellbook.flamejet_lvl;
        }
        return procRate;
    }

    public int getQuantity() {
        int quantity = 0;
        if(nested_spell instanceof ChargedBolts_Spell) {
            quantity = 5 + player.spellbook.chargedbolt_lvl/2;
        }
        if(nested_spell instanceof Flamejet_Spell) {
            quantity = 3 + player.spellbook.flamejet_lvl/2;
        }
        return quantity;
    }
}
