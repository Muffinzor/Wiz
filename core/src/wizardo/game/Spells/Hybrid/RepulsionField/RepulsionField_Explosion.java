package wizardo.game.Spells.Hybrid.RepulsionField;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.SpellAnims.RepulsionFieldAnims;
import wizardo.game.Spells.Fire.Fireball.Fireball_Projectile;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class RepulsionField_Explosion extends RepulsionField_Spell {

    public Body body;
    public RoundLight light;
    public Animation<Sprite> anim;

    boolean flipX;

    float alpha = 1;

    float radius = 200;

    float scale = 1;

    public RepulsionField_Explosion() {

        flipX = MathUtils.randomBoolean();

        targetPosition = new Vector2(player.pawn.getPosition());

        anim = RepulsionFieldAnims.repulsion_field;

        anim_element = SpellUtils.Spell_Element.ARCANE;
        main_element = SpellUtils.Spell_Element.ARCANE;
        bonus_element = FIRE;

    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            setup();
            createBody();
            createLight();
            initialized = true;
        }

        drawFrame();
        stateTime += delta;

        if(body.isActive() && stateTime >= 0.1f) {
            body.setActive(false);
        }

        if(stateTime >= anim.getAnimationDuration()) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }

    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        Vector2 direction = monster.body.getPosition().sub(body.getPosition());

            float strength = 5;
            monster.movementManager.applyPush(direction, strength, 0.4f, 0.89f);
    }

    public void handleCollision(MonsterSpell monsterSpell) {
        monsterSpell.sentBack = true;

        Vector2 contact = monsterSpell.body.getPosition();
        Vector2 origin = body.getPosition();
        Vector2 direction = new Vector2(contact).sub(origin);
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction.set(1,0);
        }

        if(fireball) {
            Fireball_Projectile fireball = new Fireball_Projectile(contact, contact.cpy().add(direction));
            fireball.setElements(this);
            fireball.speed = fireball.speed * 1.25f;
            screen.spellManager.toAdd(fireball);
        } else {
            RepulsionField_Projectile proj = new RepulsionField_Projectile(contact, contact.cpy().add(direction));
            if(arcaneMissile) {
                proj.missile = true;
                proj.targetMonster = monsterSpell.originMonster;
            }

            proj.setElements(this);
            screen.spellManager.toAdd(proj);
        }
    }

    public void setup() {
        radius = 170 + 10 * player.spellbook.overheat_lvl;
        scale = 0.9f + (0.05f * player.spellbook.overheat_lvl);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(targetPosition.x * PPM, targetPosition.y * PPM);
        frame.flip(flipX, false);
        frame.setAlpha(0.9f);
        frame.setScale(1.2f * scale);

        screen.addSortedSprite(frame);
        screen.centerSort(frame, targetPosition.y * PPM - 20);
    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(targetPosition, radius);
        body.setUserData(this);
    }

    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(0.6f,green,0.9f,lightAlpha,270, targetPosition);
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
        return 50 + 15 * player.spellbook.rift_lvl;
    }
}
