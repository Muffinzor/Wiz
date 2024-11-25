package wizardo.game.Monsters.MonsterProjectiles;

import wizardo.game.Screens.Battle.BattleScreen;

import java.util.ArrayList;

public class MonsterProjectileManager {

    BattleScreen screen;
    ArrayList<MonsterProjectile> projsToCast;
    ArrayList<MonsterProjectile> activeProjs;
    ArrayList<MonsterProjectile> projsToRemove;

    public MonsterProjectileManager(BattleScreen screen) {
        this.screen = screen;

        projsToCast = new ArrayList<>();
        activeProjs = new ArrayList<>();
        projsToRemove = new ArrayList<>();
    }

    public void update(float delta) {

        for(MonsterProjectile proj : projsToCast) {
           proj.screen = screen;
        }

        activeProjs.addAll(projsToCast);
        projsToCast.clear();

        for(MonsterProjectile proj : activeProjs) {
            proj.update(delta);
        }

        disposeFinishedProjs();
    }

    public void toAdd(MonsterProjectile proj) {
        projsToCast.add(proj);
    }

    public void toRemove(MonsterProjectile proj) {
        projsToRemove.add(proj);
    }

    public void disposeFinishedProjs() {
        activeProjs.removeAll(projsToRemove);
        for(MonsterProjectile proj : projsToRemove) {
            proj.dispose();
        }
        projsToRemove.clear();
    }
}
