package wizardo.game.Spells.Hybrid.Blizzard;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Amulet.Epic_StormAmulet;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Methods.isPositionOverlappingWithObstacle;
import static wizardo.game.Wizardo.player;

public class Blizzard_Spell extends Spell {

    public float blizz_radius;

    public float interval;
    public float duration;

    public boolean frostbolts;
    public boolean frozenorb;
    public boolean rift;

    public Vector2 blizzard_center;

    public Blizzard_Spell() {

        multicastable = false;

        string_name = "Blizzard";
        levelup_enum = LevelUpEnums.LevelUps.BLIZZARD;

        cooldown = 12;
        duration = 4f;
        speed = 15;
        dmg = 24;

        blizz_radius = 18;

    }

    public void setup() {
        if(!rift) {
            blizzard_center = player.pawn.getPosition();
        } else {
            blizzard_center = getTargetPosition();
            blizz_radius = 5.5f;
        }
        if(player.inventory.equippedAmulet instanceof Epic_StormAmulet) {
            duration *= 1.3f;
        }
    }

    @Override
    public void update(float delta) {
        if(!initialized && delta > 0) {
            initialized = true;
            setup();
            interval = get_interval();
        }

        if(stateTime % interval < delta) {
            for (int i = 0; i < 3; i++) {
                sendProjectile(delta);
            }
        }

        stateTime += delta;

        if(stateTime >= duration) {
            screen.spellManager.remove(this);
        }

    }

    public float get_interval() {
        float scaled = 0.06f;
        if(rift) {
            scaled -= 0.01f * player.spellbook.rift_lvl;
        } else {
            scaled -= 0.01f * player.spellbook.thunderstorm_lvl;
        }
        return scaled;
    }

    public void sendProjectile(float delta) {
        if(!rift) {
            blizzard_center = player.pawn.getPosition();
        }
        if(delta > 0) {
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
        dmg += 12 * player.spellbook.icespear_lvl;
        if(rift) {
            dmg += 12 * player.spellbook.energybeam_lvl;
        }
        return dmg;
    }


}
