package wizardo.game.Screens.CharacterScreen.BookTable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import wizardo.game.Screens.CharacterScreen.Anims.SpellSwap_Anim;
import wizardo.game.Screens.CharacterScreen.CharacterScreen;
import wizardo.game.Screens.CharacterScreen.SpellLabel.SpellLabel;
import wizardo.game.Spells.Arcane.ArcaneMissiles.ArcaneMissile_Spell;
import wizardo.game.Spells.Arcane.EnergyBeam.EnergyBeam_Spell;
import wizardo.game.Spells.Arcane.Rifts.Rifts_Spell;
import wizardo.game.Spells.Fire.Fireball.Fireball_Spell;
import wizardo.game.Spells.Fire.Flamejet.Flamejet_Spell;
import wizardo.game.Spells.Fire.Overheat.Overheat_Spell;
import wizardo.game.Spells.Frost.Frostbolt.Frostbolt_Spell;
import wizardo.game.Spells.Frost.Frozenorb.Frozenorb_Spell;
import wizardo.game.Spells.Frost.Icespear.Icespear_Spell;
import wizardo.game.Spells.Hybrid.Judgement.Judgement_Spell;
import wizardo.game.Spells.Hybrid.Blizzard.Blizzard_Spell;
import wizardo.game.Spells.Hybrid.CelestialStrike.CelestialStrike_Spell;
import wizardo.game.Spells.Hybrid.DragonBreath.DragonBreath_Spell;
import wizardo.game.Spells.Hybrid.EnergyRain.EnergyRain_Spell;
import wizardo.game.Spells.Hybrid.ForkedLightning.ForkedLightning_Spell;
import wizardo.game.Spells.Hybrid.FrostNova.FrostNova_Spell;
import wizardo.game.Spells.Hybrid.Laser.Laser_Spell;
import wizardo.game.Spells.Hybrid.LightningHands.LightningHands_Spell;
import wizardo.game.Spells.Hybrid.MeteorShower.MeteorShower_Spell;
import wizardo.game.Spells.Hybrid.Orbit.Orbit_Spell;
import wizardo.game.Spells.Hybrid.RepulsionField.RepulsionField_Spell;
import wizardo.game.Spells.Lightning.ChainLightning.ChainLightning_Spell;
import wizardo.game.Spells.Lightning.ChargedBolts.ChargedBolts_Spell;
import wizardo.game.Spells.Lightning.Thunderstorm.Thunderstorm_Spell;
import wizardo.game.Spells.Spell;

import static wizardo.game.Resources.SpellIcons.*;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Wizardo.player;

public class SpellIcon_Button extends ImageButton {

    CharacterScreen screen;
    public Spell spell;
    public boolean equipped;

    public SpellIcon_Button(Spell spell, boolean equipped, CharacterScreen screen) {
        super(createStyle(spell));
        adjustSize();
        this.equipped = equipped;
        this.screen = screen;
        this.spell = spell;

        addClickListener();
    }

    public static ImageButtonStyle createStyle(Spell spell) {
        ImageButtonStyle style = new ImageButtonStyle();

        TextureRegionDrawable upDrawable = new TextureRegionDrawable(new TextureRegion(background_blank));
        TextureRegionDrawable overDrawable = new TextureRegionDrawable(new TextureRegion(getBackgroundSprite(spell)));

        style.imageUp = upDrawable;
        style.imageOver = overDrawable;

        return style;
    }
    public static Sprite getBackgroundSprite(Spell spell) {
        Sprite sprite = background_blank;
        switch(spell.anim_element) {
            case FIRE -> sprite = background_fire;
            case FROST -> sprite = background_frost;
            case ARCANE -> sprite = background_arcane;
            case LIGHTNING -> sprite = background_lightning;
            case FIRELITE -> sprite = background_firelite;
            case COLDLITE -> sprite = background_coldlite;
        }
        return sprite;
    }
    public void adjustSize() {
        ImageButtonStyle newStyle = new ImageButtonStyle(getStyle());
        newStyle.imageUp = new TextureRegionDrawable(((TextureRegionDrawable) getStyle().imageUp).getRegion());

        float SPELLBUTTON_SIZE = Math.min(128 * xRatio, 128 * yRatio);

        newStyle.imageUp.setMinWidth(SPELLBUTTON_SIZE);
        newStyle.imageUp.setMinHeight(SPELLBUTTON_SIZE);

        newStyle.imageOver.setMinWidth(SPELLBUTTON_SIZE);
        newStyle.imageOver.setMinHeight(SPELLBUTTON_SIZE);

        setStyle(newStyle);
    }
    public static Sprite getSpellSprite(Spell spell) {
        Sprite used_frame = new Sprite();
        Sprite base_frame = null;
        if(spell instanceof Frostbolt_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = frostbolt_frost;
                case FIRE -> base_frame = frostbolt_fire;
                case ARCANE -> base_frame = frostbolt_arcane;
                case LIGHTNING -> base_frame = frostbolt_lightning;
            }
        }
        if(spell instanceof Icespear_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = icespear_frost;
                case FIRE -> base_frame = icespear_fire;
                case ARCANE -> base_frame = icespear_arcane;
                case LIGHTNING -> base_frame = icespear_lightning;
                case COLDLITE -> base_frame = icespear_coldlite;
            }
        }
        if(spell instanceof Frozenorb_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = frozenorb_frost;
                case FIRE -> base_frame = frozenorb_fire;
                case ARCANE -> base_frame = frozenorb_arcane;
                case LIGHTNING -> base_frame = frozenorb_lightning;
            }
        }
        if(spell instanceof ChargedBolts_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = chargedbolts_frost;
                case FIRE -> base_frame = chargedbolts_fire;
                case ARCANE -> base_frame = chargedbolts_arcane;
                case LIGHTNING -> base_frame = chargedbolts_lightning;
            }
        }
        if(spell instanceof ChainLightning_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = chainlightning_frost;
                case FIRE -> base_frame = chainlightning_fire;
                case ARCANE -> base_frame = chainlightning_arcane;
                case LIGHTNING -> base_frame = chainlightning_lightning;
            }
        }
        if(spell instanceof Thunderstorm_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = thunderstorm_frost;
                case FIRE -> base_frame = thunderstorm_fire;
                case ARCANE -> base_frame = thunderstorm_arcane;
                case LIGHTNING -> base_frame = thunderstorm_lightning;
            }
        }
        if(spell instanceof Flamejet_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = flamejet_frost;
                case FIRE -> base_frame = flamejet_fire;
                case ARCANE -> base_frame = flamejet_arcane;
                case LIGHTNING -> base_frame = flamejet_lightning;
            }
        }
        if(spell instanceof Fireball_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = fireball_frost;
                case FIRE -> base_frame = fireball_fire;
                case ARCANE -> base_frame = fireball_arcane;
                case LIGHTNING -> base_frame = fireball_lightning;
            }
        }
        if(spell instanceof Overheat_Spell) {
            switch(spell.anim_element) {
                case FIRE -> base_frame = overheat_fire;
                case LIGHTNING -> base_frame = overheat_lightning;
                case FROST -> base_frame = overheat_frost;
            }
        }
        if(spell instanceof ArcaneMissile_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = arcanemissile_frost;
                case FIRE -> base_frame = arcanemissile_fire;
                case ARCANE -> base_frame = arcanemissile_arcane;
                case LIGHTNING -> base_frame = arcanemissile_lightning;
            }
        }
        if(spell instanceof EnergyBeam_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = energybeam_frost;
                case FIRE -> base_frame = energybeam_fire;
                case ARCANE -> base_frame = energybeam_arcane;
                case LIGHTNING -> base_frame = energybeam_lightning;
            }
        }
        if(spell instanceof Rifts_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = rift_frost;
                case FIRE -> base_frame = rift_fire;
                case ARCANE -> base_frame = rift_arcane;
                case LIGHTNING -> base_frame = rift_lightning;
            }
        }
        if(spell instanceof Blizzard_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = blizzard_frost;
                case ARCANE -> base_frame = blizzard_arcane;
            }
        }
        if(spell instanceof EnergyRain_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = energyrain_frost;
                case FIRE -> base_frame = energyrain_fire;
                case ARCANE -> base_frame = energyrain_arcane;
                case LIGHTNING -> base_frame = energyrain_lightning;
            }
        }
        if(spell instanceof MeteorShower_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = meteor_frost;
                case FIRE -> base_frame = meteor_fire;
                case ARCANE -> base_frame = meteor_arcane;
                case LIGHTNING -> base_frame = meteor_lightning;
            }
        }
        if(spell instanceof Judgement_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = judgement_frost;
                case FIRE -> base_frame = judgement_fire;
                case ARCANE -> base_frame = judgement_arcane;
                case LIGHTNING -> base_frame = judgement_lightning;
            }
        }
        if(spell instanceof Laser_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = laser_frost;
                case FIRE -> base_frame = laser_fire;
                case ARCANE -> base_frame = laser_arcane;
                case LIGHTNING -> base_frame = laser_lightning;
            }
        }
        if(spell instanceof DragonBreath_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = dragonbreath_frost;
                case FIRE -> base_frame = dragonbreath_fire;
                case ARCANE -> base_frame = dragonbreath_arcane;
            }
        }
        if(spell instanceof Orbit_Spell) {
            switch(spell.anim_element) {
                case FROST -> base_frame = orbit_frost;
                case ARCANE -> base_frame = orbit_arcane;
            }
        }
        if(spell instanceof LightningHands_Spell) {
            base_frame = lightninghands_firelite;
        }
        if(spell instanceof FrostNova_Spell) {
            base_frame = frostnova_frost;
        }
        if(spell instanceof RepulsionField_Spell) {
            base_frame = repulsionfield_arcane;
        }
        if(spell instanceof ForkedLightning_Spell) {
            base_frame = forkedlightning_firelite;
        }
        if(spell instanceof CelestialStrike_Spell) {
            base_frame = celestialstrike_coldlite;
        }
        used_frame.set(base_frame);
        return used_frame;
    }

    public void drawButton() {
        Sprite frame = getSpellSprite(spell);
        Vector2 position = getCenter();
        frame.setCenter(position.x, position.y);
        float ratio = 1.33f;
        if(!equipped) {
           ratio = 1.1f;
        }
        frame.setScale(ratio * xRatio);
        frame.draw(screen.batch);
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
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hover_enter();
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hover_exit();
            }
        });
    }

    public void handleClick() {
        screen.mastery_table.uncheckAll();

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
        screen.mastery_table.mixingTable.handle_thirdButton();
        screen.mastery_table.mixingTable.updateButtons();

        handle_spell_label();
    }

    public void handle_spell_label() {
        if(screen.selectedSpell_Button != null) {
            if(screen.spell_label != null) {
                screen.spell_label.dispose();
            }
            screen.spell_label = new SpellLabel(screen.stage, screen, screen.selectedSpell_Button.spell.spellParts);
        } else {
            screen.spell_label.dispose();
            screen.spell_label = null;
        }
    }

    public void hover_enter() {
        if(screen.spell_label != null) {
            screen.spell_label.dispose();
        }
        screen.spell_label = new SpellLabel(screen.stage, screen, this.spell.spellParts);
    }

    public void hover_exit() {
        if(screen.spell_label != null) {
            screen.spell_label.dispose();
        }
        if(screen.selectedSpell_Button != null) {
            screen.spell_label = new SpellLabel(screen.stage, screen, screen.selectedSpell_Button.spell.spellParts);
        } else if(!screen.mastery_table.mixingTable.parts.isEmpty()){
            screen.spell_label = new SpellLabel(screen.stage, screen, screen.mastery_table.mixingTable.parts);
        } else {
            screen.spell_label = null;
        }
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

        if(spell_equipped.string_name.equals(spell_known.string_name)) {

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

            if(!selected_is_equipped) {
                screen.selectedButton = screen.equippedSpells_table.buttonsMatrix[screen.equippedSpells_table.x_pos][screen.equippedSpells_table.y_pos];
                screen.activeTable = screen.equippedSpells_table;
            } else {
                screen.selectedButton = screen.knownSpells_table.buttonsMatrix[screen.knownSpells_table.x_pos][screen.knownSpells_table.y_pos];
                screen.activeTable = screen.knownSpells_table;
            }
            return;
        }

        for (Spell spell : player.spellbook.equippedSpells) {
            if(spell.string_name.equals(spell_known.string_name)) {
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
            if(!selected_is_equipped) {
                screen.selectedButton = screen.equippedSpells_table.buttonsMatrix[screen.equippedSpells_table.x_pos][screen.equippedSpells_table.y_pos];
                screen.activeTable = screen.equippedSpells_table;
            } else {
                screen.selectedButton = screen.knownSpells_table.buttonsMatrix[screen.knownSpells_table.x_pos][screen.knownSpells_table.y_pos];
                screen.activeTable = screen.knownSpells_table;
            }
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
