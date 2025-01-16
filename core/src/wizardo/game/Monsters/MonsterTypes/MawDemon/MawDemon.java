package wizardo.game.Monsters.MonsterTypes.MawDemon;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.ChestLoot;
import wizardo.game.Monsters.MonsterActions.SelfFireExplosion.SelfFireExplosion;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Player.Player;
import wizardo.game.Resources.MonsterResources.MawDemonAnims;
import wizardo.game.Resources.MonsterResources.SkeletonGiantAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;

import static wizardo.game.Monsters.MonsterUtils.MONSTER_STATE.ADVANCING;
import static wizardo.game.Monsters.MonsterUtils.MONSTER_STATE.CHARGING;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class MawDemon extends Monster {

    float explosionDelay = 0.25f;


    public MawDemon(BattleScreen screen, Vector2 position, MonsterSpawner spawner) {
        super(screen, position, spawner);

        speed = 1f;
        hp = 10000;
        maxHP = 10000;
        xp = 400;
        elite = true;

        dmg = 30;

        massValue = 10000f;
        bodyRadius = 30;
        height = 100;
        width = 48;

        stateTime = (float) Math.random();
        walk_anim = MawDemonAnims.mawDemon_walk;
        death_anim = MawDemonAnims.mawDemon_death;

        movementManager = new MovementManager(this);
        stateManager = new MawDemonStateManager(this);
        monsterActionManager = new MawDemon_ActionManager(this, 2.5f);

        state = MonsterUtils.MONSTER_STATE.ADVANCING;
        heavy = true;
    }

    float chargeTimer;
    boolean charging;

    Vector2 toPlayer;

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
        MawDemon_DeathExplosion explosion = new MawDemon_DeathExplosion(this);
        screen.monsterSpellManager.toAdd(explosion);
    }

    public void handleCollision(Player player) {
        dealDmg();
        if(charging) {
            // Get the monster's velocity
            Vector2 monsterVelocity = body.getLinearVelocity();

            // If the monster has no velocity, push the player backward relative to the monster
            if (monsterVelocity.isZero()) {
                Vector2 pushDirection = player.pawn.body.getPosition()
                        .cpy()
                        .sub(body.getPosition())
                        .nor()
                        .scl(15); // Push intensity
                player.pawn.applyPush(pushDirection, 15, 0.35f, 0.9f);
                return;
            }

            // Get two possible perpendicular directions
            Vector2 perp1 = new Vector2(-monsterVelocity.y, monsterVelocity.x).nor();
            Vector2 perp2 = new Vector2(monsterVelocity.y, -monsterVelocity.x).nor();

            // Compute the direction to the player relative to the monster
            Vector2 directionToPlayer = player.pawn.body.getPosition()
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
