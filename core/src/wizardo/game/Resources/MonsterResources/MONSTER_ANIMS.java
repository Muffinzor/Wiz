package wizardo.game.Resources.MonsterResources;

public class MONSTER_ANIMS {

    public static void load_all_monster_atlas() {
        AcolyteAnims.loadAtlas();
        DragonManAnims.loadAtlas();
        MawDemonAnims.loadAtlas();
        SkeletonAnims.loadAtlas();
        SkeletonGiantAnims.loadAtlas();
        OrcMinionAnims.loadAtlas();
        OrcBruteAnims.loadAtlas();
        OrcShamanAnims.loadAtlas();
        SludgeAnims.loadAtlas();
        ZombieAnims.loadAtlas();
    }

    public static void load_all_monster_anims() {
        AcolyteAnims.loadAnimations();
        DragonManAnims.loadAnimations();
        MawDemonAnims.loadAnimations();
        SkeletonAnims.loadAnimations();
        SkeletonGiantAnims.loadAnimations();
        OrcMinionAnims.loadAnimations();
        OrcBruteAnims.loadAnimations();
        OrcShamanAnims.loadAnimations();
        SludgeAnims.loadAnimations();
        ZombieAnims.loadAnimations();
    }
}
