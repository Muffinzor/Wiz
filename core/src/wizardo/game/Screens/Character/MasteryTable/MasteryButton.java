package wizardo.game.Screens.Character.MasteryTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import wizardo.game.Spells.SpellUtils.*;

import java.util.ArrayList;

import static wizardo.game.Screens.BaseScreen.controllerActive;

public class MasteryButton extends CheckBox {

    Spell_Name spell;
    Skin skin;
    CheckBoxStyle style;
    MasteryTable table;
    ArrayList<Spell_Name> parts;

    public MasteryButton(String text, Skin skin, Spell_Name spell, MasteryTable table) {
        super(text, skin);

        this.skin = skin;
        this.spell = spell;
        this.parts = table.parts;
        this.table = table;
        pickStyle();
        adjustSize();
        addClickListener(spell);

    }

    public void pickStyle() {
        CheckBoxStyle style = null;
        switch (spell) {
            case FROSTBOLT -> style = skin.get("frostbolt", CheckBox.CheckBoxStyle.class);
            case ICESPEAR -> style = skin.get("icespear", CheckBox.CheckBoxStyle.class);
            case FROZENORB -> style = skin.get("frozenorb", CheckBox.CheckBoxStyle.class);
            case FLAMEJET -> style = skin.get("flamejet", CheckBox.CheckBoxStyle.class);
            case FIREBALL -> style = skin.get("fireball", CheckBox.CheckBoxStyle.class);
            case OVERHEAT -> style = skin.get("overheat", CheckBox.CheckBoxStyle.class);
            case CHARGEDBOLTS -> style = skin.get("chargedbolt", CheckBox.CheckBoxStyle.class);
            case CHAIN -> style = skin.get("chainlightning", CheckBox.CheckBoxStyle.class);
            case THUNDERSTORM -> style = skin.get("thunderstorm", CheckBox.CheckBoxStyle.class);
            case MISSILES -> style = skin.get("arcanemissile", CheckBox.CheckBoxStyle.class);
            case BEAM -> style = skin.get("energybeam", CheckBox.CheckBoxStyle.class);
            case RIFTS -> style = skin.get("rifts", CheckBox.CheckBoxStyle.class);
        }
        this.style = style;
    }
    public void adjustSize() {

        float xRatio = Gdx.graphics.getWidth() / 1920f;
        float yRatio = Gdx.graphics.getHeight() / 1080f;

        CheckBox.CheckBoxStyle style = this.style;
        CheckBox.CheckBoxStyle newStyle = new CheckBox.CheckBoxStyle();
        newStyle.checkboxOn = new TextureRegionDrawable(((TextureRegionDrawable) style.checkboxOn).getRegion());
        newStyle.checkboxOff = new TextureRegionDrawable(((TextureRegionDrawable) style.checkboxOff).getRegion());
        newStyle.checkboxOver = new TextureRegionDrawable(((TextureRegionDrawable) style.checkboxOver).getRegion());
        newStyle.checkboxOnOver = new TextureRegionDrawable(((TextureRegionDrawable) style.checkboxOnOver).getRegion());
        newStyle.checkboxOffDisabled = new TextureRegionDrawable(((TextureRegionDrawable) style.checkboxOffDisabled).getRegion());
        newStyle.checkboxOnDisabled = new TextureRegionDrawable(((TextureRegionDrawable) style.checkboxOnDisabled).getRegion());

        newStyle.font = skin.getFont("Messy16");

        float WIDTH = xRatio * 100;
        float HEIGHT = yRatio * 100;

        newStyle.checkboxOn.setMinWidth(WIDTH);
        newStyle.checkboxOn.setMinHeight(HEIGHT);

        newStyle.checkboxOff.setMinWidth(WIDTH);
        newStyle.checkboxOff.setMinHeight(HEIGHT);

        newStyle.checkboxOver.setMinWidth(WIDTH);
        newStyle.checkboxOver.setMinHeight(HEIGHT);

        newStyle.checkboxOnOver.setMinWidth(WIDTH);
        newStyle.checkboxOnOver.setMinHeight(HEIGHT);

        newStyle.checkboxOffDisabled.setMinWidth(WIDTH);
        newStyle.checkboxOffDisabled.setMinHeight(HEIGHT);

        newStyle.checkboxOnDisabled.setMinWidth(WIDTH);
        newStyle.checkboxOnDisabled.setMinHeight(HEIGHT);

        setStyle(newStyle);

    }

    public void handleClick() {

        if(controllerActive) {

            this.setChecked(!isChecked());
        }

        if(!isDisabled()) {

            if(parts.contains(spell)) {
                parts.remove(spell);
            } else {
                parts.add(spell);
            }
            table.updateCheckBoxes();
            table.mixingTable.updateButtons();

        }
    }

    public void handleHover() {

    }

    public void addClickListener(Spell_Name spell) {

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleClick();
                table.mixingTable.updateButtons();
            }
        });
    }

    public String toString() {
        return spell.name();
    }

}
