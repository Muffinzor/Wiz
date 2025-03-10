package wizardo.game.Spells.Lightning.ChargedBolts;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Staff.Epic_ChargedboltStaff;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class ChargedBolts_Spell extends Spell {

    public float duration;

    public int bolts;

    public boolean frostbolts;
    public boolean arcaneMissile;
    public boolean spear;
    public boolean flamejet;
    public boolean overheat;
    public boolean frozenorb;

    public ChargedBolts_Spell() {

        string_name = "Charged Bolts";

        duration = 1.5f;
        cooldown = 0.8f;
        dmg = 12;
        speed = 110f/PPM;
        bolts = 3;
        autoaimable = true;


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
            if(player.inventory.equippedStaff instanceof Epic_ChargedboltStaff && targetPosition == null) {
                uniqueStaffShooting();
            } else {
                normalBoltShooting();
            }
            screen.spellManager.remove(this);
        }

    }

    public void normalBoltShooting() {
        autoAimCheck();

        if(targetPosition == null) {
            return;
        }

        for (int i = 0; i < bolts; i++) {
            castBolt();

            if(Math.random() >= 1 - player.spellbook.chargedboltBonus/100f) {
                castBolt();
            }
        }
    }
    public void uniqueStaffShooting() {
        bolts = bolts * 5;
        for (int i = 0; i < bolts; i++) {
            Vector2 target = SpellUtils.getRandomVectorInRadius(player.pawn.body.getPosition(), 2);
            ChargedBolts_Projectile bolt = new ChargedBolts_Projectile(player.pawn.body.getPosition(), target);
            bolt.setNext(this);
            bolt.setElements(this);
            screen.spellManager.add(bolt);

            if(Math.random() >= 1 - player.spellbook.chargedboltBonus/100f) {
                Vector2 target2 = SpellUtils.getRandomVectorInRadius(player.pawn.body.getPosition(), 2);
                ChargedBolts_Projectile bolt2 = new ChargedBolts_Projectile(player.pawn.body.getPosition(), target2);
                bolt2.setNext(this);
                bolt2.setElements(this);
                screen.spellManager.add(bolt2);
            }
        }

    }

    public void setNext(ChargedBolts_Spell thisBolt) {
        frostbolts = thisBolt.frostbolts;
        arcaneMissile = thisBolt.arcaneMissile;
        flamejet = thisBolt.flamejet;
        overheat = thisBolt.overheat;
        spear = thisBolt.spear;
        frozenorb = thisBolt.frozenorb;
        speed = thisBolt.speed;
        duration = thisBolt.duration;
    }

    public void castBolt() {
        ChargedBolts_Projectile bolt = new ChargedBolts_Projectile(getSpawnPosition(), targetPosition);
        bolt.setNext(this);
        bolt.setElements(this);
        screen.spellManager.add(bolt);
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
        int dmg = this.dmg;
        dmg += 4 * getLvl();
        if(arcaneMissile) {
            dmg += 4 * player.spellbook.arcanemissile_lvl;
        }

        dmg = (int) (dmg * (1 + player.spellbook.conductiveBonusDmg /100f));
        if(overheat) {
            return dmg * 2;
        } else {
            return dmg;
        }
    }

}
