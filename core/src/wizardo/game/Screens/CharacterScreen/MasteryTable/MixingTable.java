package wizardo.game.Screens.CharacterScreen.MasteryTable;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import wizardo.game.Display.MenuTable;
import wizardo.game.Items.Equipment.Ring.Epic_OculusRing;
import wizardo.game.Screens.CharacterScreen.Anims.SpellCreation_Anim;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.GearPanel;
import wizardo.game.Screens.CharacterScreen.SpellLabel.SpellLabel;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Wizardo;

import java.util.ArrayList;
import java.util.Collections;

import static wizardo.game.Resources.Skins.masteryTableSkin;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Spells.SpellMixer.get_mixed_spell;
import static wizardo.game.Spells.SpellMixer.inverted_spell_exists;
import static wizardo.game.Wizardo.player;

public class MixingTable extends MenuTable {

    public ArrayList<SpellUtils.Spell_Name> parts;

    public ImageButton mixButton;
    public ImageButton clearButton;
    public ImageButton forgetButton;
    public ImageButton invertButton;

    Button[] buttonMatrix = new Button[3];
    float button_width = 155;
    float button_height = 95;
    public int x_pos;

    public MasteryTable masteryTable;

    public MixingTable(Stage stage, Skin skin, Wizardo game, MasteryTable masteryTable) {
        super(stage, skin, game);
        this.masteryTable = masteryTable;
        this.parts = masteryTable.parts;

        create_mixButton();
        create_clearButton();
        create_invertButton();
        //create_forgetButton();

        adjustTableSize();

        updateButtons();

        stage.addActor(table);
    }

    public void adjustTableSize() {
        int x_pos2 = Math.round(1344 * xRatio);
        int y_pos2 = Math.round(90 * yRatio);
        float width2 = 536f * xRatio;
        float height2 = 90 * yRatio;

        table.setPosition(x_pos2, y_pos2);
        table.setWidth(width2);
        table.setHeight(height2);
    }

    public void updateSelectedButton() {
        masteryTable.screen.selectedButton = buttonMatrix[x_pos];
    }

    public void updateButtons() {
        mixButton.setDisabled(parts.isEmpty());
        clearButton.setDisabled(parts.isEmpty());
        updateForgetButton();
        updateInvertButton();

        if(!parts.isEmpty()) {
            mixButton.setDisabled(!get_mixed_spell(parts).canMix());
        }

        if (parts.size() == 2 && player.inventory.dual_reagents < 1) {
            mixButton.setDisabled(true);
        } else if (parts.size() == 3 && player.inventory.triple_reagents < 1) {
            mixButton.setDisabled(true);
            if(player.inventory.equippedRing instanceof Epic_OculusRing) {
                Epic_OculusRing ring = (Epic_OculusRing) player.inventory.equippedRing;
                Collections.sort(ring.masteries);
                Collections.sort(parts);
                if(ring.masteries.equals(parts) && get_mixed_spell(parts).canMix()) {
                    mixButton.setDisabled(false);
                }
            }
        }
    }

    public void updateForgetButton() {
        if(masteryTable.screen.selectedSpell_Button != null && forgetButton != null) {
            if (player.spellbook.equippedSpells.contains(masteryTable.screen.selectedSpell_Button.spell) &&
                    player.spellbook.equippedSpells.size() == 1 && player.spellbook.knownSpells.isEmpty()) {
                        forgetButton.setDisabled(true);
            }
        }
    }
    public void updateInvertButton() {
        if(invertButton != null) {
            if(parts.size() != 2) {
                invertButton.setDisabled(true);
            } else {
                invertButton.setDisabled(!inverted_spell_exists(parts));
            }
        }
    }

    public void create_mixButton() {
        mixButton = new ImageButton(masteryTableSkin, "create_button");
        adjustSize(mixButton);
        table.add(mixButton);
        buttonMatrix[0] = mixButton;
        buttons.add(mixButton);

        mixButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!mixButton.isDisabled()) {
                    handleMix();
                }
            }
        });
    }
    public void handleMix() {
        Spell spell = get_mixed_spell(parts);
        spell.learn();

        if(parts.size() == 2) {
            player.inventory.dual_reagents --;
        }
        if(parts.size() == 3) {
            if(player.inventory.equippedRing instanceof Epic_OculusRing ring) {
                Collections.sort(ring.masteries);
                Collections.sort(parts);
                if(!ring.masteries.equals(parts)) {
                    player.inventory.triple_reagents--;
                }
            } else {
                player.inventory.triple_reagents--;
            }
        }

        boolean equipped = player.spellbook.equippedSpells.contains(spell);
        masteryTable.screen.anims.add(new SpellCreation_Anim(spell.anim_element, masteryTable.screen, equipped));

        parts.clear();
        for (Button button : masteryTable.buttons) {
            button.setChecked(false);
        }
        masteryTable.updateCheckBoxes();
        masteryTable.screen.equippedSpells_table.updateSpells();
        masteryTable.screen.knownSpells_table.updateSpells();
        masteryTable.screen.selectedSpell_Button = null;
        updateButtons();
    }
    public void create_clearButton() {
        clearButton = new ImageButton(masteryTableSkin, "clear_button");
        adjustSize(clearButton);
        table.add(clearButton).padLeft(10 * xRatio).padRight(10 * xRatio);
        buttons.add(clearButton);
        buttonMatrix[1] = clearButton;
        clearButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(!clearButton.isDisabled()) {

                    parts.clear();
                    for (Button button : masteryTable.buttons) {
                        button.setChecked(false);
                    }
                    if(masteryTable.screen.spell_label != null) {
                        masteryTable.screen.spell_label.dispose();
                        masteryTable.screen.spell_label = null;
                    }
                    masteryTable.screen.selectedSpell_Button = null;
                    masteryTable.updateCheckBoxes();
                    updateButtons();

                }
            }
        });
    }
    public void create_invertButton() {
        invertButton = new ImageButton(masteryTableSkin, "invert_button");
        adjustSize(invertButton);
        table.add(invertButton);
        buttonMatrix[2] = invertButton;
        buttons.add(invertButton);
        invertButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(!invertButton.isDisabled()) {
                    SpellUtils.Spell_Name temp = parts.getFirst();
                    parts.set(0, parts.get(1));
                    parts.set(1, temp);
                    if(masteryTable.screen.spell_label != null) {
                        masteryTable.screen.spell_label.dispose();
                    }
                    masteryTable.screen.spell_label = new SpellLabel(masteryTable.screen.stage, masteryTable.screen, parts);
                    updateButtons();
                }
            }
        });
    }
    public void handle_thirdButton() {
        if(masteryTable.screen.selectedSpell_Button != null && forgetButton == null) {
            switch_thirdButton();
        }
        if(masteryTable.screen.selectedSpell_Button == null && forgetButton != null) {
            switch_thirdButton();
        }
    }
    public void switch_thirdButton() {
        if(invertButton != null) {
            invertButton.remove();
            buttons.remove(invertButton);
            invertButton = null;
            create_forgetButton();
        } else {
            forgetButton.remove();
            buttons.remove(forgetButton);
            forgetButton = null;
            create_invertButton();
        }
    }
    public void create_forgetButton() {
        forgetButton = new ImageButton(masteryTableSkin, "forget_button");
        adjustSize(forgetButton);
        table.add(forgetButton);
        buttonMatrix[2] = forgetButton;
        buttons.add(forgetButton);
        forgetButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(!forgetButton.isDisabled()) {

                    Spell spell_to_forget = masteryTable.screen.selectedSpell_Button.spell;
                    spell_to_forget.forget();
                    if(masteryTable.screen.spell_label != null) {
                        masteryTable.screen.spell_label.dispose();
                        masteryTable.screen.spell_label = null;
                    }
                    masteryTable.screen.selectedSpell_Button = null;
                    masteryTable.screen.equippedSpells_table.updateSpells();
                    masteryTable.screen.knownSpells_table.updateSpells();
                    updateButtons();
                }
            }
        });
    }

    public void adjustSize(ImageButton button) {
        ImageButton.ImageButtonStyle style = button.getStyle();
        ImageButton.ImageButtonStyle newStyle = new ImageButton.ImageButtonStyle();
        float width = button_width * xRatio;
        float height = button_height * yRatio;

        newStyle.imageUp = new TextureRegionDrawable(((TextureRegionDrawable) style.imageUp).getRegion());
        newStyle.imageDown = new TextureRegionDrawable(((TextureRegionDrawable) style.imageDown).getRegion());
        newStyle.imageOver = new TextureRegionDrawable(((TextureRegionDrawable) style.imageOver).getRegion());
        newStyle.imageDisabled = new TextureRegionDrawable(((TextureRegionDrawable) style.imageDisabled).getRegion());

        newStyle.imageUp.setMinHeight(height);
        newStyle.imageUp.setMinWidth(width);
        newStyle.imageDown.setMinHeight(height);
        newStyle.imageDown.setMinWidth(width);
        newStyle.imageOver.setMinHeight(height);
        newStyle.imageOver.setMinWidth(width);
        newStyle.imageDisabled.setMinHeight(height);
        newStyle.imageDisabled.setMinWidth(width);

        button.setStyle(newStyle);
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
        } else if(x_pos < 0) {
            masteryTable.screen.activeTable = masteryTable.screen.knownSpells_table;
            if(player.spellbook.knownSpells.size() == 1) {
                masteryTable.screen.knownSpells_table.x_pos = 0;
            } else {
                masteryTable.screen.knownSpells_table.x_pos = 1;
            }
            masteryTable.screen.knownSpells_table.y_pos = 0;
            masteryTable.screen.knownSpells_table.updateSelectedButton();
        } else {
            updateSelectedButton();
        }
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
            handleMix();
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
            masteryTable.screen.selectedSpell_Button.spell.forget();
            masteryTable.screen.selectedSpell_Button = null;
            masteryTable.screen.equippedSpells_table.updateSpells();
            masteryTable.screen.knownSpells_table.updateSpells();
            updateButtons();
        }
    }

    @Override
    public void resize() {
        adjustTableSize();
        for(Button button : buttons) {
            ImageButton butt = (ImageButton) button;
            adjustSize(butt);
        }
    }

    public void swapToEquippedTable() {
        if(!player.spellbook.equippedSpells.isEmpty()) {
            masteryTable.screen.activeTable = masteryTable.screen.equippedSpells_table;
            if (player.spellbook.equippedSpells.size() == 1) {
                masteryTable.screen.equippedSpells_table.x_pos = 0;
                masteryTable.screen.equippedSpells_table.y_pos = 0;
                masteryTable.screen.equippedSpells_table.updateSelectedButton();
            }

            if (player.spellbook.equippedSpells.size() > 1) {
                masteryTable.screen.equippedSpells_table.x_pos = 1;
                masteryTable.screen.equippedSpells_table.y_pos = 0;
                masteryTable.screen.equippedSpells_table.updateSelectedButton();
            }
        } else {
            masteryTable.screen.activeTable = masteryTable.screen.inventory_table;
            masteryTable.screen.selectedButton = masteryTable.screen.inventory_table.buttonsMatrix[4][0];
            masteryTable.screen.inventory_table.x_pos = 4;
            masteryTable.screen.inventory_table.y_pos = 0;
            if(masteryTable.screen.inventory_table.buttonsMatrix[4][0].piece != null) {
                masteryTable.screen.activePanel = new GearPanel(masteryTable.screen.panelStage,
                        masteryTable.screen.inventory_table.buttonsMatrix[4][0].piece, false,
                        masteryTable.screen.inventory_table.buttonsMatrix[4][0]);
            }
        }
    }

    public void dispose() {
        mixButton.remove();
        clearButton.remove();
        table.clear();
        table.remove();
    }

}
