package wizardo.game.Screens.Battle.MonsterSpawner;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static wizardo.game.Utils.Methods.isPositionOverlappingWithObstacle;
import static wizardo.game.Wizardo.player;

public class SpawnerUtils {

    /**
     * randomizes a vector near the player for acolyte-style spawning
     */
    public static Vector2 getRandomRangeSpawnVector() {
        return getClearRandomPositionRing(player.pawn.getPosition(), 8, 16);
    }

    /**
     * randomizes a vector in a ring around a vector
     * @param minRadius minimum distance from center
     * @param maxRadius maximum distance
     */
    public static Vector2 getRandomVectorInRing(Vector2 areaCenter, float minRadius, float maxRadius) {
        float randomAngle = MathUtils.random(0, MathUtils.PI2); // Random angle in radians
        float randomRadius = (float) Math.sqrt(MathUtils.random(minRadius * minRadius, maxRadius * maxRadius)); // Adjusted random radius for uniform distribution
        float offsetX = randomRadius * MathUtils.cos(randomAngle); // X offset
        float offsetY = randomRadius * MathUtils.sin(randomAngle); // Y offset

        float x = areaCenter.x + offsetX;
        float y = areaCenter.y + offsetY;

        return new Vector2(x, y);
    }

    /**
     * returns a randomized position that does not collide with obstacles, if it can find one quickly
     * @param center center of the search circle
     * @param minRadius minimum distance
     */
    public static Vector2 getClearRandomPositionRing(Vector2 center, float minRadius, float maxRadius) {
        Vector2 randomTarget = getRandomVectorInRing(center, minRadius, maxRadius);
        int attempts = 0;
        while (attempts < 10) {
            if (!isPositionOverlappingWithObstacle(randomTarget)) {
                return randomTarget;
            } else {
                randomTarget = getRandomVectorInRing(center, minRadius, maxRadius);
                attempts++;
            }
        }
        return randomTarget;
    }

    /**
     * randomizes a vector in a 60 degree cone of a ring around the center
     * @param minRadius minimum distance from center
     * @param maxRadius maximum distance
     * @param centralAngle the central angle of the cone
     */
    public static Vector2 getRandomVectorInConeRing(Vector2 areaCenter, float minRadius, float maxRadius, float centralAngle) {
        float centralAngleRadians = centralAngle * MathUtils.degreesToRadians;

        float halfCone = MathUtils.PI / 4; // 30 degrees in radians
        float randomAngle = MathUtils.random(
                centralAngleRadians - halfCone,
                centralAngleRadians + halfCone
        );

        float randomRadius = MathUtils.random(minRadius, maxRadius);

        float offsetX = randomRadius * MathUtils.cos(randomAngle);
        float offsetY = randomRadius * MathUtils.sin(randomAngle);

        return new Vector2(areaCenter.x + offsetX, areaCenter.y + offsetY);
    }

    /**
     * randomizes a vector in a 60 degree cone of a ring around the center
     * will attempt to find a position with no obstacle
     * @param minRadius minimum distance from center
     * @param maxRadius maximum distance
     * @param centralAngle the central angle of the cone
     */
    public static Vector2 getClearRandomVectorInConeRing(Vector2 center, float minRadius, float maxRadius, float centralAngle) {
        Vector2 position = getRandomVectorInConeRing(center, minRadius, maxRadius, centralAngle);
        int attempts = 0;
        while (attempts < 10) {
            if (!isPositionOverlappingWithObstacle(position)) {
                return position;
            } else {
                position = getRandomVectorInConeRing(center, minRadius, maxRadius, centralAngle);
            }
            attempts++;

        }
        return position;
    }
}
