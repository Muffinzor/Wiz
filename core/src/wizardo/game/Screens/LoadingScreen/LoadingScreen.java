package wizardo.game.Screens.LoadingScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Items.Drop.DropAnims;
import wizardo.game.Maps.MapGeneration.ChunkPaths;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;
import wizardo.game.Resources.EffectAnims.AuraAnims;
import wizardo.game.Resources.EffectAnims.BlackHoleAnims;
import wizardo.game.Resources.EffectAnims.CorpseExplosionAnims;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;
import wizardo.game.Resources.MonsterResources.MawDemonAnims;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.MonsterExplosions;
import wizardo.game.Resources.ScreenResources.CharacterScreenResources;
import wizardo.game.Resources.DecorResources.GeneralDecorResources;
import wizardo.game.Resources.MonsterResources.AcolyteAnims;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.SmallProjectileAnims;
import wizardo.game.Resources.MonsterResources.SkeletonAnims;
import wizardo.game.Resources.MonsterResources.SkeletonGiantAnims;
import wizardo.game.Resources.ScreenResources.LevelUpResources;
import wizardo.game.Resources.ScreenResources.ShopScreenResources;
import wizardo.game.Resources.SpellAnims.*;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.MainMenu.MainMenuScreen;
import wizardo.game.Spells.Unique.StaticOrb.StaticOrb;
import wizardo.game.Wizardo;

import static wizardo.game.Resources.MonsterResources.MONSTER_ANIMS.load_all_monster_anims;
import static wizardo.game.Resources.MonsterResources.MONSTER_ANIMS.load_all_monster_atlas;
import static wizardo.game.Spells.SpellBank.AllSpells.createAllSpells;
import static wizardo.game.Wizardo.assetManager;

public class LoadingScreen extends BaseScreen {

    private ProgressBar progressBar;
    private boolean assetsFinishedLoading;

    public LoadingScreen(Wizardo game) {
        super(game);

        stage = new Stage(new ScreenViewport());

        Skin skin = new Skin(Gdx.files.internal("Screens/MainMenuScreen/MainMenuSkin/MainMenuSkin.json"));

        progressBar = new ProgressBar(0, 1, 0.01f, false, skin);
        progressBar.setSize(300, 40);
        progressBar.setPosition(Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 20);
        stage.addActor(progressBar);

        loadAssets();
    }

    @Override
    public void render(float delta) {

        if (assetManager.update() && !assetsFinishedLoading) {
            loadAnims();
            createAllSpells();
            game.addNewScreen(new MainMenuScreen(game));
        }

        float progress = assetManager.getProgress();
        progressBar.setValue(progress);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void setInputs() {
        
    }

    public void loadAssets() {
        FrostboltAnims.loadAtlas();
        IcespearAnims.loadAtlas();
        FrozenorbAnims.loadAtlas();
        FlamejetAnims.loadAtlas();
        FireballAnims.loadAtlas();
        OverheatAnims.loadAtlas();
        ChargedboltsAnims.loadAtlas();
        ChainLightningAnims.loadAtlas();
        ThunderstormAnims.loadAtlas();
        ArcaneMissileAnims.loadAtlas();
        RiftsAnims.loadAtlas();

        BlizzardAnims.loadAtlas();
        FrostNovaAnims.loadAtlas();
        CelestialStrikeAnims.loadAtlas();
        DragonbreathAnims.loadAtlas();
        ExplosionAnims_Toon.loadAtlas();
        ExplosionAnims_Energy.loadAtlas();
        ExplosionAnims_Elemental.loadAtlas();
        MeteorAnims.loadAtlas();
        LightningHandsAnims.loadAtlas();
        RepulsionFieldAnims.loadAtlas();
        BlackHoleAnims.loadAtlas();
        LightningBoltAnims.loadAtlas();
        IceGustAnims.loadAtlas();
        MarkAnims.loadAtlas();
        TeleportAnims.loadAtlas();
        StaticOrbAnims.loadAtlas();

        GeneralDecorResources.loadAtlas();
        DungeonDecorResources.loadAtlas();
        GearFlareAnims.loadAtlas();
        DropAnims.loadAtlas();
        AuraAnims.loadAtlas();
        CorpseExplosionAnims.loadAtlas();

        load_all_monster_atlas();

        SmallProjectileAnims.loadAtlas();
        MonsterExplosions.loadAtlas();

        CharacterScreenResources.loadAtlas();
        LevelUpResources.loadAtlas();
        ShopScreenResources.loadAtlas();

        loadDungeonMaps();
        loadForestMaps();
    }

    public void loadAnims() {
        FrostboltAnims.loadAnimations();
        IcespearAnims.loadAnimations();
        FrozenorbAnims.loadAnimations();
        FlamejetAnims.loadAnimations();
        FireballAnims.loadAnimations();
        OverheatAnims.loadAnimations();
        ChargedboltsAnims.loadAnimations();
        ChainLightningAnims.loadAnimations();
        ThunderstormAnims.loadAnimations();
        ArcaneMissileAnims.loadAnimations();
        RiftsAnims.loadAnimations();

        BlizzardAnims.loadAnimations();
        FrostNovaAnims.loadAnimations();
        CelestialStrikeAnims.loadAnimations();
        DragonbreathAnims.loadAnimations();
        ExplosionAnims_Toon.loadAnimations();
        ExplosionAnims_Energy.loadAnimations();
        ExplosionAnims_Elemental.loadAnimations();
        MeteorAnims.loadAnimations();
        LightningHandsAnims.loadAnimations();
        RepulsionFieldAnims.loadAnimations();
        BlackHoleAnims.loadAnimations();
        LightningBoltAnims.loadAnimations();
        IceGustAnims.loadAnimations();
        MarkAnims.loadAnimations();
        TeleportAnims.loadAnimations();
        StaticOrbAnims.loadAnimations();

        GeneralDecorResources.loadAnimations();
        DungeonDecorResources.loadAnimations();
        GearFlareAnims.loadAnimations();
        DropAnims.loadAnimations();
        AuraAnims.loadAnimations();
        CorpseExplosionAnims.loadAnimations();

        load_all_monster_anims();

        SmallProjectileAnims.loadAnimations();
        MonsterExplosions.loadAnimations();

        CharacterScreenResources.loadAnimations();
        LevelUpResources.loadAnimations();
        ShopScreenResources.loadAnimations();
    }

    public void loadDungeonMaps() {
        assetManager.load("Maps/DungeonMaps/Tilesets/BigTiles.png", Texture.class);
        assetManager.load("Maps/DungeonMaps/Tilesets/Carpet.png", Texture.class);
        assetManager.load("Maps/DungeonMaps/Tilesets/Dungeon_Plus_Tiles.png", Texture.class);
        assetManager.load("Maps/DungeonMaps/Tilesets/GreyStone.png", Texture.class);
        assetManager.load("Maps/DungeonMaps/Tilesets/StoneGround.png", Texture.class);
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        for(String path : ChunkPaths.getAllDungeonMapPaths()) {
            assetManager.load(path, TiledMap.class);
        }
    }

    public void loadForestMaps() {
        assetManager.load("Maps/ForestMaps/Tilesets/grass-light-mid.png", Texture.class);
        for(String path : ChunkPaths.getAllForestMapPaths()) {
            assetManager.load(path, TiledMap.class);
        }
    }

}
