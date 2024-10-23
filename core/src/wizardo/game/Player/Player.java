package wizardo.game.Player;

import static wizardo.game.Utils.Constants.PPM;

public class Player {

    public Pawn pawn;
    public Spellbook spellbook;
    public Inventory inventory;

    public float runSpeed;

    public Player(Pawn pawn) {
        this.pawn = pawn;
        this.spellbook = new Spellbook();
        this.runSpeed = 120f/PPM;
        this.inventory = new Inventory();
    }
}
