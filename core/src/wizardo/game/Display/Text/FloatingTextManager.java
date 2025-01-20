package wizardo.game.Display.Text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Screens.BaseScreen;

import java.util.ArrayList;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;

public class FloatingTextManager {

    public FloatingDamagePool pool;
    public ArrayList<FloatingDamage> activeDmgTexts;

    public ArrayList<BottomText> activeBottomTexts;
    public ArrayList<BottomText> bottomTextsQueue;

    public ArrayList<BottomText> activeGoldTexts;
    public ArrayList<BottomText> goldTextsQueue;

    public float bottomTextCooldown = 1;
    public float goldTextCooldown = 0.33f;

    SpriteBatch batch;
    BaseScreen screen;

    public FloatingTextManager(BaseScreen screen) {
        this.screen = screen;
        activeDmgTexts = new ArrayList<>();
        activeBottomTexts = new ArrayList<>();
        bottomTextsQueue = new ArrayList<>();
        activeGoldTexts = new ArrayList<>();
        goldTextsQueue = new ArrayList<>();
        this.pool = new FloatingDamagePool();
        batch = new SpriteBatch();
    }

    public void update(float delta) {
        cycleBottomTexts();

        for(FloatingDamage text : activeDmgTexts) {
            text.update(delta);
            if(text.alpha <= 0) {
                pool.poolDmgText(text);
            }
        }
        activeDmgTexts.removeIf(text -> text.alpha <= 0);

        updateBottomTexts(delta);
        updateGoldTexts(delta);
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
        for(BottomText text : activeGoldTexts) {
            text.render(batch);
        }
        batch.end();
    }

    public void updateBottomTexts(float delta) {
        for(BottomText text : activeBottomTexts) {
            text.update(delta);
        }
        activeBottomTexts.removeIf(text -> text.alpha <= 0);
    }
    public void updateGoldTexts(float delta) {
        for(BottomText text : activeGoldTexts) {
            text.update(delta);
        }
        activeGoldTexts.removeIf(text -> text.alpha <= 0);
    }

    public void addDmgText(FloatingDamage text) {
        activeDmgTexts.add(text);
    }
    public void addBottomText(BottomText text) {
        text.position = new Vector2(Gdx.graphics.getWidth()/2f - 250 * xRatio, 85 * yRatio);
        bottomTextsQueue.add(text);
    }
    public void addGoldText(BottomText text) {
        text.position = new Vector2(635 * xRatio * MathUtils.random(0.98f, 1.02f),95 * yRatio);
        goldTextsQueue.add(text);
    }

    public void cycleBottomTexts() {
        bottomTextCooldown -= Gdx.graphics.getDeltaTime();
        if(!bottomTextsQueue.isEmpty() && bottomTextCooldown <= 0) {
            activeBottomTexts.add(bottomTextsQueue.removeFirst());
            bottomTextCooldown = 1;
        }

        goldTextCooldown -= Gdx.graphics.getDeltaTime();
        if(!goldTextsQueue.isEmpty() && goldTextCooldown <= 0) {
            activeGoldTexts.add(goldTextsQueue.removeFirst());
            goldTextCooldown = 0.33f;
        }


    }


}
