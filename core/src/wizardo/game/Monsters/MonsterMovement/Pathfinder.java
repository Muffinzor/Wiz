package wizardo.game.Monsters.MonsterMovement;

import com.badlogic.gdx.math.Vector2;

public interface Pathfinder {
    Vector2 getDirection(boolean backwards);
}
