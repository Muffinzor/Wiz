package wizardo.game.Monsters.DungeonMonsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Drop.ScrollDrop;
import wizardo.game.Monsters.MonsterActionManager;
import wizardo.game.Monsters.MonsterArchetypes.MonsterMelee;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterActions.AttackSwing.AttackSwing;
import wizardo.game.Monsters.MonsterStateManager.MeleeStateManager;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Resources.MonsterResources.MonsterWeapons;
import wizardo.game.Resources.MonsterResources.SkeletonAnims;
import wizardo.game.Resources.MonsterResources.SludgeAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner_Dungeon;

import static wizardo.game.Display.SpritePool.whitePixel;
import static wizardo.game.Resources.MonsterResources.MonsterWeapons.invis_weapon;
import static wizardo.game.Utils.Constants.PPM;

public class GreenSludge extends MonsterMelee {

    int speed_frames;

    public GreenSludge(BattleScreen screen, Vector2 position, MonsterSpawner_Dungeon spawner) {
        super(screen, position, spawner);
        speed = 0.825f;
        speed_frames = MathUtils.random(0,60);
        hp = 75;
        maxHP = 75;
        xp = 15;

        dmg = 10;

        massValue = 10f;
        bodyRadius = 15;
        height = 35;
        width = 35;

        stateTime = (float) Math.random();
        walk_anim = SludgeAnims.sludge_green_move;
        death_anim = SludgeAnims.sludge_green_death;
        weaponSprite = invis_weapon;

        movementManager = new MovementManager(this);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
        stateManager = new MeleeStateManager(this);
        monsterActionManager = new MonsterActionManager(this, 1);
        attackRange = 1;
        hitboxRadius = 10;
    }

    public void specific_update(float delta) {
        speed -= 0.01f;
        speed_frames ++;

        if(speed_frames >= 60) {
            speed = 0.825f * MathUtils.random(0.9f, 1.1f);
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

    @Override
    public void onDeath() {
        super.onDeath();
        for (int i = 0; i < 3; i++) {
            GreenSludge_Mini mini = new GreenSludge_Mini(screen, body.getPosition(), (MonsterSpawner_Dungeon) spawner);
            screen.monsterSpawner.monster_to_spawn.add(mini);
        }
    }

}
