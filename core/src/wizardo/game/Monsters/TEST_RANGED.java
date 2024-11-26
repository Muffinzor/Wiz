package wizardo.game.Monsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.MonsterRanged;
import wizardo.game.Monsters.MonsterAttack.AttackManager;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterSpells.SmallProjectile.SmallProjectile;
import wizardo.game.Monsters.MonsterStateManager.RangedStateManager;
import wizardo.game.Resources.MonsterResources.AcolyteAnims;
import wizardo.game.Resources.MonsterResources.SkeletonAnims;
import wizardo.game.Screens.Battle.BattleScreen;

import static wizardo.game.Utils.Constants.PPM;

public class TEST_RANGED extends MonsterRanged {

    public TEST_RANGED(BattleScreen screen, Vector2 position) {
        super(screen, position);
        speed = 25f/PPM;
        hp = 750;
        maxHP = 750;
        massValue = 150f;

        bodyRadius = 12;

        stateTime = (float) Math.random();
        walk_anim = AcolyteAnims.acolyte_walk_T1;
        death_anim = SkeletonAnims.skelly_death_T1;

        movementManager = new MovementManager(this);
        stateManager = new RangedStateManager(this, 4.5f, 12f);
        attackManager = new AttackManager(this, 6f);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
    }

    @Override
    public void launchAttack() {
        SmallProjectile proj = new SmallProjectile(body.getPosition(), this);
        screen.monsterProjManager.toAdd(proj);
    }

    @Override
    public void initialize() {
        initialized = true;
        createBody();
        createLight(30, 0.8f);
        speed = speed * MathUtils.random(0.9f, 1.1f);
    }
}
