package wizardo.game.Spells.Arcane.ArcaneMissiles;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class ArcaneMissile_Spell extends Spell {

    public float extraProjs;

    public boolean rift;
    public boolean riftBolts; //for Missiles + rifts + chargedbolts
    public boolean icespear;
    public boolean overheat;
    public boolean flamejet;
    public boolean frostbolt;

    public float scale = 1; //for fireball cast


    public ArcaneMissile_Spell() {

        string_name = "Arcane Missiles";
        spell_enum = SpellUtils.Spell_Name.MISSILES;

        dmg = 16;
        speed = 225f/PPM;
        cooldown = 1.2f;
        autoaimable = true;

        main_element = SpellUtils.Spell_Element.ARCANE;

    }

    public void setup() {
        if(targetPosition == null) {
            float bonus = (player.spellbook.arcanemissile_lvl - 1) / 1.5f;        // change this value to modify the rate of increase
            if((bonus % 1) > 0) {
                float remainder = bonus % 1;
                if(Math.random() >= 1 - remainder) {
                    extraProjs ++;
                }
                extraProjs += (float) Math.floor(bonus);
            } else {
                extraProjs = bonus;
            }
        }
    }

    @Override
    public void update(float delta) {

        if(delta > 0) {
            setup();

            autoAimCheck();

            if(targetPosition == null) {
                return;
            }

            for (int i = 0; i < 3 + extraProjs; i++) {
                ArcaneMissile_Projectile missile = new ArcaneMissile_Projectile(getSpawnPosition(), targetPosition);
                missile.setElements(this);
                missile.scale = scale;
                missile.setMissile(this);
                screen.spellManager.add(missile);
            }
            screen.spellManager.remove(this);

        }

    }

    public void setMissile(ArcaneMissile_Spell parent) {
        rift = parent.rift;
        flamejet = parent.flamejet;
        riftBolts = parent.riftBolts;
        icespear = parent.icespear;
        overheat = parent.overheat;
        frostbolt = parent.frostbolt;
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.arcanemissile_lvl;
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg;
        dmg += 2 * getLvl();
        dmg = (int) (dmg * (1 + player.spellbook.sharpBonusDmg/100f));
        return dmg;
    }

}
