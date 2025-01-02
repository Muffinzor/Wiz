package wizardo.game.Spells.Unique;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.EffectAnims.AuraAnims;
import wizardo.game.Spells.Spell;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;
import static wizardo.game.Wizardo.world;

public class FlameAura extends Spell {

    int rotation;
    boolean flipX;
    boolean flipY;

    float stateTime2;
    float rotation2;
    boolean flipX2;
    boolean flipY2;

    Animation<Sprite> anim2;

    int damageFrameCounter = 0;
    int frameCounter1 = 0;
    int frameCounter2 = 15;

    // DST OF EFFECT = 5.625

    public FlameAura() {
        anim = AuraAnims.flame_aura;
        anim2 = AuraAnims.flame_aura2;
        rotation = MathUtils.random(360);
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();

        rotation2 = MathUtils.random(360);
        flipX2 = MathUtils.randomBoolean();
        flipY2 = MathUtils.randomBoolean();
        stateTime2 = anim2.getAnimationDuration()/2f;

        main_element = FIRE;

    }


    @Override
    public void update(float delta) {
        stateTime += delta;
        stateTime2 += delta;
        if(delta > 0) {
            frameCounter1++;
            frameCounter2++;
        }
        drawFrame();
        drawFrame2();
        areaDmg(delta);
    }

    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, false));
        frame.setCenter(player.pawn.getBodyX() * PPM, player.pawn.getBodyY() * PPM);
        frame.flip(flipX, flipY);
        frame.rotate(rotation);

        if(frameCounter1 == 30) {
            stateTime = 0;
            rotation = MathUtils.random(360);
            flipX = MathUtils.randomBoolean();
            flipY = MathUtils.randomBoolean();
            frameCounter1 = 0;
        }

        frame.setAlpha(0.9f);
        screen.addOverSprite(frame);
    }
    public void drawFrame2() {
        Sprite frame = screen.getSprite();
        frame.set(anim2.getKeyFrame(stateTime2, false));
        frame.setCenter(player.pawn.getBodyX() * PPM, player.pawn.getBodyY() * PPM);
        frame.flip(flipX2, flipY2);
        frame.rotate(rotation2);

        if(frameCounter2 == 30) {
            stateTime2 = 0;
            rotation2 = MathUtils.random(360);
            flipX2 = MathUtils.randomBoolean();
            flipY2 = MathUtils.randomBoolean();
            frameCounter2 = 0;
        }

        frame.setAlpha(0.9f);
        screen.addOverSprite(frame);
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
        int dmg = 60;
        dmg += 10 * player.spellbook.flamejet_lvl;
        dmg += 10 * player.spellbook.fireball_lvl;
        dmg += 10 * player.spellbook.overheat_lvl;
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
