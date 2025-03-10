package wizardo.game.Spells.Hybrid.LightningHands;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Wizardo.player;

public class LightningHands_Spell extends Spell {

    int number_of_bolts = 3;

    Vector2 direction;
    float lightRadius = 150;

    public boolean chainlightning;
    public boolean chargedbolts;
    public boolean thunderstorm;

    public LightningHands_Spell() {

        string_name = "Lightning Hands";

        cooldown = 3.2f;
        autoaimable = true;


        anim_element = SpellUtils.Spell_Element.FIRELITE;
        main_element = SpellUtils.Spell_Element.FIRELITE;

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

        float coneAngle = Math.min(40, 25 + 5 * number_of_bolts);
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
        number_of_bolts = 3 + player.spellbook.flamejet_lvl / 5;
        lightAlpha = 0.3f - 0.03f * number_of_bolts;
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
        int dmg = 100;
        dmg += 20 * player.spellbook.flamejet_lvl;
        dmg += 20 * player.spellbook.overheat_lvl;
        if(thunderstorm) {
            dmg += 10 * player.spellbook.thunderstorm_lvl;
        }
        dmg = (int) (dmg * (1 + player.spellbook.flashBonusDmg/100f));
        return dmg;
    }
}
