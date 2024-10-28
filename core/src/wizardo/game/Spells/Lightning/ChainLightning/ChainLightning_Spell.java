package wizardo.game.Spells.Lightning.ChainLightning;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class ChainLightning_Spell extends Spell {

    public int maxHits = 6;
    public int currentHits = 1;
    public float radius = 5;

    public boolean frostbolts;

    boolean randomTarget;


    public ChainLightning_Spell() {

        name = "Chain Lightning";

        cooldown = 1.2f;
        dmg = 35;

        main_element = SpellUtils.Spell_Element.LIGHTNING;

    }

    @Override
    public void update(float delta) {

        if(delta > 0) {

            Vector2 center;
            if(targetPosition == null) {
                Vector2 direction = new Vector2(getTargetPosition().sub(getSpawnPosition()));
                direction.nor().scl(4);
                center = new Vector2(getSpawnPosition().add(direction));
            } else {
                center = new Vector2(originBody.getPosition());
                randomTarget = true;
            }


            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(center, 5);

            if (!inRange.isEmpty()) {

                float dst = Float.MAX_VALUE;
                Monster target = null;
                if(randomTarget) {
                    int index = (int) (Math.random() * inRange.size());
                    target = inRange.get(index);
                } else {
                    for (Monster monster : inRange) {
                        float toOrigin = monster.body.getPosition().dst(getSpawnPosition());
                        if (toOrigin < dst && SpellUtils.hasLineOfSight(monster.body.getPosition(), getSpawnPosition())) {
                            dst = toOrigin;
                            target = monster;
                        }
                    }
                }

                if (target != null) {
                    ChainLightning_Hit chain = new ChainLightning_Hit(target);
                    chain.screen = currentScreen;
                    chain.monstersHit.add(target);
                    chain.setChain(this);
                    chain.setElements(this);

                    if (originBody != null) {
                        chain.originBody = originBody;
                    } else {
                        chain.originBody = player.pawn.body;
                    }

                    currentScreen.spellManager.toAdd(chain);

                }
            }

            currentScreen.spellManager.toRemove(this);

        }
    }

    public void setChain(ChainLightning_Spell parentChain) {
        nested_spell = parentChain.nested_spell;
        frostbolts = parentChain.frostbolts;
    }


    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.chainlightning_lvl;
    }
}
