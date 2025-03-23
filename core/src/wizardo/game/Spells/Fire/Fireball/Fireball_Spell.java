package wizardo.game.Spells.Fire.Fireball;

import wizardo.game.Items.Equipment.Staff.Epic_FireballStaff;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;

import static wizardo.game.Spells.SpellUtils.Spell_Element.FIRE;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class Fireball_Spell extends Spell {

    int projectiles = 1;
    float effectRatio = 1;

    public boolean frostbolts;
    public boolean frozenorb;
    public boolean flamejets;

    public boolean spearOrb; // fireball + Frozenorb + Icespear
    public boolean chainThunder; // fireball + chain + storm
    public boolean flameThunder; // fireball + flamejet + storm
    public boolean flameRift; // fireball + flamejet + rift

    public Fireball_Spell() {

        string_name = "Fireball";
        levelup_enum = LevelUpEnums.LevelUps.FIREBALL;

        dmg = 30;
        cooldown = 2;
        radius = 75;
        speed = 200f/PPM;
        autoaimable = true;

        main_element = FIRE;
    }

    public void setup() {
        if(player.inventory.equippedStaff instanceof Epic_FireballStaff) {
            projectiles = 3;
            effectRatio = 0.8f;
        }
    }

    @Override
    public void update(float delta) {

        setup();

        autoAimCheck();

        if(targetPosition == null) {
            return;
        }

        for (int i = 0; i < projectiles; i++) {
            Fireball_Projectile ball = new Fireball_Projectile(getSpawnPosition(), targetPosition);
            ball.inherit(this);
            ball.castByPawn = castByPawn;
            screen.spellManager.add(ball);
        }

        screen.spellManager.remove(this);

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.fireball_lvl;
    }

    public void inherit(Fireball_Spell parent) {
        this.frostbolts = parent.frostbolts;
        this.frozenorb = parent.frozenorb;
        this.chainThunder = parent.chainThunder;
        this.flameThunder = parent.flameThunder;
        this.flamejets = parent.flamejets;
        this.spearOrb = parent.spearOrb;
        this.flameRift = parent.flameRift;
        this.nested_spell = parent.nested_spell;
        this.castByPawn = parent.castByPawn;
        this.effectRatio = parent.effectRatio;

        setElements(parent);
        this.screen = parent.screen;
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg;
        dmg += 30 * getLvl();
        return dmg;
    }

}
