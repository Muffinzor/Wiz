package wizardo.game.Monsters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Player.Pawn;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Utils.BodyFactory;

import java.util.ArrayList;

public abstract class Monster {

    public BattleScreen screen;

    public float stateTime;
    public int frameCounter;

    public Body body;
    public float speed;
    public Vector2 position;

    public Pathfinder pathfinder;

    boolean initialized;
    boolean leftie;

    public Monster(BattleScreen screen, Vector2 position) {
        this.screen = screen;
        this.position = new Vector2(position);
        stateTime = 0;
        frameCounter = 0;
        pathfinder = new Pathfinder(this);
        leftie = MathUtils.randomBoolean();
    }

    public void update(float delta) {
        if(!initialized) {
            initialize();
        }

        stateTime += delta;
        frameCounter++;
        if(frameCounter == 10) {
            pathfinder.update();
            frameCounter = 0;
        }

        if(delta == 0) {
            body.setLinearVelocity(0,0);
        }
    }

    public abstract void initialize();
}
