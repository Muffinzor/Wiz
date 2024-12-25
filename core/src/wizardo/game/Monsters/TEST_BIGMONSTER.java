package wizardo.game.Monsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.MonsterMelee;
import wizardo.game.Monsters.MonsterAttack.AttackManager;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterActions.AttackSwing.AttackSwing;
import wizardo.game.Monsters.MonsterStateManager.MeleeStateManager;
import wizardo.game.Resources.MonsterResources.MonsterWeapons;
import wizardo.game.Resources.MonsterResources.SkeletonGiantAnims;
import wizardo.game.Screens.Battle.BattleScreen;

import static wizardo.game.Utils.Constants.PPM;

public class TEST_BIGMONSTER extends MonsterMelee {

    public TEST_BIGMONSTER(BattleScreen screen, Vector2 position) {

        super(screen, position);
        speed = 20f/PPM;
        hp = 300;
        maxHP = 300;
        massValue = 150f;

        bodyRadius = 18;
        height = 90;
        width = 32;

        stateTime = (float) Math.random();
        walk_anim = SkeletonGiantAnims.skellygiant_walk;
        death_anim = SkeletonGiantAnims.skellygiant_death;
        weaponSprite = MonsterWeapons.bone_axe;

        movementManager = new MovementManager(this);
        stateManager = new MeleeStateManager(this);
        attackManager = new AttackManager(this, 2.5f);
        attackRange = 1.5f;
        hitboxRadius = 20;
        rushDistance = 0;

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
        heavy = true;

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
