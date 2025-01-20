package wizardo.game.Items.Drop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Display.Text.BottomText;
import wizardo.game.Player.Levels.StatsBuffer;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Resources.EffectAnims.GearFlareAnims.white_flare;
import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Spells.SpellUtils.whatElementIsThis;

public class ScrollDrop extends Drop {

    Color textColor;
    String displayText;

    SpellUtils.Spell_Name SpellType;
    SpellUtils.Spell_Element Element;
    int tier;
    boolean recursive;

    SpellUtils.Spell_Name Mastery;

    /**
     *
     * @param spawnPosition
     * @param SpellType null if random
     * @param Element null if random
     * @param tier 3 + recursive if all tier accessibles
     * @param recursive true means all tiers under specified are accessible
     */
    public ScrollDrop(Vector2 spawnPosition, SpellUtils.Spell_Name SpellType, SpellUtils.Spell_Element Element, int tier, boolean recursive) {
        this.position = new Vector2(spawnPosition);
        this.SpellType = SpellType;
        this.Element = Element;
        this.tier = tier;
        this.recursive = recursive;
        displayScale = 0.3f;

        pickupAnim = GearFlareAnims.gear_pop;
        flareAnim = white_flare;
        flareScale = 0.35f;

        setup();
    }

    public void initializeMastery() {
        if(SpellType != null) {
            Mastery = SpellType;
        } else {
            Mastery = SpellUtils.getRandomMastery(Element, tier, recursive);
        }
        Element = whatElementIsThis(Mastery);
        setupText();
    }

    @Override
    public void setup() {
        initializeMastery();
        sprite = getScrollSprite(Element);
    }

    @Override
    public void pickup() {
        StatsBuffer.apply_Scroll(Mastery);
        pickedUp = true;
        stateTime = 0;
        BottomText text = new BottomText();
        text.setAll(displayText, position, inventorySkin.getFont("Gear_Title"), textColor);
        screen.displayManager.textManager.addBottomText(text);
    }

    public void setupText() {
        switch (Element) {
            case FIRE -> textColor = inventorySkin.getColor("Fire");
            case FROST -> textColor = inventorySkin.getColor("Frost");
            case LIGHTNING -> textColor = inventorySkin.getColor("Lightning");
            case ARCANE -> textColor = inventorySkin.getColor("Arcane");
        }

        switch(Mastery) {
            case FROSTBOLT -> displayText = "Frostbolts Scroll";
            case ICESPEAR -> displayText = "Ice Spear Scroll";
            case FROZENORB -> displayText = "Frozen Orb Scroll";
            case FLAMEJET -> displayText = "Flamejet Scroll";
            case FIREBALL -> displayText = "Fireball Scroll";
            case OVERHEAT -> displayText = "Overheat Scroll";
            case CHARGEDBOLTS -> displayText = "Chargedbolts Scroll";
            case CHAIN -> displayText = "Chain Lightning Scroll";
            case THUNDERSTORM -> displayText = "Thunderstorm Scroll";
            case MISSILES -> displayText = "Arcane Missiles Scroll";
            case BEAM -> displayText = "Energy Beam Scroll";
            case RIFTS -> displayText = "Rifts Scroll";
        }

    }

    public static Sprite getScrollSprite(SpellUtils.Spell_Element element) {
        Sprite sprite = null;
        switch(element) {
            case FROST -> sprite = new Sprite(new Texture("Items/Drops/Scrolls/frostScroll.png"));
            case FIRE -> sprite = new Sprite(new Texture("Items/Drops/Scrolls/fireScroll.png"));
            case ARCANE -> sprite = new Sprite(new Texture("Items/Drops/Scrolls/arcaneScroll.png"));
            case LIGHTNING -> sprite = new Sprite(new Texture("Items/Drops/Scrolls/lightningScroll.png"));
        }
        return sprite;
    }
}
