package wizardo.game.Items.Drop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Display.Text.BottomText;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;

import static wizardo.game.Resources.EffectAnims.GearFlareAnims.white_flare;
import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Wizardo.player;

public class GoldDrop extends Drop{
    int tier;
    int goldAmount;

    public GoldDrop(Vector2 spawnPosition, int tierMin, int tierMax) {
        this.position = new Vector2(spawnPosition);
        tier = MathUtils.random(tierMin, tierMax);

        flareAnim = white_flare;
        flareScale = 0.35f;
        pickupAnim = GearFlareAnims.gold_pop;
        lightRadius = 20;
        red = 1f;
        green = 0.77f;
    }

    @Override
    public void setup() {
        switch(tier) {
            case 1 -> {
                sprite = new Sprite(new Texture("Items/Drops/GoldLoot/GoldPouch.png"));
                goldAmount = MathUtils.random(7,15);
            }
            case 2 -> {
                sprite = new Sprite(new Texture("Items/Drops/GoldLoot/GoldCoin.png"));
                goldAmount = MathUtils.random(10,20);
            }
            case 3 -> {
                sprite = new Sprite(new Texture("Items/Drops/GoldLoot/GoldVase.png"));
                goldAmount = MathUtils.random(75,100);
            }
            case 4 -> {
                sprite = new Sprite(new Texture("Items/Drops/GoldLoot/GemsGoldCup.png"));
                goldAmount = MathUtils.random(85,125);
            }
            case 5 -> {
                sprite = new Sprite(new Texture("Items/Drops/GoldLoot/GoldDogStatue.png"));
                goldAmount = MathUtils.random(120,200);
            }
            case 6 -> {
                sprite = new Sprite(new Texture("Items/Drops/GoldLoot/GoldEagleStatue.png"));
                goldAmount = MathUtils.random(175,300);
            }
            case 7 -> {
                sprite = new Sprite(new Texture("Items/Drops/GoldLoot/GoldGoatStatue.png"));
                goldAmount = MathUtils.random(375,500);
            }
        }
    }

    @Override
    public void pickup() {
        pickedUp = true;
        stateTime = 0;
        player.inventory.gold += goldAmount;
        screen.battleUI.updateGoldPanel();

        BottomText text = new BottomText();
        text.setAll("+" + goldAmount, position, inventorySkin.getFont("Gear_Text"), Color.YELLOW);
        screen.displayManager.textManager.addGoldText(text);
    }


}
