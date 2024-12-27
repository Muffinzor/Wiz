package wizardo.game.Screens.Character.MasteryTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import wizardo.game.Display.MenuTable;
import wizardo.game.Screens.Character.CharacterScreen;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Wizardo;


import java.util.ArrayList;

import static wizardo.game.Resources.Skins.masteryTableSkin;
import static wizardo.game.Spells.SpellUtils.Spell_Name.*;
import static wizardo.game.Wizardo.player;

public class MasteryTable extends MenuTable {

    public ArrayList<SpellUtils.Spell_Name> parts;

    public MixingTable mixingTable;

    public CharacterScreen screen;

    public MasteryButton[][] buttonsMatrix;
    public int x_position = 0;
    public int y_position = 0;

    Label flamejet_m;
    Label fireball_m;
    Label overheat_m;
    Label frostbolt_m;
    Label icespear_m;
    Label frozenorb_m;
    Label chargedbolt_m;
    Label chain_m;
    Label storm_m;
    Label missiles_m;
    Label beam_m;
    Label rifts_m;

    public MasteryTable(Stage stage, Skin skin, Wizardo game, CharacterScreen screen) {
        super(stage, skin, game);
        this.screen = screen;
        resize();
    }

    public void createSpellButtonsTable() {
        adjustFontSize();
        float xRatio = Gdx.graphics.getWidth() / 1920f;
        float yRatio = Gdx.graphics.getHeight() / 1080f;

        float width = 575f * xRatio;
        float height = 500f * yRatio;

        int x_pos = Math.round(1330 * xRatio);
        int y_pos = Math.round(500 * yRatio);

        table = new Table();
        table.setPosition(x_pos, y_pos);
        table.setWidth(width);
        table.setHeight(height);
        stage.addActor(table);

        //table.setDebug(true);

        int bottomPad = Math.round(-8 * yRatio);
        int topPad = Math.round(-4 * yRatio);

        FrostButtons(bottomPad, topPad);
        FireButtons(bottomPad, topPad);
        LightningButtons(bottomPad, topPad);
        ArcaneButtons(bottomPad, topPad);
    }
    public void createMixingTable() {
        mixingTable = new MixingTable(stage, skin, game, this);
    }

    public void FrostButtons(int bottomPad, int topPad) {
        MasteryButton frostbolts_button = new MasteryButton("", masteryTableSkin, FROSTBOLT, this);
        table.add(frostbolts_button).expand().padBottom(bottomPad).padTop(topPad);
        MasteryButton icespear_button = new MasteryButton("", masteryTableSkin, ICESPEAR, this);
        table.add(icespear_button).expand().padBottom(bottomPad).padTop(topPad);
        MasteryButton orb_button = new MasteryButton("", masteryTableSkin, FROZENORB, this);
        table.add(orb_button).expand().padBottom(bottomPad).padTop(topPad);

        buttons.add(frostbolts_button);
        buttons.add(icespear_button);
        buttons.add(orb_button);

        buttonsMatrix[0][0] = frostbolts_button;
        buttonsMatrix[1][0] = icespear_button;
        buttonsMatrix[2][0] = orb_button;

        table.row();

        Label frostbolts = new Label("Frostbolts", skin);
        table.add(frostbolts);
        Label icespear = new Label("Icespear", skin);
        table.add(icespear);
        Label orb = new Label("Frozen Orb", skin);
        table.add(orb);

        table.row();

        frostbolt_m = new Label("Mastery : " + player.spellbook.frostbolt_lvl, skin);
        table.add(frostbolt_m);
        icespear_m = new Label("Mastery : " + player.spellbook.icespear_lvl, skin);
        table.add(icespear_m);
        frozenorb_m = new Label("Mastery : " + player.spellbook.frozenorb_lvl, skin);
        table.add(frozenorb_m);

        table.row();

    }
    public void FireButtons(int bottomPad, int topPad) {
        MasteryButton flamejet_button = new MasteryButton("", masteryTableSkin, FLAMEJET, this);
        table.add(flamejet_button).expand().padBottom(bottomPad).padTop(topPad);
        MasteryButton fireball_button = new MasteryButton("", masteryTableSkin, FIREBALL, this);
        table.add(fireball_button).expand().padBottom(bottomPad).padTop(topPad);
        MasteryButton overheat_button = new MasteryButton("", masteryTableSkin, OVERHEAT, this);
        table.add(overheat_button).expand().padBottom(bottomPad).padTop(topPad);

        buttons.add(flamejet_button);
        buttons.add(fireball_button);
        buttons.add(overheat_button);

        buttonsMatrix[0][1] = flamejet_button;
        buttonsMatrix[1][1] = fireball_button;
        buttonsMatrix[2][1] = overheat_button;

        table.row();

        Label flamejet = new Label("Flamejet", skin);
        table.add(flamejet);
        Label fireball = new Label("Fireball", skin);
        table.add(fireball);
        Label overheat = new Label("Overheat", skin);
        table.add(overheat);

        table.row();

        flamejet_m = new Label("Mastery : " + player.spellbook.flamejet_lvl, skin);
        table.add(flamejet_m);
        fireball_m = new Label("Mastery : " + player.spellbook.fireball_lvl, skin);
        table.add(fireball_m);
        overheat_m = new Label("Mastery : " + player.spellbook.overheat_lvl, skin);
        table.add(overheat_m);

        table.row();
    }
    public void LightningButtons(int bottomPad, int topPad) {
        MasteryButton chargedbolt_button = new MasteryButton("", masteryTableSkin, CHARGEDBOLTS, this);
        table.add(chargedbolt_button).expand().padBottom(bottomPad).padTop(topPad);
        MasteryButton chain_button = new MasteryButton("", masteryTableSkin, CHAIN, this);
        table.add(chain_button).expand().padBottom(bottomPad).padTop(topPad);
        MasteryButton thunder_button = new MasteryButton("", masteryTableSkin, THUNDERSTORM, this);
        table.add(thunder_button).expand().padBottom(bottomPad).padTop(topPad);

        buttons.add(chargedbolt_button);
        buttons.add(chain_button);
        buttons.add(thunder_button);

        buttonsMatrix[0][2] = chargedbolt_button;
        buttonsMatrix[1][2] = chain_button;
        buttonsMatrix[2][2] = thunder_button;

        table.row();

        Label chargedbolts = new Label("Chargedbolts", skin);
        table.add(chargedbolts);
        Label chain = new Label("Chain Lightning", skin);
        table.add(chain);
        Label thunderstorm = new Label("Thunderstorm", skin);
        table.add(thunderstorm);

        table.row();

        chargedbolt_m = new Label("Mastery : " + player.spellbook.chargedbolt_lvl, skin);
        table.add(chargedbolt_m);
        chain_m = new Label("Mastery : " + player.spellbook.chainlightning_lvl, skin);
        table.add(chain_m);
        storm_m = new Label("Mastery : " + player.spellbook.thunderstorm_lvl, skin);
        table.add(storm_m);

        table.row();
    }
    public void ArcaneButtons(int bottomPad, int topPad) {
        MasteryButton missiles_buttons = new MasteryButton("", masteryTableSkin, MISSILES, this);
        table.add(missiles_buttons).expand().padBottom(bottomPad).padTop(topPad);
        MasteryButton beam_buttom = new MasteryButton("", masteryTableSkin, BEAM, this);
        table.add(beam_buttom).expand().padBottom(bottomPad).padTop(topPad);
        MasteryButton rifts_button = new MasteryButton("", masteryTableSkin, RIFTS, this);
        table.add(rifts_button).expand().padBottom(bottomPad).padTop(topPad);

        buttons.add(missiles_buttons);
        buttons.add(beam_buttom);
        buttons.add(rifts_button);

        buttonsMatrix[0][3] = missiles_buttons;
        buttonsMatrix[1][3] = beam_buttom;
        buttonsMatrix[2][3] = rifts_button;

        table.row();

        Label missiles = new Label("Arcane Missiles", skin);
        table.add(missiles);
        Label beam = new Label("Energy Beam", skin);
        table.add(beam);
        Label rifts = new Label("Rifts", skin);
        table.add(rifts);

        table.row();

        missiles_m = new Label("Mastery : " + player.spellbook.arcanemissile_lvl, skin);
        table.add(missiles_m);
        beam_m = new Label("Mastery : " + player.spellbook.energybeam_lvl, skin);
        table.add(beam_m);
        rifts_m = new Label("Mastery : " + player.spellbook.rift_lvl, skin);
        table.add(rifts_m);

        table.row();
    }

    /**
     * Checks if some mastery buttons should be disabled
     */
    public void updateCheckBoxes() {

        int checks = 0;
        for(Button button : buttons) {
            if(button.isChecked()) {
                checks++;
            }
        }

        boolean frost = buttons.getFirst().isChecked() || buttons.get(1).isChecked() || buttons.get(2).isChecked();
        boolean fire = buttons.get(3).isChecked() || buttons.get(4).isChecked() || buttons.get(5).isChecked();
        boolean lightning = buttons.get(6).isChecked() || buttons.get(7).isChecked() || buttons.get(8).isChecked();
        boolean arcane = buttons.get(9).isChecked() || buttons.get(10).isChecked() || buttons.get(11).isChecked();

        buttons.getFirst().setDisabled(fire && lightning || fire && arcane || lightning && arcane || checks == 3 && !buttons.getFirst().isChecked() ||
                player.spellbook.frostbolt_lvl < 1);
        buttons.get(1).setDisabled(fire && lightning || fire && arcane || lightning && arcane || checks == 3 && !buttons.get(1).isChecked() ||
                player.spellbook.icespear_lvl < 1);
        buttons.get(2).setDisabled(fire && lightning || fire && arcane || lightning && arcane || checks == 3 && !buttons.get(2).isChecked() ||
                player.spellbook.frozenorb_lvl < 1);

        buttons.get(3).setDisabled(frost && lightning || frost && arcane || lightning && arcane || checks == 3 && !buttons.get(3).isChecked() ||
                player.spellbook.flamejet_lvl < 1);
        buttons.get(4).setDisabled(frost && lightning || frost && arcane || lightning && arcane || checks == 3 && !buttons.get(4).isChecked() ||
                player.spellbook.fireball_lvl < 1);
        buttons.get(5).setDisabled(frost && lightning || frost && arcane || lightning && arcane || checks == 3 && !buttons.get(5).isChecked() ||
                player.spellbook.overheat_lvl < 1);

        buttons.get(6).setDisabled(frost && fire || frost && arcane || fire && arcane || checks == 3 && !buttons.get(6).isChecked() ||
                player.spellbook.chargedbolt_lvl < 1);
        buttons.get(7).setDisabled(frost && fire || frost && arcane || fire && arcane || checks == 3 && !buttons.get(7).isChecked() ||
                player.spellbook.chainlightning_lvl < 1);
        buttons.get(8).setDisabled(frost && fire || frost && arcane || fire && arcane || checks == 3 && !buttons.get(8).isChecked() ||
                player.spellbook.thunderstorm_lvl < 1);

        buttons.get(9).setDisabled(frost && fire || frost && lightning || fire && lightning || checks == 3 && !buttons.get(9).isChecked() ||
                player.spellbook.arcanemissile_lvl < 1);
        buttons.get(10).setDisabled(frost && fire || frost && lightning || fire && lightning || checks == 3 && !buttons.get(10).isChecked() ||
                player.spellbook.energybeam_lvl < 1);
        buttons.get(11).setDisabled(frost && fire || frost && lightning || fire && lightning || checks == 3 && !buttons.get(11).isChecked() ||
                player.spellbook.rift_lvl < 1);


    }

    public void adjustFontSize() {
        float scale = Math.max(Gdx.graphics.getWidth() / 1920f, 0.75f);
        BitmapFont font = skin.getFont("Messy20");
        font.getData().setScale(scale);
        skin.get("default", Label.LabelStyle.class).font = font;
    }

    public void updateSelectedButton() {
        screen.selectedButton = buttonsMatrix[x_position][y_position];
    }


    @Override
    public void navigateDown() {
        y_position --;
        if(y_position < 0) {
            y_position = 0;
        }
        updateSelectedButton();
    }

    @Override
    public void navigateUp() {
        y_position ++;
        if(y_position > 3) {
            screen.activeTable = mixingTable;
            mixingTable.x_pos = x_position;
            mixingTable.updateSelectedButton();
            return;
        }
        updateSelectedButton();
    }

    @Override
    public void navigateLeft() {
        x_position --;
        if(x_position < 0 && player.spellbook.knownSpells.isEmpty()) {
            swapToEquippedTable();
            return;
        } else if(x_position < 0) {
            screen.activeTable = screen.knownSpells_table;
            if(player.spellbook.knownSpells.size() == 1) {
                screen.knownSpells_table.x_pos = 0;
            } else {
                screen.knownSpells_table.x_pos = 1;
            }
            screen.knownSpells_table.y_pos = 0;
            screen.knownSpells_table.updateSelectedButton();
            return;
        }
        updateSelectedButton();
    }

    public void swapToEquippedTable() {
        screen.activeTable = screen.equippedSpells_table;
        if(player.spellbook.equippedSpells.size() == 1) {
            screen.equippedSpells_table.x_position = 0;
            screen.equippedSpells_table.y_position = 0;
            screen.equippedSpells_table.updateSelectedButton();
        }

        if(player.spellbook.equippedSpells.size() > 1) {
            screen.equippedSpells_table.x_position = 1;
            screen.equippedSpells_table.y_position = 0;
            screen.equippedSpells_table.updateSelectedButton();
        }
    }

    @Override
    public void navigateRight() {
        x_position ++;
        if(x_position > 2) {
            x_position = 2;
        }
        updateSelectedButton();
    }

    @Override
    public void pressSelectedButton() {
        buttonsMatrix[x_position][y_position].handleClick();
    }

    @Override
    public void resize() {
        table.clear();
        table.remove();
        parts = new ArrayList<>();
        buttonsMatrix = new MasteryButton[3][4];

        createSpellButtonsTable();
        createMixingTable();

        updateCheckBoxes();
    }

    public void updateChanges() {
        frostbolt_m.setText("Mastery : " + player.spellbook.frostbolt_lvl);
        icespear_m.setText("Mastery : " + player.spellbook.icespear_lvl);
        frozenorb_m.setText("Mastery : " + player.spellbook.frozenorb_lvl);
        flamejet_m.setText("Mastery : " + player.spellbook.flamejet_lvl);
        fireball_m.setText("Mastery : " + player.spellbook.fireball_lvl);
        overheat_m.setText("Mastery : " + player.spellbook.overheat_lvl);
        chargedbolt_m.setText("Mastery : " + player.spellbook.chargedbolt_lvl);
        chain_m.setText("Mastery : " + player.spellbook.chainlightning_lvl);
        storm_m.setText("Mastery : " + player.spellbook.thunderstorm_lvl);
        missiles_m.setText("Mastery : " + player.spellbook.arcanemissile_lvl);
        beam_m.setText("Mastery : " + player.spellbook.energybeam_lvl);
        rifts_m.setText("Mastery : " + player.spellbook.rift_lvl);
        updateCheckBoxes();
    }

    public void dispose() {
        System.out.println("MASTERY TABLE DISPOSE METHOD");
        mixingTable.dispose();
        table.clear();
        table.remove();
    }
}
