package wizardo.game.Monsters.MonsterArchetypes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import wizardo.game.Items.Drop.GoldDrop;
import wizardo.game.Items.Drop.PotionDrop;
import wizardo.game.Items.Equipment.Book.Legendary_NecronomiconBook;
import wizardo.game.Items.Equipment.Ring.Legendary_DukeRing;
import wizardo.game.Items.Equipment.Robes.Legendary_LightningRobes;
import wizardo.game.Items.Equipment.Staff.Legendary_FrostStaff;
import wizardo.game.Lighting.RoundLight;
import wizardo.game.Monsters.MonsterActionManager;
import wizardo.game.Monsters.MonsterMovement.MovementManager;
import wizardo.game.Monsters.MonsterStateManager.StateManager;
import wizardo.game.Monsters.MonsterUtils;
import wizardo.game.Player.Player;
import wizardo.game.Resources.SpellAnims.MarkAnims;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner;
import wizardo.game.Screens.Battle.MonsterSpawner.MonsterSpawner_Dungeon;
import wizardo.game.Spells.Unique.CorpseExplosion.CorpseExplosion;
import wizardo.game.Utils.BodyFactory;

import static wizardo.game.GameSettings.monster_health_bars;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public abstract class Monster {

    public BattleScreen screen;
    public Animation<Sprite> spawn_anim;
    public boolean spawned;
    public Animation<Sprite> walk_anim;
    public Animation<Sprite> death_anim;
    public Animation<Sprite> idle_anim;
    public boolean deathFrameFlip;
    public Sprite weaponSprite;
    public float alpha = 1;

    public float stateTime;
    public int frameCounter;

    public MonsterSpawner spawner;
    public MonsterUtils.MONSTER_STATE state;
    public MovementManager movementManager;
    public StateManager stateManager;
    public MonsterActionManager monsterActionManager;

    public Body body;
    public RoundLight light;
    public float red;
    public float green;
    public float blue;

    public float massValue;
    public boolean heavy;
    public float bodyRadius;
    public float height;
    public float width;
    public float speed;
    public Vector2 directionVector;
    public Vector2 position;
    public Vector2 deathPosition;
    public boolean frameReversed;

    public float hp;
    public float maxHP;
    public int dmg;
    public int xp;
    public boolean basic;
    public boolean elite;

    public boolean dead;
    public boolean spaghettified;  // Dead from blackhole
    public float redshift = 0.75f;     // ditto

    public boolean initialized;
    public boolean tooFar;

    public float thunderImmunityTimer = 0;
    public float freezeImmunityTimer = 0;
    public float freezeTimer = 0;
    public float slowedTimer = 0;
    public float slowRatio = 1;

    public int flamejetStacks = 0;
    public boolean marked;  // for legendary ammy
    int markRotation;

    public boolean teleporting;

    public static Sprite greenHP = new Sprite(new Texture("Monsters/hpbar.png"));
    public static Sprite redHP = new Sprite(new Texture("Monsters/redbar.png"));


    public Monster(BattleScreen screen, Vector2 position, MonsterSpawner spawner) {
        this.spawner = spawner;
        if(position == null) {
            position = spawner.getSpawnPosition();
        }
        this.screen = screen;
        this.position = new Vector2(position);
        stateTime = 0;
        frameCounter = 0;
        directionVector = new Vector2();
        markRotation = MathUtils.random(360);
    }

    public void update(float delta) {
        if(!initialized) {
            initialize();
            initialized = true;
        }

        handleSpawning();

        timers(delta);
        drawFrame();
        drawMark();
        drawHealthBar();

        if(spawned) {
            stateManager.updateState(delta);
            monsterActionManager.update(delta);
        }

        if(hp <= 0) {
            onDeath();
            deathFrameFlip = player.pawn.getBodyX() < body.getPosition().x;
            dead = true;
            stateTime = 0;
        }
    }
    public void handleCollision(Player player) {

    }
    public void handleCollision(Monster monster) {

    }

    public void handleSpawning() {
        if(!spawned) {
            if(spawn_anim != null && stateTime < spawn_anim.getAnimationDuration()) {
                body.setActive(false);
            } else {
                spawned = true;
                state = MonsterUtils.MONSTER_STATE.ADVANCING;
                body.setActive(true);
            }
        }
    }



    public abstract void launchAttack();

    public void movement(float delta) {

        if(body != null) {
            movementManager.moveMonster(delta);
        }

        if(light != null) {
            adjustLight();
        }

    }

    public void dealDmg() {
        float dmg = this.dmg - player.stats.damageReduction;
        if(dmg < this.dmg/2f) {
            dmg = this.dmg/2f;
        }
        player.stats.shield -= dmg;
        player.pawn.hitTimer = 0.1f;

        if(player.inventory.equippedRobes instanceof Legendary_LightningRobes) {
            Legendary_LightningRobes robes = (Legendary_LightningRobes) player.inventory.equippedRobes;
            robes.accumulatedDmg += dmg;
        }

        if(player.inventory.equippedRing instanceof Legendary_DukeRing) {
            Legendary_DukeRing ring = (Legendary_DukeRing) player.inventory.equippedRing;
            ring.castDeathLightning(this);
        }
    }

    public void createBody() {
        if(basic) {
            body = spawner.bodyPool.getBody(position);
        } else {
            body = BodyFactory.monsterBody(position, bodyRadius);
        }
        body.setUserData(this);
        body.setActive(true);
        MassData mass = new MassData();
        float massMin = massValue * 0.8f;
        float massMax = massValue * 1.2f;
        float newMass = MathUtils.random(massMin, massMax);
        massValue = newMass;
        mass.mass = newMass;
        body.setMassData(mass);
    }

    public void createLight(float size, float alpha) {
        light = screen.lightManager.pool.getLight();
        light.setLight(red,green,blue,alpha,size, body.getPosition());
        screen.lightManager.addLight(light);
    }

    public void adjustLight() {
        light.pointLight.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM);
    }

    public void drawFrame() {
        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();

        switch(state) {
            case ADVANCING -> frame.set(walk_anim.getKeyFrame(stateTime, true));
            case SPAWNING -> {
                if(spawn_anim != null && stateTime <= spawn_anim.getAnimationDuration()) {
                    frame.set(spawn_anim.getKeyFrame(stateTime, false));
                } else {
                    frame.set(walk_anim.getKeyFrame(stateTime, true));
                }
            }
            case ATTACKING -> {
                if(idle_anim != null) {
                    frame.set(idle_anim.getKeyFrame(stateTime, true));
                } else {
                    frame.set(walk_anim.getKeyFrame(stateTime, true));
                }
            }
            default -> frame.set(walk_anim.getKeyFrame(stateTime, true));
        }

        frame.setPosition(body.getPosition().x * PPM - frame.getWidth()/2, body.getPosition().y * PPM - bodyRadius);
        //frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
        boolean flip = player.pawn.getBodyX() < body.getPosition().x;
        frame.flip(flip, false);
        if(freezeTimer > 0) {
            Color tint = new Color(0.5f, 0.5f, 1f, 1.0f);
            frame.setColor(tint);
        } else if(slowedTimer > 0) {
            Color tint = new Color(0.7f, 0.7f, 1f, 1.0f);
            frame.setColor(tint);
        }
        screen.addSortedSprite(frame);
    }

    public void drawMark() {
        if(marked) {
            Sprite frame = screen.getSprite();
            frame.set(MarkAnims.markAnim.getKeyFrame(stateTime, true));
            if(heavy || elite) {
                frame.setScale(1.4f);
                frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM - 5);
            } else {
                frame.setCenter(body.getPosition().x * PPM, body.getPosition().y * PPM);
            }
            frame.rotate(markRotation);
            markRotation++;
            screen.centerSort(frame, body.getPosition().y * PPM - 5);
            screen.addSortedSprite(frame);
        }
    }

    public void drawHealthBar() {
        if(monster_health_bars && hp < maxHP && !spaghettified) {
            float healthPercentage = hp / maxHP;
            float greenWidth = Math.max(2, width * healthPercentage);
            float newHeight = Math.min(height / 10, 4);

            Sprite frame = screen.getSprite();
            frame.set(redHP);
            frame.setSize(width, newHeight);
            frame.setPosition((body.getPosition().x * PPM) - width / 2, (body.getPosition().y * PPM) + height / 2);
            screen.addPostLightningSprite(frame);

            Sprite frame2 = screen.getSprite();
            frame2.set(greenHP);
            frame2.setSize(greenWidth, newHeight);
            frame2.setPosition((body.getPosition().x * PPM) - width / 2, (body.getPosition().y * PPM) + height / 2);
            screen.addPostLightningSprite(frame2);
        }
    }

    public void drawDeathFrame(float delta) {
        if(delta > 0) {
            alpha -= 0.0075f;
            if(alpha < 0) {
                alpha = 0;
            }
        }
        Sprite frame = screen.displayManager.spriteRenderer.pool.getSprite();
        if(!spaghettified) {
            frame.set(death_anim.getKeyFrame(stateTime, false));
            frame.setAlpha(alpha);
        } else {
            alpha -= 0.012f;
            redshift += 0.012f;
            stateTime -= delta;
            frame.set(walk_anim.getKeyFrames()[0]);
            frame.setColor(redshift,0.75f,0.75f,alpha);
        }
        frame.flip(deathFrameFlip, false);
        frame.setPosition(body.getPosition().x * PPM - frame.getWidth() / 2, body.getPosition().y * PPM - bodyRadius);
        //screen.centerSort(frame, body.getPosition().y * PPM - bodyRadius + 10);
        screen.displayManager.spriteRenderer.under_sprites.add(frame);
    }

    public void dispose() {

    }

    public void timers(float delta) {
        thunderImmunityTimer -= delta;
        freezeTimer -= delta;
        freezeImmunityTimer -= delta;
        slowedTimer -= delta;

        if(freezeTimer <= 0) {
            stateTime += delta;
        }
    }

    /**
     * slows monster
     * @param duration in seconds
     * @param ratio between 0 and 1.0f
     */
    public void applySlow(float duration, float ratio) {
        if(!elite) {
            slowedTimer = duration;
            slowRatio = ratio;
        }
    }

    public void applyFreeze(float duration, float immunity) {
        if(!elite) {
            if (freezeImmunityTimer <= 0) {
                freezeTimer = duration;
                freezeImmunityTimer = immunity;
                if (player.inventory.equippedStaff instanceof Legendary_FrostStaff) {
                    Legendary_FrostStaff staff = (Legendary_FrostStaff) player.inventory.equippedStaff;
                    staff.castNova(this);
                }
            } else {
                slowedTimer = duration;
                slowRatio = 0.75f;
            }
        }
    }

    public void onDeath() {
        corpseExplosion();

        float pot_rate = 0.995f - player.stats.luck/2000f;
        if(Math.random() >= pot_rate) {
            PotionDrop potion = new PotionDrop(body.getPosition());
            screen.dropManager.addDrop(potion);
        }

        float goldrate = 0.95f - player.stats.luck/500f;
        if(Math.random() >= goldrate) {
            GoldDrop gold = new GoldDrop(body.getPosition(),1 , 1);
            screen.dropManager.addDrop(gold);
        }
    }

    public void corpseExplosion() {
        if(player.inventory.equippedBook instanceof Legendary_NecronomiconBook) {
            if(Math.random() >= 0.8f) {
                CorpseExplosion explosion = new CorpseExplosion(this);
                screen.spellManager.add(explosion);
            }
        }
    }

    public abstract void initialize();
}
