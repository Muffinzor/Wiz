package wizardo.game.Spells.Hybrid.FrostNova;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.GameSettings.dmg_text_on;
import static wizardo.game.Resources.SpellAnims.FrostNovaAnims.frostnova_shard_sprite;
import static wizardo.game.Spells.SpellUtils.Spell_Element.FROST;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class FrostNova_ShardProjectile extends Spell {

    float rotation;
    RoundLight light;

    float scale;
    Monster originMonster;

    public FrostNova_ShardProjectile(Monster originMonster) {
        this.originMonster = originMonster;
        spawnPosition = new Vector2(originMonster.body.getPosition());
        targetPosition = SpellUtils.getRandomVectorInRadius(originMonster.body.getPosition(), 2);
        speed = MathUtils.random(5f, 7f);
        lightAlpha = 1;
        scale = MathUtils.random(1.6f, 2);
        anim_element = FROST;
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
            createLight();
        }
        stateTime += delta;
        drawFrame();
        if(delta > 0) {
            scale -= 0.04f;
        }
        if(scale <= 0.2f) {
            world.destroyBody(body);
            screen.spellManager.remove(this);
        } else {
            adjustLight();
        }
    }

    @Override
    public void handleCollision(Monster monster) {
        if(monster != originMonster) {
            dealDmg(monster);
        }
    }

    @Override
    public int getDmg() {
        float monster_part = 0.1f * originMonster.maxHP * Math.max(1, player.spellbook.overheat_lvl);
        return 30 + (int) monster_part;
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(frostnova_shard_sprite);
        frame.setRotation(rotation);
        frame.setScale(scale);
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        screen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
        screen.centerSort(frame, body.getPosition().y * PPM - 10);
    }

    public void createBody() {
        body = BodyFactory.spellProjectileCircleBody(spawnPosition, 5, true);
        body.setUserData(this);
        Vector2 direction = targetPosition.cpy().sub(spawnPosition);
        rotation = direction.angleDeg();
        if(direction.len() > 0) {
            direction.nor();
        } else {
            direction.set(1,0);
        }
        Vector2 velocity = new Vector2(direction.scl(speed));
        body.setLinearVelocity(velocity);
    }
    public void createLight() {
        light = screen.lightManager.pool.getLight();
        light.setLight(red, green, 0.8f, lightAlpha, 25, body.getPosition());
        screen.lightManager.addLight(light);
        light.dimKill(0.02f);
    }
    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().scl(PPM));
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }
}
