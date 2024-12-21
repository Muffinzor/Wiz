package wizardo.game.Monsters.MonsterActions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Wizardo.player;

public abstract class MonsterSpell implements Cloneable {

    public Body body;
    public RoundLight light;
    public Monster originMonster;

    public boolean initialized;
    public float stateTime;
    public float speed;
    public float radius;
    public float rotation;
    public Vector2 spawnPosition;
    public Vector2 targetPosition;
    public Vector2 direction;

    public BattleScreen screen;

    public boolean sentBack;     //Repulsion Field flag

    public MonsterSpell(Monster monster) {
        this.originMonster = monster;
    }


    public void update(float delta) {
        if(!initialized) {
            initialize();
            initialized = true;
        }

        stateTime += delta;
        drawFrame();
        adjustLight();

        checkState(delta);

    }

    public abstract void checkState(float delta);

    public void handleCollision(Fixture fix) {

    }

    public void handleCollision(Body playerBody) {

    }

    public abstract void drawFrame();

    public void adjustLight() {

    }

    public abstract void initialize();

    /** Default set up for projectile **/
    public void createBody() {

        if(targetPosition == null) {
            targetPosition = new Vector2(player.pawn.getPosition());
        }

        direction = new Vector2(targetPosition.cpy().sub(spawnPosition));
        direction.nor();
        //Vector2 offset = new Vector2(direction.cpy().scl(originMonster.bodyRadius/PPM));
        //Vector2 adjustedSpawn = new Vector2(spawnPosition.add(offset));

        body = BodyFactory.monsterProjectileBody(spawnPosition, radius);
        body.setUserData(this);
        Vector2 velocity = direction.scl(speed);
        rotation = velocity.angleDeg();
        body.setLinearVelocity(velocity);

    }

    public abstract void dispose();

    @Override
    public MonsterSpell clone() {
        try {
            MonsterSpell clone = (MonsterSpell) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
