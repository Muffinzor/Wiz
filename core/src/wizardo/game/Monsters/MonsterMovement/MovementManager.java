package wizardo.game.Monsters.MonsterMovement;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;

import static wizardo.game.Wizardo.player;

public class MovementManager {

    Monster monster;
    Pathfinder pathfinder;

    Vector2 pushBackForce = new Vector2();
    public float pushBackTimer = 0;
    float pushDecayRate = 0;
    int frameCounter = 0;

    public MovementManager(Monster monster) {
        this.monster = monster;
        this.pathfinder = new Pathfinder(monster);
    }

    public void moveMonster(float delta) {

        if(delta == 0) {
            stopMonster();
        } else if(pushBackTimer > 0) {
            pushMonster(delta);
        } else {

            switch(monster.state) {
                case ADVANCING -> walk(false);
                case FLEEING -> walk(true);
                case ATTACKING, STANDBY -> {
                    stopMonster();
                    frameCounter = 20;
                }
            }

        }
    }

    public void walk(boolean backwards) {
        frameCounter ++;
        if(frameCounter >= 20) {
            checkDistance();
            if(backwards) {
                monster.directionVector = pathfinder.awayFromPlayer();
            } else {
                monster.directionVector = pathfinder.towardsPlayer();
            }
            frameCounter = 0;
        }

        float trueSpeed = monster.speed;
        if(monster.freezeTimer > 0) {
            trueSpeed = 0;
        } else if (monster.slowedTimer > 0){
            trueSpeed = monster.speed * monster.slowRatio;
        }

        monster.directionVector.nor().scl(trueSpeed);
        monster.body.setLinearVelocity(monster.directionVector);
    }

    public void checkDistance() {
        float dst = monster.body.getPosition().dst(player.pawn.getPosition());
        if(dst > 55) {
            monster.tooFar = true;
        }
    }

    public void stopMonster() {
        monster.body.setLinearVelocity(0,0);
    }

    public void applyPush(Vector2 pushDirection, float strength, float duration, float decayRate) {
        if(!monster.elite) {
            if (pushBackTimer <= 0) {
                float pushStrength = strength;
                float pushDuration = duration;
                if (monster.heavy) {
                    pushStrength = strength / 2f;
                    if (decayRate < 1) {
                        pushDuration = duration * (2 / 3f);
                    }
                }
                pushBackForce.set(pushDirection.nor().scl(pushStrength));
                pushBackTimer = pushDuration;
                pushDecayRate = decayRate;
            }
        }
    }

    public void pushMonster(float delta) {
        monster.body.setLinearVelocity(pushBackForce);
        pushBackForce.scl(pushDecayRate);
        pushBackTimer -= delta;
        if(pushBackForce.len() < 0.1f) {
            pushBackTimer = 0;
        }
    }


}
