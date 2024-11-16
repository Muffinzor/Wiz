package wizardo.game.Spells.Arcane.EnergyBeam;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Hit;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class EnergyBeam_ChainLightningBody extends Spell {

    Body body;
    public boolean chargedbolts;

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            sendLightning();
        }

        stateTime += delta;

        if(stateTime >= 0.5f) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 1);
        body.setUserData(this);
    }

    public void sendLightning() {
        ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(body.getPosition(), 4, true);
        if(!inRange.isEmpty()) {
            int index = MathUtils.random(0, inRange.size() - 1);
            Monster target = inRange.get(index);
            ChainLightning_Hit chain = new ChainLightning_Hit(target);
            if(chargedbolts) {
                chain.nested_spell = new ChargedBolts_Spell();
            }
            chain.originBody = body;
            chain.maxHits = 2;
            chain.setElements(this);
            screen.spellManager.toAdd(chain);
            inRange.remove(target);
        }

        if(player.spellbook.chainlightning_lvl >= 6 && !inRange.isEmpty()) {
            int index = MathUtils.random(0, inRange.size() - 1);
            Monster target = inRange.get(index);
            ChainLightning_Hit chain = new ChainLightning_Hit(target);
            chain.originBody = body;
            chain.maxHits = 3;
            chain.setElements(this);
            screen.spellManager.toAdd(chain);
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
        return 0;
    }
}
