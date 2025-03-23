package wizardo.game.Spells.Arcane.ArcaneMissiles;

import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class ArcaneMissile_Spell extends Spell {

    public int projectiles;

    public boolean rift;
    public boolean icespear;
    public boolean overheat;
    public boolean flamejet;
    public boolean frostbolt;
    public boolean chargedbolts;

    public float scale = 1; //for fireball cast
    public float turnSpeed;


    public ArcaneMissile_Spell() {

        string_name = "Arcane Missiles";
        levelup_enum = LevelUpEnums.LevelUps.MISSILES;

        dmg = 12;
        speed = 225f/PPM;
        cooldown = 1.2f;
        autoaimable = true;

        main_element = SpellUtils.Spell_Element.ARCANE;

    }

    public void setup() {
        if(targetPosition == null) {
            projectiles = 3 + player.spellbook.arcane_missile_bonus_proj;
        } else {
            projectiles = 1;
        }
        turnSpeed = 4 + player.spellbook.arcane_missile_bonus_rotation;
    }

    @Override
    public void update(float delta) {

        if(delta > 0) {
            setup();

            autoAimCheck();

            if(targetPosition == null) {
                return;
            }

            for (int i = 0; i < projectiles; i++) {
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
        icespear = parent.icespear;
        overheat = parent.overheat;
        frostbolt = parent.frostbolt;
        chargedbolts = parent.chargedbolts;
        turnSpeed = parent.turnSpeed;
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
        dmg += 6 * getLvl();
        return dmg;
    }

}
