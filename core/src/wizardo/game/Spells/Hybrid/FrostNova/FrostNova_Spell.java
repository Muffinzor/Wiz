package wizardo.game.Spells.Hybrid.FrostNova;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class FrostNova_Spell extends Spell {

    public boolean frostbolts;

    public FrostNova_Spell() {

        multicastable = false;

        string_name = "Frost Nova";
        levelup_enum = LevelUpEnums.LevelUps.FROSTNOVA;

        dmg = 45;
        cooldown = 6.4f;

        anim_element = SpellUtils.Spell_Element.FROST;

    }
    @Override
    public void update(float delta) {

        FrostNova_Explosion explosion = new FrostNova_Explosion();
        explosion.targetPosition = new Vector2(player.pawn.body.getPosition());
        explosion.setElements(this);
        explosion.frostbolts = frostbolts;
        screen.spellManager.add(explosion);
        screen.spellManager.remove(this);

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
        int dmg = this.dmg;
        dmg += 15 * player.spellbook.overheat_lvl;
        return 0;
    }

}
