package wizardo.game.Monsters.DungeonMonsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterActionManager;
import wizardo.game.Monsters.MonsterArchetypes.MonsterMelee;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterActions.AttackSwing.AttackSwing;
import wizardo.game.Monsters.MonsterStateManager.MeleeStateManager;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Resources.MonsterResources.MonsterWeapons;
import wizardo.game.Resources.MonsterResources.SkeletonAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner.MonsterSpawner_Dungeon;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;

import static wizardo.game.Utils.Constants.PPM;

public class Skeleton_T2 extends MonsterMelee {

    public Skeleton_T2(BattleScreen screen, Vector2 position, MonsterSpawner spawner, Vector2 patrolDirection) {
        super(screen, position, spawner, patrolDirection);
        speed = 20f/PPM;
        hp = 50;
        maxHP = 50;
        xp = 8;

        basic = true;

        dmg = 8;

        massValue = 5f;
        bodyRadius = 10;
        height = 40;
        width = 20;

        stateTime = (float) Math.random();
        walk_anim = SkeletonAnims.skelly_walk_T2;
        death_anim = SkeletonAnims.skelly_death_T2;
        shatter_anim = SkeletonAnims.skelly_shatter;
        weaponSprite = MonsterWeapons.bone_knife;

        movementManager = new MovementManager(this, patrolDirection);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
        stateManager = new MeleeStateManager(this);
        monsterActionManager = new MonsterActionManager(this, 1.5f);
        attackRange = 1;
        hitboxRadius = 10;
        rushDistance = 2f;
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
