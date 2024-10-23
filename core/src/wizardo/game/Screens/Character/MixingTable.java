package wizardo.game.Screens.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import wizardo.game.Account.Unlocked;
import wizardo.game.Display.MenuTable;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Wizardo;

import java.util.ArrayList;
import java.util.HashSet;

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


    public MasteryTable masteryTable;

    public MixingTable(Stage stage, Skin skin, Wizardo game, MasteryTable masteryTable) {
        super(stage, skin, game);
        this.masteryTable = masteryTable;
        this.parts = masteryTable.parts;
        createTable();

        create_mixButton();
        create_clearButton();

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
                    updateButtons();

                    System.out.println(player.spellbook.equippedSpells);
                    System.out.println(player.spellbook.knownSpells);

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

    public void dispose() {
        mixButton.remove();
        clearButton.remove();
        labelTable.clear();
        labelTable.remove();
        table.clear();
        table.remove();
    }

    public String getPartsString() {

        String s = "";
        if(!parts.isEmpty()) {
            int counter = 0;
            for(SpellUtils.Spell_Name part : parts) {
                s += getSpellString(part);
                if((counter == 0 && parts.size() > 1) || (counter == 1 && parts.size() > 2)) {
                    s += " + ";
                }
                counter++;
            }
        }

        return s;
    }

}
