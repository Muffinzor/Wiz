package wizardo.game.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Skins {

    public static Skin mainMenuSkin = new Skin(Gdx.files.internal("Screens/MainMenuScreen/MainMenuSkin/MainMenuSkin.json"));
    public static Skin masteryTableSkin = new Skin(Gdx.files.internal("Screens/CharacterScreen/MasteryTable/MasteryTable_Skin.json"));
    public static Skin bookTableSkin = new Skin(Gdx.files.internal("Screens/CharacterScreen/BookTable/BookTableSkin.json"));
    public static Skin levelUpSkin = new Skin(Gdx.files.internal("Screens/LevelUp/LevelUpSkin/LevelUpSkin.json"));


    public static Color light_orange = mainMenuSkin.getColor("LightOrange");
    public static Color light_blue = mainMenuSkin.getColor("LightBlue");
    public static Color light_pink = mainMenuSkin.getColor("LightPink");
    public static Color light_yellow = mainMenuSkin.getColor("LightYellow");
    public static Color light_teal = mainMenuSkin.getColor("LightTeal");

}
