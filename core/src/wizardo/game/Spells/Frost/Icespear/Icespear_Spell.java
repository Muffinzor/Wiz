package wizardo.game.Spells.Frost.Icespear;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Items.Equipment.Staff.Epic_IcespearStaff;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class Icespear_Spell extends Spell {

    float split_chance = 0.45f;
    int split_shards = 2;

    float minimumTimeForSplit = 0.15f;
    public int currentSplits = 0;
    public int maxSplits = 2;
    boolean canSplit;

    int maxCollisions = 5;
    public float duration = 1.5f;
    public boolean indestructible;


    public boolean celestialStrike;
    public boolean arcaneMissile;
    public boolean flamejet;
    public boolean frostbolts;
    public boolean fireball;
    public boolean beam;
    public boolean rift;
    public boolean overheat;
    public boolean overheatBall; //for Icespear+Overheat+Fireball
    public boolean frozenorb; // for Icespear+Frozenorb+Beam
    public boolean thunderspear; // for Icespear+Thunderstorm+Chargedbolts;

    public Icespear_Spell() {
        string_name = "Ice Spear";
        levelup_enum = LevelUpEnums.LevelUps.ICESPEAR;
        dmg = 20;
        speed = 400f/PPM;
        cooldown = 0.8f;
        autoaimable = true;

        main_element = SpellUtils.Spell_Element.FROST;

    }

    public void setup() {
        if(player.inventory.equippedStaff instanceof Epic_IcespearStaff) {
            minimumTimeForSplit = 0;
            speed *= 1.2f;
            split_shards ++;
        }
        speed = getScaledSpeed();
        split_shards += player.spellbook.icespear_bonus_shard;
        split_chance -= player.spellbook.icespear_bonus_split_chance/100f;
    }

    public void update(float delta) {
        stateTime += delta;
        setup();
        autoAimCheck();

        if(targetPosition == null) {
            return;
        }

        if(player.inventory.equippedStaff instanceof Epic_IcespearStaff && castByPawn) {
            boomstaff_casting();
            screen.spellManager.remove(this);
        } else {
            Icespear_Projectile spear = new Icespear_Projectile(getSpawnPosition(), targetPosition);
            spear.setNextSpear(this);
            screen.spellManager.add(spear);
            screen.spellManager.remove(this);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return player.spellbook.icespear_lvl;
    }

    public void setNextSpear(Icespear_Spell parent) {
        this.nested_spell = parent.nested_spell;
        this.arcaneMissile = parent.arcaneMissile;
        this.fireball = parent.fireball;
        this.overheatBall = parent.overheatBall;
        this.frozenorb = parent.frozenorb;
        this.overheat = parent.overheat;
        this.celestialStrike = parent.celestialStrike;
        this.flamejet = parent.flamejet;
        this.beam = parent.beam;
        this.rift = parent.rift;
        this.thunderspear = parent.thunderspear;
        this.frostbolts = parent.frostbolts;
        this.setElements(parent);
        this.duration = parent.duration;

        this.maxSplits = parent.maxSplits;
        this.indestructible = parent.indestructible;
        this.speed = parent.speed;
        this.maxCollisions = parent.maxCollisions;
        this.minimumTimeForSplit = parent.minimumTimeForSplit;
        this.split_chance = parent.split_chance;
        this.split_shards = parent.split_shards;
    }

    @Override
    public int getDmg() {
        int dmg = this.dmg;
        dmg += 20 * getLvl();
        return dmg;
    }

    public void boomstaff_casting() {
        Vector2 direction = new Vector2(targetPosition.sub(player.pawn.body.getPosition()));

        int shards = split_shards;
        float initialAngle = direction.angleDeg();
        float halfCone = 3f * shards / 2;
        float stepSize = 3f * shards / (shards - 1);

        for (int i = 0; i < shards; i++) {
            float angle = initialAngle - halfCone + (stepSize * i);
            Vector2 direction2 = new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
            Icespear_Projectile spear = new Icespear_Projectile(player.pawn.getPosition(), player.pawn.getPosition().cpy().add(direction2));
            spear.currentSplits = 1;
            spear.scale = 0.9f;
            spear.stateTime = stateTime;
            spear.setNextSpear(this);
            screen.spellManager.add(spear);
        }
    }

}
