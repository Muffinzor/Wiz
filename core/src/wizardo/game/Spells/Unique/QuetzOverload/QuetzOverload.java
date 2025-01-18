package wizardo.game.Spells.Unique.QuetzOverload;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.OverheatAnims;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Hit;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Hit;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class QuetzOverload extends Spell {

    Body body;
    RoundLight light;

    boolean flipX;
    boolean flipY;
    int rotation;

    int thundersCast;
    float thunderInterval = 0.12f;

    public QuetzOverload() {
        this.targetPosition = new Vector2(player.pawn.getPosition());

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

        anim = OverheatAnims.overheat_anim_lightning;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
            chainLightnings();
        }

        drawFrame();

        stateTime += delta;

        if(stateTime >= 0.1f) {
            body.setActive(false);
            light.dimKill(0.01f);
        }

        if(stateTime >= (thundersCast + 1) * thunderInterval) {
            thundersCast++;
            thunders();
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }

    }

    public void handleCollision(Monster monster) {
        Vector2 direction = monster.body.getPosition().sub(body.getPosition());
        monster.movementManager.applyPush(direction, 14, 0.75f, 0.91f);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.setScale(1.4f);
        frame.flip(flipX, flipY);
        screen.centerSort(frame, body.getPosition().y * PPM - 30);
        screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 150);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(1, 1, 0.9f, 1, 150, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void chainLightnings() {
        ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(body.getPosition(), 4, true);
        if(!inRange.isEmpty()) {
            Collections.shuffle(inRange);
            int chains = Math.min(inRange.size(), 5);
            for (int i = 0; i < chains; i++) {
                ChainLightning_Hit chain = new ChainLightning_Hit(inRange.get(i));
                chain.anim_element = SpellUtils.Spell_Element.LIGHTNING;
                chain.originBody = body;
                screen.spellManager.add(chain);
            }
        }
    }

    public void thunders() {
        ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(body.getPosition(), 5, false);
        if(!inRange.isEmpty()) {
            Collections.shuffle(inRange);
            Thunderstorm_Hit thunder = new Thunderstorm_Hit(inRange.getFirst().body.getPosition());
            thunder.anim_element = SpellUtils.Spell_Element.LIGHTNING;
            screen.spellManager.add(thunder);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public int getDmg() {
        return 0;
    }
}
