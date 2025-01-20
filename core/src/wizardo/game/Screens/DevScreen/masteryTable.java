package wizardo.game.Screens.DevScreen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import wizardo.game.Audio.Sounds.SoundPlayer;
import wizardo.game.Display.MenuTable;
import wizardo.game.GameSettings;
import wizardo.game.Items.Equipment.Amulet.Epic_DragonbreathAmulet;
import wizardo.game.Items.Equipment.Book.Legendary_NecronomiconBook;
import wizardo.game.Items.Equipment.Ring.Rare_BeamRing;
import wizardo.game.Items.Equipment.Robes.Legendary_ArcaneRobes;
import wizardo.game.Items.Equipment.Staff.Epic_FireballStaff;
import wizardo.game.Items.Equipment.Staff.Legendary_FireStaff;
import wizardo.game.Screens.MainMenu.MainMenuButton;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.GameSettings.*;
import static wizardo.game.Resources.Skins.mainMenuSkin;
import static wizardo.game.Wizardo.player;

public class masteryTable extends MenuTable {

    public masteryTable(Stage stage, Skin skin, Wizardo game) {
        super(stage, skin, game);

        table.setFillParent(true);
        stage.addActor(table);

        flamejetButtons();
        fireballButtons();
        overheatButtons();
        frostboltButtons();
        icespearButtons();
        frozenorbButtons();
        chargedboltsButtons();
        chainButtons();
        thunderButtons();
        missilesButtons();
        beamButtons();
        riftsButtons();
        reagentsButtons();

    }

    public void flamejetButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Flamejet:" + player.spellbook.flamejet_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.flamejet_lvl++;
                label.setText("Flamejet:" + player.spellbook.flamejet_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.flamejet_lvl--;
                label.setText("Flamejet:" + player.spellbook.flamejet_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }
    public void fireballButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Fireball:" + player.spellbook.fireball_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.fireball_lvl++;
                label.setText("Fireball:" + player.spellbook.fireball_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.fireball_lvl--;
                label.setText("Fireball:" + player.spellbook.fireball_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }
    public void overheatButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Overheat:" + player.spellbook.overheat_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.overheat_lvl++;
                label.setText("Overheat:" + player.spellbook.overheat_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.overheat_lvl--;
                label.setText("Overheat:" + player.spellbook.overheat_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }
    public void frostboltButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Frostbolts:" + player.spellbook.frostbolt_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.frostbolt_lvl++;
                label.setText("Frostbolts:" + player.spellbook.frostbolt_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.frostbolt_lvl--;
                label.setText("Frostbolts:" + player.spellbook.frostbolt_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }
    public void icespearButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Ice Spear:" + player.spellbook.icespear_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.icespear_lvl++;
                label.setText("Ice Spear:" + player.spellbook.icespear_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.icespear_lvl--;
                label.setText("Ice Spear:" + player.spellbook.icespear_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }
    public void frozenorbButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Frozen Orb:" + player.spellbook.frozenorb_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.frozenorb_lvl++;
                label.setText("Frozen Orb:" + player.spellbook.frozenorb_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.frozenorb_lvl--;
                label.setText("Frozen Orb:" + player.spellbook.frozenorb_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }
    public void chargedboltsButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Chargedbolts:" + player.spellbook.chargedbolt_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.chargedbolt_lvl++;
                label.setText("Chargedbolts:" + player.spellbook.chargedbolt_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.chargedbolt_lvl--;
                label.setText("Chargedbolts:" + player.spellbook.chargedbolt_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }
    public void chainButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Chain Lightning:" + player.spellbook.chainlightning_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.chainlightning_lvl++;
                label.setText("Chain Lightning:" + player.spellbook.chainlightning_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.chainlightning_lvl--;
                label.setText("Chain Lightning:" + player.spellbook.chainlightning_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }
    public void thunderButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Thunderstorm:" + player.spellbook.thunderstorm_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.thunderstorm_lvl++;
                label.setText("Thunderstorm:" + player.spellbook.thunderstorm_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.thunderstorm_lvl--;
                label.setText("Thunderstorm:" + player.spellbook.thunderstorm_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }
    public void missilesButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Missiles:" + player.spellbook.arcanemissile_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.arcanemissile_lvl++;
                label.setText("Missiles:" + player.spellbook.arcanemissile_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.arcanemissile_lvl--;
                label.setText("Missiles:" + player.spellbook.arcanemissile_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }
    public void beamButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Energy Beam:" + player.spellbook.energybeam_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.energybeam_lvl++;
                label.setText("Energy Beam:" + player.spellbook.energybeam_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.energybeam_lvl--;
                label.setText("Energy Beam:" + player.spellbook.energybeam_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }
    public void riftsButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Rifts:" + player.spellbook.rift_lvl);
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.rift_lvl++;
                label.setText("Rifts:" + player.spellbook.rift_lvl);
            }
        });
        table.add(PLUS).padRight(5);

        ImageButton MINUS = new ImageButton(mainMenuSkin);
        MINUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.spellbook.rift_lvl--;
                label.setText("Rifts:" + player.spellbook.rift_lvl);
            }
        });
        table.add(MINUS);
        table.row();
    }

    public void reagentsButtons() {
        Label label = new Label("", mainMenuSkin);
        label.setText("Get Loot");
        table.add(label).padRight(5);
        ImageButton PLUS = new ImageButton(mainMenuSkin);
        PLUS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.inventory.dual_reagents = 30;
                player.inventory.triple_reagents = 30;
                player.inventory.holdingBox[14] = new Legendary_ArcaneRobes();
                player.inventory.holdingBox[13] = new Epic_FireballStaff();
                player.inventory.holdingBox[12] = new Legendary_NecronomiconBook();
                player.inventory.holdingBox[11] = new Epic_DragonbreathAmulet();
                player.inventory.holdingBox[10] = new Rare_BeamRing();

            }
        });
        table.add(PLUS).padRight(5);

        table.row();

        Label label2 = new Label("", mainMenuSkin);
        label2.setText("Get Gud");
        table.add(label2).padRight(5);
        ImageButton PLUS2 = new ImageButton(mainMenuSkin);
        PLUS2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.stats.runSpeed = 12.7f;
                player.stats.maxShield = 50000;
                player.stats.shield = 50000;
            }
        });
        table.add(PLUS2).padRight(5);


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
    public void pressSelectedButton() {

    }

    @Override
    public void resize() {

    }
}
