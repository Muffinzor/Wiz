package wizardo.game.Spells.Hybrid.FrostNova;
import wizardo.game.Monsters.MonsterArchetypes.Monster;
import wizardo.game.Spells.Frost.Icespear.Icespear_Break;
import wizardo.game.Spells.Spell;

public class FrostNova_MonsterShatter extends Spell {

    int quantity = 5;
    Monster monster;

    public FrostNova_MonsterShatter(Monster monster) {
        this.monster = monster;
    }

    @Override
    public void update(float delta) {
        if(!monster.elite) {
            monster.hp = -1;
            monster.shattered = true;
        }
        for (int i = 0; i < quantity; i++) {
            FrostNova_ShardProjectile shard = new FrostNova_ShardProjectile(monster);
            shard.setElements(this);
            screen.spellManager.add(shard);
        }
        Icespear_Break break_anim = new Icespear_Break(monster.body.getPosition());
        screen.spellManager.add(break_anim);
        screen.spellManager.remove(this);
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public int getDmg() {
        return 0;
    }
}
