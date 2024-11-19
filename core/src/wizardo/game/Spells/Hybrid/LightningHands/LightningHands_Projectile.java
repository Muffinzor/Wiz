package wizardo.game.Spells.Hybrid.LightningHands;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.Monster;
import wizardo.game.Resources.Skins;
import wizardo.game.Resources.SpellAnims.LightningHandsAnims;
import wizardo.game.Spells.Hybrid.ForkedLightning.ForkedLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class LightningHands_Projectile extends LightningHands_Spell {

    Vector2 direction;
    Body body;
    float rotation;

    boolean flipX;
    boolean flipY;
    float animTime;
    int frameCounter = 0;


    public LightningHands_Projectile(Vector2 direction) {
        this.direction = direction;
        spawnPosition = new Vector2(player.pawn.getPosition());

        animTime = (float) Math.random();
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

        anim = LightningHandsAnims.lightning_branch_anim;
        textColor = Skins.light_orange;
        main_element = SpellUtils.Spell_Element.LIGHTNING;
        bonus_element = FIRE;
        anim_element = FIRE;

        red = 0.75f;
        green = 0.15f;
        lightAlpha = 0.3f;



    }

    public void update(float delta) {
        if(!initialized) {
            createBody();
            initialized = true;
        }

        createLights(delta);
        animTime += delta;
        stateTime += delta;
        drawFrame();


        if(!body.getLinearVelocity().isZero() && isTooFar()) {
            body.setLinearVelocity(0, 0);
        }

        if(stateTime > 0.4f) {
            world.destroyBody(body);
            screen.spellManager.toRemove(this);
        }
    }

    public void handleCollision(Monster monster) {
        dealDmg(monster);
        chainlightning(monster);
        chargedbolts(monster);
    }

    public void handleCollision(Fixture fix) {
        body.setLinearVelocity(0,0);
    }

    public void drawFrame() {
        Vector2 originPosition = new Vector2(player.pawn.getPosition());
        Vector2 dir = body.getPosition().sub(originPosition);
        rotation = dir.angleDeg();

        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(animTime, true));
        frame.setOrigin(0, frame.getHeight()/2f);
        frame.setRotation(rotation);
        frame.setPosition(originPosition.x * PPM, originPosition.y * PPM - frame.getHeight()/2);

        float dst = originPosition.dst(body.getPosition()) + 1.5f;
        float totalLength = frame.getHeight();
        float Yscale = dst*PPM/(totalLength/1.25f);
        float Xscale = dst*PPM/totalLength;
        frame.setScale(Xscale, Yscale);
        frame.flip(flipX, flipY);

        screen.addSortedSprite(frame);
    }

    public void createBody() {
        body = BodyFactory.spellProjectileCircleBody(spawnPosition, 50, true);
        body.setUserData(this);

        if(direction.isZero()) {
            direction.set(1,0);
        } else {
            direction.nor();
        }

        rotation = direction.angleDeg();
        Vector2 velocity = direction.scl(150);
        body.setLinearVelocity(velocity);


    }

    public void createLights(float delta) {
        frameCounter ++;
        if(frameCounter >= 7 && delta > 0 && stateTime > 0) {
            frameCounter = 0;
            Vector2 currentPosition = new Vector2(body.getPosition());
            float distance = player.pawn.getPosition().dst(currentPosition);
            int numLights = (int) (distance * 1.5f);
            if(numLights < 1) {
                numLights = 1;
            }
            System.out.println(numLights);

            for (int i = 0; i <= numLights; i++) {
                float t = i / (float) numLights; // Interpolation factor (0 to 1)
                Vector2 lightPosition = new Vector2(
                        MathUtils.lerp(player.pawn.getPosition().x, currentPosition.x, t),
                        MathUtils.lerp(player.pawn.getPosition().y, currentPosition.y, t)
                );

                // Create the light (you can adjust the radius and color as needed)
                RoundLight light = screen.lightManager.pool.getLight();
                light.setLight(red, green, blue, lightAlpha + 0.01f * i, lightRadius, lightPosition);
                light.dimKill(0.02f);
                screen.lightManager.addLight(light);
            }
        }
    }

    public boolean isTooFar() {
        return body.getPosition().dst(spawnPosition) > 9.5f;
    }

    public void setup() {

    }

    public void chainlightning(Monster monster) {
        if(chainlightning) {
            float procRate = 0.933f - 0.033f * player.spellbook.chainlightning_lvl;
            if(Math.random() >= procRate) {
                ForkedLightning_Spell lightning = new ForkedLightning_Spell();
                lightning.setElements(this);
                lightning.originBody = monster.body;
                screen.spellManager.toAdd(lightning);
            }
        }
    }

    public void chargedbolts(Monster monster) {
        if(chargedbolts) {
            float procRate = 0.95f - 0.05f * player.spellbook.chargedbolt_lvl;
            if(Math.random() >= procRate) {
                for (int i = 0; i < 3; i++) {
                    ChargedBolts_Spell bolt = new ChargedBolts_Spell();
                    bolt.setElements(this);
                    bolt.spawnPosition = new Vector2(monster.body.getPosition());
                    bolt.targetPosition = SpellUtils.getRandomVectorInRadius(monster.body.getPosition(), 2);
                    bolt.flamejet = true;
                    screen.spellManager.toAdd(bolt);
                }
            }
        }
    }

}
