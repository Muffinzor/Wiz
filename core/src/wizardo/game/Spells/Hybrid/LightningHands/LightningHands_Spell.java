package wizardo.game.Spells.Hybrid.LightningHands;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class LightningHands_Spell extends Spell {

    int number_of_bolts;

    Vector2 direction;
    float lightRadius = 150;

    public boolean chainlightning;
    public boolean chargedbolts;
    public boolean thunderstorm;

    public LightningHands_Spell() {

        string_name = "Lightning Hands";
        levelup_enum = LevelUpEnums.LevelUps.LIGHTNINGHANDS;

        cooldown = 4.2f;
        autoaimable = true;
        dmg = 40;

        anim_element = SpellUtils.Spell_Element.FIRELITE;

    }


    @Override
    public void update(float delta) {

        setup();

        completeAutoAimCheck();

        if(targetPosition == null) {
            return;
        }

        spawnPosition = getSpawnPosition();
        direction = targetPosition.cpy().sub(spawnPosition);

        float coneAngle = 10 + 8 * number_of_bolts;
        float angleStep = coneAngle / (number_of_bolts - 1);
        float startAngle = -coneAngle / 2;

        for (int i = 0; i < number_of_bolts; i++) {
            Vector2 dir = direction.cpy().rotateDeg(startAngle + i * angleStep);
            LightningHands_Projectile lightning = new LightningHands_Projectile(dir);
            lightning.setBolt(this);
            lightning.setElements(this);
            screen.spellManager.add(lightning);
        }
        screen.spellManager.remove(this);

    }

    public void setup() {
        number_of_bolts = 2 + player.spellbook.lightninghands_bonus_branch;
        lightAlpha = 0.3f - 0.025f * number_of_bolts;
    }

    public void setBolt(LightningHands_Spell parent) {
        this.lightRadius = parent.lightRadius;
        this.lightAlpha = parent.lightAlpha;
        this.chainlightning = parent.chainlightning;
        this.chargedbolts = parent.chargedbolts;
        this.thunderstorm = parent.thunderstorm;
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
        dmg += 20 * player.spellbook.flamejet_lvl;
        dmg += 20 * player.spellbook.overheat_lvl;
        if(thunderstorm) {
            dmg += 20 * player.spellbook.thunderstorm_lvl;
        }
        return dmg;
    }
}
