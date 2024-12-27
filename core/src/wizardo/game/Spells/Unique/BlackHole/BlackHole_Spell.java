package wizardo.game.Spells.Unique.BlackHole;

import wizardo.game.Spells.Spell;

public class BlackHole_Spell extends Spell {

    public BlackHole_Spell() {
        cooldown = 5;
    }

    @Override
    public void update(float delta) {
        BlackHole_Effect blackhole = new BlackHole_Effect(getTargetPosition());
        screen.spellManager.toAdd(blackhole);
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

    @Override
    public boolean isLearnable() {
        return false;
    }
}
