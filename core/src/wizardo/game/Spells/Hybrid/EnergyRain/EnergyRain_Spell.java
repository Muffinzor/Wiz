package wizardo.game.Spells.Hybrid.EnergyRain;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Amulet.Epic_StormAmulet;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Spells.SpellUtils.Spell_Element.ARCANE;
import static wizardo.game.Utils.Methods.isPositionOverlappingWithObstacle;
import static wizardo.game.Wizardo.player;

public class EnergyRain_Spell extends Spell {

    public int projectiles;
    public int projectilesSent = 0;

    public float interval;

    public boolean frostbolt;
    public boolean chargedbolts;
    public boolean flamejet;
    public boolean thunderstorm;

    public boolean rifts;

    public EnergyRain_Spell() {

        multicastable = false;

        string_name = "Energy Rain";
        levelup_enum = LevelUpEnums.LevelUps.ENERGYRAIN;
        cooldown = 12;
        dmg = 65;
        interval = 0.2f;
        radius = 16;

    }


    @Override
    public void update(float delta) {
        if(!initialized) {
            setup();
            initialized = true;
        }
        stateTime += delta;

        if(stateTime >= projectilesSent * interval) {

            regularTargeting();


        }

        if(projectilesSent >= projectiles) {
            screen.spellManager.remove(this);
        }

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg + player.spellbook.energybeam_lvl * 35;
        return dmg;
    }

    public void setup() {
        projectiles = 6 + player.spellbook.rift_lvl * 2 + player.spellbook.energyrain_bonus_proj;

        if(player.inventory.equippedAmulet instanceof Epic_StormAmulet) {
            projectiles = (projectiles * 130) / 100;
        }
    }

    public Monster findTarget() {
        ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), radius, false);
        if(!inRange.isEmpty()) {
            int index = (int) (Math.random() * inRange.size());
            return inRange.get(index);
        }
        return null;
    }

    public Vector2 getRandomizedPosition(Monster target) {
        Vector2 randomTarget = SpellUtils.getRandomVectorInRadius(target.body.getPosition(), 3);;
        int attempts = 0;
        while (attempts < 10) {
            attempts++;
            Vector2 attempt = SpellUtils.getRandomVectorInRadius(target.body.getPosition(), 3);
            if (!isPositionOverlappingWithObstacle(attempt) || attempts == 5) {
                return attempt;
            }
        }
        return randomTarget;
    }

    public void regularTargeting() {
        Monster target = findTarget();
        if(target != null) {
            Vector2 randomPosition = getRandomizedPosition(target);
            EnergyRain_Projectile beam = new EnergyRain_Projectile();
            beam.setElements(this);
            beam.targetPosition = randomPosition;
            beam.frostbolt = frostbolt;
            beam.rifts = rifts;
            beam.thunderstorm = thunderstorm;
            beam.flamejet = flamejet;
            beam.chargedbolts = chargedbolts;
            screen.spellManager.add(beam);
        }
        projectilesSent++;
    }
}
