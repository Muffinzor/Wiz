package wizardo.game.Screens.Character.BookTable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import wizardo.game.Spells.Spell;

import static wizardo.game.Resources.Skins.bookTableSkin;
import static wizardo.game.Screens.BaseScreen.screenRatio;

public class SpellIcon_Button extends ImageButton {


    public SpellIcon_Button(Spell spell, boolean equipped) {
        super(getStyleForSpell(spell, bookTableSkin, equipped));
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
}
