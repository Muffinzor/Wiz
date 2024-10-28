package wizardo.game.Spells.Arcane.Rifts;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.Monster;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Utils.Methods.isPositionOverlappingWithObstacle;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class Rifts_Spell extends Spell {

    public int maxRifts = 20;
    public int riftsCast = 0;
    public float interval = 0.05f;
    public float spread = 4.5f;
    public float radius = 50;

    public Rifts_Spell() {

        name = "Rifts";

        cooldown = 5f;
        dmg = 15;

        main_element = SpellUtils.Spell_Element.ARCANE;

    }

    @Override
    public void update(float delta) {

        if(delta > 0) {

            if (!initialized) {
                initialized = true;
                setTargetPosition();
            }

            stateTime += delta;

            castRifts();

            if (riftsCast >= maxRifts) {
                currentScreen.spellManager.toRemove(this);
            }

        }
    }

    public void castRifts() {

        if (stateTime > riftsCast * interval) {
            Vector2 randomTarget = null;
            int attempts = 0;
            while (randomTarget == null && attempts < 10) {
                attempts++;
                Vector2 attempt = SpellUtils.getRandomVectorInRadius(targetPosition, spread);
                if (!isPositionOverlappingWithObstacle(attempt)) {
                    randomTarget = attempt;
                }
            }

            if(randomTarget != null) {
                Rift_Zone rift = new Rift_Zone(randomTarget);
                currentScreen.spellManager.toAdd(rift);
            }

            riftsCast++;

        }
    }

    @Override
    public void dispose() {

    }


    public void setTargetPosition() {
        if(null == targetPosition) {
            Vector2 temp = getTargetPosition();
            Vector2 playerPos = player.pawn.body.getPosition();
            Vector2 direction = new Vector2(temp.sub(playerPos));
            if(direction.len() > 0) {
                direction.nor();
            } else {
                direction.set(1,0);
            }
            targetPosition = new Vector2(playerPos.add(direction.scl(8)));
            ArrayList<Monster> inRange = SpellUtils.findMonstersInRangeOfVector(targetPosition, 8);
            if(!inRange.isEmpty()) {
                float dst = Float.MAX_VALUE;
                for(Monster monster: inRange) {
                    if(monster.body.getPosition().dst(playerPos) < dst) {
                        dst = monster.body.getPosition().dst(playerPos);
                        targetPosition = monster.body.getPosition();
                    }
                }
            }
        }
    }

    @Override
    public int getLvl() {
        return player.spellbook.rift_lvl;
    }
}
