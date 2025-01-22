package wizardo.game.Resources.MonsterResources;

import wizardo.game.Monsters.MonsterTypes.MawDemon.MawDemon;

public class MONSTER_ANIMS {

    public static void load_all_monster_atlas() {
        AcolyteAnims.loadAtlas();
        DragonManAnims.loadAtlas();
        MawDemonAnims.loadAtlas();
        SkeletonAnims.loadAtlas();
        SkeletonGiantAnims.loadAtlas();
    }

    public static void load_all_monster_anims() {
        AcolyteAnims.loadAnimations();
        DragonManAnims.loadAnimations();
        MawDemonAnims.loadAnimations();
        SkeletonAnims.loadAnimations();
        SkeletonGiantAnims.loadAnimations();
    }
}
