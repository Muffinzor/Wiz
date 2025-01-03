package wizardo.game.Screens.CharacterScreen.BookTable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import wizardo.game.Screens.CharacterScreen.Anims.SpellSwap_Anim;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Spells.Spell;

import static wizardo.game.Resources.Skins.bookTableSkin;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Wizardo.player;

public class SpellIcon_Button extends ImageButton {

    CharacterScreen screen;
    public Spell spell;
    public boolean equipped;

    public SpellIcon_Button(Spell spell, boolean equipped, CharacterScreen screen) {
        super(getStyleForSpell(spell, bookTableSkin));
        adjustSize();
        this.equipped = equipped;
        this.screen = screen;
        this.spell = spell;

        addClickListener();
    }

    private static ImageButtonStyle getStyleForSpell(Spell spell, Skin skin) {
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

            case "Overheat" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("overheat_fire", ImageButtonStyle.class);
                    case FROST -> style = skin.get("overheat_frost", ImageButtonStyle.class);
                    case LIGHTNING -> style = skin.get("overheat_lightning", ImageButtonStyle.class);
                }
                break;

            case "Charged Bolts" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("chargedbolts_fire", ImageButtonStyle.class);
                    case FROST, COLDLITE -> style = skin.get("chargedbolts_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("chargedbolts_arcane", ImageButtonStyle.class);
                    case LIGHTNING -> style = skin.get("chargedbolts_lightning", ImageButtonStyle.class);
                }
                break;

            case "Chain Lightning" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("chainlightning_fire", ImageButtonStyle.class);
                    case FROST, COLDLITE -> style = skin.get("chainlightning_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("chainlightning_arcane", ImageButtonStyle.class);
                    case LIGHTNING -> style = skin.get("chainlightning_lightning", ImageButtonStyle.class);
                }
                break;

            case "Thunderstorm" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("thunderstorm_fire", ImageButtonStyle.class);
                    case FROST, COLDLITE -> style = skin.get("thunderstorm_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("thunderstorm_arcane", ImageButtonStyle.class);
                    case LIGHTNING-> style = skin.get("thunderstorm_lightning", ImageButtonStyle.class);
                }
                break;

            case "Frostbolts" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("frostbolt_fire", ImageButtonStyle.class);
                    case FROST -> style = skin.get("frostbolt_frost", ImageButtonStyle.class);
                    case LIGHTNING, COLDLITE -> style = skin.get("frostbolt_frost", ImageButtonStyle.class);
                }
                break;

            case "Ice Spear" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("icespear_fire", ImageButtonStyle.class);
                    case FROST -> style = skin.get("icespear_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("icespear_arcane", ImageButtonStyle.class);
                    case LIGHTNING -> style = skin.get("icespear_lightning", ImageButtonStyle.class);
                }
                break;

            case "Frozen Orb" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("frozenorb_fire", ImageButtonStyle.class);
                    case FROST -> style = skin.get("frozenorb_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("frozenorb_arcane", ImageButtonStyle.class);
                    case LIGHTNING -> style = skin.get("frozenorb_lightning", ImageButtonStyle.class);
                    case COLDLITE -> style = skin.get("frozenorb_coldlite", ImageButtonStyle.class);
                }
                break;

            case "Arcane Missiles" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("arcanemissile_fire", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("arcanemissile_arcane", ImageButtonStyle.class);
                    case FROST -> style = skin.get("arcanemissile_frost", ImageButtonStyle.class);

                }
                break;

            case "Energy Beam" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("energybeam_fire", ImageButtonStyle.class);
                    case FROST -> style = skin.get("energybeam_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("energybeam_arcane", ImageButtonStyle.class);
                    case LIGHTNING -> style = skin.get("energybeam_lightning", ImageButtonStyle.class);
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

            case "Lasers" :
                switch(spell.anim_element) {
                    case FIRE -> style = skin.get("laser_fire", ImageButtonStyle.class);
                    case FROST -> style = skin.get("laser_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("laser_arcane", ImageButtonStyle.class);
                    case LIGHTNING -> style = skin.get("laser_lightning", ImageButtonStyle.class);
                }
                break;

            case "Blizzard" :
                switch(spell.anim_element) {
                    case FROST -> style = skin.get("blizzard_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("blizzard_arcane", ImageButtonStyle.class);
                }
                break;

            case "Dragonbreath" :
                switch(spell.anim_element) {
                    case FROST -> style = skin.get("dragonbreath_frost", ImageButtonStyle.class);
                    case FIRE -> style = skin.get("dragonbreath_fire", ImageButtonStyle.class);
                }
                break;

            case "Meteor Shower" :
                switch(spell.anim_element) {
                    case LIGHTNING -> style = skin.get("meteorshower_lightning", ImageButtonStyle.class);
                    case FIRE -> style = skin.get("meteorshower_fire", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("meteorshower_arcane", ImageButtonStyle.class);

                }
                break;

            case "Judgement" :
                switch(spell.anim_element) {
                    case FROST -> style = skin.get("artillery_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("artillery_arcane", ImageButtonStyle.class);
                    case FIRE -> style = skin.get("artillery_fire", ImageButtonStyle.class);
                }
                break;

            case "Energy Rain" :
                switch(spell.anim_element) {
                    case FROST -> style = skin.get("energyrain_frost", ImageButtonStyle.class);
                    case ARCANE -> style = skin.get("energyrain_arcane", ImageButtonStyle.class);
                    case FIRE -> style = skin.get("energyrain_fire", ImageButtonStyle.class);
                    case LIGHTNING -> style = skin.get("energyrain_lightning", ImageButtonStyle.class);

                }
                break;

            case "Frost Nova" :
                style = skin.get("frostnova_frost", ImageButtonStyle.class);
                break;

            case "Orbit" :
                style = skin.get("orbitingice", ImageButtonStyle.class);
                break;

            case "Lightning Hands" :
                style = skin.get("lightninghands", ImageButtonStyle.class);
                break;

            case "Forked Lightning" :
                style = skin.get("forkedlightning", ImageButtonStyle.class);
                break;

            case "Celestial Strike" :
                style = skin.get("celestialstrike", ImageButtonStyle.class);
                break;

            case "Repulsion Field" :
                style = skin.get("repulsionfield", ImageButtonStyle.class);
                break;
        }

        return style;
    }

    public void adjustSize() {
        ImageButtonStyle newStyle = new ImageButtonStyle(getStyle());
        newStyle.imageUp = new TextureRegionDrawable(((TextureRegionDrawable) getStyle().imageUp).getRegion());

        float SPELLBUTTON_SIZE_X = 128 * xRatio;
        float SPELLBUTTON_SIZE_Y = 128 * yRatio;

        if(!equipped) {
            SPELLBUTTON_SIZE_X *= 0.8f;
            SPELLBUTTON_SIZE_Y *= 0.8f;
        }

        newStyle.imageUp.setMinWidth(SPELLBUTTON_SIZE_X);
        newStyle.imageUp.setMinHeight(SPELLBUTTON_SIZE_Y);

        setStyle(newStyle);
    }

    public Vector2 getCenter() {
        float x = getParent().getParent().getX();
        float y = getParent().getParent().getY();

        float table_x = getParent().getX();
        float table_y = getParent().getY();

        float own_x = getX();
        float own_y = getY();

        float actual_x = x + table_x + own_x + getWidth()/2f;
        float actual_y = y + table_y + own_y + getHeight()/2f;

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
            if (getParent().getParent().equals(screen.knownSpells_table.table) &&
                    screen.selectedSpell_Button.getParent().getParent().equals(screen.equippedSpells_table.table)) {
                attemptSwap(true);
                return;
            } else if (getParent().getParent().equals(screen.equippedSpells_table.table) &&
                    screen.selectedSpell_Button.getParent().getParent().equals(screen.knownSpells_table.table)) {
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
            spell_known.resetCD();
            player.spellbook.knownSpells.set(known_index, spell_equipped);
            if(selected_is_equipped) {
                createSwapAnims(this, screen.selectedSpell_Button, spell_equipped, spell_known);
            } else {
                createSwapAnims(screen.selectedSpell_Button, this, spell_equipped, spell_known);
            }

            screen.knownSpells_table.updateSpells();
            screen.equippedSpells_table.updateSpells();
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
            screen.knownSpells_table.updateSpells();
            screen.equippedSpells_table.updateSpells();
            screen.selectedSpell_Button = null;
            screen.mastery_table.mixingTable.updateButtons();
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
