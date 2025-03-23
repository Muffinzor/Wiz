package wizardo.game.Spells.Hybrid.Laser;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Staff.Epic_EnergybeamStaff;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public class Laser_Spell extends Spell {

    public boolean thunderstorm;
    public boolean frostbolt;
    public boolean chargedbolt;
    public boolean rifts;
    public boolean flamejet;
    public boolean overheat;

    int lasers;
    int lasersCast;

    Monster target;
    int targetCounter;
    ArrayList<Monster> inRange;

    public Laser_Spell() {

        string_name = "Lasers";
        levelup_enum = LevelUpEnums.LevelUps.LASERS;

        cooldown = 0.8f;
        speed = 15f;
        dmg = 10;
        autoaimable = true;

        inRange = new ArrayList<>();

    }

    @Override
    public void update(float delta) {

        if(!initialized) {
            setup();
            initialized = true;
        }

        stateTime += delta;

        if(stateTime >= 0.05f * lasersCast) {

            selectTarget();

            if(target != null) {

                Laser_Projectile laser = new Laser_Projectile();
                laser.spawnPosition = new Vector2(originBody.getPosition());
                laser.targetPosition = new Vector2(target.body.getPosition());
                laser.setLaser(this);
                screen.spellManager.add(laser);

                uniqueStaff();
                lasersCast ++;
            }

        }

        if(lasersCast >= lasers) {
            screen.spellManager.remove(this);
        }

    }

    public void uniqueStaff() {
        if(player.inventory.equippedStaff instanceof Epic_EnergybeamStaff) {
            Vector2 reverseVector;
            Vector2 direction = target.body.getPosition().sub(getSpawnPosition());
            direction.scl(-1);
            reverseVector = player.pawn.getPosition().add(direction);

            Laser_Projectile laser = new Laser_Projectile();
            laser.spawnPosition = player.pawn.getPosition();
            laser.targetPosition = reverseVector;
            laser.setElements(this);
            laser.setLaser(this);
            screen.spellManager.add(laser);
        }
    }

    public void setup() {

        if(originBody == null) {
            originBody = player.pawn.body;
            lasers = 3 + player.spellbook.arcanemissile_lvl/2;
        } else {
            lasers = 1;
        }

        inRange = SpellUtils.findMonstersInRangeOfVector(originBody.getPosition(), 10, true);
        if (originBody == player.pawn.body) {
            inRange.sort((m1, m2) -> {
                float distance1 = m1.body.getPosition().dst(player.pawn.getPosition());
                float distance2 = m2.body.getPosition().dst(player.pawn.getPosition());
                return Float.compare(distance1, distance2);
            });

            if (inRange.size() > lasers) {
                inRange.subList(lasers, inRange.size()).clear();
            }
        }
    }

    public void setLaser(Laser_Spell parent) {
        setElements(parent);
        frostbolt = parent.frostbolt;
        rifts = parent.rifts;
        chargedbolt = parent.chargedbolt;
        thunderstorm = parent.thunderstorm;
        flamejet = parent.flamejet;
    }

    public void selectTarget() {
        if(targetCounter == Math.min(inRange.size(), 3)) {
            targetCounter = 0;
        }

        if(!inRange.isEmpty()) {
            target = inRange.get(targetCounter);
            targetCounter++;
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return (player.spellbook.energybeam_lvl + player.spellbook.arcanemissile_lvl)/2;
    }


    @Override
    public int getDmg() {
        int dmg = this.dmg;
        dmg += 10 * player.spellbook.energybeam_lvl;
        dmg = (int) (dmg * (1 + player.spellbook.energyBonusDmg/100f));
        return dmg;
    }

}
