package wizardo.game.Screens.CharacterScreen.StatsTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import wizardo.game.Resources.Skins;

import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Wizardo.player;

public class StatsTable {

    Stage stage;
    Container<Table> container;
    Table contentTable;

    Table leftTable;
    Table rightTable;

    public void setup() {
        // Load and scale Pixmap
        Pixmap ogPixmap = new Pixmap(Gdx.files.internal("Screens/CharacterScreen/panelBorder1.png"));
        int newWidth = (int) (ogPixmap.getWidth() * xRatio);
        int newHeight = (int) (ogPixmap.getHeight() * yRatio); // Corrected scaling
        Pixmap scaledPixmap = new Pixmap(newWidth, newHeight, ogPixmap.getFormat());

        scaledPixmap.drawPixmap(
                ogPixmap,
                0, 0, ogPixmap.getWidth(), ogPixmap.getHeight(),
                0, 0, newWidth, newHeight
        );

        Texture scaledTexture = new Texture(scaledPixmap);
        ogPixmap.dispose();
        scaledPixmap.dispose();

        // Define padding for the NinePatch
        int xPad = (int) (85 * xRatio);
        int yPad = (int) (68 * yRatio);

        // Create NinePatch
        NinePatch ninePatch = new NinePatch(scaledTexture, xPad, xPad, yPad, yPad);
        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(ninePatch);

        // Configure the Container
        container.setBackground(backgroundDrawable);
        container.setActor(contentTable);

        // Apply padding and size
        container.padLeft(65 * xRatio);
        container.padRight(65 * xRatio);
        container.padTop(60 * yRatio);
        container.padBottom(75 * yRatio);

        // Set the container size
        container.setSize(550 * xRatio, 420 * yRatio);

        // Ensure layout updates to reflect changes
        container.validate();
    }


    public StatsTable(Stage stage) {
        this.stage = stage;

        container = new Container<>();
        container.align(Align.topLeft);
        contentTable = new Table();

        stage.addActor(container);
        container.setActor(contentTable);

        createNewPanel();

    }


    public void createNewPanel() {
        setup();
        setPosition();
        contentTable.clear();
        leftTable = new Table();
        rightTable = new Table();
        contentTable.add(leftTable).fill().expandX().width(container.getWidth() / 2f);
        contentTable.add(rightTable).fill().expandX().width(container.getWidth() / 2f);
        addTitles();
        SpellCastingTable();
        container.pack();
    }

    public void addTitles() {
        Table titleLabel = new Table();
        Label label = new Label("Damage", inventorySkin, "Stats_Title");
        //label.setColor(Skins.light_yellow);
        label.setAlignment(Align.center);
        titleLabel.add(label).expandX().fill();
        leftTable.add(titleLabel);
        leftTable.row().padTop(20);

        Label label2 = new Label("Defense", inventorySkin, "Stats_Title");
        //label2.setColor(Skins.light_yellow);
        label2.setAlignment(Align.center);
        rightTable.add(label2).expandX().fill();
        rightTable.row().padBottom(20);
    }

    public void SpellCastingTable() {
        Table elementalTable = new Table();
        elementalTable.align(Align.left);

        /** FROST DAMAGE **/
        Label frost = new Label("Frost damage: ", inventorySkin, "Gear_Text");
        frost.setColor(Skins.light_blue);
        elementalTable.add(frost).expandX().fill();
        frost.setAlignment(Align.left);

        String frostString = "" + (int) player.spellbook.frostBonusDmg + "%";
        Label frostDmg = new Label(frostString, inventorySkin, "Gear_Text");
        frostDmg.setColor(Color.GREEN);
        elementalTable.add(frostDmg).expandX();
        elementalTable.row();

        /** Fire DAMAGE **/
        Label fire = new Label("Fire damage: ", inventorySkin, "Gear_Text");
        fire.setColor(Skins.light_orange);
        elementalTable.add(fire).expandX().fill();
        fire.setAlignment(Align.left);

        String fireString = "" + (int) player.spellbook.fireBonusDmg + "%";
        Label fireDmg = new Label(fireString, inventorySkin, "Gear_Text");
        fireDmg.setColor(Color.GREEN);
        elementalTable.add(fireDmg);
        elementalTable.row();

        /** LIGHTNING DAMAGE **/
        Label lite = new Label("Lightning damage: ", inventorySkin, "Gear_Text");
        lite.setColor(Skins.light_yellow);
        elementalTable.add(lite).expandX().fill();
        lite.setAlignment(Align.left);

        String liteString = "" + (int) player.spellbook.lightningBonusDmg + "%";
        Label liteDmg = new Label(liteString, inventorySkin, "Gear_Text");
        liteDmg.setColor(Color.GREEN);
        elementalTable.add(liteDmg);
        elementalTable.row();

        /** ARCANE DAMAGE **/
        Label arcane = new Label("Arcane damage: ", inventorySkin, "Gear_Text");
        arcane.setColor(Skins.light_pink);
        elementalTable.add(arcane).expandX().fill();
        arcane.setAlignment(Align.left);

        String arcString = "" + (int) player.spellbook.arcaneBonusDmg + "%";
        Label arcDmg = new Label(arcString, inventorySkin, "Gear_Text");
        arcDmg.setColor(Color.GREEN);
        elementalTable.add(arcDmg);
        elementalTable.row();

        /** ALL DAMAGE **/
        Label all = new Label("All damage: ", inventorySkin, "Gear_Text");
        all.setColor(Skins.light_green);
        elementalTable.add(all).expandX().fill();
        all.setAlignment(Align.left);

        String allString = "" + (int) player.spellbook.allBonusDmg + "%";
        Label allDmg = new Label(allString, inventorySkin, "Gear_Text");
        allDmg.setColor(Color.GREEN);
        elementalTable.add(allDmg);
        elementalTable.row().padTop(20);

        /** CAST SPEED **/
        Label castSpeed = new Label("Cooldown reduction: ", inventorySkin, "Gear_Text");
        elementalTable.add(castSpeed).expandX().fill();
        castSpeed.setAlignment(Align.left);

        String speedString = "" + (int) player.spellbook.castSpeed + "%";
        Label speed = new Label(speedString, inventorySkin, "Gear_Text");
        speed.setColor(Color.GREEN);
        elementalTable.add(speed).expandX();
        elementalTable.row();

        /** MULTICAST **/
        Label multicast = new Label("Multicast chance: ", inventorySkin, "Gear_Text");
        elementalTable.add(multicast).expandX().fill();
        multicast.setAlignment(Align.left);

        String multiString = "" + (int) player.spellbook.multicast + "%";
        Label multi = new Label(multiString, inventorySkin, "Gear_Text");
        multi.setColor(Color.GREEN);
        elementalTable.add(multi).expandX();
        elementalTable.row();

        /** PROJ SPEED **/
        Label projspeed = new Label("Projectile speed: ", inventorySkin, "Gear_Text");
        elementalTable.add(projspeed).expandX().fill();
        projspeed.setAlignment(Align.left);

        String projString = "" + (int) player.spellbook.projSpeedBonus + "%";
        Label proj = new Label(projString, inventorySkin, "Gear_Text");
        proj.setColor(Color.GREEN);
        elementalTable.add(proj).expandX();
        elementalTable.row();

        leftTable.add(elementalTable).expandX().fill();
        leftTable.row().padTop(20);
    }

    public void setPosition() {
        container.setPosition(600 * xRatio, 545 * yRatio);
    }

    public void dispose() {
        contentTable.clear();
        container.clear();
        container.remove();
    }
}
