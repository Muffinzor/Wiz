package wizardo.game.Screens.MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import wizardo.game.Audio.Sounds.SoundPlayer;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.Hub.HubScreen;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Screens.BaseScreen.screenRatio;

public class MainMenuTable extends MenuTable {

    private int lastHoveredButtonIndex = -1;

    public MainMenuTable(Stage stage, Skin skin, ArrayList<Button> buttons, Wizardo game) {
        super(stage, skin, buttons, game);
        this.game = game;

        resize();

    }

    public void setPosition() {
        System.out.println(screenRatio);
        int x = Gdx.graphics.getWidth()/2;
        int y = Gdx.graphics.getHeight()/2 - (int)(200 * screenRatio);
        table.setPosition(x,y);
        stage.addActor(table);
    }

    public void createButtons() {
        playButton();
        settingsButton();
        quitButton();
    }

    public void playButton() {
        MainMenuButton playButton = new MainMenuButton("Play", skin);
        buttons.add(playButton);
        table.add(playButton);
        table.row();

        playButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (selectedButtonIndex != 0 || lastHoveredButtonIndex != 0) {
                    selectedButtonIndex = 0;
                    updateButtonStates();
                    SoundPlayer.getSoundPlayer().playSound("Sounds/menu_selection_1.mp3", 0.3f);
                }
                lastHoveredButtonIndex = 0;
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
                SoundPlayer.getSoundPlayer().playSound("Sounds/click_1.mp3", 1);
                game.setOverScreen(new HubScreen(game));
            }
        });

    }
    public void settingsButton() {

        MainMenuButton settingsButton = new MainMenuButton("Settings", skin);
        buttons.add(settingsButton);
        table.add(settingsButton);
        table.row();

        settingsButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (selectedButtonIndex != 1 || lastHoveredButtonIndex != 1) {
                    selectedButtonIndex = 1;
                    updateButtonStates();
                    SoundPlayer.getSoundPlayer().playSound("Sounds/menu_selection_1.mp3", 0.3f);
                }
                lastHoveredButtonIndex = 1;
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
                SoundPlayer.getSoundPlayer().playSound("Sounds/click_1.mp3", 1);
                System.out.println("No settings");
            }
        });
    }
    public void quitButton() {



        MainMenuButton exitButton = new MainMenuButton("Quit", skin);
        buttons.add(exitButton);
        table.add(exitButton);

        exitButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (selectedButtonIndex != 2 || lastHoveredButtonIndex != 2) {
                    selectedButtonIndex = 2;
                    updateButtonStates();
                    SoundPlayer.getSoundPlayer().playSound("Sounds/menu_selection_1.mp3", 0.3f);
                }
                lastHoveredButtonIndex = 2;
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
                SoundPlayer.getSoundPlayer().playSound("Sounds/click_1.mp3", 1);
                Gdx.app.exit();
            }
        });
    }

    /**
     * Adjusts the currently buttons' visuals
     */
    public void updateButtonStates() {
        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            button.setChecked(i == selectedButtonIndex);
        }
    }

    public void navigateUp() {
        SoundPlayer.getSoundPlayer().playSound("Sounds/menu_selection_1.mp3", 0.3f);
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
        SoundPlayer.getSoundPlayer().playSound("Sounds/menu_selection_1.mp3", 0.3f);
        selectedButtonIndex--;
        if(selectedButtonIndex < 0) {
            selectedButtonIndex = buttons.size() - 1;
        }
        updateButtonStates();
    }

    public void clickSelectedButton() {
        table.clearChildren();
        table = null;
        switch (selectedButtonIndex) {
            case 0 -> game.setOverScreen(new HubScreen(game));
            case 1 -> System.out.println("Nope");
            case 2 -> Gdx.app.exit();
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
