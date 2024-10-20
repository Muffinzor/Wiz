package wizardo.game.Screens.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Wizardo;


import java.util.HashSet;

import static wizardo.game.Resources.Skins.masteryTableSkin;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public class MasteryTable extends MenuTable {

    public HashSet<SpellUtils.Spell_Name> parts;


    public MasteryTable(Stage stage, Skin skin, Wizardo game) {
        super(stage, skin, game);
        parts = new HashSet<>();

        createTable();
        FrostButtons(-8, -4);
        FireButtons(-8, -4);
        LightningButtons(-8, -4);
        ArcaneButtons(-8, -4);

    }

    public void createTable() {
        adjustFontSize();
        float xRatio = Gdx.graphics.getWidth() / 1920f;
        float yRatio = Gdx.graphics.getHeight() / 1080f;

        float width = 535f * xRatio;
        float height = 500f * yRatio;

        int x_pos = Math.round(1350 * xRatio);
        int y_pos = Math.round(400 * yRatio);

        table = new Table();
        table.setPosition(x_pos, y_pos);
        table.setWidth(width);
        table.setHeight(height);
        stage.addActor(table);

        table.setDebug(true);
    }

    public void FrostButtons(int bottomPad, int topPad) {
        MasteryButton frostbolts_button = new MasteryButton("", masteryTableSkin, FROSTBOLT, parts);
        table.add(frostbolts_button).expand().padBottom(bottomPad).padTop(topPad).fillX();
        MasteryButton icespear_button = new MasteryButton("", masteryTableSkin, ICESPEAR, parts);
        table.add(icespear_button).expand().padBottom(bottomPad).padTop(topPad).fillX();
        MasteryButton orb_button = new MasteryButton("", masteryTableSkin, FROZENORB, parts);
        table.add(orb_button).expand().padBottom(bottomPad).padTop(topPad).fillX();

        table.row();

        Label frostbolts = new Label("Frostbolts", skin);
        table.add(frostbolts);
        Label icespear = new Label("Icespear", skin);
        table.add(icespear);
        Label orb = new Label("Frozen Orb", skin);
        table.add(orb);

        table.row();

        Label frostbolts_mastery = new Label("Mastery : " + player.spellbook.frostbolt_lvl, skin);
        table.add(frostbolts_mastery);
        Label icespear_mastery = new Label("Mastery : " + player.spellbook.icespear_lvl, skin);
        table.add(icespear_mastery);
        Label orb_mastery = new Label("Mastery : " + player.spellbook.frozenorb_lvl, skin);
        table.add(orb_mastery);

        table.row();

    }
    public void FireButtons(int bottomPad, int topPad) {
        MasteryButton flamejet_button = new MasteryButton("", masteryTableSkin, FLAMEJET, parts);
        table.add(flamejet_button).expand().padBottom(bottomPad).padTop(topPad).fillX();
        MasteryButton fireball_button = new MasteryButton("", masteryTableSkin, FIREBALL, parts);
        table.add(fireball_button).expand().padBottom(bottomPad).padTop(topPad).fillX();
        MasteryButton overheat_button = new MasteryButton("", masteryTableSkin, OVERHEAT, parts);
        table.add(overheat_button).expand().padBottom(bottomPad).padTop(topPad).fillX();

        table.row();

        Label flamejet = new Label("Flamejet", skin);
        table.add(flamejet);
        Label fireball = new Label("Fireball", skin);
        table.add(fireball);
        Label overheat = new Label("Overheat", skin);
        table.add(overheat);

        table.row();

        Label flamejet_mastery = new Label("Mastery : " + player.spellbook.flamejet_lvl, skin);
        table.add(flamejet_mastery);
        Label fireball_mastery = new Label("Mastery : " + player.spellbook.fireball_lvl, skin);
        table.add(fireball_mastery);
        Label overheat_mastery = new Label("Mastery : " + player.spellbook.overheat_lvl, skin);
        table.add(overheat_mastery);

        table.row();
    }
    public void LightningButtons(int bottomPad, int topPad) {
        MasteryButton chargedbolt_button = new MasteryButton("", masteryTableSkin, CHARGEDBOLTS, parts);
        table.add(chargedbolt_button).expand().padBottom(bottomPad).padTop(topPad).fillX();
        MasteryButton chain_button = new MasteryButton("", masteryTableSkin, CHAIN, parts);
        table.add(chain_button).expand().padBottom(bottomPad).padTop(topPad).fillX();
        MasteryButton thunder_button = new MasteryButton("", masteryTableSkin, THUNDERSTORM, parts);
        table.add(thunder_button).expand().padBottom(bottomPad).padTop(topPad).fillX();

        table.row();

        Label chargedbolts = new Label("Chargedbolts", skin);
        table.add(chargedbolts);
        Label chain = new Label("Chain Lightning", skin);
        table.add(chain);
        Label thunderstorm = new Label("Thunderstorm", skin);
        table.add(thunderstorm);

        table.row();

        Label chargedbolt_mastery = new Label("Mastery : " + player.spellbook.chargedbolt_lvl, skin);
        table.add(chargedbolt_mastery);
        Label chain_mastery = new Label("Mastery : " + player.spellbook.chainlightning_lvl, skin);
        table.add(chain_mastery);
        Label thunderstorm_mastery = new Label("Mastery : " + player.spellbook.thunderstorm_lvl, skin);
        table.add(thunderstorm_mastery);

        table.row();
    }
    public void ArcaneButtons(int bottomPad, int topPad) {
        MasteryButton missiles_buttons = new MasteryButton("", masteryTableSkin, MISSILES, parts);
        table.add(missiles_buttons).expand().padBottom(bottomPad).padTop(topPad).fillX();
        MasteryButton beam_buttom = new MasteryButton("", masteryTableSkin, BEAM, parts);
        table.add(beam_buttom).expand().padBottom(bottomPad).padTop(topPad).fillX();
        MasteryButton rifts_button = new MasteryButton("", masteryTableSkin, RIFTS, parts);
        table.add(rifts_button).expand().padBottom(bottomPad).padTop(topPad).fillX();

        table.row();

        Label missiles = new Label("Arcane Missiles", skin);
        table.add(missiles);
        Label beam = new Label("Energy Beam", skin);
        table.add(beam);
        Label rifts = new Label("Rifts", skin);
        table.add(rifts);

        table.row();

        Label missile_mastery = new Label("Mastery : " + player.spellbook.arcanemissile_lvl, skin);
        table.add(missile_mastery);
        Label beam_mastery = new Label("Mastery : " + player.spellbook.energybeam_lvl, skin);
        table.add(beam_mastery);
        Label rifts_mastery = new Label("Mastery : " + player.spellbook.rift_lvl, skin);
        table.add(rifts_mastery);

        table.row();
    }

    public void adjustFontSize() {
        float scale = Math.max(Gdx.graphics.getWidth() / 1920f, 0.75f);
        BitmapFont font = skin.getFont("Messy20");
        font.getData().setScale(scale);
        skin.get("default", Label.LabelStyle.class).font = font;
    }

    public void TESTBUTTONS() {



        table.row();

        for (int j = 0; j < 3; j++) {
            Label label = new Label("Frostbolts", skin);
            table.add(label);
        }

        table.row();

        for (int x = 0; x < 3; x++) {
            Label label = new Label("Mastery : 1", skin);
            table.add(label);
        }

        table.row();

        MasteryButton button3 = new MasteryButton("", masteryTableSkin, FLAMEJET, parts);
        table.add(button3).expand().padBottom(-8).padTop(-4).fillX();
        MasteryButton button4 = new MasteryButton("", masteryTableSkin, FIREBALL, parts);
        table.add(button4).expand().padBottom(-8).padTop(-4).fillX();
        MasteryButton button5 = new MasteryButton("", masteryTableSkin, OVERHEAT, parts);
        table.add(button5).expand().padBottom(-8).padTop(-4).fillX();

        table.row();

        for (int j = 0; j < 3; j++) {
            Label label = new Label("Frostbolts", skin);
            table.add(label);
        }

        table.row();

        for (int x = 0; x < 3; x++) {
            Label label = new Label("Mastery : 1", skin);
            table.add(label);
        }

        table.row();

        MasteryButton button7 = new MasteryButton("", masteryTableSkin, CHARGEDBOLTS, parts);
        table.add(button7).expand().padBottom(-8).padTop(-4).fillX();
        MasteryButton button8 = new MasteryButton("", masteryTableSkin, CHAIN, parts);
        table.add(button8).expand().padBottom(-8).padTop(-4).fillX();
        MasteryButton button9 = new MasteryButton("", masteryTableSkin, THUNDERSTORM, parts);
        table.add(button9).expand().padBottom(-8).padTop(-4).fillX();

        table.row();

        for (int j = 0; j < 3; j++) {
            Label label = new Label("Frostbolts", skin);
            table.add(label);
        }

        table.row();

        for (int x = 0; x < 3; x++) {
            Label label = new Label("Mastery : 1", skin);
            table.add(label);
        }

        table.row();

        MasteryButton button10 = new MasteryButton("", masteryTableSkin, MISSILES, parts);
        table.add(button10).expand().padBottom(-8).padTop(-4).fillX();
        MasteryButton button11 = new MasteryButton("", masteryTableSkin, BEAM, parts);
        table.add(button11).expand().padBottom(-8).padTop(-4).fillX();
        MasteryButton button12 = new MasteryButton("", masteryTableSkin, RIFTS, parts);
        table.add(button12).expand().padBottom(-8).padTop(-4).fillX();

        table.row();

        for (int j = 0; j < 3; j++) {
            Label label = new Label("Frostbolts", skin);
            table.add(label);
        }

        table.row();

        for (int x = 0; x < 3; x++) {
            Label label = new Label("Mastery : 1", skin);
            table.add(label);
        }

        table.row();
    }

    @Override
    public void navigateDown() {

    }

    @Override
    public void navigateUp() {

    }

    @Override
    public void navigateLeft() {

    }

    @Override
    public void navigateRight() {

    }

    @Override
    public void clickSelectedButton() {

    }

    @Override
    public void resize() {

    }
}
