package wizardo.game.Monsters.DungeonMonsters.MawDemon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Resources.MonsterResources.MawDemonAnims;

import static wizardo.game.Utils.Constants.PPM;

public class MawDemon_Pentagram extends MonsterSpell {

    Sprite pentaFrame;
    Vector2 animPosition;
    float alpha = 0.1f;

    public MawDemon_Pentagram(Monster monster) {
        super(monster);
    }

    @Override
    public void checkState(float delta) {
        if(alpha < 1 && stateTime < 1) {
            alpha += 0.05f;
        } else if(stateTime > 4.5f) {
            alpha -= 0.02f;
        }
        if(alpha < 0.1f) {
            screen.monsterSpellManager.toRemove(this);
        }
    }

    @Override
    public void drawFrame() {
        Sprite frame = screen.getSprite();
        frame.set(pentaFrame);
        frame.setAlpha(alpha);
        frame.setCenter(animPosition.x * PPM, animPosition.y * PPM - 15);
        screen.addUnderSprite(frame);
    }


    @Override
    public void initialize() {
        pentaFrame = new Sprite(new Texture("Monsters/MawDemon/Pentagram.png"));
        animPosition = new Vector2(originMonster.body.getPosition());
    }

    @Override
    public void dispose() {

    }
}
