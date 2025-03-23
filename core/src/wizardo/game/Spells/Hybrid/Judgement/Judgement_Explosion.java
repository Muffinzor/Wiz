package wizardo.game.Spells.Hybrid.Judgement;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.ExplosionAnims_Energy;
import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class Judgement_Explosion extends Judgement_Spell {

    public boolean arcaneMissiles;

    Body body;
    RoundLight light;

    int rotation;
    boolean flipX;
    boolean flipY;

    public Judgement_Explosion() {

        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

    }

    public void update(float delta) {
        if(!initialized) {
            pickAnim();
            createBody();
            createLight();
            initialized = true;
            arcaneMissiles();
            flamejets();
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.2f) {
            body.setActive(false);
            light.dimKill(0.01f);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
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

    public void pickAnim() {
        anim = ExplosionAnims_Energy.getExplosionAnim(anim_element);
        switch(anim_element) {
            case FROST -> {
                red = 0.2f;
                blue = 0.9f;
            }
            case ARCANE -> {
                red = 0.75f;
                blue = 0.95f;
            }
            case LIGHTNING -> {
                red = 0.55f;
                green = 0.35f;
            }
        }
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, 75);
        body.setUserData(this);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,lightAlpha,170,targetPosition);
        screen.lightManager.addLight(light);
    }

    public void arcaneMissiles() {
        if(arcaneMissiles) {
            int quantity = 5 + player.spellbook.arcanemissile_lvl/3;
            for (int i = 0; i < quantity; i++) {
                ArcaneMissile_Spell missile = new ArcaneMissile_Spell();
                missile.setElements(this);
                missile.spawnPosition = new Vector2(body.getPosition());
                missile.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                screen.spellManager.add(missile);
            }
        }
    }

    public void flamejets() {
        if(false) {
            int quantity = 3 + player.spellbook.flamejet_lvl / 3;
            for (int i = 0; i < quantity; i++) {
                Flamejet_Spell flame = new Flamejet_Spell();
                flame.setElements(this);
                flame.spawnPosition = new Vector2(body.getPosition());
                flame.targetPosition = SpellUtils.getRandomVectorInRadius(body.getPosition(), 2);
                screen.spellManager.add(flame);
            }
        }
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg/2;
        dmg += 15 * player.spellbook.energybeam_lvl;
        if(anim_element == FIRE) {
            dmg += 15 * player.spellbook.overheat_lvl;
        }
        return dmg;
    }
}
