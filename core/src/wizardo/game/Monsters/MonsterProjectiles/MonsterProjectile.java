package wizardo.game.Monsters.MonsterProjectiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public abstract class MonsterProjectile {

    public Body body;
    public RoundLight light;
    public Monster originMonster;

    boolean initialized;
    float stateTime;
    float speed;
    float radius;
    float rotation;
    public Vector2 spawnPosition;
    public Vector2 targetPosition;
    public Vector2 direction;

    BattleScreen screen;

    public MonsterProjectile(Vector2 spawnPosition, Monster monster) {
        this.spawnPosition = new Vector2(spawnPosition);
        this.originMonster = monster;
    }


    public void update(float delta) {
        if(!initialized) {
            createBody();
            createLight();
            initialized = true;
        }

        stateTime += delta;
        drawFrame();
        adjustLight();

    }

    public abstract void drawFrame();

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

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
    public abstract void createLight();

    public abstract void dispose();

}
