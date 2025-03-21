package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Unique.FreezingBlast.FreezingBlast_Explosion;

public class Legendary_FrostStaff extends Staff {

    public Legendary_FrostStaff() {
        sprite = new Sprite(new Texture("Items/Staff/Heatdeath.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Heatdeath_Over.png"));
        displayRotation = 25;

        name = "Heat Death";
        title = "Legendary Staff";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.MASTERY_FROST);
        quantity_gearStats.add(1);
        gearStats.add(ItemUtils.GearStat.FROSTDMG);
        quantity_gearStats.add(MathUtils.random(15,20));

    }

    public String getDescription() {
        return String.format("""
            Your frost damage deals 150%% damage
            to frozen enemies and bosses
            
            When you freeze an enemy, there is a
            chance it releases a freezing mist""");
    }

    public String getFlavorText() {
        return "\" True Equilibrium\"";
    }

    public void castNova(Monster monster) {
        if(Math.random() >= 0.8f) {
            FreezingBlast_Explosion nova = new FreezingBlast_Explosion(monster.body.getPosition());
            monster.screen.spellManager.add(nova);
        }

    }

}
