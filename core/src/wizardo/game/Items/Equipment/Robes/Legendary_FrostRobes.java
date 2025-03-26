package wizardo.game.Items.Equipment.Robes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Unique.FreezingBlast.FreezingBlast_Explosion;
import wizardo.game.Spells.Unique.FreezingMist.FreezingMist_Pulse;

import static wizardo.game.Wizardo.player;

public class Legendary_FrostRobes extends Robes{

    float stateTime;

    public Legendary_FrostRobes() {
        sprite = new Sprite(new Texture("Items/Robes/FreezeRobes.png"));
        spriteOver = new Sprite(new Texture("Items/Robes/FreezeRobes_Over.png"));
        displayScale = 1.4f;

        name = "Boreas' Cloak";
        title = "Legendary Robes";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.FROSTDMG);
        quantity_gearStats.add(MathUtils.random(15, 20));

    }

    public String getDescription() {
        return String.format("""
            Periodically release a freezing mist
            
            Freezing monsters can also release a
            cloud of freezing mist""");
    }

    public String getFlavorText() {
        return "\" Hear my voice,\n I am the howling winds of the North.\"";
    }

    public void update(float delta) {
        stateTime += delta;
        if(stateTime >= 1.2f) {
            FreezingMist_Pulse gust = new FreezingMist_Pulse(player.pawn.getPosition());
            player.screen.spellManager.add(gust);
            stateTime = 0;
        }
    }

    public void castNova(Monster monster) {
        if(Math.random() >= 0.8f) {
            FreezingBlast_Explosion nova = new FreezingBlast_Explosion(monster.body.getPosition());
            monster.screen.spellManager.add(nova);
        }
    }
}
