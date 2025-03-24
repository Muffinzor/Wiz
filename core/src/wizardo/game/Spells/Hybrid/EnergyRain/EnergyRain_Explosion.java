package wizardo.game.Spells.Hybrid.EnergyRain;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Energy;
import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class EnergyRain_Explosion extends EnergyRain_Spell {

    public boolean arcaneMissiles;

    Body body;
    RoundLight light;

    int rotation;
    boolean flipX;
    boolean flipY;

    public EnergyRain_Explosion() {

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            createBody();
            createLight();
            initialized = true;
            chargedbolts();
            arcaneMissiles();
            flamejets();
            rifts();
            thunderstorm();
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2f) {
            body.setActive(false);
            light.dimKill(0.01f);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        if(frostbolt) {
            monster.applySlow(5, 0.7f);
            frostbolts(monster);
        }
        dealDmg(monster);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.setScale(1 * (1 + player.spellbook.energyrain_bonus_radius/100f));
        frame.flip(flipX, flipY);
        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void pickAnim() {
        anim = ExplosionAnims_Energy.getExplosionAnim(anim_element);
        switch(anim_element) {
            case FROST -> {
                red = 0.2f;
                blue = 0.9f;
            }
            case ARCANE -> {
                red = 0.75f;
                blue = 0.95f;
            }
            case LIGHTNING -> {
                red = 0.55f;
                green = 0.35f;
            }
        }
    }

    public void createBody() {
        float radius = 75 * (1 + player.spellbook.energyrain_bonus_radius/100f);
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,170,targetPosition);

        screen.lightManager.addLight(light);
    }

    public void frostbolts(Monster monster) {
        float procRate = 0.9f - 0.05f * player.spellbook.frostbolt_lvl;
        if(Math.random() >= procRate) {
            Frostbolt_Explosion explosion = new Frostbolt_Explosion();
            explosion.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), monster.bodyRadius/PPM);
            explosion.setElements(this);
            screen.spellManager.add(explosion);
        }
    }

    public void chargedbolts() {
        if(chargedbolts) {
            int quantity = 5 + player.spellbook.chargedbolt_lvl/3;
            for (int i = 0; i < quantity; i++) {
                ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                bolt.spawnPosition = new Vector2(body.getPosition());
                bolt.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 3);
                bolt.setElements(this);
                screen.spellManager.add(bolt);
            }
        }
    }

    public void arcaneMissiles() {
        if(arcaneMissiles) {
            int quantity = 5 + player.spellbook.arcanemissile_lvl/3;
            for (int i = 0; i < quantity; i++) {
                ArcaneMissile_Spell missile = new ArcaneMissile_Spell();
                missile.setElements(this);
                missile.spawnPosition = new Vector2(body.getPosition());
                missile.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                screen.spellManager.add(missile);
            }
        }
    }

    public void flamejets() {
        if(flamejet) {
            int quantity = 3 + player.spellbook.flamejet_lvl / 3;
            for (int i = 0; i < quantity; i++) {
                Flamejet_Spell flame = new Flamejet_Spell();
                flame.setElements(this);
                flame.spawnPosition = new Vector2(body.getPosition());
                flame.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                screen.spellManager.add(flame);
            }
        }
    }

    public void rifts() {
        if(rifts) {
            float procRate = 0.66f - 0.033f * player.spellbook.rift_lvl;
            if(Math.random() >= procRate) {
                for (int i = 0; i < 3; i++) {
                    Rift_Zone rift = new Rift_Zone(SpellUtils.getClearRandomPosition(body.getPosition(), 3));
                    rift.setElements(this);
                    screen.spellManager.add(rift);
                }
            }
        }
    }

    public void thunderstorm() {
        if(thunderstorm) {
            float procChance = 0.8f - 0.08f * player.spellbook.thunderstorm_lvl;
            if(Math.random() >= procChance) {
                Thunderstorm_Spell storm = new Thunderstorm_Spell();
                storm.setElements(this);
                storm.spawnPosition = new Vector2(body.getPosition());
                storm.radius = 4;
                storm.duration = 1.2f;
                screen.spellManager.add(storm);
            }

        }
    }

}
