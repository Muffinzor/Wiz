package wizardo.game.Monsters.DungeonMonsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterActionManager;
import wizardo.game.Monsters.MonsterArchetypes.MonsterMelee;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterActions.AttackSwing.AttackSwing;
import wizardo.game.Monsters.MonsterStateManager.MeleeStateManager;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Resources.MonsterResources.SludgeAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner.MonsterSpawner_Dungeon;

import static wizardo.game.Resources.MonsterResources.MonsterWeapons.invis_weapon;

public class GreenSludge_Mini extends MonsterMelee {

    int speed_frames = 0;

    public GreenSludge_Mini(BattleScreen screen, Vector2 position, MonsterSpawner_Dungeon spawner, Vector2 patrolDirection) {
        super(screen, position, spawner, patrolDirection);
        speed = 0.825f;
        hp = 35;
        maxHP = 35;
        xp = 8;

        basic = true;

        dmg = 8;

        massValue = 8;
        bodyRadius = 10;
        height = 25;
        width = 20;

        stateTime = (float) Math.random();
        walk_anim = SludgeAnims.sludge_green_move_mini;
        death_anim = SludgeAnims.sludge_green_death_mini;
        spawn_anim = SludgeAnims.sludge_green_move_mini;
        weaponSprite = invis_weapon;

        movementManager = new MovementManager(this, patrolDirection);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
        stateManager = new MeleeStateManager(this);
        monsterActionManager = new MonsterActionManager(this, 1);
        attackRange = 0.75f;
        hitboxRadius = 8;
    }

    public void specific_update(float delta) {
        speed -= 0.01f;
        speed_frames ++;

        if(speed_frames >= 60) {
            speed = 0.825f;
            speed_frames = 0;
        }
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
