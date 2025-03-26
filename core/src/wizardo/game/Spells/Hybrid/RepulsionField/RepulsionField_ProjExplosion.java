package wizardo.game.Spells.Hybrid.RepulsionField;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Toon;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class RepulsionField_ProjExplosion extends Spell {

    Body body;
    RoundLight light;

    float rotation;
    boolean flipX;
    boolean flipY;

    public boolean arcaneMissile;

    public RepulsionField_ProjExplosion(Vector2 targetPosition) {

        this.targetPosition = new Vector2(targetPosition);
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            anim = ExplosionAnims_Toon.getExplosionAnim(SpellUtils.Spell_Element.ARCANE);
            initialized = true;
            createBody();
            createLight();
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime > 0.2f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        frame.setRotation(rotation);
        frame.flip(flipX, flipY);

        screen.centerSort(frame, body.getPosition().y * PPM - 10);
        screen.addSortedSprite(frame);
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
        light.setLight(0.6f, 0, 0.9f, 1, 85, body.getPosition());
        light.dimKill(0.02f);
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
    public int getDmg() {
        int dmg  = 80;
        dmg += 20 * player.spellbook.rift_lvl;
        dmg += 20 * player.spellbook.overheat_lvl;
        if(arcaneMissile) {
            dmg += 20 * player.spellbook.arcanemissile_lvl;
        }
        dmg = (int) (dmg * (1 + player.spellbook.gravityBonusDmg/100f));
        return dmg;
    }
}
