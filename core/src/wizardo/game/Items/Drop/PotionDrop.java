package wizardo.game.Items.Drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Ring.Epic_NagelRing;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;

import static wizardo.game.Resources.EffectAnims.GearFlareAnims.white_flare;
import static wizardo.game.Wizardo.player;

public class PotionDrop extends Drop {

    public PotionDrop(Vector2 spawnPosition) {
        this.spawnPosition = new Vector2(spawnPosition);
        sprite = new Sprite(new Texture("Items/Drops/Potion.png"));
        flareAnim = white_flare;
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
        float regenAmount = MathUtils.random(10, 20) * (1 + (player.stats.luck/100f));
        if(player.inventory.equippedRing instanceof Epic_NagelRing) {
            regenAmount *= 2;
        }
        player.stats.shield += regenAmount;
        if(player.stats.shield > player.stats.maxShield) {
            player.stats.shield = player.stats.maxShield;
        }
    }
}
