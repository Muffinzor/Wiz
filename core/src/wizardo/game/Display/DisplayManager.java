package wizardo.game.Display;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import wizardo.game.Display.Text.FloatingTextManager;
import wizardo.game.Screens.BaseScreen;

public class DisplayManager {

    public SpriteRenderer spriteRenderer;
    public FloatingTextManager textManager;
    public SpriteBatch mainBatch;
    BaseScreen screen;

    public DisplayManager(BaseScreen screen) {
        this.screen = screen;
        mainBatch = new SpriteBatch();
        spriteRenderer = new SpriteRenderer(screen);
        textManager = new FloatingTextManager(screen);
    }

    public void update(float delta) {
        mainBatch.setProjectionMatrix(screen.mainCamera.combined);

        spriteRenderer.renderSprites();

        screen.rayHandler.setCombinedMatrix(screen.mainCamera);
        screen.rayHandler.updateAndRender();

        spriteRenderer.renderPostLightningSprites();
        spriteRenderer.renderUI();

        spriteRenderer.clearSpriteArrays();

        textManager.update(delta);
        textManager.renderAlLTexts();

    }

}
