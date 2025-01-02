package wizardo.game.Spells.Frost.Frostbolt;

import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class Frostbolt_Spell extends Spell {

    public int projectiles = 1;

    public boolean chargedbolt;
    public boolean missile;
    public boolean rifts;
    public boolean superBolt; // Frostbolt + Fireball + Overheat

    public Frostbolt_Spell() {

        name = "Frostbolts";

        speed = 7;
        radius = 25;
        dmg = 24;
        cooldown = 0.8f;
        autoaimable = true;

        main_element = SpellUtils.Spell_Element.FROST;

    }

    public void setup() {
        float extraProjs = 0;
        if(targetPosition == null) {
            float bonus = (player.spellbook.frostbolt_lvl - 1) / 2f;
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
        projectiles += extraProjs;
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
        dmg = (int) (dmg * (1 + player.spellbook.explosivesBonusDmg/100f));
        return dmg;
    }

    public void setBolt(Frostbolt_Spell parent) {
        this.missile = parent.missile;
        this.rifts = parent.rifts;
        this.superBolt = parent.superBolt;
        this.chargedbolt = parent.chargedbolt;
        this.projectiles = parent.projectiles;
    }
}
