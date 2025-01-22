package wizardo.game.Spells.Unique;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Items.Equipment.Book.Rare_FireballBook;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.FireballAnims;
import wizardo.game.Resources.SpellAnims.MarkAnims;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Display.DisplayUtils.getSprite;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class MarkDetonation extends Spell {

    Body body;
    RoundLight light;

    int rotation;
    boolean flipX;
    boolean flipY;

    float explosionRadius = 50;

    public MarkDetonation(Vector2 targetPosition) {
        this.targetPosition = targetPosition;

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

        anim = MarkAnims.getDetonateAnim();
        red = 0.85f;

    }

    public void update(float delta) {
        if(!initialized) {
            createBody();
            createLight();
            initialized = true;
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.1f) {
            body.setActive(false);
            light.dimKill(0.02f);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void drawFrame() {
        Sprite frame = getSprite(screen);
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);

        frame.flip(flipX, flipY);
        frame.setRotation(rotation);

        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, explosionRadius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 100, body.getPosition());
        screen.lightManager.addLight(light);
    }


    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public void dealDmg(Monster monster) {
        monster.hp -= getDmg() * MathUtils.random(0.9f,1.1f);
    }

    @Override
    public int getDmg() {
        int dmg = 45 + 5 * player.level;
        dmg = (int) (dmg * (1 + (player.spellbook.allBonusDmg/100f)));
        return dmg;
    }
}
