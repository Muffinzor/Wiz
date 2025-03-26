package wizardo.game.Spells.Hybrid.Orbit;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Book.Epic_OrbitBook;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;

import static wizardo.game.Wizardo.player;

public class Orbit_Spell extends Spell {

    public int orbs;
    public float orbitRadius = 5;

    public boolean frostbolt;

    public float duration;

    public Orbit_Spell() {
        string_name = "Orbit";
        levelup_enum = LevelUpEnums.LevelUps.ORBIT;

        speed = 2;
        dmg = 30;
        cooldown = 12;
        duration = 6;
    }

    public void setup() {
        speed = speed * (1 + player.spellbook.orbit_bonus_speed /100f);
        duration += player.spellbook.orbit_bonus_duration;
        if(player.inventory.equippedBook instanceof Epic_OrbitBook) {
            speed = speed * 1.2f;
        }
        orbs = 2 + player.spellbook.rift_lvl;
    }

    @Override
    public void update(float delta) {
        setup();
        createOrbs();
        screen.spellManager.remove(this);
    }

    public void createOrbs() {
        int randomStart = MathUtils.random(360);
        for (int i = 0; i < orbs; i++) {
            float angle = i * 2 * MathUtils.PI / orbs;
            angle+= randomStart;
            float x = player.pawn.getPosition().x + orbitRadius * MathUtils.cos(angle);
            float y = player.pawn.getPosition().y + orbitRadius * MathUtils.sin(angle);
            Vector2 startPos = new Vector2(x, y);
            Orbit_Projectile orb = new Orbit_Projectile(startPos, angle);
            orb.set_orb(this);
            screen.spellManager.add(orb);
        }

        if(player.inventory.equippedBook instanceof Epic_OrbitBook) {
            orbitRadius += orbitRadius/2;
            for (int i = 0; i < orbs; i++) {
                float angle = i * 2 * MathUtils.PI / orbs;
                angle+= randomStart;
                float x = player.pawn.getPosition().x + orbitRadius * MathUtils.cos(angle);
                float y = player.pawn.getPosition().y + orbitRadius * MathUtils.sin(angle);
                Vector2 startPos = new Vector2(x, y);
                Orbit_Projectile orb = new Orbit_Projectile(startPos, angle);
                orb.reverse = true;
                orb.set_orb(this);
                screen.spellManager.add(orb);
            }
        }
    }

    public void set_orb(Orbit_Spell orb) {
        this.speed = orb.speed;
        this.duration = orb.duration;
        this.frostbolt = orb.frostbolt;
        this.orbitRadius = orb.orbitRadius;
        setElements(orb);
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public int getDmg() {
        return this.dmg + 15 * player.spellbook.frozenorb_lvl;
    }
}
