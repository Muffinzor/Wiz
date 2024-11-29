package wizardo.game.Monsters.MonsterActions;

import wizardo.game.Screens.Battle.BattleScreen;

import java.util.ArrayList;

public class MonsterSpellManager {

    BattleScreen screen;
    ArrayList<MonsterSpell> projsToCast;
    ArrayList<MonsterSpell> activeProjs;
    ArrayList<MonsterSpell> projsToRemove;

    public MonsterSpellManager(BattleScreen screen) {
        this.screen = screen;

        projsToCast = new ArrayList<>();
        activeProjs = new ArrayList<>();
        projsToRemove = new ArrayList<>();
    }

    public void update(float delta) {

        for(MonsterSpell proj : projsToCast) {
           proj.screen = screen;
        }

        activeProjs.addAll(projsToCast);
        projsToCast.clear();

        for(MonsterSpell proj : activeProjs) {
            proj.update(delta);
        }

        disposeFinishedProjs();
    }

    public void toAdd(MonsterSpell proj) {
        projsToCast.add(proj);
    }

    public void toRemove(MonsterSpell proj) {
        projsToRemove.add(proj);
    }

    public void disposeFinishedProjs() {
        activeProjs.removeAll(projsToRemove);
        for(MonsterSpell proj : projsToRemove) {
            proj.dispose();
        }
        projsToRemove.clear();
    }
}
