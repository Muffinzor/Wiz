package wizardo.game.Screens.CharacterScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Display.MenuTable;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Resources.ScreenResources.CharacterScreenResources;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.CharacterScreen.Anims.Screen_Anim;
import wizardo.game.Screens.CharacterScreen.BookTable.EquippedSpells_Table;
import wizardo.game.Screens.CharacterScreen.BookTable.KnownSpells_Table;
import wizardo.game.Screens.CharacterScreen.BookTable.SpellIcon_Button;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.EquipmentTable;
import wizardo.game.Screens.CharacterScreen.EquipmentTable.GearPanel;
import wizardo.game.Screens.CharacterScreen.InventoryTable.InventoryTable;
import wizardo.game.Screens.CharacterScreen.MasteryTable.MasteryTable;
import wizardo.game.Screens.CharacterScreen.StatsTable.StatsTable;
import wizardo.game.Wizardo;

import java.util.ArrayList;

import static wizardo.game.Resources.Skins.*;
import static wizardo.game.Wizardo.player;

public class CharacterScreen extends BaseScreen {

    float stateTime;
    public ArrayList<Screen_Anim> anims;

    public MasteryTable mastery_table;
    public EquippedSpells_Table equippedSpells_table;
    public KnownSpells_Table knownSpells_table;
    public EquipmentTable equipment_table;
    public InventoryTable inventory_table;
    public StatsTable stats_Table;
    public Equipment selectedEquipmentPiece;

    public Button selectedButton;
    public MenuTable activeTable;
    public GearPanel activePanel;
    public Stage panelStage;

    public SpellIcon_Button selectedSpell_Button;
    int selected_rotation;
    public float selected_scale = 1;
    boolean selected_growing;

    private final Sprite background;

    public CharacterScreen(Wizardo game) {
        super(game);
        this.anims = new ArrayList<>();

        stage = new Stage(new ScreenViewport(uiCamera));
        panelStage = new Stage(new ScreenViewport(uiCamera));

        background = new Sprite(new Texture("Screens/CharacterScreen/Background_Larger.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        mastery_table = new MasteryTable(stage, masteryTableSkin, game, this);
        activeTable = mastery_table;

        equipment_table = new EquipmentTable(stage, inventorySkin, game, this);
        inventory_table = new InventoryTable(stage, inventorySkin, game, this);

        equippedSpells_table = new EquippedSpells_Table(stage, bookTableSkin, game, this);
        knownSpells_table = new KnownSpells_Table(stage, bookTableSkin, game, this);
        stats_Table = new StatsTable(stage);
    }
    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;

        super.resize(width, height);

        background.setSize(width, height);
        batch.setProjectionMatrix(uiCamera.combined);

        stage.setViewport(new ScreenViewport(uiCamera));
        stage.getViewport().update(width, height, true);

        panelStage.clear();
        panelStage.getViewport().update(width, height, true);

        equipment_table.resize();
        inventory_table.resize();

        mastery_table.resize();
        mastery_table.updateChanges();
        mastery_table.updateCheckBoxes();
        mastery_table.updateSelectedButton();

        equippedSpells_table.resize();
        knownSpells_table.resize();

        activeTable = mastery_table;

        setInputs();

        stats_Table.createNewPanel();

    }

    @Override
    public void render(float delta) {
        stateTime += delta;
        globalCD -= delta;

        batch.begin();
        background.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();

        equipment_table.update();
        inventory_table.update();

        panelStage.act(delta);
        panelStage.draw();

        if(controllerActive) {
            drawSelectedButton();
        }

        if(selectedSpell_Button != null && !paused) {
            drawSelectedSpell();
        }

        drawSpellCreation(delta);

    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        if(selectedEquipmentPiece != null && choiceConfirmed) {
            player.inventory.destroyItem(selectedEquipmentPiece);
            selectedEquipmentPiece = null;
            choiceConfirmed = false;
            inventory_table.resize();
        }
        selectedSpell_Button = null;
        paused = false;
    }

    @Override
    public void dispose() {
        mastery_table.dispose();
    }

    @Override
    public void show() {
        setCursorTexture();
        if(controllerActive) {
            hideCursor();
        }
    }


    public void drawSelectedButton() {
        Sprite frame = getSprite();
        frame.set(CharacterScreenResources.selected_button_anim.getKeyFrame(stateTime, true));

        Vector2 buttonLocalPos = new Vector2(selectedButton.getX(), selectedButton.getY());

        Vector2 tableLocalPos = new Vector2(selectedButton.getParent().getX(), selectedButton.getParent().getY());

        float screenButton_X = tableLocalPos.x + buttonLocalPos.x;
        float screenButton_Y = tableLocalPos.y + buttonLocalPos.y;

        screenButton_X += selectedButton.getWidth()/2;
        screenButton_Y += selectedButton.getHeight()/2;

        frame.setScale(Gdx.graphics.getWidth()/1920f);
        frame.setCenter(screenButton_X, screenButton_Y);


        batch.begin();
        frame.draw(batch);
        batch.end();
    }

    public void drawSelectedSpell() {
        if (selectedSpell_Button == null || selectedSpell_Button.getParent() == null) {
            return; // Skip drawing if the button or its parent is null
        }

        selected_rotation += 3f;
        if(selected_growing) {
            selected_scale += 0.0025f;
            if(selected_scale > 1) {
                selected_growing = false;
            }
        } else {
            selected_scale -= 0.0025f;
            if(selected_scale < 0.85) {
                selected_growing = true;
            }
        }

        boolean equipped = player.spellbook.equippedSpells.contains(selectedSpell_Button.spell);

        Sprite frame = getSprite();
        frame.set(CharacterScreenResources.circle_selected);

        frame.setCenter(selectedSpell_Button.getCenter().x, selectedSpell_Button.getCenter().y);
        frame.setRotation(selected_rotation);

        if(equipped) {
            frame.setScale(selected_scale * Gdx.graphics.getWidth() / 1920f);
        } else {
            frame.setScale(selected_scale * Gdx.graphics.getWidth() / 1920f * 0.75f);
        }

        batch.begin();
        frame.draw(batch);
        batch.end();

    }
    public void drawSpellCreation(float delta) {
        batch.begin();
        for(Screen_Anim anim : anims) {
            anim.draw(batch);
            anim.stateTime += delta;
        }
        anims.removeIf(Screen_Anim::isFinished);
        batch.end();
    }

}
