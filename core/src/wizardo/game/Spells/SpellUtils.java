package wizardo.game.Spells;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import wizardo.game.Monsters.MonsterArchetypes.Monster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

import static wizardo.game.Utils.Methods.isPositionOverlappingWithObstacle;
import static wizardo.game.Wizardo.world;

public class SpellUtils {

    public enum Spell_Element {
        ARCANE,
        FROST,
        FIRE,
        LIGHTNING
    }

    public enum Spell_Name {
        FROSTBOLT,
        ICESPEAR,
        FROZENORB,
        FLAMEJET,
        FIREBALL,
        OVERHEAT,
        MISSILES,
        BEAM,
        RIFTS,
        CHARGEDBOLTS,
        CHAIN,
        THUNDERSTORM
    }

    public static String getSpellString(Spell_Name spell) {
        String s = "";
        switch(spell) {
            case FROSTBOLT -> s = "Frostbolts";
            case ICESPEAR -> s = "Icespear";
            case FROZENORB -> s = "Frozen Orb";
            case FLAMEJET -> s = "Flamejet";
            case FIREBALL -> s = "Fireball";
            case OVERHEAT -> s = "Overheat";
            case CHARGEDBOLTS -> s = "Chargedbolts";
            case CHAIN -> s = "Chain";
            case THUNDERSTORM -> s = "Storm";
            case MISSILES -> s = "Missiles";
            case BEAM -> s = "Beam";
            case RIFTS -> s = "Rifts";
        }
        return s;
    }

    public static boolean hasLineOfSight(Vector2 origin, Vector2 target) {

        AtomicBoolean clearLOS = new AtomicBoolean(true);

        world.rayCast((fixture, point, normal, fraction) -> {

            short obstacleCategory = 0x0010;

            if ((fixture.getFilterData().categoryBits & obstacleCategory) != 0) {
                clearLOS.set(false);
                return 0; // Stop raycast
            }
            return 1; // Continue raycast
        }, origin, target);

        return clearLOS.get();

    }

    /**
     * randomizes a vector in a radius
     * @param areaCenter
     * @param radius
     * @return Vector with randomized coordinates
     */
    public static Vector2 getRandomVectorInRadius(Vector2 areaCenter, float radius) {
        float randomAngle = MathUtils.random(0, MathUtils.PI2); // Random angle in radians
        float randomRadius = (float) Math.sqrt(MathUtils.random(0f, 1f)) * radius; // Adjusted random radius for uniform distribution
        float offsetX = randomRadius * MathUtils.cos(randomAngle); // X offset
        float offsetY = randomRadius * MathUtils.sin(randomAngle); // Y offset

        // Calculate the randomized coordinates
        float x = areaCenter.x + offsetX;
        float y = areaCenter.y + offsetY;

        return new Vector2(x, y);
    }

    /**
     * returns a randomized position that does not collide with obstacles, if it can find one quickly
     * @param center center of the search circle
     * @return null if nothing is found
     */
    public static Vector2 getClearRandomPosition(Vector2 center, float radius) {
        Vector2 randomTarget = null;
        int attempts = 0;
        while (randomTarget == null && attempts < 10) {
            attempts++;
            Vector2 attempt = SpellUtils.getRandomVectorInRadius(center, radius);
            if (!isPositionOverlappingWithObstacle(attempt) || attempts == 10) {
                randomTarget = attempt;
            }
        }
        return randomTarget;
    }

    /**
     * returns the coordinate at a certain distance of an origin, in the direction of target
     * @param origin
     * @param target
     * @param distance
     * @return
     */
    public static Vector2 getTargetVector(Vector2 origin, Vector2 target, float distance) {
        Vector2 direction = new Vector2(target).sub(origin);

        direction.nor();
        direction.scl(distance);

        return new Vector2(origin).add(direction);
    }


    public static ArrayList<Monster> findMonstersInRangeOfVector(Vector2 origin, float radius, boolean need_LoS) {
        ArrayList<Monster> monstersInRange = new ArrayList<>();

        // Define the AABB
        float lowerX = origin.x - radius;
        float lowerY = origin.y - radius;
        float upperX = origin.x + radius;
        float upperY = origin.y + radius;

        QueryCallback callback = fixture -> {

            if (fixture.getBody().getUserData() instanceof Monster monster) {
                Vector2 monsterPosition = fixture.getBody().getPosition();

                float distance = origin.dst(monsterPosition);

                if (distance <= radius && monster.hp > 0) {
                    if(need_LoS && hasLineOfSight(origin, monster.body.getPosition())) {
                        monstersInRange.add(monster);
                    } else if (!need_LoS ){
                        monstersInRange.add(monster);
                    }
                }
            }
            return true; // Continue the query
        };

        world.QueryAABB(callback, lowerX, lowerY, upperX, upperY);

        HashSet<Monster> seenMonsters = new HashSet<>();
        monstersInRange.removeIf(monster -> !seenMonsters.add(monster));

        return monstersInRange;
    }
}
