package wizardo.game.Items.Drop;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;

import static wizardo.game.Wizardo.player;

public class EquipmentDrop extends Drop {

    public Equipment piece;

    public EquipmentDrop(Vector2 spawnPosition, Equipment piece) {
        this.piece = piece;
        this.position = new Vector2(spawnPosition);
        stateTime = (float) Math.random();
    }

    @Override
    public void setup() {
        sprite = piece.sprite;
        displayScale = 0.4f * piece.displayScale;
        flareRotation = MathUtils.random(360);
        pickupAnim = GearFlareAnims.gear_pop;

        switch(piece.quality) {
            case LEGENDARY -> {
                flareAnim = GearFlareAnims.red_flare;
                red = 0.8f;
            }
            case EPIC -> {
                flareAnim = GearFlareAnims.purple_flare;
                red = 1f;
                green = 0.2f;
                blue = 0.92f;
                flareScale = 0.8f;
            }
            case RARE -> {
                flareAnim = GearFlareAnims.blue_flare;
                blue = 0.6f;
                flareScale = 0.65f;
            }
            case NORMAL -> {
                flareAnim = GearFlareAnims.green_flare;
                green = 0.6f;
                flareScale = 0.5f;
            }
        }
    }

    @Override
    public void pickup() {
        if(player.inventory.hasSpace()) {
            piece.pickup();
            pickedUp = true;
            stateTime = 0;
        } else {
            goToPlayer = false;
            frameCounter = 0;
        }
    }

}
