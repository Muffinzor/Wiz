package wizardo.game.Spells.Hybrid.ArcaneArtillery;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Explosion;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Spells.SpellUtils.getClearRandomPosition;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class ArcaneArtillery_Explosion extends ArcaneArtillery_Spell {

    Body frozenBody;

    float riftInterval = 0.2f;
    int nbrOfRifts = 10;
    int riftsCast = 0;

    float explosionInterval = 0.25f;
    float explosions = 3;
    int explosionsCast = 0;

    public ArcaneArtillery_Explosion() {

    }

    public void update(float delta) {
        if(!initialized) {
            frozenOrb();
            thunderstorm();
            initialized = true;
            initialExplosion();
        }

        stateTime += delta;

        rifts();
        megaRifts();


        if(frozenorb && frozenBody != null && stateTime >= 0.25f) {
            world.destroyBody(frozenBody);
            frozenBody = null;
        }



        if(explosionsCast < explosions && stateTime > explosionsCast * explosionInterval) {

            EnergyRain_Explosion explosion = new EnergyRain_Explosion();
            explosion.targetPosition = getClearRandomPosition(targetPosition, 3);
            explosion.arcaneMissiles = arcaneMissiles;
            explosion.setElements(this);
            screen.spellManager.toAdd(explosion);

            explosionsCast++;
        }

        if(riftsCast >= nbrOfRifts && explosionsCast >= explosions) {
            screen.spellManager.toRemove(this);
        }


    }

    public void handleCollision(Monster monster) {
        float duration = 3 + 0.2f * player.spellbook.frozenorb_lvl;
        monster.applyFreeze(duration, duration * 2);
    }

    public void initialExplosion() {
        EnergyRain_Explosion explosion = new EnergyRain_Explosion();
        explosion.targetPosition = new Vector2(targetPosition);
        explosion.arcaneMissiles = arcaneMissiles;
        explosion.setElements(this);
        screen.spellManager.toAdd(explosion);
    }

    public void rifts() {
        if(rift) {
            if (riftsCast < nbrOfRifts && stateTime > riftsCast * riftInterval) {

                Rift_Zone rift = new Rift_Zone(getClearRandomPosition(targetPosition, 4));
                rift.lightAlpha = 0.85f;
                rift.setElements(this);
                screen.spellManager.toAdd(rift);

                riftsCast++;
            }
        }
    }

    public void megaRifts() {
        if(megaRift) {

            if (riftsCast < nbrOfRifts && stateTime > riftsCast * riftInterval) {

                nbrOfRifts = 1 + player.spellbook.rift_lvl / 5;

                Vector2 targetPos;
                if (riftsCast == 0) {
                    targetPos = new Vector2(targetPosition);
                } else {
                    targetPos = SpellUtils.getClearRandomPosition(targetPosition, 8);
                }
                Rift_Zone rift = new Rift_Zone(targetPos);
                rift.overheat = true;
                rift.anim_element = FIRE;
                screen.spellManager.toAdd(rift);
                riftsCast++;


            }
        }
    }

    public void frozenOrb() {

        if(frozenorb) {
            float frozenRadius = 110 + 10 * player.spellbook.frozenorb_lvl;
            frozenBody = BodyFactory.spellExplosionBody(targetPosition, frozenRadius);
            frozenBody.setUserData(this);
        }
    }

    public void thunderstorm() {
        if(thunderstorm) {
            Thunderstorm_Spell storm = new Thunderstorm_Spell();
            storm.setElements(this);
            storm.spawnPosition = new Vector2(targetPosition);
            storm.arcaneMissile = true;
            storm.radius = 5;
            storm.duration = 1.5f;
            screen.spellManager.toAdd(storm);
        }
    }


}
