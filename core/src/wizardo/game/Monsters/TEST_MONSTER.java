package wizardo.game.Monsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterStateManager.MeleeStateManager;
import wizardo.game.Resources.MonsterResources.SkeletonAnims;
import wizardo.game.Screens.Battle.BattleScreen;

import static wizardo.game.Utils.Constants.PPM;

public class TEST_MONSTER extends Monster {

    public TEST_MONSTER(BattleScreen screen, Vector2 position) {
        super(screen, position);
        speed = 25f/PPM;
        hp = 750;
        maxHP = 750;
        massValue = 5f;

        bodyRadius = 10;

        stateTime = (float) Math.random();
        walk_anim = SkeletonAnims.skelly_walk_T1;
        death_anim = SkeletonAnims.skelly_death_T1;

        movementManager = new MovementManager(this);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
        stateManager = new MeleeStateManager();
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
