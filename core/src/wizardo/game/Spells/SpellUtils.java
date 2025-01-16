package wizardo.game.Spells;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import wizardo.game.Monsters.MonsterArchetypes.Monster;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static wizardo.game.Utils.Methods.isPositionOverlappingWithObstacle;
import static wizardo.game.Wizardo.world;

public class SpellUtils {

    public enum Spell_Element {
        ARCANE,
        FROST,
        FIRE,
        LIGHTNING,
        FIRELITE,
        COLDLITE
    }

    public static Spell_Element getRandomClassicElement() {
        Spell_Element ele = null;
        int random = MathUtils.random(1,4);
        switch (random) {
            case 1 -> ele = Spell_Element.FIRE;
            case 2 -> ele = Spell_Element.FROST;
            case 3 -> ele = Spell_Element.ARCANE;
            case 4 -> ele = Spell_Element.LIGHTNING;
        }
        return ele;
    }

    public enum Spell_Name {
        FROSTBOLT,
        FLAMEJET,
        MISSILES,
        CHARGEDBOLTS,
        ICESPEAR,
        FIREBALL,
        BEAM,
        CHAIN,
        FROZENORB,
        OVERHEAT,
        RIFTS,
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

            if (fixture != null && (fixture.getFilterData().categoryBits & obstacleCategory) != 0) {
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
     * randomizes a vector in a ring around a vector
     * @param minRadius minimum distance from center
     * @param maxRadius maximum distance
     * @return Vector with randomized coordinates
     */
    public static Vector2 getRandomVectorInRing(Vector2 areaCenter, float minRadius, float maxRadius) {
        float randomAngle = MathUtils.random(0, MathUtils.PI2); // Random angle in radians
        float randomRadius = (float) Math.sqrt(MathUtils.random(minRadius * minRadius, maxRadius * maxRadius)); // Adjusted random radius for uniform distribution
        float offsetX = randomRadius * MathUtils.cos(randomAngle); // X offset
        float offsetY = randomRadius * MathUtils.sin(randomAngle); // Y offset

        // Calculate the randomized coordinates
        float x = areaCenter.x + offsetX;
        float y = areaCenter.y + offsetY;

        return new Vector2(x, y);
    }

    /**
     * randomizes a vector in a 60 degree cone of a ring around the center
     * @param minRadius minimum distance from center
     * @param maxRadius maximum distance
     * @param centralAngle the central angle of the cone
     * @return Vector with randomized coordinates
     */
    public static Vector2 getRandomVectorInConeRing(Vector2 areaCenter, float minRadius, float maxRadius, float centralAngle) {
        // Convert central angle from degrees to radians
        float centralAngleRadians = centralAngle * MathUtils.degreesToRadians;

        // Generate a random angle constrained within ±30 degrees of the central angle
        float halfCone = MathUtils.PI / 4; // 30 degrees in radians
        float randomAngle = MathUtils.random(
                centralAngleRadians - halfCone,
                centralAngleRadians + halfCone
        );

        // Generate a random radius within the ring boundaries
        float randomRadius = MathUtils.random(minRadius, maxRadius);

        // Compute offsets for X and Y using polar coordinates
        float offsetX = randomRadius * MathUtils.cos(randomAngle);
        float offsetY = randomRadius * MathUtils.sin(randomAngle);

        // Return the final coordinates in Vector2 form
        return new Vector2(areaCenter.x + offsetX, areaCenter.y + offsetY);
    }

    /**
     * returns a randomized position that does not collide with obstacles, if it can find one quickly
     * @param center center of the search circle
     * @return
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
     * returns a randomized position that does not collide with obstacles, if it can find one quickly
     * @param center center of the search circle
     * @param minRadius minimum distance
     */
    public static Vector2 getClearRandomPositionRing(Vector2 center, float minRadius, float maxRadius) {
        Vector2 randomTarget = SpellUtils.getRandomVectorInRing(center, minRadius, maxRadius);
        int attempts = 0;
        while (attempts < 10) {
            if (!isPositionOverlappingWithObstacle(randomTarget)) {
                return randomTarget;
            } else {
                randomTarget = SpellUtils.getRandomVectorInRing(center, minRadius, maxRadius);
                attempts++;
            }
        }
        return randomTarget;
    }

    public static Vector2 getClearRandomPositionCone(Vector2 center, float minRadius, float maxRadius, float centralAngle) {
        Vector2 position = SpellUtils.getRandomVectorInConeRing(center, minRadius, maxRadius, centralAngle);
        int attempts = 0;
        while (attempts < 10) {
            if (!isPositionOverlappingWithObstacle(position)) {
                return position;
            } else {
                position = SpellUtils.getRandomVectorInConeRing(center, minRadius, maxRadius, centralAngle);
            }
            attempts++;

        }
        return position;
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

    /** recursive will also make the previous tiers available as picks.
     * null element for all access
     * */
    public static Spell_Name getRandomMastery(SpellUtils.Spell_Element element, int tier, boolean recursive) {
        List<Spell_Name> list = new ArrayList<>();
        if(element != null) {
            switch(element) {
                case FROST -> {
                    list.add(Spell_Name.FROSTBOLT);
                    list.add(Spell_Name.ICESPEAR);
                    list.add(Spell_Name.FROZENORB);
                }
                case FIRE -> {
                    list.add(Spell_Name.FLAMEJET);
                    list.add(Spell_Name.FIREBALL);
                    list.add(Spell_Name.OVERHEAT);
                }
                case LIGHTNING -> {
                    list.add(Spell_Name.CHARGEDBOLTS);
                    list.add(Spell_Name.CHAIN);
                    list.add(Spell_Name.THUNDERSTORM);
                }
                case ARCANE -> {
                    list.add(Spell_Name.MISSILES);
                    list.add(Spell_Name.BEAM);
                    list.add(Spell_Name.RIFTS);
                }
            }
            Collections.shuffle(list);
            return list.getFirst();

        } else {
            list = Arrays.asList(Spell_Name.values());
            int maxIndex = tier * 4 - 1;
            int minIndex = 0;
            if(!recursive) {
                if(tier == 2) {
                    minIndex = 4;
                } else if(tier == 3) {
                    minIndex = 8;
                }
            }
            return list.get(MathUtils.random(minIndex, maxIndex));
        }

    }

    public static Spell_Element whatElementIsThis(Spell_Name spell) {
        Spell_Element element = null;
        switch(spell) {
            case FROSTBOLT, ICESPEAR, FROZENORB -> element = Spell_Element.FROST;
            case FLAMEJET, FIREBALL, OVERHEAT -> element = Spell_Element.FIRE;
            case CHARGEDBOLTS, CHAIN, THUNDERSTORM -> element = Spell_Element.LIGHTNING;
            case MISSILES, BEAM, RIFTS -> element = Spell_Element.ARCANE;
        }
        return element;
    }
}
