package wizardo.game.Screens.LoadingScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import wizardo.game.Resources.DecorResources.DungeonDecorResources;
import wizardo.game.Resources.EffectAnims.BlackHoleAnims;
import wizardo.game.Resources.EffectAnims.GearFlareAnims;
import wizardo.game.Resources.ScreenResources.CharacterScreenResources;
import wizardo.game.Resources.DecorResources.GeneralDecorResources;
import wizardo.game.Resources.MonsterResources.AcolyteAnims;
import wizardo.game.Resources.MonsterResources.MonsterProjectiles.SmallProjectileAnims;
import wizardo.game.Resources.MonsterResources.SkeletonAnims;
import wizardo.game.Resources.MonsterResources.SkeletonGiantAnims;
import wizardo.game.Resources.ScreenResources.LevelUpResources;
import wizardo.game.Resources.SpellAnims.*;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.MainMenu.MainMenuScreen;
import wizardo.game.Wizardo;

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
            assetsFinishedLoading = true;
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

        GeneralDecorResources.loadAtlas();
        DungeonDecorResources.loadAtlas();
        GearFlareAnims.loadAtlas();

        SkeletonAnims.loadAtlas();
        SkeletonGiantAnims.loadAtlas();
        AcolyteAnims.loadAtlas();

        SmallProjectileAnims.loadAtlas();

        CharacterScreenResources.loadAtlas();
        LevelUpResources.loadAtlas();
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

        GeneralDecorResources.loadAnimations();
        DungeonDecorResources.loadAnimations();
        GearFlareAnims.loadAnimations();

        SkeletonAnims.loadAnimations();
        SkeletonGiantAnims.loadAnimations();
        AcolyteAnims.loadAnimations();

        SmallProjectileAnims.loadAnimations();

        CharacterScreenResources.loadAnimations();
        LevelUpResources.loadAnimations();
    }

}
