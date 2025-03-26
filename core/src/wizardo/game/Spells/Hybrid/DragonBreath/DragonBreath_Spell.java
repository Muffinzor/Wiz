package wizardo.game.Spells.Hybrid.DragonBreath;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Amulet.Epic_DragonbreathAmulet;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Wizardo.player;

public class DragonBreath_Spell extends Spell {

    public boolean frostbolts;
    public boolean frozenorb;
    public boolean fireball;
    public boolean rift;

    public ArrayList<Monster> monsters_hit;

    public DragonBreath_Spell() {

        string_name = "Dragonbreath";
        levelup_enum = LevelUpEnums.LevelUps.DRAGONBREATH;

        dmg = 10;

        cooldown = 3.2f;

    }

    @Override
    public void update(float delta) {
        monsters_hit = new ArrayList<>();
        completeAutoAimCheck();

        if(player.inventory.equippedAmulet instanceof Epic_DragonbreathAmulet) {
            dualBreath();
        } else {
            shootBreath(getTargetPosition());
        }
        screen.spellManager.remove(this);
    }

    public void shootBreath(Vector2 targetDirection) {
        DragonBreath_Projectile dragonbreath = new DragonBreath_Projectile();
        dragonbreath.spawnPosition = new Vector2(getSpawnPosition());
        dragonbreath.targetPosition = targetDirection;
        dragonbreath.frostbolts = frostbolts;
        dragonbreath.frozenorb = frozenorb;
        dragonbreath.fireball = fireball;
        dragonbreath.rift = rift;
        dragonbreath.monsters_hit = monsters_hit;
        dragonbreath.setElements(this);
        screen.spellManager.add(dragonbreath);
    }

    public void dualBreath() {
        Vector2 targetPosition = getTargetPosition();
        Vector2 spawnPosition = getSpawnPosition();
        Vector2 direction = new Vector2(targetPosition.sub(spawnPosition));
        Vector2 target1 = new Vector2(spawnPosition.cpy().add(direction.cpy().rotateDeg(25)));
        Vector2 target2 = new Vector2(spawnPosition.cpy().add(direction.cpy().rotateDeg(-25)));
        shootBreath(target1);
        shootBreath(target2);
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
        int dmg = this.dmg;
        dmg += 10 * player.spellbook.flamejet_lvl;
        dmg += 10 * player.spellbook.overheat_lvl;
        return dmg;
    }

}
