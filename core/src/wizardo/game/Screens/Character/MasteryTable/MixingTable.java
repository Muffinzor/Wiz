package wizardo.game.Screens.Character.MasteryTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.Character.Anims.SpellCreation_Anim;
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
    public ImageButton forgetButton;

    public Table labelTable;
    public Label spell_parts;
    public Label status;

    Button[] buttonMatrix = new Button[3];
    float button_width = 155;
    float button_height = 95;
    public int x_pos;

    public MasteryTable masteryTable;

    public MixingTable(Stage stage, Skin skin, Wizardo game, MasteryTable masteryTable) {
        super(stage, skin, game);
        this.masteryTable = masteryTable;
        this.parts = masteryTable.parts;
        createTable();

        create_mixButton();
        create_clearButton();
        create_forgetButton();

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
        updateForgetButton();


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

    public void updateForgetButton() {
        forgetButton.setDisabled(masteryTable.screen.selectedSpell_Button == null);
        if(masteryTable.screen.selectedSpell_Button != null) {
            if (player.spellbook.equippedSpells.contains(masteryTable.screen.selectedSpell_Button.spell) &&
                    player.spellbook.equippedSpells.size() == 1 && player.spellbook.knownSpells.isEmpty()) {
                forgetButton.setDisabled(true);
            }
        }
    }

    public void create_mixButton() {
        mixButton = new ImageButton(masteryTableSkin, "create_button");
        ImageButton.ImageButtonStyle newStyle = mixButton.getStyle();
        float width = button_width * (Gdx.graphics.getWidth()/1920f);
        float height = button_height * (Gdx.graphics.getHeight()/1080f);

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

                    boolean equipped = player.spellbook.equippedSpells.contains(spell);
                    masteryTable.screen.anims.add(new SpellCreation_Anim(spell.anim_element, masteryTable.screen, equipped));

                    parts.clear();
                    for (Button button : masteryTable.buttons) {
                        button.setChecked(false);
                    }
                    masteryTable.updateCheckBoxes();
                    masteryTable.screen.equippedSpells_table.resize();
                    masteryTable.screen.knownSpells_table.resize();
                    masteryTable.screen.selectedSpell_Button = null;
                    updateButtons();
                }

            }
        });
    }
    public void create_clearButton() {
        clearButton = new ImageButton(masteryTableSkin, "clear_button");
        ImageButton.ImageButtonStyle newStyle = clearButton.getStyle();
        float width = button_width * (Gdx.graphics.getWidth()/1920f);
        float height = button_height * (Gdx.graphics.getHeight()/1080f);

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
    public void create_forgetButton() {
        forgetButton = new ImageButton(masteryTableSkin, "forget_button");
        ImageButton.ImageButtonStyle newStyle = forgetButton.getStyle();
        float width = button_width * (Gdx.graphics.getWidth()/1920f);
        float height = button_height * (Gdx.graphics.getHeight()/1080f);

        newStyle.imageUp.setMinHeight(height);
        newStyle.imageUp.setMinWidth(width);
        newStyle.imageDown.setMinHeight(height);
        newStyle.imageDown.setMinWidth(width);
        newStyle.imageOver.setMinHeight(height);
        newStyle.imageOver.setMinWidth(width);
        newStyle.imageDisabled.setMinHeight(height);
        newStyle.imageDisabled.setMinWidth(width);

        forgetButton.setStyle(newStyle);
        table.add(forgetButton);
        buttonMatrix[2] = forgetButton;
        forgetButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(!forgetButton.isDisabled()) {

                    forgetSpell();

                    masteryTable.screen.selectedSpell_Button = null;
                    masteryTable.screen.equippedSpells_table.resize();
                    masteryTable.screen.knownSpells_table.resize();
                    updateButtons();

                }

            }
        });
    }

    public void forgetSpell() {
        Spell spell_to_forget = masteryTable.screen.selectedSpell_Button.spell;
        boolean equipped = player.spellbook.equippedSpells.contains(spell_to_forget);

        if(equipped && player.spellbook.equippedSpells.size() == 1) {
            player.spellbook.equippedSpells.remove(spell_to_forget);
            Spell spell_to_move = player.spellbook.knownSpells.getFirst();
            player.spellbook.equippedSpells.add(spell_to_move);
            player.spellbook.knownSpells.remove(spell_to_move);
            return;
        }


        if(equipped) {
            player.spellbook.equippedSpells.remove(spell_to_forget);
            ArrayList<String> spells_equipped = new ArrayList<>();

            for(Spell spell : player.spellbook.equippedSpells) {
                spells_equipped.add(spell.name);
            }

            for(Spell spell : player.spellbook.knownSpells) {
                if(!spells_equipped.contains(spell.name)) {
                    player.spellbook.equippedSpells.add(spell);
                    player.spellbook.knownSpells.remove(spell);
                    return;
                }
            }
        }

        if(!equipped) {
            player.spellbook.knownSpells.remove(spell_to_forget);
        }

    }

    @Override
    public void navigateDown() {
        masteryTable.screen.activeTable = masteryTable;
        masteryTable.x_position = x_pos;
        masteryTable.y_position = 3;
        masteryTable.updateSelectedButton();
    }

    @Override
    public void navigateUp() {

    }

    @Override
    public void navigateLeft() {
        x_pos--;
        if(x_pos < 0 && player.spellbook.knownSpells.isEmpty()) {
            swapToEquippedTable();
            return;
        } else if(x_pos < 0) {
            masteryTable.screen.activeTable = masteryTable.screen.knownSpells_table;
            if(player.spellbook.knownSpells.size() == 1) {
                masteryTable.screen.knownSpells_table.x_pos = 0;
            } else {
                masteryTable.screen.knownSpells_table.x_pos = 1;
            }
            masteryTable.screen.knownSpells_table.y_pos = 0;
            masteryTable.screen.knownSpells_table.updateSelectedButton();
            return;
        }
        updateSelectedButton();
    }

    @Override
    public void navigateRight() {
        x_pos++;
        if(x_pos > 2) {
            x_pos = 2;
        }
        updateSelectedButton();
    }

    @Override
    public void pressSelectedButton() {
        if(x_pos == 0 && !mixButton.isDisabled()) {
            Spell spell = getMixedSpell(parts);
            spell.learn();

            boolean equipped = player.spellbook.equippedSpells.contains(spell);
            masteryTable.screen.anims.add(new SpellCreation_Anim(spell.anim_element, masteryTable.screen, equipped));

            parts.clear();
            for (Button button : masteryTable.buttons) {
                button.setChecked(false);
            }
            masteryTable.updateCheckBoxes();
            masteryTable.screen.equippedSpells_table.resize();
            masteryTable.screen.knownSpells_table.resize();
            masteryTable.screen.selectedSpell_Button = null;
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

        if(x_pos == 2 && !forgetButton.isDisabled()) {
            forgetSpell();

            masteryTable.screen.selectedSpell_Button = null;
            masteryTable.screen.equippedSpells_table.resize();
            masteryTable.screen.knownSpells_table.resize();
            updateButtons();
        }
    }

    @Override
    public void resize() {

    }

    public void swapToEquippedTable() {
        masteryTable.screen.activeTable = masteryTable.screen.equippedSpells_table;
        if(player.spellbook.equippedSpells.size() == 1) {
            masteryTable.screen.equippedSpells_table.x_position = 0;
            masteryTable.screen.equippedSpells_table.y_position = 0;
            masteryTable.screen.equippedSpells_table.updateSelectedButton();
        }

        if(player.spellbook.equippedSpells.size() > 1) {
            masteryTable.screen.equippedSpells_table.x_position = 1;
            masteryTable.screen.equippedSpells_table.y_position = 0;
            masteryTable.screen.equippedSpells_table.updateSelectedButton();
        }
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
