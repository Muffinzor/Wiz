package wizardo.game.Spells.Arcane.EnergyBeam;

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

    public EnergyBeam_Spell() {

        name = "Energy Beam";

        baseDmg = 80;

        cooldown = 3.2f;

        main_element = SpellUtils.Spell_Element.ARCANE;

    }

    @Override
    public void update(float delta) {
        setup();

        if(delta > 0) {

            EnergyBeam_Projectile beam = new EnergyBeam_Projectile(getSpawnPosition(), getTargetPosition());
            beam.setBeam(this);
            beam.setElements(this);
            screen.spellManager.toAdd(beam);
            screen.spellManager.toRemove(this);

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
        int dmg = baseDmg;
        dmg += getLvl() * 20;
        dmg = (int) (dmg * (1 + player.spellbook.energyBonusDmg/100f));
        return dmg;
    }

    @Override
    public boolean isLearnable() {
        return player.spellbook.energybeam_lvl > 0;
    }

}
