package wizardo.game.Spells;

import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.Battle.BattleScreen;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public class SpellManager {

    private ArrayList<Spell> spellsToCast;
    private ArrayList<Spell> activeSpells;
    private ArrayList<Spell> spellsToRemove;

    BaseScreen screen;

    private float cooldown1;
    private float cooldown2;


    public SpellManager(BaseScreen screen) {
        spellsToCast = new ArrayList<>();
        activeSpells = new ArrayList<>();
        spellsToRemove = new ArrayList<>();
        this.screen = screen;
    }


    /**
     * 1. handles autocast -> adds spells to active list on cooldown
     * 2. updates active spells
     * 3. handles spell removal
     * @param delta delta
     */
    public void update(float delta) {
        castSpell(delta);

        activeSpells.addAll(spellsToCast);
        spellsToCast.clear();

        for(Spell spell : activeSpells) {
            spell.update(delta);
        }

        disposeFinishedSpells();
    }

    public void castSpell(float delta) {
        cooldown1 -= delta;
        if (cooldown1 <= 0) {
            activeSpells.add(player.spellbook.equippedSpells.getFirst().clone());
            cooldown1 = player.spellbook.equippedSpells.getFirst().cooldown;
        }

        cooldown2 -= delta;
        if (cooldown2 <= 0 && player.spellbook.equippedSpells.size() > 1) {
            activeSpells.add(player.spellbook.equippedSpells.get(1).clone());
            cooldown2 = player.spellbook.equippedSpells.get(1).cooldown;
        }
    }

    public void toAdd(Spell spell) {
        spellsToCast.add(spell);
    }

    public void toRemove(Spell spell) {
        spellsToRemove.add(spell);
    }

    public void disposeFinishedSpells() {
        activeSpells.removeAll(spellsToRemove);
        for(Spell spell : spellsToRemove) {
            spell.dispose();
        }
        spellsToRemove.clear();
    }

}
