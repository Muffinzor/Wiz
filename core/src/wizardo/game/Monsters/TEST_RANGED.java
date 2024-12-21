package wizardo.game.Monsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterActions.SmallProjectile.SmallLaser_Action;
import wizardo.game.Monsters.MonsterArchetypes.MonsterRanged;
import wizardo.game.Monsters.MonsterAttack.AttackManager;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterActions.SmallProjectile.SmallLaser_Projectile;
import wizardo.game.Monsters.MonsterStateManager.RangedStateManager;
import wizardo.game.Resources.MonsterResources.AcolyteAnims;
import wizardo.game.Screens.Battle.BattleScreen;

import static wizardo.game.Utils.Constants.PPM;

public class TEST_RANGED extends MonsterRanged {

    public TEST_RANGED(BattleScreen screen, Vector2 position) {
        super(screen, position);
        speed = 25f/PPM;
        hp = 1000;
        maxHP = 1000;
        massValue = 75;

        bodyRadius = 12;
        width = 24;
        height = 32;

        stateTime = (float) Math.random();
        walk_anim = AcolyteAnims.acolyte_walk_blue;
        death_anim = AcolyteAnims.acolyte_death_blue;

        movementManager = new MovementManager(this);
        stateManager = new RangedStateManager(this, 4.5f, 12f);
        attackManager = new AttackManager(this, 6f);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;

        projectile = new SmallLaser_Action(this);
    }

    @Override
    public void initialize() {
        initialized = true;
        createBody();
        createLight(30, 0.8f);
        speed = speed * MathUtils.random(0.9f, 1.1f);
    }
}
