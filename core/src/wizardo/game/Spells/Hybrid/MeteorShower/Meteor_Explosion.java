package wizardo.game.Spells.Hybrid.MeteorShower;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.FireballAnims;
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
            light.dimKill(0.01f);
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

        if(anim_element == LIGHTNING) {
            frame.flip(flipX, flipY);
            frame.setRotation(rotation);
        }

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
        light.setLight(red, green, blue, lightAlpha, 150, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void pickAnim() {
        switch(anim_element) {
            case LIGHTNING -> {
                anim = FireballAnims.fireball_explosion_anim_lightning;
                red = 0.75f;
                green = 0.45f;
                frameScale = 1;
                explosionRadius = 50;
            }
            case FIRE -> {
                anim = FireballAnims.fireball_explosion_anim_fire;
                red = 0.85f;
                green = 0.25f;
                frameScale = 1f;
                explosionRadius = 70;
            }
        }
    }

    public void shootProjs() {
        if(nested_spell != null) {

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

    public int getQuantity() {
        int quantity = 0;
        if(nested_spell instanceof ChargedBolts_Spell) {
            quantity = 5 + player.spellbook.chargedbolt_lvl/2;
        }
        return quantity;
    }
}
