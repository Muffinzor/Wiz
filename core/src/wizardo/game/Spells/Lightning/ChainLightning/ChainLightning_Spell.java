package wizardo.game.Spells.Lightning.ChainLightning;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Wizardo.player;

public class ChainLightning_Spell extends Spell {

    public int maxHits = 7;
    public int currentHits = 1;
    public float radius = 5;

    public int splits = 0;
    public int maxSplits = 0;

    public boolean frostbolts;
    public boolean arcaneMissile;
    public boolean rifts;
    public boolean beam;
    public boolean spear;
    public boolean fireball;

    boolean randomTarget;


    public ChainLightning_Spell() {

        raycasted = true;
        aimReach = 5;

        name = "Chain Lightning";

        cooldown = 1.2f;
        baseDmg = 35;

        main_element = SpellUtils.Spell_Element.LIGHTNING;


    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            setup();
            initialized = true;
        }

        if(delta > 0) {

            Vector2 center = findTarget();

            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(center, 5, true);

            shootLightning(center, inRange);

            screen.spellManager.toRemove(this);

        }
    }

    public void setup() {
        maxHits = maxHits + getLvl()/2;
        if(spear) {
            baseDmg += player.spellbook.icespear_lvl * 5;
            for (int i = 0; i < 2; i++) {
                maxHits = maxHits - (player.spellbook.icespear_lvl/4);
            }
            maxSplits = 1 + player.spellbook.icespear_lvl / 4;
            radius = 3;
        }
    }

    public Vector2 findTarget() {
        Vector2 center;
        if(targetPosition == null) {
            if(!controllerActive) {
                Vector2 direction = new Vector2(getTargetPosition().sub(getSpawnPosition()));
                direction.nor().scl(4);
                center = new Vector2(getSpawnPosition().add(direction));
            } else {
                center = getTargetPosition();
            }
        } else {
            center = new Vector2(originBody.getPosition());
            randomTarget = true;
        }
        return center;
    }

    public void shootLightning(Vector2 center, ArrayList<Monster> inRange) {
        if (!inRange.isEmpty()) {

            float dst = Float.MAX_VALUE;
            Monster target = null;
            if(randomTarget) {
                int index = (int) (Math.random() * inRange.size());
                target = inRange.get(index);
            } else {
                for (Monster monster : inRange) {
                    float toOrigin = monster.body.getPosition().dst(center);
                    if (toOrigin < dst && SpellUtils.hasLineOfSight(monster.body.getPosition(), getSpawnPosition())) {
                        dst = toOrigin;
                        target = monster;
                    }
                }
            }

            if (target != null) {
                ChainLightning_Hit chain = new ChainLightning_Hit(target);
                chain.firstChain = true;
                chain.monstersHit.add(target);
                chain.setChain(this);
                chain.setElements(this);

                if (originBody != null) {
                    chain.originBody = originBody;
                } else {
                    chain.originBody = player.pawn.body;
                }

                screen.spellManager.toAdd(chain);

            }
        }
    }

    public void setChain(ChainLightning_Spell parentChain) {
        nested_spell = parentChain.nested_spell;
        frostbolts = parentChain.frostbolts;
        rifts = parentChain.rifts;
        fireball = parentChain.fireball;
        beam = parentChain.beam;
        spear = parentChain.spear;
        arcaneMissile = parentChain.arcaneMissile;
        radius = parentChain.radius;
        maxSplits = parentChain.maxSplits;
        maxHits = parentChain.maxHits;
    }


    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.chainlightning_lvl;
    }

    @Override
    public int getDmg() {
        int dmg = baseDmg;
        dmg += 8 * getLvl();
        dmg = (int) (dmg * (1 + player.spellbook.voltageBonusDmg/100f));
        return dmg;
    }

}
