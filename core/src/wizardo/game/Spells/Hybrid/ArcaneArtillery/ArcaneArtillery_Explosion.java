package wizardo.game.Spells.Hybrid.ArcaneArtillery;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Spells.Arcane.Rifts.Rift_Zone;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Explosion;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Spells.SpellUtils.getClearRandomPosition;

public class ArcaneArtillery_Explosion extends ArcaneArtillery_Spell {

    float riftInterval = 0.02f;
    float rifts = 10;
    int riftsCast = 0;

    float explosionInterval = 0.25f;
    float explosions = 5;
    int explosionsCast = 0;

    public ArcaneArtillery_Explosion() {

    }

    public void update(float delta) {
        if(!initialized) {
            frozenOrb();
            thunderstorm();
            initialized = true;
        }

        stateTime += delta;
        if(riftsCast < rifts && stateTime > riftsCast * riftInterval) {

            Rift_Zone rift = new Rift_Zone(getClearRandomPosition(targetPosition, 4));
            rift.lightAlpha = 0.7f;
            rift.setElements(this);
            screen.spellManager.toAdd(rift);

            riftsCast++;
        }

        if(explosionsCast < explosions && stateTime > explosionsCast * explosionInterval) {

            EnergyRain_Explosion explosion = new EnergyRain_Explosion();
            explosion.targetPosition = getClearRandomPosition(targetPosition, 3);
            explosion.setElements(this);
            screen.spellManager.toAdd(explosion);

            explosionsCast++;
        }

        if(riftsCast >= rifts && explosionsCast >= explosions) {
            screen.spellManager.toRemove(this);
        }


    }

    public void frozenOrb() {
        if(frozenorb) {
            Frozenorb_Spell orb = new Frozenorb_Spell();
            orb.spawnPosition = new Vector2(targetPosition.x, targetPosition.y);
            orb.speed = 0;
            orb.duration = 3;
            orb.setElements(this);
            screen.spellManager.toAdd(orb);
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
