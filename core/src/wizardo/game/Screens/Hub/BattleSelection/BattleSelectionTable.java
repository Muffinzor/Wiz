package wizardo.game.Screens.Hub.BattleSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.EscapeMenu.EscapeMenuButton;
import wizardo.game.Screens.Hub.HubScreen;
import wizardo.game.Wizardo;

import java.util.ArrayList;


public class BattleSelectionTable extends MenuTable {

    public BattleSelectionTable(Stage stage, Skin skin, Wizardo game) {
        super(stage, skin, game);
        resize();
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
    public void clickSelectedButton() {

    }

    public void setPosition() {

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        table.setPosition(screenWidth/2f - table.getWidth()/2, screenHeight/2f - table.getHeight()/2);
        stage.addActor(table);

    }

    public void createButtons() {

        EscapeMenuButton playButton = new EscapeMenuButton("Dungeon", skin);
        buttons.add(playButton);
        table.add(playButton);
        table.row();

        playButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = 0;

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = -1;

            }
        });

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clearChildren();
                game.freshScreen(new BattleScreen(game, "Dungeon"));
            }
        });

        EscapeMenuButton settingsButton = new EscapeMenuButton("Forest", skin);
        buttons.add(settingsButton);
        table.add(settingsButton);
        table.row();

        settingsButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = 1;

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = -1;
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("No settings");
            }
        });

        EscapeMenuButton exitButton = new EscapeMenuButton("Cancel", skin);
        buttons.add(exitButton);
        table.add(exitButton);

        exitButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = 2;

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = -1;

            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setPreviousScreen();
            }
        });
    }

    @Override
    public void resize() {
        setPosition();
        createButtons();
    }
}
