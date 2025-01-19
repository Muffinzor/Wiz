package wizardo.game.Screens.Settings;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import wizardo.game.Audio.Sounds.SoundPlayer;
import wizardo.game.Display.MenuTable;
import wizardo.game.GameSettings;
import wizardo.game.Screens.MainMenu.MainMenuButton;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.GameSettings.*;
import static wizardo.game.Resources.Skins.mainMenuSkin;
import static wizardo.game.Wizardo.player;

public class SettingsTable extends MenuTable {

    public SettingsTable(Stage stage, Skin skin, Wizardo game) {
        super(stage, skin, game);

        table.setFillParent(true);
        stage.addActor(table);

        volume_slider();
        autoAim_checkbox();
        dmg_numbers_checkbox();
        healthbars_checkbox();
        zoom();
        debug_camera_checkbox();
        quitButton();

    }

    public void autoAim_checkbox() {
        CheckBox checkBox = new CheckBox("Auto-aim", mainMenuSkin);

        checkBox.setChecked(autoAim_On);

        checkBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                autoAim_On = !autoAim_On;
            }
        });

        table.row();
        table.add(checkBox);
    }

    public void volume_slider() {
        Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, mainMenuSkin);
        volumeSlider.setValue(sound_volume);

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sound_volume = volumeSlider.getValue();
            }
        });

        table.row();
        table.add(volumeSlider).width(400);
    }

    public void dmg_numbers_checkbox() {
        CheckBox checkBox = new CheckBox("Damage Text", mainMenuSkin);

        checkBox.setChecked(dmg_text_on);

        checkBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dmg_text_on = !dmg_text_on;
            }
        });

        table.row();
        table.add(checkBox);

    }

    public void debug_camera_checkbox() {
        CheckBox checkBox = new CheckBox("Debug Camera", mainMenuSkin);

        checkBox.setChecked(debug_camera);

        checkBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                debug_camera = !debug_camera;
            }
        });

        table.row();
        table.add(checkBox);
    }

    public void healthbars_checkbox() {
        CheckBox checkBox = new CheckBox("Monster Healthbars", mainMenuSkin);

        checkBox.setChecked(monster_health_bars);

        checkBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                monster_health_bars = !monster_health_bars;
            }
        });

        table.row();
        table.add(checkBox);
    }

    public void zoom() {

        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                zoomLevel += 1;
                if(zoomLevel > 3) {
                    zoomLevel = 0;
                }
                game.mainCamera.zoom = 0.8f + zoomLevel * 0.2f;
            }
        });
        table.add(PLUS);

    }

    public void quitButton() {

        MainMenuButton exitButton = new MainMenuButton("Quit", skin);
        table.row();
        buttons.add(exitButton);
        table.add(exitButton);

        exitButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                SoundPlayer.getSoundPlayer().playSound("Sounds/menu_selection_1.mp3", 0.3f);

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {

            }

        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.getSoundPlayer().playSound("Sounds/click_1.mp3", 1);
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
