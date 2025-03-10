package wizardo.game.Spells.Fire.Overheat;

import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Hit;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Overheat_Spell extends Spell {

    public boolean frostbolts;
    public boolean frozenorb;
    public boolean fireball;
    public boolean thunderstorm;
    public boolean chainlightning;
    public boolean chargedbolts;
    public boolean icespear;
    public boolean flameBeam;  // Overheat + Beam + Flamejet

    public Overheat_Spell() {

        multicastable = false;

        string_name = "Overheat";

        radius = 200;
        cooldown = 6.4f;
        dmg = 125;

        main_element = SpellUtils.Spell_Element.FIRE;
    }

    public void update(float delta) {
        stateTime += delta;

        Overheat_Explosion explosion = new Overheat_Explosion(getSpawnPosition());
        explosion.frostbolts = frostbolts;
        explosion.fireball = fireball;
        explosion.frozenorb = frozenorb;
        explosion.icespear = icespear;
        explosion.chargedbolts = chargedbolts;
        explosion.thunderstorm = thunderstorm;
        explosion.chainlightning = chainlightning;
        explosion.flameBeam = flameBeam;
        explosion.setElements(this);
        explosion.nested_spell = nested_spell;
        screen.spellManager.add(explosion);
        screen.spellManager.remove(this);

        if(thunderstorm) {
            Thunderstorm_Hit thunder = new Thunderstorm_Hit(player.pawn.getPosition());
            thunder.setElements(this);
            screen.spellManager.add(thunder);
        }


    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.overheat_lvl;
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg;
        dmg += 25 * getLvl();
        dmg = (int) (dmg * (1 + player.spellbook.explosivesBonusDmg/100f));
        return dmg;
    }

}
