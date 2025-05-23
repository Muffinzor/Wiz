package wizardo.game.Monsters.ForestMonsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Drop.ScrollDrop;
import wizardo.game.Monsters.MonsterActionManager;
import wizardo.game.Monsters.MonsterActions.AttackSwing.AttackSwing;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterActions.SmallFirebolts.SmallFirebolts_Action;
import wizardo.game.Monsters.MonsterActions.SmallLaser.SmallLaser_Action;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterStateManager.RangedStateManager;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Resources.MonsterResources.MonsterWeapons;
import wizardo.game.Resources.MonsterResources.OrcShamanAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;

import static wizardo.game.Utils.Constants.PPM;

public class OrcShaman extends Monster {

    MonsterSpell projectile;

    public OrcShaman(BattleScreen screen, Vector2 position, MonsterSpawner spawner, Vector2 patrolDirection) {
        super(screen, position, spawner, patrolDirection);
        speed = 25f/PPM;
        hp = 50;
        maxHP = 50;
        xp = 15;

        dmg = 10;

        massValue = 75;
        bodyRadius = 12;
        width = 24;
        height = 32;

        walk_anim = OrcShamanAnims.orcShaman_walk;
        death_anim = OrcShamanAnims.orcShaman_death;
        idle_anim = OrcShamanAnims.orcShaman_stop;

        movementManager = new MovementManager(this, patrolDirection);
        stateManager = new RangedStateManager(this, 3f, 9f);
        monsterActionManager = new MonsterActionManager(this, 5f);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;

        projectile = new SmallFirebolts_Action(this);
        hitboxRadius = 5;
        weaponSprite = MonsterWeapons.short_staff;
    }

    @Override
    public void launchAttack() {
        AttackSwing swing = new AttackSwing(this);
        screen.monsterSpellManager.toAdd(swing);

        MonsterSpell proj = projectile.clone();
        screen.monsterSpellManager.toAdd(proj);
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
        if(Math.random() > 0.97f) {
            ScrollDrop scroll = new ScrollDrop(body.getPosition(), null,null,3,true);
            screen.dropManager.addDrop(scroll);
        }
    }
}
