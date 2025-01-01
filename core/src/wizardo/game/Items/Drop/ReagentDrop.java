package wizardo.game.Items.Drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;

import static wizardo.game.Resources.EffectAnims.GearFlareAnims.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class ReagentDrop extends Drop {

    boolean triple;
    Animation<Sprite> anim;

    /**
     * @param spawnPosition
     * @param chance from 0 to 1, chance to be better reagent
     */
    public ReagentDrop(Vector2 spawnPosition, float chance) {
        this.spawnPosition = new Vector2(spawnPosition);
        displayScale = 0.25f;
        lightRadius = 25f;
        sprite = new Sprite(new Texture("Items/Drops/Potion.png"));
        triple = Math.random() > 1f - chance/100f;
    }

    @Override
    public void setup() {
        if(triple) {
            anim = DropAnims.tripleGem_anim;
            flareAnim = purple_flare;
            flareScale = 0.7f;
            red = 0.6f;
            blue = 0.9f;
            green = 0.25f;
        } else {
            anim = DropAnims.dualGem_anim;
            flareAnim = white_flare;
            flareScale = 0.45f;
            red = 1;
            green = 0.92f;
        }
        pickupAnim = GearFlareAnims.gear_pop;
    }

    @Override
    public void pickup() {
        pickedUp = true;
        stateTime = 0;

        if(triple) {
            player.inventory.triple_reagents ++;
        } else {
            player.inventory.dual_reagents ++;
        }
    }

    public void drawFrame() {
        if(!pickedUp) {
            if (flareAnim != null) {
                Sprite frame2 = screen.getSprite();
                frame2.set(flareAnim.getKeyFrame(stateTime, true));
                frame2.setScale(0.8f * flareScale);
                frame2.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
                screen.addSortedSprite(frame2);
                screen.centerSort(frame2, body.getPosition().y * PPM + 5);

            }

            Sprite frame = screen.getSprite();
            frame.set(anim.getKeyFrame(stateTime, true));
            frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
            frame.flip(flipX, false);
            frame.setScale(displayScale);
            screen.addSortedSprite(frame);
            screen.centerSort(frame, body.getPosition().y * PPM);
        } else {
            Sprite frame = screen.getSprite();
            frame.set(pickupAnim.getKeyFrame(stateTime, false));
            frame.rotate(flareRotation);
            frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
            screen.addSortedSprite(frame);
        }
    }
}
