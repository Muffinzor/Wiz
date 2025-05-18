package wizardo.game.Monsters.ForestMonsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Drop.GoldDrop;
import wizardo.game.Monsters.MonsterActionManager;
import wizardo.game.Monsters.MonsterArchetypes.MonsterMelee;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterActions.AttackSwing.AttackSwing;
import wizardo.game.Monsters.MonsterStateManager.MeleeStateManager;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Resources.MonsterResources.MonsterWeapons;
import wizardo.game.Resources.MonsterResources.OrcBruteAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class OrcBrute extends MonsterMelee {

    public OrcBrute(BattleScreen screen, Vector2 position, MonsterSpawner spawner) {
        super(screen, position, spawner);
        speed = 20f/PPM;
        hp = 120;
        maxHP = 120;
        xp = 25;

        dmg = 20;

        massValue = 150f;
        bodyRadius = 18;
        height = 90;
        width = 32;

        stateTime = (float) Math.random();
        walk_anim = OrcBruteAnims.orcBrute_walk;
        death_anim = OrcBruteAnims.orcBrute_death;
        weaponSprite = MonsterWeapons.orc_bludgeon;

        movementManager = new MovementManager(this);
        stateManager = new MeleeStateManager(this);
        monsterActionManager = new MonsterActionManager(this, 2.5f);
        attackRange = 1.75f;
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

    @Override
    public void onDeath() {
        super.onDeath();
        float goldrate = 0.9f - player.stats.luck/500f;
        if(Math.random() >= goldrate) {
            GoldDrop gold = new GoldDrop(body.getPosition(),2 , 3);
            screen.dropManager.addDrop(gold);
        }
    }
}
