package wizardo.game.Screens.Shop;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Display.MenuTable;
import wizardo.game.Maps.Shop.MapShop;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

import static wizardo.game.Resources.Skins.shopSkin;

public class ShopScreen extends BaseScreen {

    MapShop shop;

    ShopMenuTable shopTable;

    public ShopScreen(Wizardo game, MapShop shop) {
        super(game);
        this.shop = shop;

        stage = new Stage(new ScreenViewport(uiCamera));
        shopTable = new ShopMenuTable(stage, shopSkin, game, shop);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
