package wizardo.game.Screens.Hub.HubDecor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import wizardo.game.Maps.EnvironmentObject;
import wizardo.game.Maps.MapGeneration.MapChunk;
import wizardo.game.Maps.MapUtils;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.world;

public class Cauldron extends EnvironmentObject {

    Body body;
    Animation<Sprite> anim;

    public Cauldron(MapChunk chunk, MapObject object) {
        super(chunk, object);
        createAnim();
    }

    public void createAnim() {
        Sprite[] frames = new Sprite[5];
        frames[0] = new Sprite(new Texture("Maps/Decor/Hub/Cauldron/cauldron1.png"));
        frames[1] = new Sprite(new Texture("Maps/Decor/Hub/Cauldron/cauldron2.png"));
        frames[2] = new Sprite(new Texture("Maps/Decor/Hub/Cauldron/cauldron3.png"));
        frames[3] = new Sprite(new Texture("Maps/Decor/Hub/Cauldron/cauldron4.png"));
        frames[4] = new Sprite(new Texture("Maps/Decor/Hub/Cauldron/cauldron5.png"));
        anim = new Animation<Sprite>(0.1f, frames);
    }

    @Override
    public void update(float delta) {
        if(!initialized) {
            initialized = true;
            createBody();
        }
        drawFrame();
        stateTime+= delta;
    }

    public void createBody() {
        body = MapUtils.createCircleObstacleBody_FromTiledMap(chunk, object, 10);
    }

    public void drawFrame() {
        Sprite frame = chunk.screen.getSprite();
        frame.set(anim.getKeyFrame(stateTime, true));
        frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        chunk.screen.addSortedSprite(frame);
        chunk.screen.centerSort(frame, body.getPosition().y * PPM - 10);
    }

    @Override
    public void dispose() {
        world.destroyBody(body);
    }
}
