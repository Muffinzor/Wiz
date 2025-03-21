package wizardo.game.Items.Equipment.Ring;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Unique.DukeLightning.DukeLightningHit;

import static wizardo.game.Wizardo.player;

public class Legendary_DukeRing extends Ring {

    public Legendary_DukeRing() {
        name = "The Duke's Signet";
        sprite = new Sprite(new Texture("Items/Rings/DukeRing.png"));
        spriteOver = new Sprite(new Texture("Items/Rings/DukeRing_Over.png"));
        displayScale = 0.75f;

        title = "Legendary Ring";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.MAXSHIELD);
        quantity_gearStats.add(35);
        gearStats.add(ItemUtils.GearStat.WALKSPEED);
        quantity_gearStats.add(10);

    }

    public String getDescription() {

        return String.format("""
            When you take damage from close-by
            monsters, kill the attacker""");
    }

    public String getFlavorText() {
        return "\" The Sleeper must awaken.\"";
    }

    public void castDeathLightning(Monster monster) {
        float dst = monster.body.getPosition().dst(player.pawn.getPosition());
        if(dst < 5) {
            DukeLightningHit chain = new DukeLightningHit(monster);
            player.screen.spellManager.add(chain);
        }
    }
}
