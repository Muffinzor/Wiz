package wizardo.game.Spells.Lightning.Thunderstorm;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.SpellAnims.ThunderstormAnims;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.world;

public class Thunderstorm_Hit extends Thunderstorm_Spell {

    boolean initialized;
    Vector2 adjustedPosition;

    Body body;
    RoundLight light;

    boolean flipX;

    public Thunderstorm_Hit(Vector2 targetPosition) {

        this.targetPosition = new Vector2(targetPosition);

        flipX = MathUtils.randomBoolean();

        anim = ThunderstormAnims.thunder_lightning_anim;
    }

    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
            nestedProjectiles();
        }

        drawFrame();

        stateTime += delta;

        if(stateTime > 0.2f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setScale(0.7f);
        frame.flip(flipX, false);
        frame.setPosition(body.getPosition().x * PPM - frame.getWidth()/2, body.getPosition().y * PPM - 170);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        adjustedPosition = new Vector2(SpellUtils.getRandomVectorInRadius(targetPosition, 15f/PPM));
        body = BodyFactory.spellExplosionBody(adjustedPosition, radius);
        body.setUserData(this);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0.9f, 0.9f, 0.4f, 0.8f, 160, adjustedPosition);
        screen.lightManager.addLight(light);
        light.dimKill(0.015f);
    }

    public void nestedProjectiles() {
        if(nested_spell != null) {

            float procRate = getProcRate();
            int quantity = getQuantity();

            if (Math.random() >= procRate) {
                for (int i = 0; i < quantity; i++) {
                    Spell proj = nested_spell.clone();
                    proj.setElements(this);
                    proj.screen = screen;
                    proj.originBody = body;
                    proj.spawnPosition = new Vector2(body.getPosition());
                    proj.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), getProjRadius());
                    screen.spellManager.toAdd(proj);
                }
            }

        }
    }

    public float getProjRadius() {
        float projRadius = 2.5f;
        return projRadius;
    }


    public float getProcRate() {
        float procRate = 0.5f;
        float level = (float) (getLvl() + nested_spell.getLvl()) / 2;
        if(nested_spell instanceof ChargedBolts_Spell) {
            procRate = 0.525f - level * .025f;
        }
        return procRate;
    }
    public int getQuantity() {
        int quantity = 1;
        if(nested_spell instanceof ChargedBolts_Spell) {
            quantity = 2 + nested_spell.getLvl();
        }
        if(nested_spell instanceof Frostbolt_Spell) {
            quantity = 2 + nested_spell.getLvl();
        }
        return quantity;
    }

}
