package wizardo.game.Spells.Lightning.ChainLightning;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Hat.Legendary_SentientHat;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.GameSettings.autoAim_On;
import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Wizardo.player;

public class ChainLightning_Spell extends Spell {

    public int maxHits = 7;
    public int currentHits = 1;
    public float radius = 5;

    public int splits = 0;
    public int maxSplits = 0;
    public float splitChance = 0;
    public boolean forked;

    public boolean frostbolts;
    public boolean frozenorb;
    public boolean arcaneMissile;
    public boolean rifts;
    public boolean beam;
    public boolean spear;
    public boolean fireball;

    boolean randomTarget;

    public ChainLightning_Spell() {
        raycasted = true;
        aimReach = 5;

        string_name = "Chain Lightning";
        levelup_enum = LevelUpEnums.LevelUps.CHAIN;
        cooldown = 1.2f;
        dmg = 32;
        autoaimable = true;

        main_element = SpellUtils.Spell_Element.LIGHTNING;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            setup();
            initialized = true;
        }

        if(delta > 0) {
            Vector2 center = checkAutoAim();
            float radius = checkRadius();
            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(center, radius, true);
            shootLightning(center, inRange);
            screen.spellManager.remove(this);
        }
    }

    public void setup() {
        maxHits += player.spellbook.chainlightning_bonus_jump;
        if(spear) {
            splitChance = 0.9f - 0.05f * player.spellbook.icespear_lvl;
            maxSplits = player.spellbook.icespear_lvl;
            radius = 4;
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
                    if (toOrigin < dst && toOrigin > 0 && SpellUtils.hasLineOfSight(monster.body.getPosition(), getSpawnPosition())) {
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
                screen.spellManager.add(chain);
            }
        }
    }

    public void setChain(ChainLightning_Spell parentChain) {
        nested_spell = parentChain.nested_spell;
        frostbolts = parentChain.frostbolts;
        frozenorb = parentChain.frozenorb;
        rifts = parentChain.rifts;
        fireball = parentChain.fireball;
        beam = parentChain.beam;
        spear = parentChain.spear;
        arcaneMissile = parentChain.arcaneMissile;
        radius = parentChain.radius;
        maxSplits = parentChain.maxSplits;
        maxHits = parentChain.maxHits;
        splitChance = parentChain.splitChance;
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
        int dmg = this.dmg;
        if(forked) {
            dmg = this.dmg/2;
        }
        dmg += 16 * getLvl();
        if(arcaneMissile) {
            dmg += 8 * (player.spellbook.arcanemissile_lvl - 1);
        }
        return dmg;
    }

    public Vector2 checkAutoAim() {
        if(player.inventory.equippedHat instanceof Legendary_SentientHat || autoAim_On) {
            return new Vector2(player.pawn.getPosition());
        } else {
            return findTarget();
        }
    }

    public float checkRadius() {
        if(player.inventory.equippedHat instanceof Legendary_SentientHat || autoAim_On) {
            return 8;
        } else {
            return 5;
        }
    }
}
