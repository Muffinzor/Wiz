package wizardo.game.NPC;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Resources.NpcResources;
import wizardo.game.Screens.Hub.HubScreen;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;

public class Witch_NPC extends NPC {

    Witch_Trigger trigger;

    public Witch_NPC(MapChunk chunk, MapObject object) {
        super(chunk, object);
        anim = NpcResources.witch_anim;
        create_body();
        create_trigger();
    }

    public void create_body() {
        Vector2 position = new Vector2(x, y);
        body = BodyFactory.NpcBody(position);
    }

    public void create_trigger() {
        trigger = new Witch_Trigger(chunk, object);
    }

}
