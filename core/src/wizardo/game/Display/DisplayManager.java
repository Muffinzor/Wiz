package wizardo.game.Display;

import wizardo.game.Screens.BaseScreen;

public class DisplayManager {

    public SpriteRenderer spriteRenderer;
    BaseScreen screen;

    public DisplayManager(BaseScreen screen) {
        this.screen = screen;
        spriteRenderer = new SpriteRenderer(screen);
    }

    public void update(float delta) {
        spriteRenderer.renderSprites();

        screen.rayHandler.setCombinedMatrix(screen.camera);
        screen.rayHandler.updateAndRender();

        spriteRenderer.renderUI();
        spriteRenderer.clearSpriteArrays();
    }

}
