package wizardo.game.Items.Equipment.Robes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Unique.FlameAura;
import wizardo.game.Spells.Unique.WarpAura;

import static wizardo.game.Wizardo.player;

public class Legendary_ArcaneRobes extends Robes {

    WarpAura aura;

    public Legendary_ArcaneRobes() {
        sprite = new Sprite(new Texture("Items/Robes/WarpedRobes.png"));
        spriteOver = new Sprite(new Texture("Items/Robes/WarpedRobes_Over.png"));
        displayScale = 0.65f;

        name = "Relativistic Cloak";
        title = "Legendary Robes";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.ARCANEDMG);
        quantity_gearStats.add(MathUtils.random(15, 20));

    }

    public String getDescription() {
        return String.format("""
            Slows down enemy projectiles that
            travel near you""");
    }

    public String getFlavorText() {
        return "Time is not experienced the same\nfor all things";
    }

    @Override
    public void equip() {
        super.equip();
        aura = new WarpAura();
        player.screen.spellManager.add(aura);
        player.pawn.light.setLight(1f,0f,1,0.8f,200, player.pawn.body.getPosition());
    }

    public void unequip() {
        super.unequip();
        player.screen.spellManager.remove(aura);
        player.pawn.light.setLight(0,0,0,0.8f,120, player.pawn.body.getPosition());
    }


}
