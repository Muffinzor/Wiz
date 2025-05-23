package wizardo.game.Monsters.DungeonMonsters.MawDemon;

import wizardo.game.Monsters.MonsterStateManager.StateManager;
import wizardo.game.Monsters.MonsterUtils;

import static wizardo.game.Monsters.MonsterUtils.MONSTER_STATE.*;
import static wizardo.game.Wizardo.player;

public class MawDemonStateManager implements StateManager {

    int frameCounter;
    float chargeCooldown = 6;

    MawDemon demon;

    public MawDemonStateManager(MawDemon demon) {
        this.demon = demon;
    }

    @Override
    public void updateState(float delta) {
        chargeCooldown -= delta;
        frameCounter ++;

        if(frameCounter >= 10) {

            float dst = demon.body.getPosition().dst(player.pawn.getPosition());

            if(chargeCooldown <= 0 && dst < 12) {
                if(MonsterUtils.hasCompleteLoS_withPlayer(demon)) {
                    chargeCooldown = 6;
                    demon.state = CHARGING;
                    demon.initiateCharge();
                }
            } else if(dst > 4 && demon.state != CHARGING) {
                demon.state = ADVANCING;
            }
        }
    }
}
