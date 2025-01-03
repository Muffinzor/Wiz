package wizardo.game.Display.Text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Screens.BaseScreen;

import java.util.ArrayList;

public class FloatingTextManager {

    public FloatingDamagePool pool;
    public ArrayList<FloatingDamage> activeDmgTexts;
    public ArrayList<BottomText> activeBottomTexts;
    public ArrayList<BottomText> bottomTextsQueue;
    public float bottomTextCooldown = 1;

    SpriteBatch batch;
    BaseScreen screen;

    public FloatingTextManager(BaseScreen screen) {
        this.screen = screen;
        activeDmgTexts = new ArrayList<>();
        activeBottomTexts = new ArrayList<>();
        bottomTextsQueue = new ArrayList<>();
        this.pool = new FloatingDamagePool();
        batch = new SpriteBatch();
    }

    public void update(float delta) {
        cycleBottomTexts(delta);

        for(FloatingDamage text : activeDmgTexts) {
            text.update(delta);
            if(text.alpha <= 0) {
                pool.poolDmgText(text);
            }
        }
        activeDmgTexts.removeIf(text -> text.alpha <= 0);

        for(BottomText text : activeBottomTexts) {
            text.update(delta);
        }
        activeBottomTexts.removeIf(text -> text.alpha <= 0);
    }

    public void renderAlLTexts() {
        batch.setProjectionMatrix(screen.mainCamera.combined);
        batch.begin();
        for(FloatingText text : activeDmgTexts) {
            text.render(batch);
        }
        batch.end();

        batch.setProjectionMatrix(screen.uiCamera.combined);
        batch.begin();
        for(BottomText text : activeBottomTexts) {
            text.render(batch);
        }
        batch.end();
    }

    public void addDmgText(FloatingDamage text) {
        activeDmgTexts.add(text);
    }
    public void addBottomText(BottomText text) {
        text.position = new Vector2(Gdx.graphics.getWidth()/2f, 100);
        bottomTextsQueue.add(text);
    }

    public void cycleBottomTexts(float delta) {
        bottomTextCooldown -= delta;
        if(!bottomTextsQueue.isEmpty() && bottomTextCooldown <= 0) {
            activeBottomTexts.add(bottomTextsQueue.removeFirst());
            bottomTextCooldown = 1;
        }
    }


}
