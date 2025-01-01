package wizardo.game.Spells.Hybrid.ArcaneArtillery;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Hat.Legendary_TripleCastHat;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Spells.SpellUtils.Spell_Element.ARCANE;
import static wizardo.game.Wizardo.player;

public class Judgement_Spell extends Spell {

    int projectiles = 1;
    int projectilesCast = 0;

    public boolean frozenorb;
    public boolean thunderstorm;

    public boolean arcaneMissiles; // Beam + Overheat + Missiles

    public boolean rift; //for multiple energy rain explosions

    public Judgement_Spell() {
        name = "Judgement";

        baseDmg = 80;

        main_element = ARCANE;

        cooldown = 5;
    }

    public void setup() {
        if(player.inventory.equippedHat instanceof Legendary_TripleCastHat) {
            projectiles = 2;
        }
    }
    @Override
    public void update(float delta) {
        if(!initialized) {
            setup();
            initialized = true;
        }

        stateTime += delta;

        if(stateTime >= projectilesCast * 0.33f) {
            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(player.pawn.getPosition(), 14, false);
            if(!inRange.isEmpty()) {
                Collections.shuffle(inRange);
                Monster target = inRange.getFirst();
                for (int i = 1; i < inRange.size(); i++) {
                    if(inRange.get(i).hp > target.hp) {
                        target = inRange.get(i);
                    }
                }
                Judgement_Projectile projectile = new Judgement_Projectile();
                projectile.targetPosition = new Vector2(target.body.getPosition());
                projectile.setElements(this);
                projectile.frozenorb = frozenorb;
                projectile.rift = rift;
                screen.spellManager.add(projectile);
            }
            projectilesCast ++;
        }


        if(projectilesCast >= projectiles) {
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
        int dmg = baseDmg;
        dmg += 20 * player.spellbook.energybeam_lvl;
        if(frozenorb) {
            dmg += 2 * player.spellbook.frozenorb_lvl;
        } else {
            dmg += 20 * player.spellbook.overheat_lvl;
        }
        if(arcaneMissiles) {
            dmg += 10 * player.spellbook.arcanemissile_lvl;
        }
        dmg = (int) (dmg * (1 + player.spellbook.divineBonusDmg/100f));
        return dmg;
    }
}
