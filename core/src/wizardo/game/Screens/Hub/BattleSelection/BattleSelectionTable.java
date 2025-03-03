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
import wizardo.game.Screens.EscapeMenu.SelectionMenuButton;
import wizardo.game.Wizardo;


public class BattleSelectionTable extends MenuTable {

    public BattleSelectionTable(Stage stage, Skin skin, Wizardo game) {
        super(stage, skin, game);
        resize();
    }

    @Override
    public void navigateDown() {
        selectedButtonIndex--;
        if(selectedButtonIndex < 0) {
            selectedButtonIndex = buttons.size() - 1;
        }
        updateButtonStates();
    }

    @Override
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

    @Override
    public void pressSelectedButton() {
        table.clearChildren();
        table = null;
        switch (selectedButtonIndex) {
            case 0 -> game.freshScreen(new BattleScreen(game, "Dungeon"));
            case 1 -> game.freshScreen(new BattleScreen(game, "Forest"));
            case 2 -> game.setPreviousScreen();
        }
    }

    public void updateButtonStates() {
        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            button.setChecked(i == selectedButtonIndex);
        }
    }

    public void setPosition() {

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        table.setPosition(screenWidth/2f - table.getWidth()/2, screenHeight/2f - table.getHeight()/2);
        stage.addActor(table);

    }

    public void createButtons() {

        SelectionMenuButton dungeon_button = new SelectionMenuButton("Dungeon", skin);
        buttons.add(dungeon_button);
        table.add(dungeon_button);
        table.row();

        dungeon_button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = 0;

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = -1;

            }
        });

        dungeon_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clearChildren();
                game.freshScreen(new BattleScreen(game, "Dungeon"));
            }
        });

        SelectionMenuButton forest_button = new SelectionMenuButton("Forest", skin);
        buttons.add(forest_button);
        table.add(forest_button);
        table.row();

        forest_button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = 1;

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectedButtonIndex = -1;
            }
        });

        forest_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clearChildren();
                game.freshScreen(new BattleScreen(game, "Forest"));
            }
        });

        SelectionMenuButton exitButton = new SelectionMenuButton("Cancel", skin);
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
        updateButtonStates();
    }
}
