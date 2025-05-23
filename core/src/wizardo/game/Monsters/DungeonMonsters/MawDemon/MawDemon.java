package wizardo.game.Monsters.DungeonMonsters.MawDemon;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Drop.EquipmentDrop;
import wizardo.game.Items.Drop.ScrollDrop;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Player.Player;
import wizardo.game.Resources.MonsterResources.MawDemonAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;
import wizardo.game.Screens.Battle.MonsterSpawner.SpawnerUtils;

import static wizardo.game.Monsters.MonsterUtils.MONSTER_STATE.CHARGING;
import static wizardo.game.Monsters.MonsterUtils.MONSTER_STATE.SPAWNING;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class MawDemon extends Monster {

    float explosionDelay = 0.25f;
    float chargeTimer;
    boolean charging;
    boolean respawn;
    Vector2 toPlayer;

    public MawDemon(BattleScreen screen, Vector2 position, MonsterSpawner spawner, Vector2 patrolDirection) {
        super(screen, position, spawner, patrolDirection);
        alpha = 0;
        speed = 1f;
        hp = 2500;
        maxHP = 2500;
        xp = 500;
        elite = true;
        heavy = true;
        dmg = 20;

        massValue = 10000f;
        bodyRadius = 30;
        height = 100;
        width = 48;

        stateTime = (float) Math.random();
        walk_anim = MawDemonAnims.mawDemon_walk;
        death_anim = MawDemonAnims.mawDemon_death;

        movementManager = new MovementManager(this, patrolDirection);
        stateManager = new MawDemonStateManager(this);
        monsterActionManager = new MawDemon_ActionManager(this, 2.5f);

        state = SPAWNING;
        MawDemon_Pentagram pentagram = new MawDemon_Pentagram(this);
        screen.monsterSpellManager.toAdd(pentagram);
    }

    public void handleSpawning(float delta) {
        if(!spawned) {
            alpha += 0.005f;
            if(respawn) {
                alpha += 0.0075f;
            }
            stateTime -= delta;
            body.setActive(false);
            if(alpha >= 1) {
                alpha = 1;
                spawned = true;
                body.setActive(true);
                state = MonsterUtils.MONSTER_STATE.ADVANCING;
                MawDemon_SkullExplosion explosion = new MawDemon_SkullExplosion(this);
                screen.monsterSpellManager.toAdd(explosion);
            }
        }
    }

    public void handleTooFar() {
        state = SPAWNING;
        spawned = false;
        respawn = true;
        alpha = 0.1f;
        tooFar = false;
        Vector2 newPosition = SpawnerUtils.getRandomCloseSpawnVector();
        body.setTransform(newPosition, 0);
        body.setActive(false);
        MawDemon_Pentagram pentagram = new MawDemon_Pentagram(this);
        screen.monsterSpellManager.toAdd(pentagram);
    }

    @Override
    public void launchAttack() {
        MawDemon_FireboltAttack attack = new MawDemon_FireboltAttack(this);
        screen.monsterSpellManager.toAdd(attack);
    }

    public void drawFrame() {
        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();

        if(spawn_anim != null && stateTime <= spawn_anim.getAnimationDuration()) {
            frame.set(spawn_anim.getKeyFrame(stateTime, false));
        } else {
            frame.set(walk_anim.getKeyFrame(stateTime, true));
        }

        frame.setPosition(body.getPosition().x * PPM - frame.getWidth()/2, body.getPosition().y * PPM - bodyRadius);

        boolean flip;
        if(state == CHARGING) {
            flip = frameReversed;
        } else {
            flip = player.pawn.getBodyX() < body.getPosition().x;
        }
        frame.flip(flip, false);
        frame.setAlpha(alpha);

        screen.addSortedSprite(frame);
    }

    public void initiateCharge() {
        toPlayer = player.pawn.getPosition().cpy().sub(body.getPosition());
        toPlayer.nor().scl(speed * 4);

        frameReversed = player.pawn.getPosition().x < body.getPosition().x;

        body.setLinearVelocity(toPlayer);
        charging = true;
        chargeTimer = 2f;
    }

    @Override
    public void initialize() {
        red = 0.6f;
        green = 0.15f;
        createBody();
        createLight(75, 1f);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM + 20);
    }

    @Override
    public void onDeath() {
        super.onDeath();
        MawDemon_SkullExplosion explosion = new MawDemon_SkullExplosion(this);
        screen.monsterSpellManager.toAdd(explosion);

        double roll = Math.random();
        if(roll > 0.75f) {
            EquipmentDrop drop = new EquipmentDrop(body.getPosition(), screen.dropManager.getEquipmentForDrop(ItemUtils.EquipSlot.ALL, ItemUtils.EquipQuality.LEGENDARY, null));
            screen.dropManager.addDrop(drop);
        } else if(roll > 0.5f){
            EquipmentDrop drop = new EquipmentDrop(body.getPosition(), screen.dropManager.getEquipmentForDrop(ItemUtils.EquipSlot.ALL, ItemUtils.EquipQuality.EPIC, null));
            screen.dropManager.addDrop(drop);
        } else {
            ScrollDrop drop = new ScrollDrop(body.getPosition(), null, null, 3, true);
            screen.dropManager.addDrop(drop);
        }
    }

    public void handleCollision(Player player) {
        dealDmg();
        if(charging) {
            Vector2 monsterVelocity = body.getLinearVelocity();

            if (monsterVelocity.isZero()) {
                Vector2 pushDirection = player.pawn.body.getPosition().cpy().sub(body.getPosition()).nor().scl(15);
                player.pawn.applyPush(pushDirection, 15, 0.35f, 0.9f);
                return;
            }

            Vector2 perp1 = new Vector2(-monsterVelocity.y, monsterVelocity.x).nor();
            Vector2 perp2 = new Vector2(monsterVelocity.y, -monsterVelocity.x).nor();

            Vector2 directionToPlayer = player.pawn.body.getPosition().cpy().sub(body.getPosition()).nor();

            float dot1 = perp1.dot(directionToPlayer);
            float dot2 = perp2.dot(directionToPlayer);
            Vector2 chosenDirection = dot1 > dot2 ? perp1 : perp2;

            chosenDirection.scl(15);
            player.pawn.applyPush(chosenDirection, 8, 0.35f, 0.9f);
        }
    }

    public void handleCollision(Monster monster) {
        if(charging) {
            // Get the monster's velocity
            Vector2 monsterVelocity = body.getLinearVelocity();

            // If the monster has no velocity, push the player backward relative to the monster
            if (monsterVelocity.isZero()) {
                Vector2 pushDirection = monster.body.getPosition()
                        .cpy()
                        .sub(body.getPosition())
                        .nor()
                        .scl(15); // Push intensity
                monster.movementManager.applyPush(pushDirection, 8, 0.35f, 0.9f);
                return;
            }

            // Get two possible perpendicular directions
            Vector2 perp1 = new Vector2(-monsterVelocity.y, monsterVelocity.x).nor();
            Vector2 perp2 = new Vector2(monsterVelocity.y, -monsterVelocity.x).nor();

            // Compute the direction to the player relative to the monster
            Vector2 directionToPlayer = monster.body.getPosition()
                    .cpy()
                    .sub(body.getPosition())
                    .nor();

            // Determine which perpendicular direction is closer to the player
            float dot1 = perp1.dot(directionToPlayer);
            float dot2 = perp2.dot(directionToPlayer);

            // Pick the perpendicular direction that is closer to the player
            Vector2 chosenDirection = dot1 > dot2 ? perp1 : perp2;

            // Apply push to the player in the chosen direction
            chosenDirection.scl(15); // Adjust push intensity
            monster.movementManager.applyPush(chosenDirection, 3, 0.15f, 0.9f);
        }
    }
}
