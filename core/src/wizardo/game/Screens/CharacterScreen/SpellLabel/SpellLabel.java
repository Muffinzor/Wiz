package wizardo.game.Screens.CharacterScreen.SpellLabel;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import wizardo.game.Resources.Skins;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellMixer;
import wizardo.game.Spells.SpellUtils;

import java.util.ArrayList;

import static wizardo.game.Resources.Skins.*;
import static wizardo.game.Resources.SpellIcons.background_blank;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Screens.CharacterScreen.BookTable.SpellIcon_Button.getSpellSprite;
import static wizardo.game.Spells.SpellUtils.get_base_color;

public class SpellLabel {

    Stage stage;
    CharacterScreen screen;
    Table type_table;
    Table element_table;
    Table parts_table;
    Table parts_table2;

    ArrayList<Label> labels;
    ArrayList<SpellUtils.Spell_Name> parts;

    Spell spell;

    public SpellLabel(Stage stage, CharacterScreen screen, ArrayList<SpellUtils.Spell_Name> parts) {
        this.stage = stage;
        this.screen = screen;
        this.spell = SpellMixer.get_mixed_spell(parts);
        this.parts = parts;

        type_table = new Table();
        element_table = new Table();
        parts_table = new Table();
        parts_table2 = new Table();

        create_type_table();
        create_element_table();
        create_parts_tables();

        stage.addActor(type_table);
        stage.addActor(element_table);
        stage.addActor(parts_table);
        stage.addActor(parts_table2);
    }

    public void drawFrame() {
        Spell spell_to_draw;
        if(spell != null) {
            spell_to_draw = spell;
        } else {
            spell_to_draw = SpellMixer.get_mixed_spell(parts);
        }

        int x_pos = Math.round(1750 * xRatio);
        int y_pos = Math.round(280 * yRatio);
        Sprite back_frame = background_blank;
        back_frame.setCenter(x_pos, y_pos);
        back_frame.draw(screen.batch);

        Sprite spell_frame = getSpellSprite(spell_to_draw);
        spell_frame.setCenter(x_pos, y_pos);
        spell_frame.draw(screen.batch);
    }

    public void create_type_table() {
        int x_pos = Math.round(1380 * xRatio);
        int y_pos = Math.round(370 * yRatio);

        type_table.clear();
        type_table.setPosition(x_pos, y_pos);
        type_table.align(Align.left);

        Label type = new Label("Type: ", inventorySkin, "Stats_Title");
        type.setFontScale(getFontScale());
        type_table.add(type);

        Label type_name = new Label(spell.string_name, inventorySkin, "Stats_Title");
        type_name.setFontScale(getFontScale());
        type_name.setColor(mainMenuSkin.getColor("SoftGreen"));
        type_table.add(type_name).padRight(40 * xRatio);

        Label status = new Label("Status: ", inventorySkin, "Stats_Title");
        status.setFontScale(getFontScale());
        type_table.add(status);

        Label status_type = new Label("Unknown", inventorySkin, "Stats_Title");
        status_type.setFontScale(getFontScale());
        status_type.setColor(Skins.light_orange);
        type_table.add(status_type);
    }

    public void create_element_table() {
        int x_pos = Math.round(1380 * xRatio);
        int y_pos = Math.round(325 * yRatio);

        element_table.clear();
        element_table.setPosition(x_pos, y_pos);
        element_table.align(Align.left);

        Label element = new Label("Element: ", inventorySkin, "Stats_Title");
        element.setFontScale(getFontScale());
        element_table.add(element);

        Label element_name = new Label(spell.get_element_string(), inventorySkin, "Stats_Title");
        element_name.setFontScale(getFontScale());
        element_name.setColor(spell.get_element_color());
        element_table.add(element_name);
    }

    public void create_parts_tables() {
        int x_pos = Math.round(1380 * xRatio);
        int y_pos = Math.round(280 * yRatio);

        parts_table.clear();
        parts_table.setPosition(x_pos, y_pos);
        parts_table.align(Align.left);
        Label parts = new Label("Parts: ", inventorySkin, "Stats_Title");
        parts.setFontScale(getFontScale());
        parts_table.add(parts);

        int x_pos2 = Math.round(1470 * xRatio);
        int y_pos2 = Math.round(292 * yRatio);
        parts_table2.clear();
        parts_table2.setPosition(x_pos2, y_pos2);
        parts_table2.align(Align.topLeft);

        create_parts_tokens();
    }

    public void create_parts_tokens() {
        if(spell != null) {
            for (int i = 0; i < spell.spellParts.size(); i++) {
                Label part = new Label(SpellUtils.getSpellString(spell.spellParts.get(i)), inventorySkin, "Stats_Title");
                part.setFontScale(getFontScale() * 0.9f);
                part.setColor(get_base_color(spell.spellParts.get(i)));
                part.setAlignment(Align.left);
                parts_table2.add(part).align(Align.left).row();
            }
        }
    }

    public void adjustFontSize(Label label) {
        label.setFontScale(xRatio);
    }

    public float getFontScale() {
        return Math.max(0.75f, xRatio);
    }

    public void resize() {
        for(Label label : labels) {
            adjustFontSize(label);
        }
    }

    public void dispose() {
        type_table.clear();
        type_table.remove();
        element_table.clear();
        element_table.remove();
        parts_table.clear();
        parts_table.remove();
        parts_table2.clear();
        parts_table2.remove();
    }


}
