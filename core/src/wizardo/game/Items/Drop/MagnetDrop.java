package wizardo.game.Items.Drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Ring.Epic_NagelRing;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;

import static wizardo.game.Resources.EffectAnims.GearFlareAnims.blue_flare;
import static wizardo.game.Wizardo.player;

public class MagnetDrop extends Drop {

    public MagnetDrop(Vector2 spawnPosition) {
        this.position = new Vector2(spawnPosition);
        sprite = new Sprite(new Texture("Items/Drops/Magnet.png"));
        flareAnim = blue_flare;
        flareScale = 0.35f;
        pickupAnim = GearFlareAnims.gear_pop;
    }

    @Override
    public void setup() {

    }

    @Override
    public void pickup() {
        pickedUp = true;
        stateTime = 0;
        screen.dropManager.attractGold();
    }
}
