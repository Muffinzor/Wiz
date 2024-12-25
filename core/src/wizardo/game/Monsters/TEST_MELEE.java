package wizardo.game.Monsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.MonsterMelee;
import wizardo.game.Monsters.MonsterAttack.AttackManager;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterActions.AttackSwing.AttackSwing;
import wizardo.game.Monsters.MonsterStateManager.MeleeStateManager;
import wizardo.game.Resources.MonsterResources.SkeletonAnims;
import wizardo.game.Screens.Battle.BattleScreen;

import static wizardo.game.Utils.Constants.PPM;

public class TEST_MELEE extends MonsterMelee {

    public TEST_MELEE(BattleScreen screen, Vector2 position) {
        super(screen, position);
        speed = 20f/PPM;
        hp = 60;
        maxHP = 60;
        massValue = 5f;

        bodyRadius = 10;
        height = 40;
        width = 20;

        stateTime = (float) Math.random();
        walk_anim = SkeletonAnims.skelly_walk_T1;
        death_anim = SkeletonAnims.skelly_death_T1;
        weaponSprite = SkeletonAnims.bone;

        movementManager = new MovementManager(this);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
        stateManager = new MeleeStateManager(this);
        attackManager = new AttackManager(this, 1.5f);
        attackRange = 1;
        hitboxRadius = 10;
        rushDistance = 2.5f;
    }

    @Override
    public void launchAttack() {
        AttackSwing swing = new AttackSwing(this);
        screen.monsterSpellManager.toAdd(swing);
    }

    @Override
    public void initialize() {
        initialized = true;
        createBody();
        speed = speed * MathUtils.random(0.9f, 1.1f);
    }

}
