package wizardo.game.Screens.MainMenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.compression.lzma.Base;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

public class MainMenuScreen extends BaseScreen {

    Sprite background;
    Sprite title;

    public MainMenuScreen(Wizardo game) {
        super(game);

        background = new Sprite(new Texture("MainMenuScreen/MainBackground.png"));
        title = new Sprite(new Texture("MainMenuScreen/Title.png"));
        
    }

    @Override
    public void render(float delta) {
        batch.begin();
        background.draw(batch);
        title.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
