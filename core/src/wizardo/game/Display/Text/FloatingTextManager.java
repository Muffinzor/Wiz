package wizardo.game.Display.Text;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import wizardo.game.Screens.BaseScreen;

import java.util.ArrayList;

public class FloatingTextManager {

    public FloatingDamagePool pool;
    public ArrayList<FloatingDamage> activeDmgTexts;

    SpriteBatch batch;
    BaseScreen screen;

    public FloatingTextManager(BaseScreen screen) {
        this.screen = screen;
        activeDmgTexts = new ArrayList<>();
        this.pool = new FloatingDamagePool();
        batch = new SpriteBatch();
    }

    public void update(float delta) {
        for(FloatingDamage text : activeDmgTexts) {
            text.update(delta);
            if(text.alpha <= 0) {
                pool.poolDmgText(text);
            }
        }
        activeDmgTexts.removeIf(text -> text.alpha <= 0);
    }

    public void render() {
        batch.setProjectionMatrix(screen.mainCamera.combined);
        batch.begin();
        for(FloatingText text : activeDmgTexts) {
            text.render(batch);
        }
        batch.end();
    }

    public void addDmgText(FloatingDamage text) {
        activeDmgTexts.add(text);
    }


}
