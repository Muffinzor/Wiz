package wizardo.game.Player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Maps.TriggerObject;
import wizardo.game.Resources.PlayerResources;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Spells.SpellManager;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Screens.BaseScreen.yRatio;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class Player {

    public BattleScreen screen;

    public SpellManager spellManager;
    public TriggerObject nearbyTriggerObject = null;

    public Pawn pawn;
    public Spellbook spellbook;
    public Inventory inventory;
    public Stats stats;

    public int level = 1;
    public int currentXP = 0;
    public int neededXP = 100;
    public int levelup_choices = 4;

    int shieldtick = 0;


    public Player(Pawn pawn) {
        this.pawn = pawn;
        this.spellbook = new Spellbook();
        this.inventory = new Inventory();
        this.stats = new Stats();
    }


    public void update(float delta) {
        updateEquippedGear(delta);
        drawPlayerHP();

        if(stats.shield < 0) {
            stats.shield = 0;
        }
        if(delta > 0) {
            shieldtick++;
            if (shieldtick > 60) {
                stats.shield += stats.baseRecharge;
                shieldtick = 0;
                if (stats.shield > stats.maxShield) {
                    stats.shield = stats.maxShield;
                }
            }
        }
    }
    public void updateEquippedGear(float delta) {
        for(Equipment piece : inventory.equippedGear) {
            piece.update(delta);
        }
    }
    public void drawPlayerHP() {

        Sprite frame1 = screen.getSprite();
        frame1.set(PlayerResources.shieldEffect);
        frame1.setScale(1, 1); // Ensure no scaling distortions
        frame1.setOrigin(0, 0);
        frame1.setPosition(80 * xRatio, 65 * yRatio);

        float shieldPercentage = stats.shield / stats.maxShield;

        float originalWidth = frame1.getRegionWidth();
        float renderWidth = originalWidth * shieldPercentage;
        frame1.setRegion(frame1.getRegionX(), frame1.getRegionY(), (int) renderWidth, frame1.getRegionHeight());

        frame1.setSize(renderWidth * xRatio, frame1.getHeight() * yRatio);
        screen.displayManager.spriteRenderer.ui_sprites.add(frame1);


        Sprite frame2 = screen.getSprite();
        frame2.set(PlayerResources.shieldBar);
        frame2.setScale(xRatio, yRatio);
        frame2.setOrigin(0,0);
        frame2.setPosition(50 * xRatio, 45 * yRatio);
        screen.displayManager.spriteRenderer.ui_sprites.add(frame2);

    }
}
