package wizardo.game.Player;

import static wizardo.game.Utils.Constants.PPM;

public class Player {

    public Pawn pawn;
    public Spellbook spellbook;
    public Inventory inventory;
    public Stats stats;

    public float runSpeed;

    public Player(Pawn pawn) {
        this.pawn = pawn;
        this.spellbook = new Spellbook();
        this.runSpeed = 80f/PPM;
        this.inventory = new Inventory();
        this.stats = new Stats();
    }
}
