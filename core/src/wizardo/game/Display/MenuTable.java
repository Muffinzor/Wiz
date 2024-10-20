package wizardo.game.Display;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Wizardo;

import java.util.ArrayList;

public abstract class MenuTable {

    public Wizardo game;
    public Stage stage;
    public Skin skin;
    public Table table;
    public ArrayList<Button> buttons;

    public int selectedButtonIndex;

    public MenuTable(Stage stage, Skin skin, Wizardo game) {
        this.stage = stage;
        this.skin = skin;
        this.buttons = new ArrayList<>();
        this.game = game;
        selectedButtonIndex = -1;
        table = new Table();
    }

    public abstract void navigateDown();
    public abstract void navigateUp();
    public abstract void navigateLeft();
    public abstract void navigateRight();

    public abstract void clickSelectedButton();

    public abstract void resize();


}
