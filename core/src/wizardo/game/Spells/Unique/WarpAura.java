package wizardo.game.Spells.Unique;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Resources.EffectAnims.AuraAnims;
import wizardo.game.Spells.Spell;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class WarpAura extends Spell {

    // DST OF EFFECT = 5.625

    ArrayList<Float> alphas;
    ArrayList<Integer> rotations;
    ArrayList<Float> stateTimes;

    public WarpAura() {

        alphas = new ArrayList<>();
        rotations = new ArrayList<>();
        stateTimes = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            stateTimes.add(i * 0.6f);
            alphas.add(Math.min(0.2f,i * 0.25f));
            rotations.add(MathUtils.random(360));
        }

        anim = AuraAnims.warp_aura;

    }

    public void handleCollision(MonsterSpell projectile) {
        if(projectile.isProjectile) {
            Vector2 velocity = projectile.body.getLinearVelocity();
            velocity.scl(0.5f);
            projectile.body.setLinearVelocity(velocity);
            projectile.speed = projectile.speed/2f;
        }
    }

    public void exitCollision(MonsterSpell projectile) {
        if(projectile.isProjectile) {
            Vector2 velocity = projectile.body.getLinearVelocity();
            velocity.scl(2);
            projectile.body.setLinearVelocity(velocity);
            projectile.speed = projectile.speed * 2;
        }
    }


    @Override
    public void update(float delta) {

        if(!initialized) {
            createBody();
            initialized = true;
        }

        for (int i = 0; i < stateTimes.size(); i++) {
            float time = stateTimes.get(i);
            time += delta;
            stateTimes.set(i, time);
            adjustAlpha(i);
        }

        for (int i = 0; i < stateTimes.size(); i++) {
            drawFrame(i);
        }

        loopValues();

        body.setTransform(player.pawn.getPosition().x, player.pawn.getPosition().y, 0);

    }

    public void createBody() {
        body = BodyFactory.spellExplosionBody(player.pawn.getPosition(), 200);
        body.setUserData(this);
    }

    public void adjustAlpha(int index) {
        float alpha = alphas.get(index);
        if(stateTimes.get(index) < anim.getAnimationDuration()/2) {
            alpha += 0.005f;
            if(alpha > 0.2f) alpha = 0.2f;
            alphas.set(index, alpha);
        } else  {
            alpha -= 0.005f;
            if(alpha < 0) alpha = 0;
            alphas.set(index, alpha);
        }
    }

    public void loopValues() {
        for (int i = 0; i < stateTimes.size(); i++) {
            if(stateTimes.get(i) > anim.getAnimationDuration()) {
                rotations.set(i, MathUtils.random(360));
                stateTimes.set(i, 0f);
                alphas.set(i, 0f);
            }
        }
    }

    public void drawFrame(int i) {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTimes.get(i), true));
        frame.setRotation(rotations.get(i));
        frame.setAlpha(alphas.get(i));

        frame.setCenter(player.pawn.getBodyX() * PPM, player.pawn.getBodyY() * PPM);

        screen.addOverSprite(frame);
    }

    @Override
    public void dispose() {
        world.destroyBody(body);
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
