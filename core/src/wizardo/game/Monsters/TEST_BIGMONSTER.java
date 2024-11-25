package wizardo.game.Monsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Resources.MonsterResources.SkeletonGiantAnims;
import wizardo.game.Screens.Battle.BattleScreen;

import static wizardo.game.Utils.Constants.PPM;

public class TEST_BIGMONSTER extends Monster {

    public TEST_BIGMONSTER(BattleScreen screen, Vector2 position) {
        super(screen, position);
        speed = 25f/PPM;
        hp = 2500;
        maxHP = 2500;
        massValue = 15f;

        bodyRadius = 16;

        stateTime = (float) Math.random();
        walk_anim = SkeletonGiantAnims.skellyGiant_walk_T1;
        death_anim = SkeletonGiantAnims.skellyGiant_death_T1;

        movementManager = new MovementManager(this);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
        heavy = true;
    }

    @Override
    public void launchAttack() {

    }

    @Override
    public void initialize() {
        initialized = true;
        createBody();
        speed = speed * MathUtils.random(0.9f, 1.1f);
    }
}
