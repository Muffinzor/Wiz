package wizardo.game.Spells.Fire.Overheat;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.Equipment.Hat.Legendary_SentientHat;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Hit;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.GameSettings.dmg_text_on;
import static wizardo.game.Wizardo.player;

public class Overheat_Spell extends Spell {

    public boolean frostbolts;
    public boolean frozenorb;
    public boolean fireball;
    public boolean thunderstorm;
    public boolean chainlightning;
    public boolean chargedbolts;
    public boolean icespear;
    public boolean flameBeam;  // Overheat + Beam + Flamejet

    public Overheat_Spell() {

        string_name = "Overheat";
        levelup_enum = LevelUpEnums.LevelUps.OVERHEAT;

        radius = 200;
        cooldown = 6.4f;
        dmg = 90;

    }

    public void update(float delta) {
        stateTime += delta;

        Overheat_Explosion explosion = new Overheat_Explosion(getSpawnPosition());
        explosion.frostbolts = frostbolts;
        explosion.fireball = fireball;
        explosion.frozenorb = frozenorb;
        explosion.icespear = icespear;
        explosion.chargedbolts = chargedbolts;
        explosion.thunderstorm = thunderstorm;
        explosion.chainlightning = chainlightning;
        explosion.flameBeam = flameBeam;
        explosion.setElements(this);
        explosion.nested_spell = nested_spell;
        screen.spellManager.add(explosion);
        screen.spellManager.remove(this);

        if(thunderstorm) {
            Thunderstorm_Hit thunder = new Thunderstorm_Hit(player.pawn.getPosition());
            thunder.setElements(this);
            screen.spellManager.add(thunder);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.overheat_lvl;
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg;
        dmg += 45 * getLvl();
        return dmg;
    }

    @Override
    public float getCooldown() {
        float castspeed_bonus = player.spellbook.castSpeed/100f + player.spellbook.overheat_bonus_cdreduction/100f;
        return Math.max(cooldown * (1 - castspeed_bonus), cooldown/2);
    }

}
