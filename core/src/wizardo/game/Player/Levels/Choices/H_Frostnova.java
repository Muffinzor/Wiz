package wizardo.game.Player.Levels.Choices;

import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Player.Levels.LevelUpEnums;
import wizardo.game.Screens.LevelUp.LevelUpScreen;
import wizardo.game.Screens.LevelUp.PanelButton;

import static wizardo.game.Wizardo.player;

public class H_Frostnova extends PanelButton {

    int roll;
    int MAX_BONUS_AOE = 30;
    int MAX_BONUS_SHATTERCHANCE = 30;

    public H_Frostnova(LevelUpScreen screen) {
        super(screen);
        type = LevelUpEnums.LevelUps.FROSTNOVA;
        super.setup();
        pick_type();
        set_text();
    }

    public void pick_type() {
        roll = 3;

        // If bonus effect is rolled
        if ((player.spellbook.frostnova_bonus_radius < MAX_BONUS_AOE ||
                player.spellbook.frostnova_bonus_shatterchance < MAX_BONUS_SHATTERCHANCE)
                && Math.random() > bonus_effect_chance) {
            boolean bonus_freeze = MathUtils.randomBoolean();
            roll = (bonus_freeze && player.spellbook.frostnova_bonus_radius < MAX_BONUS_AOE) ||
                    (!bonus_freeze && player.spellbook.frostnova_bonus_shatterchance >= MAX_BONUS_SHATTERCHANCE)
                    ? 1 : 2;
        }
    }

    public void apply_stats() {
        switch(roll) {
            case 1 -> player.spellbook.frostnova_bonus_radius += 15;
            case 2 -> player.spellbook.frostnova_bonus_shatterchance += 10;
            case 3 -> player.spellbook.frostnova_bonus_dmg += spell_dmg_buff;
        }
    }

    public void set_text() {
        String s = "";
        switch(roll) {
            case 1 -> s = "+15% Radius";
            case 2 -> s = "+10% Chance to Shatter Enemies";
            case 3 -> s = String.format("+%d%% Damage", spell_dmg_buff);
        }
        String text = String.format("""
            FROST NOVA
            
            %s
            """, s);
        setText(text);
    }
}
