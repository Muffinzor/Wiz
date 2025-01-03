package wizardo.game.Screens.CharacterScreen.Anims;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Screen_Anim {

    public float stateTime;

    public abstract void draw(SpriteBatch batch);

    public abstract boolean isFinished();
}
