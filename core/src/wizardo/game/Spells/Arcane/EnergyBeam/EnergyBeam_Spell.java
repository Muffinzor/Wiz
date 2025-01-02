package wizardo.game.Spells.Arcane.EnergyBeam;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Staff.Epic_EnergybeamStaff;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public class EnergyBeam_Spell extends Spell {

    public boolean frostbolt;
    public boolean frozenorb;
    public boolean chargedbolts;
    public boolean chainlightning;
    public boolean thunderstorm;
    public boolean flamejet;
    public boolean fireball;
    public boolean overheat;
    public boolean arcaneMissile;
    public boolean rift;

    public boolean reverse;

    public int monstersHit = 0;

    public EnergyBeam_Spell() {

        name = "Energy Beam";

        dmg = 80;
        cooldown = 3.2f;
        autoaimable = true;

        main_element = SpellUtils.Spell_Element.ARCANE;

    }

    @Override
    public void update(float delta) {
        setup();

        autoAimCheck();

        if(targetPosition == null) {
            return;
        }

        if(delta > 0) {


            EnergyBeam_Projectile beam = new EnergyBeam_Projectile(getSpawnPosition(), targetPosition);
            beam.setBeam(this);
            beam.setElements(this);
            screen.spellManager.add(beam);


            uniqueStaff();
            screen.spellManager.remove(this);
        }

    }

    public void uniqueStaff() {
        if(player.inventory.equippedStaff instanceof Epic_EnergybeamStaff) {
            Vector2 reverseVector;
            Vector2 direction = getTargetPosition().sub(getSpawnPosition());
            direction.scl(-1);
            reverseVector = player.pawn.getPosition().add(direction);

            EnergyBeam_Projectile beam = new EnergyBeam_Projectile(getSpawnPosition(), reverseVector);
            beam.setBeam(this);
            beam.setElements(this);
            beam.reverse = true;
            screen.spellManager.add(beam);
        }
    }

    public void setBeam(EnergyBeam_Spell parent) {
        frostbolt = parent.frostbolt;
        chargedbolts = parent.chargedbolts;
        frozenorb = parent.frozenorb;
        rift = parent.rift;
        chainlightning = parent.chainlightning;
        thunderstorm = parent.thunderstorm;
        flamejet = parent.flamejet;
        fireball = parent.fireball;
        overheat = parent.overheat;
        arcaneMissile = parent.arcaneMissile;
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.energybeam_lvl;
    }

    public void setup() {
        if(arcaneMissile) {
            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), 15, true);
            Monster target = null;
            if(!inRange.isEmpty()) {
                float max = 0;
                for(Monster monster : inRange) {
                    if(monster.heavy && monster.hp > max) {
                        target = monster;
                        max = monster.hp;
                    }
                }
            }

            if(target != null) {
                targetPosition = target.body.getPosition();
            }
        }
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg;
        dmg += getLvl() * 20;
        dmg = (int) (dmg * (1 + player.spellbook.energyBonusDmg/100f));

        if(monstersHit == 1) {
            dmg = (int) (dmg * (1f + player.spellbook.energybeamBonus/100f));
        }

        return dmg;
    }


}
