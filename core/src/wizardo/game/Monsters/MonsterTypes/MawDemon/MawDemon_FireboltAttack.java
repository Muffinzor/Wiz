package wizardo.game.Monsters.MonsterTypes.MawDemon;

import com.badlogic.gdx.math.Vector2;
import wizardo.game.Monsters.MonsterActions.MonsterSpell;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Hybrid.LightningHands.LightningHands_Projectile;

import static wizardo.game.Wizardo.player;

public class MawDemon_FireboltAttack extends MonsterSpell {

    public MawDemon_FireboltAttack(Monster monster) {
        super(monster);

    }

    @Override
    public void checkState(float delta) {

        targetPosition = new Vector2(player.pawn.getPosition());
        direction = targetPosition.cpy().sub(originMonster.body.getPosition());

        int bolts = 3;
        float coneAngle = 25;
        float angleStep = coneAngle / (bolts - 1);
        float startAngle = -coneAngle / 2;

        for (int i = 0; i < bolts; i++) {
            Vector2 dir = direction.cpy().rotateDeg(startAngle + i * angleStep);
            MawDemon_FireboltProjectile proj = new MawDemon_FireboltProjectile(originMonster);
            proj.spawnPosition = originMonster.body.getPosition();
            proj.direction = dir;
            screen.monsterSpellManager.toAdd(proj);
        }

        screen.monsterSpellManager.toRemove(this);
    }

    @Override
    public void drawFrame() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void dispose() {

    }
}
