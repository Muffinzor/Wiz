package wizardo.game.Screens.Battle;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import wizardo.game.Controls.KeyboardMouseListener_TABLEMENU;
import wizardo.game.Controls.ControllerListener_TABLEMENU;
import wizardo.game.Maps.Hub.HubChunk;
import wizardo.game.Maps.MapChunk;
import wizardo.game.Player.Pawn;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Wizardo;

public class BattleScreen extends BaseScreen {

    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public Pawn playerPawn;
    public MapChunk chunk;

    public BattleScreen(Wizardo game) {
        super(game);

        chunk = new HubChunk("Maps/TEST/Map_Project1.tmx", 0, 0, game, this);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
