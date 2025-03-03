package wizardo.game.Monsters.DungeonMonsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Drop.ScrollDrop;
import wizardo.game.Monsters.MonsterActionManager;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterActions.SmallProjectile.SmallLaser_Action;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterStateManager.RangedStateManager;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Resources.MonsterResources.AcolyteAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner_Dungeon;

import static wizardo.game.Utils.Constants.PPM;

public class AcolyteBlue extends Monster {

    MonsterSpell projectile;

    public AcolyteBlue(BattleScreen screen, Vector2 position, MonsterSpawner_Dungeon spawner) {
        super(screen, position, spawner);
        speed = 25f/PPM;
        hp = 50;
        maxHP = 50;
        xp = 15;

        dmg = 15;

        massValue = 75;
        bodyRadius = 12;
        width = 24;
        height = 32;

        spawn_anim = AcolyteAnims.acolyte_spawn_blue;
        walk_anim = AcolyteAnims.acolyte_walk_blue;
        death_anim = AcolyteAnims.acolyte_death_blue;

        movementManager = new MovementManager(this);
        stateManager = new RangedStateManager(this, 4.5f, 12f);
        monsterActionManager = new MonsterActionManager(this, 6f);

        state = MonsterUtils.MONSTER_STATE.SPAWNING;

        projectile = new SmallLaser_Action(this);
    }

    @Override
    public void launchAttack() {

    }

    @Override
    public void initialize() {
        initialized = true;
        createBody();
        createLight(30, 0.8f);
        speed = speed * MathUtils.random(0.9f, 1.1f);
    }

    @Override
    public void onDeath() {
        super.onDeath();
        if(Math.random() > 0.97f) {
            ScrollDrop scroll = new ScrollDrop(body.getPosition(), null,null,3,true);
            screen.dropManager.addDrop(scroll);
        }
    }
}
