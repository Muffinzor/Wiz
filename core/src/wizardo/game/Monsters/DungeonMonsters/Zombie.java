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
import wizardo.game.Resources.MonsterResources.ZombieAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner.MonsterSpawner_Dungeon;

import static wizardo.game.Utils.Constants.PPM;

public class Zombie extends MonsterMelee {

    public Zombie(BattleScreen screen, Vector2 position, MonsterSpawner_Dungeon spawner, Vector2 patrolDirection) {
        super(screen, position, spawner, patrolDirection);
        speed = 16f/PPM;
        hp = 250;
        maxHP = 250;
        xp = 50;

        dmg = 15;
        heavy = true;

        massValue = 250f;
        bodyRadius = 25;
        height = 65;
        width = 40;

        stateTime = (float) Math.random();
        walk_anim = ZombieAnims.zombie_walk;
        death_anim = ZombieAnims.zombie_death;
        weaponSprite = MonsterWeapons.bone_club;

        movementManager = new MovementManager(this, patrolDirection);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
        stateManager = new MeleeStateManager(this);
        monsterActionManager = new MonsterActionManager(this, 1.5f);
        attackRange = 2;
        hitboxRadius = 20;
        rushDistance = 0;
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
