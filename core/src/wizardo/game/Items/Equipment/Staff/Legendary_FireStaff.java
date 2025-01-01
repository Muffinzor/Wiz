package wizardo.game.Items.Equipment.Staff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.Unique.Brand.Brand_Explosion;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public class Legendary_FireStaff extends Staff {

    public ArrayList<Monster> brandedMonsters;
    public ArrayList<Float> brandedMonstersTimers;

    public Legendary_FireStaff() {
        sprite = new Sprite(new Texture("Items/Staff/Sunstaff.png"));
        spriteOver = new Sprite(new Texture("Items/Staff/Sunstaff_over.png"));
        displayRotation = 25;

        brandedMonsters = new ArrayList<>();
        brandedMonstersTimers = new ArrayList<>();

        name = "Sol's Devotion";
        title = "Legendary Staff";
        quality = ItemUtils.EquipQuality.LEGENDARY;

        gearStats.add(ItemUtils.GearStat.MASTERY_FIRE);
        quantity_gearStats.add(2);
        gearStats.add(ItemUtils.GearStat.FIREDMG);
        quantity_gearStats.add(MathUtils.random(15,20));

    }

    public String getDescription() {
        return String.format("""
            Your fire damage has a chance to
            brand monsters
            
            Branded monsters will periodically
            explode until they die
            """);
    }

    public String getFlavorText() {
        return "\" Oh to be so grossly incandescent...\"";
    }

    public void castBrand(Monster monster, Spell spell) {
        float procRate;
        if(spell instanceof Flamejet_Spell) {
            procRate = 0.15f;
        } else {
            procRate = 0.25f;
        }
        if(Math.random() >= 1 - procRate) {
            if(!brandedMonsters.contains(monster)) {
                brandedMonsters.add(monster);
                brandedMonstersTimers.add(1f);
            }
        }
    }

    public void update(float delta) {
        for (int i = 0; i < brandedMonsters.size(); i++) {
            if(brandedMonsters.get(i) == null || brandedMonsters.get(i).hp <= 0) {
                brandedMonsters.remove(i);
                brandedMonstersTimers.remove(i);
                i--;
            }
        }

        for (int i = 0; i < brandedMonsters.size(); i++) {
            float timer = brandedMonstersTimers.get(i);
            timer -= delta;
            brandedMonstersTimers.set(i, timer);

            if(brandedMonstersTimers.get(i) <= 0) {
                float newTimer = MathUtils.random(3f, 4f);
                brandedMonstersTimers.set(i, newTimer);
                Brand_Explosion explosion = new Brand_Explosion(brandedMonsters.get(i).body.getPosition());
                player.screen.spellManager.add(explosion);
            }
        }
    }

}
