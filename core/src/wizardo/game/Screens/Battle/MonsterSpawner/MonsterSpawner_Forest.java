package wizardo.game.Screens.Battle.MonsterSpawner;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.ForestMonsters.OrcBrute;
import wizardo.game.Monsters.ForestMonsters.OrcMinion;
import wizardo.game.Monsters.ForestMonsters.OrcShaman;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Monsters.MonsterTypes.MawDemon.MawDemon;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Spells.SpellUtils;
import wizardo.game.Utils.BodyPool;

import static wizardo.game.Screens.BaseScreen.xRatio;
import static wizardo.game.Wizardo.player;

public class MonsterSpawner_Forest extends MonsterSpawner {

    public MonsterSpawner_Forest(BattleScreen screen) {
        super(screen);
        bodyPool = new BodyPool();

        playerPreviousLocation = player.pawn.getPosition();
        playerCurrentLocation = player.pawn.getPosition();
    }

    public void update(float delta) {
        super.update(delta);
    }

}
