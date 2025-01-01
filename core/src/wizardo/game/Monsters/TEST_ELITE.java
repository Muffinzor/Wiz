package wizardo.game.Monsters;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.MonsterMelee;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterStateManager.MeleeStateManager;
import wizardo.game.Resources.MonsterResources.MonsterWeapons;
import wizardo.game.Resources.MonsterResources.SkeletonGiantAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;

import static wizardo.game.Utils.Constants.PPM;

public class TEST_ELITE extends MonsterMelee {

    public TEST_ELITE(BattleScreen screen, Vector2 position, MonsterSpawner spawner) {
        super(screen, position, spawner);
        speed = 20f/PPM;
        hp = 150;
        maxHP = 150;
        xp = 30;

        dmg = 20;

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
        monsterActionManager = new MonsterActionManager(this, 2.5f);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
        heavy = true;

    }

    @Override
    public void launchAttack() {

    }

    @Override
    public void initialize() {

    }
}
