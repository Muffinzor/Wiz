package wizardo.game.Spells.Unique;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.TeleportAnims;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class TeleportMonster extends Spell {

    public Monster monster;

    public Vector2 initialPosition;
    public Vector2 landingPosition;

    boolean successfulTeleport;

    boolean flipX;
    boolean flipY;
    int rotation;

    public TeleportMonster(Monster monster) {
        this.monster = monster;
        anim = TeleportAnims.teleport_anim;

        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        rotation = MathUtils.random(360);

        if(monster.hp > 0) {
            monster.teleporting = true;
        }
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            setup();
            moveMonsterBody(monster);
            createLights();
        }

        if(successfulTeleport) {
            drawFrame(initialPosition);
            drawFrame(landingPosition);
        }

        stateTime += delta;


        if(stateTime > anim.getAnimationDuration()) {
            screen.spellManager.remove(this);
            if(monster.hp > 0) {
                monster.teleporting = false;
            }
        }
    }

    public void drawFrame(Vector2 position) {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.rotate(rotation);
        frame.flip(flipX, flipY);
        frame.setCenter(position.x * PPM, position.y * PPM);
        screen.addSortedSprite(frame);

    }

    public void setup() {
        initialPosition = new Vector2(monster.body.getPosition());

        Vector2 direction = new Vector2(monster.body.getPosition().sub(player.pawn.body.getPosition()));
        direction.nor().rotateDeg(MathUtils.random(-30,30)).scl(MathUtils.random(8, 10));
        Vector2 targetArea = monster.body.getPosition().add(direction);
        landingPosition = SpellUtils.getClearRandomPosition(targetArea, 3);
    }

    public void createLights() {
        if(successfulTeleport) {
            RoundLight light1 = screen.lightManager.pool.getLight();
            light1.setLight(0.9f, 0, 0.75f, 1, 45, initialPosition);
            light1.dimKill(0.02f);
            screen.lightManager.addLight(light1);

            RoundLight light2 = screen.lightManager.pool.getLight();
            light2.setLight(0.9f, 0, 0.75f, 1, 45, landingPosition);
            light2.dimKill(0.02f);
            screen.lightManager.addLight(light2);
        }
    }

    public void moveMonsterBody(Monster monster) {
        if(monster.hp > 0) {
            monster.body.setTransform(landingPosition, 0);
            successfulTeleport = true;
        } else {
            screen.spellManager.remove(this);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public int getDmg() {
        return 0;
    }
}
