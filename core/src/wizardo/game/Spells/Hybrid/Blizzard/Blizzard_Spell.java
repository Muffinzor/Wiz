package wizardo.game.Spells.Hybrid.Blizzard;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Amulet.Epic_StormAmulet;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Methods.isPositionOverlappingWithObstacle;
import static wizardo.game.Wizardo.player;

public class Blizzard_Spell extends Spell {

    public float blizz_radius;

    public float interval;
    public float frequency;
    public float duration = 6f;

    public boolean frostbolts;
    public boolean frozenorb;
    public boolean rift;

    public Vector2 blizzard_center;
    int projsSent;

    public Blizzard_Spell() {

        multicastable = false;

        name = "Blizzard";

        cooldown = 12;

        speed = 15;
        dmg = 24;

        blizz_radius = 20;
        radius = 25;

        main_element = SpellUtils.Spell_Element.FROST;
    }

    public void setup() {
        if(!rift) {
            frequency = 40 + 4 * player.spellbook.thunderstorm_lvl;
            blizzard_center = player.pawn.getPosition();
        } else {
            frequency = 40 + 4 * player.spellbook.rift_lvl;
            blizzard_center = getTargetPosition();
            blizz_radius = 5;
        }
        frequency = frequency * (1 + player.spellbook.empyreanFrequencyBonus/100f);
        interval = 1/frequency;

        if(player.inventory.equippedAmulet instanceof Epic_StormAmulet) {
            duration *= 1.5f;
        }
    }

    @Override
    public void update(float delta) {

        if(!initialized && delta > 0) {
            initialized = true;
            setup();
            interval = 0.05f;
        }

        int projectiles = getNumberOfProjs();
        for (int i = 0; i < projectiles; i++) {
            sendProjectile(delta);
        }


        stateTime += delta;

        if(stateTime > projsSent * interval) {
            projsSent++;
        }

        if(stateTime >= duration) {
            screen.spellManager.remove(this);
        }

    }

    public int getNumberOfProjs() {
        int quantity = 1;
        int relevantSpellLevel;
        if(rift) {
            relevantSpellLevel = player.spellbook.rift_lvl;
        } else {
            relevantSpellLevel = player.spellbook.thunderstorm_lvl;
        }
        float doubleProc = 1.1f - (relevantSpellLevel * 0.1f) - (player.spellbook.empyreanFrequencyBonus/100f);
        if(doubleProc < 0) {
            quantity++;
            if(Math.random() > 1 - Math.abs(doubleProc)) {
                quantity++;
            }
        } else {
            if(Math.random() > doubleProc) {
                quantity++;
            }
        }

        return quantity;
    }

    public void sendProjectile(float delta) {
        blizzard_center = player.pawn.getPosition();

        if(delta > 0) {
            projsSent++;
            Vector2 randomTarget = null;
            int attempts = 0;
            while (randomTarget == null && attempts < 10) {
                attempts++;
                Vector2 attempt = SpellUtils.getRandomVectorInRadius(blizzard_center, blizz_radius);
                if (!isPositionOverlappingWithObstacle(attempt)) {
                    randomTarget = attempt;
                }
            }

            if (randomTarget != null) {
                Blizzard_Projectile proj = new Blizzard_Projectile(randomTarget);
                proj.setElements(this);
                proj.screen = screen;
                proj.frostbolts = frostbolts;
                screen.spellManager.add(proj);
            }
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 1;
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg;
        dmg += 8 * player.spellbook.icespear_lvl;
        dmg = (int) (dmg * (1 + player.spellbook.sharpBonusDmg/100f));
        return dmg;
    }


}
