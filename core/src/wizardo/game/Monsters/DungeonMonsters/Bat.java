package wizardo.game.Monsters.DungeonMonsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.MassData;
import wizardo.game.Monsters.MonsterActionManager;
import wizardo.game.Monsters.MonsterArchetypes.MonsterFlying;
import wizardo.game.Monsters.MonsterArchetypes.MonsterMelee;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterActions.AttackSwing.AttackSwing;
import wizardo.game.Monsters.MonsterStateManager.MeleeStateManager;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Resources.MonsterResources.BatAnims;
import wizardo.game.Resources.MonsterResources.MonsterWeapons;
import wizardo.game.Resources.MonsterResources.SkeletonAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.DungeonSpawner.MonsterSpawner_Dungeon;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;

public class Bat extends MonsterMelee {

    public Bat(BattleScreen screen, Vector2 position, MonsterSpawner spawner, Vector2 patrolDirection) {
        super(screen, position, spawner, patrolDirection);
        speed = 30f/PPM;
        hp = 25;
        maxHP = 25;
        xp = 5;

        basic = true;

        dmg = 5;

        massValue = 5f;
        bodyRadius = 10;
        height = 20;
        width = 20;

        stateTime = (float) Math.random();
        walk_anim = BatAnims.bat_walk;
        death_anim = BatAnims.bat_death;
        shatter_anim = BatAnims.bat_shatter;
        weaponSprite = MonsterWeapons.invis_weapon;

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
        stateManager = new MeleeStateManager(this);
        monsterActionManager = new MonsterActionManager(this, 1.2f);
        attackRange = 1;
        hitboxRadius = 10;
        rushDuration = 1;
        rushDistance = 3;
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
        frame_y_adjust = 30;
        speed = speed * MathUtils.random(0.9f, 1.1f);
    }

    public void createBody() {
        body = BodyFactory.flyingBody(position, bodyRadius);
        frame_y_adjust = bodyRadius;
        body.setUserData(this);
        body.setActive(true);
        MassData mass = new MassData();
        float massMin = massValue * 0.8f;
        float massMax = massValue * 1.2f;
        float newMass = MathUtils.random(massMin, massMax);
        massValue = newMass;
        mass.mass = newMass;
        body.setMassData(mass);
    }

}
