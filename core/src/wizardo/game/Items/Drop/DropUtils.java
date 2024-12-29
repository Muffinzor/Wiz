package wizardo.game.Items.Drop;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DropUtils {

    public static Vector2 getRandomDropVector(Vector2 areaCenter, float radius) {
        float randomAngle = MathUtils.random(MathUtils.PI, MathUtils.PI2); // Random angle in radians
        float randomRadius = (float) Math.sqrt(MathUtils.random(0f, 1f)) * radius; // Adjusted random radius for uniform distribution
        float offsetX = randomRadius * MathUtils.cos(randomAngle); // X offset
        float offsetY = randomRadius * MathUtils.sin(randomAngle); // Y offset

        // Calculate the randomized coordinates
        float x = areaCenter.x + offsetX;
        float y = areaCenter.y + offsetY;

        return new Vector2(x, y);
    }
}
