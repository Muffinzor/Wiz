package wizardo.game.Spells.Lightning.ChargedBolts;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class ChargedBolts_Spell extends Spell {

    public float duration;
    public int maxCollisions;

    public int bolts;

    public boolean frostbolts;
    public boolean arcaneMissile;
    public boolean spear;
    public boolean flamejet;
    public boolean overheat;

    public ChargedBolts_Spell() {

        name = "Charged Bolts";

        duration = 2.5f;
        cooldown = 0.8f;
        baseDmg = 12;
        speed = 80f/PPM;
        bolts = 3;

        main_element = SpellUtils.Spell_Element.LIGHTNING;


    }
    @Override
    public void update(float delta) {

        if(spawnPosition != null) {
            bolts = 1;
            speed = 55f/PPM;
        } else {
            bolts = 2 + getLvl();
            if(overheat) {
                bolts = Math.max(bolts/2, 2);
            }
        }

        if(delta > 0) {
            for (int i = 0; i < bolts; i++) {
                castBolt();

                if(Math.random() >= 1 - player.spellbook.chargedboltBonus/100f) {
                    castBolt();
                }
            }
            screen.spellManager.toRemove(this);
        }

    }

    public void setNext(ChargedBolts_Spell thisBolt) {
        frostbolts = thisBolt.frostbolts;
        arcaneMissile = thisBolt.arcaneMissile;
        flamejet = thisBolt.flamejet;
        overheat = thisBolt.overheat;
        spear = thisBolt.spear;
        speed = thisBolt.speed;
        duration = thisBolt.duration;
    }

    public void castBolt() {
        ChargedBolts_Projectile bolt = new ChargedBolts_Projectile(getSpawnPosition(), getTargetPosition());
        bolt.setNext(this);
        bolt.setElements(this);
        screen.spellManager.toAdd(bolt);
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.chargedbolt_lvl;
    }

    @Override
    public int getDmg() {
        int dmg = baseDmg;
        dmg += 4 * getLvl();
        if(arcaneMissile) {
            dmg += 4 * player.spellbook.arcanemissile_lvl;
        }

        dmg = (int) (dmg * (1 + player.spellbook.voltageBonusDmg/100f));
        if(overheat) {
            return dmg * 2;
        } else {
            return dmg;
        }
    }

    @Override
    public boolean isLearnable() {
        return player.spellbook.chargedbolt_lvl > 0;
    }
}
