package wizardo.game.Player;

import wizardo.game.Account.AccountProgress;
import wizardo.game.Items.Equipment.Equipment;
import wizardo.game.Maps.TriggerObject;
import wizardo.game.Player.Levels.LevelUp_Manager;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Spells.SpellManager;

public class Player {

    public BattleScreen screen;
    public TriggerObject nearbyTriggerObject = null;

    public SpellManager spellManager;
    public LevelUp_Manager levelUpManager;

    public Pawn pawn;
    public Spellbook spellbook;
    public Inventory inventory;
    public Stats stats;

    public int level = 1;
    public int monsterSouls = 0;
    public float currentXP = 0;
    public float neededXP = 100;
    public int levelup_choices = AccountProgress.loaded.max_levelup_choices;
    public int max_equipped_spells = AccountProgress.loaded.max_equipped_spells;

    int shieldtick = 0;


    public Player(Pawn pawn) {
        this.pawn = pawn;
        this.spellbook = new Spellbook();
        this.inventory = new Inventory();
        this.levelUpManager = new LevelUp_Manager();
        this.stats = new Stats();
    }

    public void update(float delta) {
        updateEquippedGear(delta);

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

    public void load_progress() {
        if (AccountProgress.loaded != null) {
            this.monsterSouls = AccountProgress.loaded.monster_souls;
            this.levelup_choices = AccountProgress.loaded.max_levelup_choices;
            this.max_equipped_spells = AccountProgress.loaded.max_equipped_spells;
        }
    }

}
