package wizardo.game.Screens.Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import wizardo.game.Display.MenuTable;
import wizardo.game.Maps.Shop.MapShop;
import wizardo.game.Wizardo;

import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;

public class ShopMenuTable extends MenuTable {

    MapShop shop;
    Container<Table> container;
    Table contentTable;

    public ShopMenuTable(Stage stage, Skin skin, Wizardo game, MapShop shop) {
        super(stage, skin, game);
        this.shop = shop;


        container = new Container<>();
        backgroundSetup();
        contentTable = new Table();
        container.setActor(contentTable);
        container.setPosition(100,100);
        stage.addActor(container);

        Label lol = new Label("LOL", inventorySkin, "Gear_Title");
        contentTable.add(lol);

        container.pack();

        stage.setDebugAll(true);
    }

    public void update(float delta) {

    }

    public void backgroundSetup() {
        Pixmap ogPixmap = new Pixmap(Gdx.files.internal("Screens/Shop/ShopBackground.png"));
        int newWidth = (int) (ogPixmap.getWidth() * xRatio);
        int newHeight = (int) (ogPixmap.getHeight() * yRatio);
        Pixmap scaledPixmap = new Pixmap(newWidth, newHeight, ogPixmap.getFormat());

        scaledPixmap.drawPixmap(
                ogPixmap,
                0, 0, ogPixmap.getWidth(), ogPixmap.getHeight(),
                0, 0, newWidth, newHeight
        );

        Texture scaledTexture = new Texture(scaledPixmap);
        ogPixmap.dispose();
        scaledPixmap.dispose();

        Sprite sprite = new Sprite(scaledTexture);
        SpriteDrawable background = new SpriteDrawable(sprite);

        container.setBackground(background);
        container.setActor(contentTable);
        container.padLeft(40* xRatio);
        container.padRight(40* xRatio);
        container.padTop(40* yRatio);
        container.padBottom(40* yRatio);
    }

    @Override
    public void navigateDown() {

    }

    @Override
    public void navigateUp() {

    }

    @Override
    public void navigateLeft() {

    }

    @Override
    public void navigateRight() {

    }

    @Override
    public void pressSelectedButton() {

    }

    @Override
    public void resize() {

    }
}
