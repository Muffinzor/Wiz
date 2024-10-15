package wizardo.game.Monsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Utils.BodyFactory;

public class TEST_MONSTER extends Monster {

    public TEST_MONSTER(BattleScreen screen, Vector2 position) {
        super(screen, position);
        speed = 1;
    }

    @Override
    public void initialize() {
        initialized = true;
        body = BodyFactory.monsterBody(position, 8);
        body.setUserData(this);
        speed = speed * MathUtils.random(0.9f, 1.1f);
    }

    public void createBody() {

    }
}
