package wizardo.game.Spells.Hybrid.Judgement;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Spells.SpellUtils.Spell_Element.ARCANE;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Wizardo.player;

public class Judgement_Spell extends Spell {

    int projectiles = 1;
    int projectilesCast = 0;

    public boolean frozenorb;
    public boolean ministorm;

    public boolean arcaneMissiles; // Beam + Overheat + Missiles

    public boolean rift; //for multiple energy rain explosions

    public Judgement_Spell() {
        string_name = "Judgement";
        levelup_enum = LevelUpEnums.LevelUps.JUDGEMENT;

        dmg = 60;

        main_element = ARCANE;

        cooldown = 5;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
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
                projectile.ministorm = ministorm;
                projectile.arcaneMissiles = arcaneMissiles;
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
        int dmg = this.dmg;
        dmg += 30 * player.spellbook.energybeam_lvl;
        if(anim_element == FIRE) {
            dmg += 30 * player.spellbook.overheat_lvl;
        }
        return dmg;
    }

    public float getCooldown() {
        float real_cooldown = cooldown - player.spellbook.judgement_bonus_cdreduction * 0.4f;
        float castspeed_bonus = player.spellbook.castSpeed/100;
        return Math.max(real_cooldown * (1 - castspeed_bonus), real_cooldown/2);
    }
}
