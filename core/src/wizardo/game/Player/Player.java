package wizardo.game.Player;

import wizardo.game.Spells.SpellManager;

import static wizardo.game.Utils.Constants.PPM;

public class Player {

    public SpellManager spellManager;

    public Pawn pawn;
    public Spellbook spellbook;
    public Inventory inventory;
    public Stats stats;

    public int level = 1;
    public int currentXP = 0;
    public int neededXP = 100;
    public int levelup_choices = 4;


    public Player(Pawn pawn) {
        this.pawn = pawn;
        this.spellbook = new Spellbook();
        this.inventory = new Inventory();
        this.stats = new Stats();
    }
}
