package wizardo.game.Screens.Popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import wizardo.game.Display.MenuTable;
import wizardo.game.Wizardo;

import static wizardo.game.Resources.Skins.inventorySkin;

public class YesOrNoTable extends MenuTable {

    Container<Table> container;
    String message;

    public YesOrNoTable(Stage stage, Skin mainMenuSkin, Wizardo game, String message) {
        super(stage, mainMenuSkin, game);
        this.message = message;

        Sprite sprite = new Sprite(new Texture("Screens/SmallSheet.png"));
        System.out.println(sprite.getWidth() + " " + sprite.getHeight());
        SpriteDrawable background = new SpriteDrawable(sprite);
        container = new Container<>();
        container.setSize(sprite.getWidth(), sprite.getHeight());

        container.align(Align.center);
        container.setDebug(true);

        container.setActor(table);
        container.setBackground(background);
        container.fill();

        container.setPosition(Gdx.graphics.getWidth()/2f - background.getMinWidth()/2,
                Gdx.graphics.getHeight()/2f - background.getMinHeight()/2);

        stage.addActor(container);

        createTable();

    }

    public void createTable() {
        Label text = new Label(message, inventorySkin, "Gear_Name");
        text.setColor(Color.BLACK);
        table.add(text).colspan(2).padBottom(15);
        table.row();

        ImageButton YesButton = new ImageButton(skin, "GreenOK");
        table.add(YesButton);

        YesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getPreviousScreen().choiceConfirmed = true;
                game.setPreviousScreen();
            }
        });

        ImageButton CancelButton = new ImageButton(skin, "RedCancel");
        table.add(CancelButton);

        CancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getPreviousScreen().choiceConfirmed = false;
                game.setPreviousScreen();
            }
        });
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
