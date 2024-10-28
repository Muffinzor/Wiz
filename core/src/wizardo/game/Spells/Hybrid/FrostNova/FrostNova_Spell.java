package wizardo.game.Spells.Hybrid.FrostNova;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class FrostNova_Spell extends Spell {

    public boolean frostbolts;

    public FrostNova_Spell() {

        dmg = 20;
        cooldown = 5f;

        main_element = SpellUtils.Spell_Element.FROST;
        anim_element = SpellUtils.Spell_Element.FROST;

    }
    @Override
    public void update(float delta) {

        FrostNova_Explosion explosion = new FrostNova_Explosion();
        explosion.targetPosition = new Vector2(player.pawn.body.getPosition());
        explosion.setElements(this);
        explosion.frostbolts = frostbolts;
        screen.spellManager.toAdd(explosion);
        screen.spellManager.toRemove(this);

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }
}
