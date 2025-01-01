package wizardo.game.Spells.Arcane.EnergyBeam;

import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Explosion;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class EnergyBeam_FreezeBody extends Spell {

    public boolean frostbolt;

    Body body;

    public EnergyBeam_FreezeBody() {

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            createBody();
            frostbolts();
            initialized = true;
        }

        stateTime += delta;

        if(stateTime >= 0.2f) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }

    }

    public void createBody() {
        float rad = 24 + 6 * player.spellbook.frozenorb_lvl;
        body = BodyFactory.spellExplosionBody(targetPosition, rad);
        body.setUserData(this);
    }

    public void handleCollision(Monster monster) {
        float freezeDuration = 1.4f + 0.1f * player.spellbook.frozenorb_lvl;
        monster.applyFreeze(freezeDuration, freezeDuration * 2.5f);
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

    public void frostbolts() {
        if(frostbolt) {
            int quantity = 2 + player.spellbook.frostbolt_lvl/2;
            for (int i = 0; i < quantity; i++) {
                Frostbolt_Explosion explosion = new Frostbolt_Explosion();
                explosion.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2.5f);
                explosion.setElements(this);
                screen.spellManager.add(explosion);
            }
        }
    }
}
