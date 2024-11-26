package wizardo.game.Spells;

public class BlankSpell extends Spell{

    public BlankSpell() {
        cooldown = 100000;
    }

    @Override
    public void update(float delta) {

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
