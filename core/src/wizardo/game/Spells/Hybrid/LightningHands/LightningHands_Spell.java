package wizardo.game.Spells.Hybrid.LightningHands;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Spells.Spell;

import static wizardo.game.Wizardo.player;

public class LightningHands_Spell extends Spell {

    int number_of_bolts = 3;

    Vector2 direction;
    float lightRadius = 150;

    public boolean chainlightning;
    public boolean chargedbolts;

    public LightningHands_Spell() {

        name = "Lightning Hands";

        cooldown = 2;

    }


    @Override
    public void update(float delta) {

        setup();

        targetPosition = getTargetPosition();
        spawnPosition = getSpawnPosition();
        direction = targetPosition.cpy().sub(spawnPosition);

        float coneAngle = Math.min(40, 25 + 5 * number_of_bolts);
        float angleStep = coneAngle / (number_of_bolts - 1);
        float startAngle = -coneAngle / 2;

        for (int i = 0; i < number_of_bolts; i++) {
            Vector2 dir = direction.cpy().rotateDeg(startAngle + i * angleStep);
            LightningHands_Projectile lightning = new LightningHands_Projectile(dir);
            lightning.setBolt(this);
            screen.spellManager.toAdd(lightning);
        }
        screen.spellManager.toRemove(this);

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
        return 100;
    }
}
