package wizardo.game.Spells.Frost.Frostbolt;

import wizardo.game.Items.Equipment.Hat.Rare_FrostboltHat;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Frostbolt_Spell extends Spell {

    public int projectiles = 1;

    public boolean chargedbolt;
    public boolean chainlightning;
    public boolean thunderstorm;
    public boolean missile;
    public boolean rifts;
    public boolean beam;
    public boolean superBolt; // Frostbolt + Fireball + Overheat

    public Frostbolt_Spell() {

        string_name = "Frostbolts";
        levelup_enum = LevelUpEnums.LevelUps.FROSTBOLT;
        speed = 7f;
        radius = 25;
        dmg = 24;
        cooldown = 0.8f;
        autoaimable = true;

        main_element = SpellUtils.Spell_Element.FROST;

    }

    public void setup() {

        if(targetPosition != null) {
            projectiles = 1;
        } else {
            projectiles = getLvl() + player.spellbook.frostbolts_bonus_proj;
            if(player.inventory.equippedHat instanceof Rare_FrostboltHat) {
                projectiles++;
            }
        }
        if(beam) {
            speed += 2 * player.spellbook.energybeam_lvl;
        }

    }

    @Override
    public void update(float delta) {
        setup();

        autoAimCheck();

        if(targetPosition == null) {
            return;
        }

        for (int i = 0; i < projectiles; i++) {
            Frostbolt_Projectile bolt = new Frostbolt_Projectile(getSpawnPosition(), targetPosition);
            bolt.setBolt(this);
            bolt.setElements(this);
            screen.spellManager.add(bolt);
        }
        screen.spellManager.remove(this);

    }

    public void dispose() {

    }

    public int getLvl() {
        return player.spellbook.frostbolt_lvl;
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg;
        dmg += 6 * getLvl();
        if(superBolt) {
            dmg += 5 * player.spellbook.fireball_lvl;
        }
        if(beam) {
            dmg += 2 * player.spellbook.energybeam_lvl;
        }
        return dmg;
    }

    public void setBolt(Frostbolt_Spell parent) {
        this.beam = parent.beam;
        this.missile = parent.missile;
        this.rifts = parent.rifts;
        this.superBolt = parent.superBolt;
        this.chargedbolt = parent.chargedbolt;
        this.chainlightning = parent.chainlightning;
        this.thunderstorm = parent.thunderstorm;
        this.projectiles = parent.projectiles;
    }
}
