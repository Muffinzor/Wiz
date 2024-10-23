package wizardo.game.Screens.EscapeMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.Hub.HubScreen;
import wizardo.game.Screens.MainMenu.MainMenuScreen;
import wizardo.game.Screens.Settings.SettingsScreen;
import wizardo.game.Wizardo;

import java.util.ArrayList;

public class EscapeMenuTable extends MenuTable {

    public EscapeMenuTable(Stage stage, Skin skin, Wizardo game) {
        super(stage, skin, game);
        table = new Table();

        resize();

    }

    public void setPosition() {

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        table.setPosition(screenWidth/2f - table.getWidth()/2, screenHeight/2f - table.getHeight()/2);
        stage.addActor(table);

    }

    public void createButtons() {

        EscapeMenuButton playButton = new EscapeMenuButton("Resume", skin);
        buttons.add(playButton);
        table.add(playButton);
        table.row();

        playButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = 0;
                updateButtonStates();
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = -1;
                updateButtonStates();
            }
        });

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clearChildren();
                game.setPreviousScreen();
            }
        });

        EscapeMenuButton settingsButton = new EscapeMenuButton("Settings", skin);
        buttons.add(settingsButton);
        table.add(settingsButton);
        table.row();

        settingsButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = 1;
                updateButtonStates();
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = -1;
                updateButtonStates();
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.skipToScreen(new SettingsScreen(game));
            }
        });

        EscapeMenuButton exitButton = new EscapeMenuButton("Quit", skin);
        buttons.add(exitButton);
        table.add(exitButton);

        exitButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = 2;
                updateButtonStates();
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = -1;
                updateButtonStates();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.freshScreen(new MainMenuScreen(game));
            }
        });
    }

    public void updateButtonStates() {
        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            button.setChecked(i == selectedButtonIndex);
        }
    }

    public void navigateUp() {
        selectedButtonIndex++;
        if(selectedButtonIndex > buttons.size() - 1 ) {
            selectedButtonIndex = 0;
        }
        updateButtonStates();
    }

    @Override
    public void navigateLeft() {

    }

    @Override
    public void navigateRight() {

    }

    public void navigateDown() {
        selectedButtonIndex--;
        if(selectedButtonIndex < 0) {
            selectedButtonIndex = buttons.size() - 1;
        }
        updateButtonStates();
    }

    public void pressSelectedButton() {
        table.clearChildren();
        table = null;
        switch (selectedButtonIndex) {
            case 0 -> game.setPreviousScreen();
            case 1 -> System.out.println("Nope");
            case 2 -> game.freshScreen(new MainMenuScreen(game));
        }
    }

    public void resize() {
        buttons.clear();
        table.clearChildren();
        table = new Table();
        setPosition();
        createButtons();
        stage.addActor(table);
        updateButtonStates();
    }

    public void dispose() {
        buttons.clear();
        table.clearChildren();
        table = null;
        stage.clear();
    }

}
