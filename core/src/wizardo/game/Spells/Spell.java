package wizardo.game.Spells;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import wizardo.game.Audio.Sounds.SoundPlayer;
import wizardo.game.Monsters.Monster;
import wizardo.game.Screens.BaseScreen;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Spells.SpellUtils.*;
import wizardo.game.Wizardo;

import java.util.ArrayList;
import java.util.HashSet;

import static wizardo.game.GameSettings.sound_volume;
import static wizardo.game.Screens.BaseScreen.controllerActive;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.currentScreen;
import static wizardo.game.Wizardo.player;

public abstract class Spell implements Cloneable {

    public Animation<Sprite> anim;

    public Vector2 spawnPosition;
    public Vector2 targetPosition;
    public boolean castByPawn;
    public Body originBody;
    public float stateTime = 0;
    public boolean initialized;
    public boolean finished;
    public String soundPath;

    public ArrayList<Spell_Element> elements = new ArrayList<>();
    public float dmg;
    public float speed;
    public float radius;
    public float cooldown;

    public Spell nested_spell;
    public BaseScreen screen;

    public HashSet<Spell_Name> spellParts = new HashSet<>();

    public Spell() {

    }

    public abstract void update(float delta);

    public void handleCollision(Monster monster) {

    }

    public void handleCollision(Fixture obstacle) {

    }

    @Override
    public Spell clone() {
        try {
            return (Spell) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public abstract void dispose();


    /**
     * if no spawnPosition is given to the spell, it will take the player's position
     * @return Vector containing coordinates of spawn position
     */
    public Vector2 getSpawnPosition() {
        if(spawnPosition != null) {
            return spawnPosition;
        } else {
            return player.pawn.getPosition();
        }
    }

    /**
     * Mouse position or Controller position normalized to 300
     * works for projectile spells
     * @return Vector of the target position
     */
    public Vector2 getTargetPosition() {
        if(!controllerActive) {
            Vector3 mouseUnprojected = currentScreen.mainCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            return new Vector2(mouseUnprojected.x / PPM, mouseUnprojected.y / PPM);
        } else {
            Vector3 mouseUnprojected = currentScreen.mainCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            return new Vector2(mouseUnprojected.x / PPM, mouseUnprojected.y / PPM);
        }
    }

    public void playSound(Vector2 position) {
        float dst_factor = 1;
        float dst = player.pawn.body.getPosition().dst(position);
        dst_factor -= dst * 0.025f;
        SoundPlayer.getSoundPlayer().playSound(soundPath, dst_factor * sound_volume);
    }

    public void regularDraw(Sprite frame) {
        currentScreen.displayManager.spriteRenderer.regular_sorted_sprites.add(frame);
    }

}
