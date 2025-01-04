package wizardo.game.Spells.Unique;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.EffectAnims.AuraAnims;
import wizardo.game.Spells.Spell;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class FlameAura extends Spell {

    int[] frameCounters;
    float[] stateTimes;
    boolean[] flipXs;
    boolean[] flipYs;
    int[] rotations;

    int i = 10;
    int interval = 6;
    int duration = 30;
    float alpha = 0.35f;

    Animation<Sprite> anim2;
    int damageFrameCounter = 0;

    // DST OF EFFECT = 5.8

    public FlameAura() {
        anim = AuraAnims.flame_aura;
        anim2 = AuraAnims.flame_aura2;
        main_element = FIRE;

        frameCounters = new int[i];
        stateTimes = new float[i];
        flipXs = new boolean[i];
        flipYs = new boolean[i];
        rotations = new int[i];

        setupArrays();
    }

    public void setupArrays() {
        for (int j = 0; j < i; j++) {
            frameCounters[j] = j * interval;
            rotations[j] = MathUtils.random(360);
        }
    }

    public void cycleArrays(float delta) {
        for (int j = 0; j < i; j++) {
            frameCounters[j] = frameCounters[j] + 1;
            stateTimes[j] = stateTimes[j] + delta;

            if(frameCounters[j] == duration) {
                frameCounters[j] = 0;
                stateTimes[j] = 0;
                flipXs[j] = MathUtils.randomBoolean();
                flipYs[j] = MathUtils.randomBoolean();
                rotations[j] = MathUtils.random(360);
            }

        }
    }
    public void drawFrames() {
        for (int j = 0; j < i; j++) {
            Sprite frame = screen.getSprite();
            if(j % 2 == 0) {
                frame.set(anim.getKeyFrame(stateTimes[j],false));
            } else {
                frame.set(anim2.getKeyFrame(stateTimes[j],false));
            }
            frame.setRotation(rotations[j]);
            frame.flip(flipXs[j], flipYs[j]);
            frame.setCenter(player.pawn.getBodyX() * PPM, player.pawn.getBodyY() * PPM);
            frame.setAlpha(alpha);
            screen.addOverSprite(frame);
        }
    }

    public void createLight() {
        RoundLight light = new RoundLight(screen);
        light.setLight(1, 0.3f, 0, 0.75f, 300, player.pawn.getPosition());
        light.dimKill(0.02f);
        screen.lightManager.addLight(light);
    }


    @Override
    public void update(float delta) {
        if(delta > 0) {
            cycleArrays(delta);
        }
        drawFrames();
        areaDmg(delta);
    }


    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    public void dealDmg(Monster monster) {
        float dmg = getDmg() / 12f;
        getScaledDmg(dmg);
        dmg = applyGearModifiers(monster, dmg);
        monster.hp -= dmg;
    }

    @Override
    public int getDmg() {
        int dmg = 20;
        dmg += 4 * player.spellbook.flamejet_lvl;
        dmg += 4 * player.spellbook.fireball_lvl;
        dmg += 4 * player.spellbook.overheat_lvl;
        return dmg;
    }

    public void areaDmg(float delta) {
        if (delta > 0) {
            damageFrameCounter++;
            if (damageFrameCounter == 5) {
                damageFrameCounter = 0;

                // Define the query area bounds
                float radius = 5.8f; // Your search radius
                Vector2 center = player.pawn.body.getPosition();
                float lowerX = center.x - radius;
                float lowerY = center.y - radius;
                float upperX = center.x + radius;
                float upperY = center.y + radius;

                // Query for fixtures in the area
                world.QueryAABB(fixture -> {
                    Object userData = fixture.getBody().getUserData();
                    if (userData instanceof Monster) {
                        Monster monster = (Monster) userData;
                        if (monster.body.getPosition().dst(center) < radius) {
                            dealDmg(monster);
                            monster.hp -= getDmg() / 12f;
                        }
                    }
                    return true;
                }, lowerX, lowerY, upperX, upperY);
            }
        }
    }
}
