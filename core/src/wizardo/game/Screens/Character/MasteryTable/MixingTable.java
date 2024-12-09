package wizardo.game.Screens.Character.MasteryTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import wizardo.game.Display.MenuTable;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Resources.Skins.masteryTableSkin;
import static wizardo.game.Spells.SpellMixer.getMixedSpell;
import static wizardo.game.Spells.SpellUtils.getSpellString;
import static wizardo.game.Wizardo.player;

public class MixingTable extends MenuTable {

    public ArrayList<SpellUtils.Spell_Name> parts;

    public ImageButton mixButton;
    public ImageButton clearButton;

    public Table labelTable;
    public Label spell_parts;
    public Label status;

    Button[] buttonMatrix = new Button[2];
    public int x_pos;

    public MasteryTable masteryTable;

    public MixingTable(Stage stage, Skin skin, Wizardo game, MasteryTable masteryTable) {
        super(stage, skin, game);
        this.masteryTable = masteryTable;
        this.parts = masteryTable.parts;
        createTable();

        create_mixButton();
        create_clearButton();
        //create_forgetButton();

        createLabelTable();
        createLabels();

        updateButtons();
    }

    public void createLabelTable() {
        float xRatio = Gdx.graphics.getWidth() / 1920f;
        float yRatio = Gdx.graphics.getHeight() / 1080f;

        int x_pos = Math.round(1330 * xRatio);
        int y_pos = Math.round(300 * yRatio);
        float height = 150 * yRatio;
        float width = 530 * xRatio;

        labelTable = new Table();
        labelTable.setPosition(x_pos, y_pos);
        labelTable.setSize(width, height);
        labelTable.setDebug(true);
        stage.addActor(labelTable);
    }
    public void createLabels() {
        spell_parts = new Label("Parts : " + getPartsString(), skin);
        labelTable.top().left();
        labelTable.add(spell_parts).align(Align.left);

        labelTable.row();

        status = new Label("Status : ", skin);
        labelTable.add(status).align(Align.left);
    }

    public void createTable() {
        //adjustFontSize();
        float xRatio = Gdx.graphics.getWidth() / 1920f;
        float yRatio = Gdx.graphics.getHeight() / 1080f;

        float width = 530f * xRatio;
        float height = 100f * yRatio;

        int x_pos = Math.round(1330 * xRatio);
        int y_pos = Math.round(130 * yRatio);

        table = new Table();
        table.setPosition(x_pos, y_pos);
        table.setWidth(width);
        table.setHeight(height);
        stage.addActor(table);

        table.setDebug(true);
    }

    public void updateSelectedButton() {
        masteryTable.screen.selectedButton = buttonMatrix[x_pos];
    }

    public void updateButtons() {
        mixButton.setDisabled(parts.isEmpty());
        clearButton.setDisabled(parts.isEmpty());

        if(!parts.isEmpty()) {
            mixButton.setDisabled(!getMixedSpell(parts).canMix());
        }

        if (parts.size() == 2 && player.inventory.dual_reagents < 1) {
            mixButton.setDisabled(true);
        } else if (parts.size() == 3 && player.inventory.triple_reagents < 1) {
            mixButton.setDisabled(true);
        }

        spell_parts.setText("Parts : " + getPartsString());
    }

    public void create_mixButton() {
        mixButton = new ImageButton(masteryTableSkin, "create_button");
        ImageButton.ImageButtonStyle newStyle = mixButton.getStyle();
        float width = 210 * (Gdx.graphics.getWidth()/1920f);
        float height = 125 * (Gdx.graphics.getHeight()/1080f);

        newStyle.imageUp.setMinHeight(height);
        newStyle.imageUp.setMinWidth(width);
        newStyle.imageDown.setMinHeight(height);
        newStyle.imageDown.setMinWidth(width);
        newStyle.imageOver.setMinHeight(height);
        newStyle.imageOver.setMinWidth(width);
        newStyle.imageDisabled.setMinHeight(height);
        newStyle.imageDisabled.setMinWidth(width);

        mixButton.setStyle(newStyle);

        table.add(mixButton);
        buttonMatrix[0] = mixButton;

        mixButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(!mixButton.isDisabled()) {

                    Spell spell = getMixedSpell(parts);
                    spell.learn();

                    parts.clear();
                    for (Button button : masteryTable.buttons) {
                        button.setChecked(false);
                    }
                    masteryTable.updateCheckBoxes();
                    masteryTable.screen.equippedSpells_table.resize();
                    updateButtons();


                }

            }
        });
    }

    public void create_clearButton() {
        clearButton = new ImageButton(masteryTableSkin, "clear_button");
        ImageButton.ImageButtonStyle newStyle = clearButton.getStyle();
        float width = 210 * (Gdx.graphics.getWidth()/1920f);
        float height = 125 * (Gdx.graphics.getHeight()/1080f);

        newStyle.imageUp.setMinHeight(height);
        newStyle.imageUp.setMinWidth(width);
        newStyle.imageDown.setMinHeight(height);
        newStyle.imageDown.setMinWidth(width);
        newStyle.imageOver.setMinHeight(height);
        newStyle.imageOver.setMinWidth(width);
        newStyle.imageDisabled.setMinHeight(height);
        newStyle.imageDisabled.setMinWidth(width);

        clearButton.setStyle(newStyle);
        table.add(clearButton);
        buttonMatrix[1] = clearButton;
        clearButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(!clearButton.isDisabled()) {

                    parts.clear();
                    for (Button button : masteryTable.buttons) {
                        button.setChecked(false);
                    }
                    masteryTable.updateCheckBoxes();
                    updateButtons();

                }

            }
        });
    }

    @Override
    public void navigateDown() {
        masteryTable.screen.activeTable = masteryTable;
        if(x_pos < 1) {
            masteryTable.x_position = 0;
            masteryTable.y_position = 3;
        } else {
            masteryTable.x_position = 2;
            masteryTable.y_position = 3;
        }
        masteryTable.updateSelectedButton();
    }

    @Override
    public void navigateUp() {

    }

    @Override
    public void navigateLeft() {
        x_pos--;
        if(x_pos < 0) {
            masteryTable.screen.activeTable = masteryTable.screen.equippedSpells_table;
            if(player.spellbook.equippedSpells.size() > 1) {
                masteryTable.screen.equippedSpells_table.x_position = 1;
            } else {
                masteryTable.screen.equippedSpells_table.x_position = 0;
            }
            masteryTable.screen.equippedSpells_table.y_position = 0;
            masteryTable.screen.equippedSpells_table.updateSelectedButton();
            return;
        }
        updateSelectedButton();
    }

    @Override
    public void navigateRight() {
        x_pos++;
        if(x_pos > 1) {
            x_pos = 1;
        }
        updateSelectedButton();
    }

    @Override
    public void pressSelectedButton() {
        if(x_pos == 0 && !mixButton.isDisabled()) {
            Spell spell = getMixedSpell(parts);
            spell.learn();

            parts.clear();
            for (Button button : masteryTable.buttons) {
                button.setChecked(false);
            }
            masteryTable.updateCheckBoxes();
            masteryTable.screen.equippedSpells_table.resize();
            updateButtons();
        }

        if(x_pos == 1 && !clearButton.isDisabled()) {
            parts.clear();
            for (Button button : masteryTable.buttons) {
                button.setChecked(false);
            }
            masteryTable.updateCheckBoxes();
            updateButtons();
        }
    }

    @Override
    public void resize() {

    }

    public void dispose() {
        mixButton.remove();
        clearButton.remove();
        labelTable.clear();
        labelTable.remove();
        table.clear();
        table.remove();
    }

    public String getPartsString() {

        StringBuilder s = new StringBuilder();
        if(!parts.isEmpty()) {
            int counter = 0;
            for(SpellUtils.Spell_Name part : parts) {
                s.append(getSpellString(part));
                if((counter == 0 && parts.size() > 1) || (counter == 1 && parts.size() > 2)) {
                    s.append(" + ");
                }
                counter++;
            }
        }

        return s.toString();
    }

}
