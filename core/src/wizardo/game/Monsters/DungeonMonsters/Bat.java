package wizardo.game.Monsters.DungeonMonsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterActionManager;
import wizardo.game.Monsters.MonsterArchetypes.MonsterFlying;
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

public class Bat extends MonsterMelee {

    public Bat(BattleScreen screen, Vector2 position, MonsterSpawner spawner) {
        super(screen, position, spawner);
        speed = 20f/PPM;
        hp = 25;
        maxHP = 25;
        xp = 5;

        basic = true;

        dmg = 5;

        massValue = 5f;
        bodyRadius = 10;
        height = 40;
        width = 20;

        stateTime = (float) Math.random();
        walk_anim = SkeletonAnims.skelly_walk_T1;
        death_anim = SkeletonAnims.skelly_death_T1;
        shatter_anim = SkeletonAnims.skelly_shatter;
        weaponSprite = MonsterWeapons.bone;

        movementManager = new MovementManager(this);

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
