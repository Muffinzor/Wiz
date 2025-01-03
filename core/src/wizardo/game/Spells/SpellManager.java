package wizardo.game.Spells;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Items.Equipment.Hat.Legendary_TripleCastHat;
import wizardo.game.Screens.BaseScreen;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class SpellManager {

    private ArrayList<Spell> spellsToCast;
    private ArrayList<Spell> activeSpells;
    private ArrayList<Spell> spellsToRemove;
    private ArrayList<Body> bodiesToRemove;
    int garbageRemovalCounter;
    ArrayList<Spell> multicastedSpells;
    ArrayList<Float> delays;

    BaseScreen screen;

    public float cooldown1;
    public float cooldown2;
    public float cooldown3;
    public float cooldown4;
    public float defensive_cooldown;
    public float utility_cooldown;



    public SpellManager(BaseScreen screen) {
        spellsToCast = new ArrayList<>();
        activeSpells = new ArrayList<>();
        spellsToRemove = new ArrayList<>();
        multicastedSpells = new ArrayList<>();
        bodiesToRemove = new ArrayList<>();
        delays = new ArrayList<>();
        this.screen = screen;


        cooldown1 = MathUtils.random(1f);
        cooldown2 = MathUtils.random(1f);
        cooldown3 = MathUtils.random(1f);
    }


    /**
     * 1. handles autocast -> adds spells to active list on cooldown
     * 2. updates active spells
     * 3. handles spell removal
     * @param delta delta
     */
    public void update(float delta) {
        castSpell(delta);
        updateMulticasted(delta);

        for(Spell spell : spellsToCast) {
            spell.screen = screen;
        }
        activeSpells.addAll(spellsToCast);
        spellsToCast.clear();

        for(Spell spell : activeSpells) {
            spell.update(delta);
        }

        disposeFinishedSpells();

    }

    public void castSpell(float delta) {
        if(delta > 0) {
            cooldown1 -= delta;
            if (cooldown1 <= 0 && !player.spellbook.equippedSpells.isEmpty()) {
                Spell clone = player.spellbook.equippedSpells.getFirst().clone();
                clone.castByPawn = true;
                spellsToCast.add(clone);
                cooldown1 = clone.getCooldown();
                attemptMulticast(player.spellbook.equippedSpells.getFirst().clone());
            }

            cooldown2 -= delta;
            if (cooldown2 <= 0 && player.spellbook.equippedSpells.size() > 1) {
                Spell clone = player.spellbook.equippedSpells.get(1).clone();
                clone.castByPawn = true;
                spellsToCast.add(clone);
                cooldown2 = clone.getCooldown();
                attemptMulticast(player.spellbook.equippedSpells.get(1).clone());
            }

            cooldown3 -= delta;
            if (cooldown3 <= 0 && player.spellbook.equippedSpells.size() > 2) {
                Spell clone = player.spellbook.equippedSpells.get(2).clone();
                clone.castByPawn = true;
                spellsToCast.add(clone);
                cooldown3 = clone.getCooldown();
                attemptMulticast(player.spellbook.equippedSpells.get(2).clone());
            }
        }
    }

    public void attemptMulticast(Spell spell) {
        if(spell.multicastable && Math.random() > 1 - player.spellbook.multicast/100f) {
            spell.multicasted = true;
            multicastedSpells.add(spell);
            delays.add(0.2f);
            if(player.inventory.equippedHat instanceof Legendary_TripleCastHat) {
                multicastedSpells.add(spell.clone());
                delays.add(0.4f);
            }
        }
    }

    public void updateMulticasted(float delta) {
        for (int i = 0; i < multicastedSpells.size(); i++) {
            Float delay = delays.get(i);
            delay -= delta;
            if(delay <= 0) {
                Spell spell = multicastedSpells.get(i);
                spellsToCast.add(spell);
                multicastedSpells.remove(i);
                delays.remove(i);
                break;
            } else {
                delays.set(i, delay);
            }
        }
    }

    public void add(Spell spell) {
        spellsToCast.add(spell);
    }
    public void remove(Spell spell) {
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
