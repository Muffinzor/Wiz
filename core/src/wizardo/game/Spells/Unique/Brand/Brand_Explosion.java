package wizardo.game.Spells.Unique.Brand;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Toon;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Brand_Explosion extends Spell {

    RoundLight light;

    int rotation;
    boolean flipX;
    boolean flipY;

    public Brand_Explosion(Vector2 position) {
        this.targetPosition = SpellUtils.getRandomVectorInRadius(position, 1.2f);
        anim_element = SpellUtils.Spell_Element.FIRE;

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

        red = 0.8f;
        green = 0.2f;

        anim = ExplosionAnims_Toon.getExplosionAnim(SpellUtils.Spell_Element.FIRE);

    }
    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2f) {
            body.setActive(false);
            light.dimKill(0.02f);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            screen.spellManager.remove(this);
            world.destroyBody(body);
        }

    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 60);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, blue, lightAlpha, 100, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);
        screen.centerSort(frame, body.getPosition().y * PPM - 15);
        screen.addSortedSprite(frame);
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
        int dmg = 16;
        dmg += 8 * player.spellbook.fireball_lvl;
        dmg += 8 * player.spellbook.overheat_lvl;
        dmg += 8 * player.spellbook.flamejet_lvl;
        return dmg;
    }
}
