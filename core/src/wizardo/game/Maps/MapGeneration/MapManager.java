package wizardo.game.Maps.MapGeneration;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import wizardo.game.Maps.Dungeon.DungeonChunk;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Wizardo;

import java.util.HashMap;
import java.util.Map;

import static wizardo.game.Maps.MapGeneration.ChunkPaths.chunkPaths;
import static wizardo.game.Maps.MapGeneration.ChunkPaths.fillChunks;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class MapManager {

    public int chunksLoaded = 0;

    public Wizardo game;
    public BattleScreen screen;
    public OrthographicCamera camera;

    public static final int CHUNK_SIZE = 2048;
    public static final int INITIAL_GRID_SIZE = 2; // 9x9

    int startingTileX = 50;
    int startingTileY = 50;

    Map<String, MapChunk> chunks = new HashMap<>();

    int loadedChunksX = 0;
    int loadedChunksY = 0;

    String biome;

    public MapManager(String biome, Wizardo game, BattleScreen screen) {
        this.game = game;
        this.screen = screen;
        this.biome = biome;
        this.camera = screen.mainCamera;
        fillChunks(biome);
        loadInitialGrid(biome);
    }

    public void update(float playerX, float playerY) {
        int playerChunkX = (int) (playerX / CHUNK_SIZE);
        int playerChunkY = (int) (playerY / CHUNK_SIZE);

        if (playerChunkX != loadedChunksX || playerChunkY != loadedChunksY) {
            loadNewChunks(playerChunkX, playerChunkY);
            loadedChunksX = playerChunkX;
            loadedChunksY = playerChunkY;
        }
    }

    public void render(float delta) {
        for (MapChunk chunk : chunks.values()) {
            chunk.render(delta);
        }
    }

    private void setPlayerStartPosition() {
        player.pawn.body.setTransform((1524f / PPM) * startingTileX, (1524f / PPM) * startingTileY, 0);
        camera.position.set(player.pawn.getPosition().scl(PPM), 0);
    }

    private void loadMapChunk(String mapPath, int chunkX, int chunkY) {
        chunksLoaded ++;
        String chunkKey = chunkKey(chunkX, chunkY);
        if (!chunks.containsKey(chunkKey)) {
            MapChunk newChunk;
            if (biome.equals("Dungeon")) {
                newChunk = new DungeonChunk(mapPath, chunkX * CHUNK_SIZE, chunkY * CHUNK_SIZE, game, screen);
            } else {
                newChunk = new DungeonChunk(mapPath, chunkX * CHUNK_SIZE, chunkY * CHUNK_SIZE, game, screen);
            }
            chunks.put(chunkKey, newChunk);
        }
    }
    private String chunkKey(int x, int y) {
        return x + "," + y;
    }

    private void loadStartingChunk(int playerChunkX, int playerChunkY, String biome) {
        if (biome.equals("Dungeon")) {
            loadMapChunk(chunkPaths.getFirst(), playerChunkX, playerChunkY);
        } else {
            loadMapChunk("Map/ForestMap/ChunkMaps/StartingChunk.tmx", playerChunkX, playerChunkY);
        }
    }
    private void loadInitialGrid(String biome) {
        setPlayerStartPosition();
        loadStartingChunk(startingTileX, startingTileY, biome);
        loadChunksAroundPlayer(startingTileX, startingTileY);
        loadedChunksX = startingTileX;
        loadedChunksY = startingTileY;
    }
    private void loadChunksAroundPlayer(int playerChunkX, int playerChunkY) {

        for (int x = playerChunkX - INITIAL_GRID_SIZE; x <= playerChunkX + INITIAL_GRID_SIZE; x++) {
            for (int y = playerChunkY - INITIAL_GRID_SIZE; y <= playerChunkY + INITIAL_GRID_SIZE; y++) {
                if (x == startingTileX && y == startingTileY) continue;
                loadMapChunk(getRandomChunk(), x, y);
            }
        }
    }
    private void loadNewChunks(int playerChunkX, int playerChunkY) {
        int dx = playerChunkX - loadedChunksX;
        int dy = playerChunkY - loadedChunksY;

        if (dx != 0) {
            int startChunkX, endChunkX;
            if (dx > 0) {
                startChunkX = loadedChunksX + INITIAL_GRID_SIZE + 1;
                endChunkX = playerChunkX + INITIAL_GRID_SIZE;
            } else {
                startChunkX = playerChunkX - INITIAL_GRID_SIZE;
                endChunkX = loadedChunksX - INITIAL_GRID_SIZE - 1;
            }
            for (int x = startChunkX; x <= endChunkX; x++) {
                for (int y = playerChunkY - INITIAL_GRID_SIZE; y <= playerChunkY + INITIAL_GRID_SIZE; y++) {
                    loadMapChunk(getRandomChunk(), x, y);
                }
            }
        }

        if (dy != 0) {
            int startChunkY, endChunkY;
            if (dy > 0) {
                startChunkY = loadedChunksY + INITIAL_GRID_SIZE + 1;
                endChunkY = playerChunkY + INITIAL_GRID_SIZE;
            } else {
                startChunkY = playerChunkY - INITIAL_GRID_SIZE;
                endChunkY = loadedChunksY - INITIAL_GRID_SIZE - 1;
            }
            for (int y = startChunkY; y <= endChunkY; y++) {
                for (int x = playerChunkX - INITIAL_GRID_SIZE; x <= playerChunkX + INITIAL_GRID_SIZE; x++) {
                    loadMapChunk(getRandomChunk(), x, y);
                }
            }
        }
    }
    private String getRandomChunk() {
        int index = MathUtils.random(chunkPaths.size() - 1);
        return chunkPaths.get(index);
    }


    public void dispose() {
        for (MapChunk chunk : chunks.values()) {
            chunk.dispose();
        }
    }
}
