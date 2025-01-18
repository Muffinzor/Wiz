package wizardo.game.Items.Equipment.Robes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.Unique.FlameAura;
import wizardo.game.Spells.Unique.FreezingMist.FreezingMist_Pulse;
import wizardo.game.Spells.Unique.VogonAura;

import static wizardo.game.Wizardo.player;

public class Legendary_FireRobes extends Robes {

    FlameAura aura;

    public Legendary_FireRobes() {
        sprite = new Sprite(new Texture("Items/Robes/FireRobes.png"));
        spriteOver = new Sprite(new Texture("Items/Robes/FireRobes_Over.png"));
        displayScale = 0.9f;

        name = "The Phoenix Pelt";
        title = "Legendary Robes";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.FIREDMG);
        quantity_gearStats.add(MathUtils.random(15, 20));

    }

    public String getDescription() {
        return String.format("""
            Monsters near you continuously take
            fire damage that scales with all your
            fire masteries""");
    }

    public String getFlavorText() {
        return "Made from the Blood Prince's\ndearest pet";
    }

    @Override
    public void equip() {
        super.equip();
        aura = new FlameAura();
        player.screen.spellManager.add(aura);
        player.pawn.light.setLight(0.9f,0.3f,0,0.8f,300, player.pawn.body.getPosition());
    }

    public void unequip() {
        super.unequip();
        player.screen.spellManager.remove(aura);
        player.pawn.light.setLight(0,0,0,0.8f,120, player.pawn.body.getPosition());
    }

}
