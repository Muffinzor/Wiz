package wizardo.game.Spells.Arcane.ArcaneMissiles;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class ArcaneMissile_Spell extends Spell {

    public boolean rift;
    public boolean riftBolts; //for Missiles + rifts + chargedbolts
    public boolean icespear;
    public boolean overheat;
    public boolean flamejet;


    public ArcaneMissile_Spell() {

        name = "Arcane Missiles";

        baseDmg = 20;
        speed = 225f/PPM;
        cooldown = 0.75f;

        main_element = SpellUtils.Spell_Element.ARCANE;

    }

    @Override
    public void update(float delta) {

        if(delta > 0) {

            int missiles = 2 + player.spellbook.arcanemissile_lvl / 4;
            if(targetPosition != null) {
                missiles = 1;
            }

            for (int i = 0; i < missiles; i++) {
                ArcaneMissile_Projectile missile = new ArcaneMissile_Projectile(getSpawnPosition(), getTargetPosition());
                missile.setElements(this);
                missile.setMissile(this);
                currentScreen.spellManager.toAdd(missile);
            }
            currentScreen.spellManager.toRemove(this);

        }

    }

    public void setMissile(ArcaneMissile_Spell parent) {
        rift = parent.rift;
        flamejet = parent.flamejet;
        riftBolts = parent.riftBolts;
        icespear = parent.icespear;
        overheat = parent.overheat;
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
        int dmg = baseDmg;
        dmg += 10 * getLvl();
        return dmg;
    }
}
