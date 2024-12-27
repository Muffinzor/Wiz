package wizardo.game.Screens.Character.EquipmentTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Resources.Skins.masteryTableSkin;

public class GearPanel {

    Sprite gem;
    NinePatch ninePatch = new NinePatch(new Texture("Screens/CharacterScreen/GearTable/smallNinePatch.png"), 60, 60, 65, 72);
    NinePatchDrawable backgroundDrawable = new NinePatchDrawable(ninePatch);

    Stage stage;
    Container<Table> container;
    Label titleLabel;
    ImageButton gemButton;
    Table contentTable;
    Table gearStatsTable;
    Equipment piece;

    boolean equipped;
    MenuButton button;

    public void setup() {
        switch(piece.quality) {
            case NORMAL -> gem = new Sprite(new Texture("Screens/CharacterScreen/GearTable/Gem_Green.png"));
            case RARE -> gem = new Sprite(new Texture("Screens/CharacterScreen/GearTable/Gem_Blue.png"));
            case EPIC -> gem = new Sprite(new Texture("Screens/CharacterScreen/GearTable/Gem_Purple.png"));
            case LEGENDARY -> gem = new Sprite(new Texture("Screens/CharacterScreen/GearTable/Gem_Red.png"));
        }
        container.setBackground(backgroundDrawable);
        container.setActor(contentTable);
        container.padLeft(45);
        container.padRight(42);
        container.padTop(70);
        container.padBottom(30);
    }

    public GearPanel(Stage stage, Equipment piece, boolean equipped, MenuButton button) {
        this.stage = stage;
        this.piece = piece;
        this.equipped = equipped;
        this.button = button;
        container = new Container<>();
        container.align(Align.top);
        contentTable = new Table();
        setup();

        createNameLabel();
        createDescriptionLabel();
        createStatLabels();
        createFlavorLabel();

        container.pack();
        setPosition();
        stage.addActor(container);
        createTitleLabel();
        createGemImage();

        //contentTable.setDebug(true);

    }

    public void setPosition() {
        if(equipped) {
            container.setPosition(500, 580 - container.getHeight()/2);
        } else {
            container.setPosition(540, 100);
        }
    }

    public void createTitleLabel() {
        float containerX = container.getX();
        float containerY = container.getY();
        float containerHeight = container.getHeight();
        float containerWidth = container.getWidth();
        float adjustedH = containerHeight - (30) + containerY;
        float centerW = containerWidth/2 + containerX;

        titleLabel = new Label(piece.title, inventorySkin, "Gear_Title");
        titleLabel.setColor(getTitleColor());
        //titleLabel.setDebug(true);
        titleLabel.setAlignment(Align.center);
        titleLabel.pack();
        titleLabel.setPosition(centerW - titleLabel.getWidth()/2, adjustedH - titleLabel.getHeight()/2);
        stage.addActor(titleLabel);

    }
    private Color getTitleColor() {
        Color color = null;
        switch(piece.quality) {
            case NORMAL -> color = inventorySkin.getColor("Green");
            case RARE -> color = inventorySkin.getColor("Blue");
            case EPIC -> color = inventorySkin.getColor("Purple");
            case LEGENDARY -> color = inventorySkin.getColor("Red");
        }
        return color;
    }

    public void createNameLabel() {
        Label nameLabel = new Label(piece.name, inventorySkin, "Gear_Name");
        nameLabel.setColor(getTitleColor());
        nameLabel.setAlignment(Align.center);
        contentTable.add(nameLabel).padTop(15).padBottom(20).expandX().align(Align.center).colspan(2);
        contentTable.row();
    }

    public void createDescriptionLabel() {
        if(piece.getDescription() != null) {
            Label textLabel = new Label(piece.getDescription(), inventorySkin, "Gear_Text");
            textLabel.setAlignment(Align.center);
            contentTable.add(textLabel).expandX().align(Align.center).colspan(2);
            contentTable.row().padTop(20);
        }
    }

    public void createStatLabels() {
        if(!piece.masteries.isEmpty() || !piece.gearStats.isEmpty()) {
            gearStatsTable = new Table();
        }
        if(!piece.masteries.isEmpty()) {
            for (int i = 0; i < piece.masteries.size(); i++) {
                String s = "+" + piece.quantity_masteries.get(i) + " to " + getSpellString(piece.masteries.get(i)) + " mastery";
                Label masteryLabel = new Label(s , inventorySkin, "Gear_Text");
                masteryLabel.setColor(getMasteryColor(piece.masteries.get(i)));
                masteryLabel.setAlignment(Align.left);
                gearStatsTable.add(masteryLabel).align(Align.left).fillX();
                gearStatsTable.row().padTop(5);
            }
        }
        if(!piece.gearStats.isEmpty()) {
            for (int i = 0; i < piece.gearStats.size(); i++) {
                String s = "" + getGearStatString(piece.gearStats.get(i));
                Label gearStatLabel = new Label(s, inventorySkin, "Gear_Text");
                gearStatLabel.setColor(getGearStatColor(piece.gearStats.get(i)));
                gearStatLabel.setAlignment(Align.left);
                gearStatsTable.add(gearStatLabel).align(Align.left).expandX();

                String value = "" + piece.getStatValue(i) + "%";
                Label gearStatValue = new Label(value, inventorySkin, "Gear_Text");
                gearStatValue.setColor(Color.GREEN);
                gearStatValue.setAlignment(Align.left);
                gearStatsTable.pack();
                gearStatsTable.add(gearStatValue).align(Align.left);
                gearStatsTable.row().padTop(5);
            }
        }
        contentTable.add(gearStatsTable).align(Align.left);
        contentTable.row();
        container.pack();
    }
    public Color getMasteryColor(SpellUtils.Spell_Name spell) {
        Color color = null;
        switch(spell) {
            case FLAMEJET, FIREBALL, OVERHEAT -> color = inventorySkin.getColor("Fire");
            case FROSTBOLT, ICESPEAR, FROZENORB-> color = inventorySkin.getColor("Frost");
            case CHARGEDBOLTS, CHAIN, THUNDERSTORM -> color = inventorySkin.getColor("Lightning");
            case MISSILES, BEAM, RIFTS -> color = inventorySkin.getColor("Arcane");
        }
        return color;
    }
    public Color getGearStatColor(ItemUtils.GearStat stat) {
        Color color = Color.WHITE;
        if(stat == null) {
            return color;
        }
        switch (stat) {
            case FIREDMG -> color = inventorySkin.getColor("Fire");
            case FROSTDMG -> color = inventorySkin.getColor("Frost");
            case LITEDMG -> color = inventorySkin.getColor("Lightning");
            case ARCANEDMG -> color = inventorySkin.getColor("Arcane");
        }
        return color;
    }
    public String getSpellString(SpellUtils.Spell_Name spell) {
        String s = "";
        switch(spell) {
            case FROSTBOLT -> s = "Frostbolts";
            case ICESPEAR -> s = "Icespear";
            case FROZENORB -> s = "Frozen Orb";
            case FLAMEJET -> s = "Flamejet";
            case FIREBALL -> s = "Fireball";
            case OVERHEAT -> s = "Overheat";
            case CHARGEDBOLTS -> s = "Chargedbolts";
            case CHAIN -> s = "Chain Lightning";
            case THUNDERSTORM -> s = "Thunderstorm";
            case MISSILES -> s = "Arcane Missiles";
            case BEAM -> s = "Energy Beam";
            case RIFTS -> s = "Rifts";
        }
        return s;
    }
    public String getGearStatString(ItemUtils.GearStat stat) {
        String s = "";
        if(stat == null) {
            return "Current bonus: ";
        }
        switch(stat) {
            case FIREDMG -> s = "Increased fire damage: ";
            case FROSTDMG -> s = "Increased frost damage: ";
            case LITEDMG -> s = "Increased lightning damage: ";
            case ARCANEDMG -> s = "Increased arcane damage: ";
        }
        return s;
    }


    public void createFlavorLabel() {
        if(piece.getFlavorText() != null) {
            Label textLabel = new Label(piece.getFlavorText(), inventorySkin, "Gear_Flavor");
            textLabel.setAlignment(Align.center);
            contentTable.add(textLabel).padBottom(60).padTop(15).expandX().align(Align.top).colspan(2);
            contentTable.row();
        }
    }

    public void createGemImage() {
        float containerX = container.getX();
        float containerY = container.getY();
        float containerWidth = container.getWidth();
        float adjustedH = containerY + 33;
        float centerW = containerWidth/2 + containerX;

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new SpriteDrawable(gem);
        gemButton = new ImageButton(style);

        gemButton.setPosition(centerW - gemButton.getWidth()/2, adjustedH - gemButton.getHeight()/2);
        stage.addActor(gemButton);
    }

    public void dispose() {
        contentTable.clear();
        titleLabel.remove();
        gemButton.remove();
        container.clear();
        container.remove();
    }
}
