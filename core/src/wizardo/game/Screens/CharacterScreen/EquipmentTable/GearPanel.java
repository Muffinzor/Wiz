package wizardo.game.Screens.CharacterScreen.EquipmentTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Items.ItemUtils;
import wizardo.game.Spells.SpellUtils;

import static wizardo.game.Resources.Skins.inventorySkin;
import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;

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

        Pixmap ogPixmap = new Pixmap(Gdx.files.internal("Screens/CharacterScreen/GearTable/smallNinePatch.png"));
        int newWidth = (int) (ogPixmap.getWidth() * xRatio);
        int newHeight = (int) (ogPixmap.getHeight() * yRatio);
        Pixmap scaledPixmap = new Pixmap(newWidth, newHeight, ogPixmap.getFormat());

        scaledPixmap.drawPixmap(
                ogPixmap,
                0, 0, ogPixmap.getWidth(), ogPixmap.getHeight(),
                0, 0, newWidth, newHeight
        );

        Texture scaledTexture = new Texture(scaledPixmap);
        ogPixmap.dispose();
        scaledPixmap.dispose();

        int xPad = (int) (60 * xRatio);
        NinePatch ninePatch = new NinePatch(scaledTexture, xPad, xPad,
                (int) (65 * yRatio), (int) (72 * yRatio));
        backgroundDrawable = new NinePatchDrawable(ninePatch);

        container.setBackground(backgroundDrawable);
        container.setActor(contentTable);
        container.padLeft(45* xRatio);
        container.padRight(42* xRatio);
        container.padTop(70* yRatio);
        container.padBottom(30* yRatio);
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


    }

    public void setPosition() {
        if(equipped) {
            container.setPosition(500 * xRatio, (580 - container.getHeight()/2) * yRatio);
        } else {
            container.setPosition(540 * xRatio, 100 * yRatio);
        }
    }

    public void createTitleLabel() {
        float containerX = container.getX();
        float containerY = container.getY();
        float containerHeight = container.getHeight();
        float containerWidth = container.getWidth();
        float adjustedH = containerHeight - (30 * yRatio) + containerY;
        float centerW = containerWidth/2 + containerX;

        titleLabel = new Label(piece.title, inventorySkin, "Gear_Title");
        titleLabel.setColor(getTitleColor());
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
        contentTable.add(nameLabel).padTop(15* yRatio).padBottom(20* yRatio).expandX().align(Align.center).colspan(2);
        contentTable.row();
    }

    public void createDescriptionLabel() {
        if(piece.getDescription() != null) {
            Label textLabel = new Label(piece.getDescription(), inventorySkin, "Gear_Text");
            textLabel.setAlignment(Align.center);
            contentTable.add(textLabel).expandX().align(Align.center).colspan(2).padBottom(20* yRatio);
            contentTable.row();
        }
    }

    public void createStatLabels() {
        if(!piece.masteries.isEmpty() || !piece.gearStats.isEmpty()) {
            gearStatsTable = new Table();
            if (!piece.masteries.isEmpty()) {
                for (int i = 0; i < piece.masteries.size(); i++) {
                    String s = "+" + piece.quantity_masteries.get(i) + " to " + getSpellString(piece.masteries.get(i)) + " mastery";
                    Label masteryLabel = new Label(s, inventorySkin, "Gear_Text");
                    masteryLabel.setColor(getMasteryColor(piece.masteries.get(i)));
                    masteryLabel.setAlignment(Align.left);
                    gearStatsTable.add(masteryLabel).align(Align.left).fillX();
                    gearStatsTable.row().padTop(5* yRatio);
                }
            }
            if (!piece.gearStats.isEmpty()) {
                for (int i = 0; i < piece.gearStats.size(); i++) {
                    Table smalltable = new Table();
                    String s = "" + getGearStatString(piece.gearStats.get(i));
                    Label gearStatLabel = new Label(s, inventorySkin, "Gear_Text");
                    gearStatLabel.setColor(getGearStatColor(piece.gearStats.get(i)));
                    gearStatLabel.setAlignment(Align.left);
                    smalltable.add(gearStatLabel).align(Align.left);

                    String percent = getPercentIfNeeded(piece.gearStats.get(i));
                    float statValue = piece.getStatValue(i);
                    String formattedValue = (statValue == Math.floor(statValue))
                            ? String.format("%.0f", statValue)
                            : String.format("%.1f", statValue);

                    String value = formattedValue + percent;
                    Label gearStatValue = new Label(value, inventorySkin, "Gear_Text");
                    gearStatValue.setColor(Color.GREEN);
                    gearStatValue.setAlignment(Align.left);
                    smalltable.add(gearStatValue).align(Align.left);
                    gearStatsTable.add(smalltable).align(Align.left);
                    gearStatsTable.row().padTop(5* yRatio);
                }
            }
            contentTable.add(gearStatsTable).align(Align.left);
            contentTable.row().padTop(20* yRatio);
            container.pack();
        }

    }

    public String getPercentIfNeeded(ItemUtils.GearStat stat) {
        String s = "%";
        if(stat == null) {
            return s;
        }
        switch(stat) {
            case LUCK, DEFENSE, MASTERY_FROST, MASTERY_ARCANE, MASTERY_LIGHTNING, MASTERY_FIRE, REGEN, MASTERY_ALL -> s = "";
        }
        return s;
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
            case FIREDMG, MASTERY_FIRE -> color = inventorySkin.getColor("Fire");
            case FROSTDMG, MASTERY_FROST -> color = inventorySkin.getColor("Frost");
            case LITEDMG, MASTERY_LIGHTNING -> color = inventorySkin.getColor("Lightning");
            case ARCANEDMG, MASTERY_ARCANE -> color = inventorySkin.getColor("Arcane");
            case REGEN -> color = inventorySkin.getColor("Regen");
            case MASTERY_ALL -> color = Color.GREEN;
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
            case ALLDMG -> s = "All damage increased by: ";
            case CASTSPEED -> s = "Cooldown reduction: ";
            case MULTICAST -> s = "Multicast chance: ";
            case LUCK -> s = "Luck: ";
            case MASTERY_FROST -> s = "Frost masteries increased by ";
            case MASTERY_FIRE -> s = "Fire masteries increased by ";
            case MASTERY_LIGHTNING -> s = "Lightning masteries increased by ";
            case MASTERY_ARCANE -> s = "Arcane masteries increased by ";
            case MASTERY_ALL -> s = "All masteries increased by ";
            case REGEN -> s = "Bonus shield per second: ";
            case DEFENSE -> s = "Damage received reduced by: ";
            case PROJSPEED -> s = "Increased projectile speed: ";
            case WALKSPEED -> s = "Increased walking speed: ";
        }
        return s;
    }


    public void createFlavorLabel() {
        if(piece.getFlavorText() != null) {
            Label textLabel = new Label(piece.getFlavorText(), inventorySkin, "Gear_Flavor");
            textLabel.setAlignment(Align.center);
            contentTable.add(textLabel).padBottom(60* yRatio).expandX().align(Align.top).colspan(2);
            contentTable.row();
        }
    }

    public void createGemImage() {
        float containerX = container.getX();
        float containerY = container.getY();
        float containerWidth = container.getWidth();
        float adjustedH = containerY + 33* yRatio;
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
