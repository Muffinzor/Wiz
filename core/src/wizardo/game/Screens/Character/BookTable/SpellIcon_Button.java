package wizardo.game.Screens.Character.BookTable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.Character.Anims.SpellSwap_Anim;
import wizardo.game.Screens.Character.CharacterScreen;
import wizardo.game.Spells.Spell;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Resources.Skins.bookTableSkin;
import static wizardo.game.Screens.BaseScreen.screenRatio;
import static wizardo.game.Wizardo.player;

public class SpellIcon_Button extends ImageButton {

    CharacterScreen screen;
    public Spell spell;

    public SpellIcon_Button(Spell spell, boolean equipped, CharacterScreen screen) {
        super(getStyleForSpell(spell, bookTableSkin, equipped));
        this.screen = screen;
        this.spell = spell;

        addClickListener();
    }

    private static ImageButtonStyle getStyleForSpell(Spell spell, Skin skin, boolean equipped) {
        String name = spell.name;
        ImageButtonStyle style = null;

        switch(name) {
            case "Fireball" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("fireball_fire", ImageButtonStyle.class);
                    case FROST -> style = skin.get("fireball_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("fireball_arcane", ImageButtonStyle.class);
                    case LIGHTNING -> style = skin.get("fireball_lightning", ImageButtonStyle.class);
                }
                break;

            case "Flamejet" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("flamejet_fire", ImageButtonStyle.class);
                    case FROST -> style = skin.get("flamejet_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("flamejet_arcane", ImageButtonStyle.class);
                }
                break;

            case "Chain Lightning" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("chainlightning_fire", ImageButtonStyle.class);
                    case FROST -> style = skin.get("chainlightning_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("chainlightning_arcane", ImageButtonStyle.class);
                    case LIGHTNING -> style = skin.get("chainlightning_lightning", ImageButtonStyle.class);
                }
                break;

            case "Rifts" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("rift_fire", ImageButtonStyle.class);
                    case FROST -> style = skin.get("rift_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("rift_arcane", ImageButtonStyle.class);
                    case LIGHTNING -> style = skin.get("rift_lightning", ImageButtonStyle.class);
                }
                break;
        }

        ImageButtonStyle newStyle = new ImageButtonStyle(style);
        newStyle.imageUp = new TextureRegionDrawable(((TextureRegionDrawable) style.imageUp).getRegion());

        float SPELLBUTTON_SIZE = 128 * screenRatio;

        if(!equipped) {
            SPELLBUTTON_SIZE *= 0.8f;
        }

        newStyle.imageUp.setMinWidth(SPELLBUTTON_SIZE);
        newStyle.imageUp.setMinHeight(SPELLBUTTON_SIZE);

        return newStyle;
    }

    public Vector2 getCenter() {

        float table_x = getParent().getX();
        float table_y = getParent().getY();

        float own_x = getX();
        float own_y = getY();

        float actual_x = table_x + own_x + getWidth()/2f;
        float actual_y = table_y + own_y + getHeight()/2f;

        return new Vector2(actual_x, actual_y);
    }

    public void addClickListener() {

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleClick();
            }
        });
    }

    public void handleClick() {
        screen.selected_scale = 1;

        if(screen.selectedSpell_Button != null) {
            if (getParent().equals(screen.knownSpells_table.table) &&
                    screen.selectedSpell_Button.getParent().equals(screen.equippedSpells_table.table)) {
                attemptSwap(true);
                return;
            } else if (getParent().equals(screen.equippedSpells_table.table) &&
                    screen.selectedSpell_Button.getParent().equals(screen.knownSpells_table.table)) {
                attemptSwap(false);
                return;
            }
        }

        if(screen.selectedSpell_Button != this) {
            screen.selectedSpell_Button = this;
        } else {
            screen.selectedSpell_Button = null;
        }

        screen.mastery_table.mixingTable.updateButtons();

    }

    public void attemptSwap(boolean selected_is_equipped) {
        Spell spell_equipped;
        Spell spell_known;
        if(selected_is_equipped) {
            spell_equipped = screen.selectedSpell_Button.spell;
            spell_known = this.spell;
        } else {
            spell_equipped = this.spell;
            spell_known = screen.selectedSpell_Button.spell;
        }

        boolean canSwap = true;
        int equipped_index = player.spellbook.equippedSpells.indexOf(spell_equipped);
        int known_index = player.spellbook.knownSpells.indexOf(spell_known);

        if(spell_equipped.name.equals(spell_known.name)) {

            player.spellbook.equippedSpells.set(equipped_index, spell_known);
            player.spellbook.knownSpells.set(known_index, spell_equipped);
            if(selected_is_equipped) {
                createSwapAnims(this, screen.selectedSpell_Button, spell_equipped, spell_known);
            } else {
                createSwapAnims(screen.selectedSpell_Button, this, spell_equipped, spell_known);
            }

            screen.knownSpells_table.resize();
            screen.equippedSpells_table.resize();
            screen.selectedSpell_Button = null;

            return;
        }

        for (Spell spell : player.spellbook.equippedSpells) {
            if(spell.name.equals(spell_known.name)) {
                canSwap = false;
            }
        }

        if(canSwap) {
            player.spellbook.equippedSpells.set(equipped_index, spell_known);
            player.spellbook.knownSpells.set(known_index, spell_equipped);
            if(selected_is_equipped) {
                createSwapAnims(this, screen.selectedSpell_Button, spell_equipped, spell_known);
            } else {
                createSwapAnims(screen.selectedSpell_Button, this, spell_equipped, spell_known);
            }
            screen.knownSpells_table.resize();
            screen.equippedSpells_table.resize();
            screen.selectedSpell_Button = null;
        } else {
            screen.selectedSpell_Button = this;
        }

    }

    public void createSwapAnims(SpellIcon_Button button1, SpellIcon_Button button2, Spell spell1, Spell spell2) {
        SpellSwap_Anim anim1 = new SpellSwap_Anim(button1.getCenter(), screen, spell1.anim_element);
        screen.anims.add(anim1);

        SpellSwap_Anim anim2 = new SpellSwap_Anim(button2.getCenter(), screen, spell2.anim_element);
        screen.anims.add(anim2);
    }
}
