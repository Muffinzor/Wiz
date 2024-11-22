package wizardo.game.Spells.Hybrid.ArcaneArtillery;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Projectile;
import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Spells.SpellUtils.Spell_Element.ARCANE;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Wizardo.player;

public class ArcaneArtillery_Spell extends Spell {

    public boolean frozenorb;
    public boolean thunderstorm;

    public ArcaneArtillery_Spell() {
        name = "Arcane Artillery";

        main_element = ARCANE;

        cooldown = 5;
    }

    @Override
    public void update(float delta) {

        ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), 15, false);
        if(!inRange.isEmpty()) {
            Monster target = inRange.getFirst();
            for (int i = 1; i < inRange.size(); i++) {
                if(inRange.get(i).hp > target.hp) {
                    target = inRange.get(i);
                }
            }
            ArcaneArtillery_Projectile projectile = new ArcaneArtillery_Projectile();
            projectile.targetPosition = new Vector2(target.body.getPosition());
            projectile.setElements(this);
            projectile.frozenorb = frozenorb;
            projectile.thunderstorm = thunderstorm;
            screen.spellManager.toAdd(projectile);
        }

        screen.spellManager.toRemove(this);
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
        return 0;
    }
}
