package wizardo.game.Monsters.MonsterActions.AttackSwing;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterArchetypes.MonsterMelee;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class AttackSwing extends MonsterSpell {

    Sprite weaponSprite;
    boolean flipY;

    float swingDuration = 0.2f;
    float swingProgress; // 0 to 1

    float startAngle = 45;
    float endAngle = -45;
    float currentAngle; // interpolation from progress

    boolean hasHit;

    MonsterMelee monster;


    public AttackSwing(Monster monster) {
        super(monster);
        this.weaponSprite = monster.weaponSprite;
        this.monster = (MonsterMelee) originMonster;
        flipY = player.pawn.getPosition().x < monster.body.getPosition().x;
    }

    public void handleCollision(Body playerBody) {
        if(!hasHit) {
            Vector2 direction = player.pawn.getPosition().sub(originMonster.body.getPosition());
            float strength = 5;
            float duration = 0.1f;
            if(monster.heavy) {
                strength = 8;
                duration = 0.2f;
            }
            player.pawn.applyPush(direction, strength, duration, 0.9f);
            hasHit = true;
        }
    }

    @Override
    public void checkState(float delta) {
        swingProgress = Math.min(stateTime / swingDuration, 1f);
        currentAngle = Interpolation.smooth.apply(startAngle, endAngle, swingProgress);

        if (body.isActive()) {
            Vector2 weaponTip = calculateHitBoxPosition();
            body.setTransform(weaponTip, 0); // Keep the body rotation at 0
        }

        if(stateTime >= swingDuration + 0.1f) {
            screen.monsterSpellManager.toRemove(this);
            world.destroyBody(body);
        }
    }

    @Override
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(weaponSprite);
        frame.setOrigin(0, frame.getHeight()/2);
        frame.setPosition(originMonster.body.getPosition().x * PPM, originMonster.body.getPosition().y * PPM);
        frame.setRotation(currentAngle);
        frame.flip(false, flipY);
        screen.addSortedSprite(frame);
        screen.centerSort(frame, originMonster.body.getPosition().y * PPM - originMonster.height/2);
    }

    @Override
    public void initialize() {
        calculateAngleToPlayer();
        createBody();
    }

    public void createBody() {
        body = BodyFactory.monsterProjectileBody(calculateHitBoxPosition(), monster.hitboxRadius);
        body.setUserData(this);
    }

    private Vector2 calculateHitBoxPosition() {
        float angleRad = (float) Math.toRadians(currentAngle);
        Vector2 monsterPosition = originMonster.body.getPosition();
        float tipX = monsterPosition.x + (float) Math.cos(angleRad) * (weaponSprite.getWidth()/PPM * (1/2f));
        float tipY = monsterPosition.y + (float) Math.sin(angleRad) * (weaponSprite.getWidth()/PPM * (1/2f));
        return new Vector2(tipX, tipY);
    }

    public void calculateAngleToPlayer() {
        float monsterX = originMonster.body.getPosition().x;
        float monsterY = originMonster.body.getPosition().y;
        float playerX = player.pawn.getPosition().x;
        float playerY = player.pawn.getPosition().y;

        float angleToPlayer = (float) Math.toDegrees(Math.atan2(playerY - monsterY, playerX - monsterX));

        if(playerX < monsterX) {
            startAngle = angleToPlayer - 45;
            endAngle = angleToPlayer + 45;
        } else {
            startAngle = angleToPlayer + 45;
            endAngle = angleToPlayer - 45;
        }

        currentAngle = Interpolation.smooth.apply(startAngle, endAngle, swingProgress);
    }

    @Override
    public void dispose() {

    }
}
