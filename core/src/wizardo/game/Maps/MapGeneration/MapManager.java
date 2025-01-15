package wizardo.game.Maps.MapGeneration;

import com.badlogic.gdx.graphics.OrthographicCamera;
import wizardo.game.Maps.Dungeon.DungeonChunk;
import wizardo.game.Screens.Battle.BattleScreen;
import wizardo.game.Wizardo;

import java.util.*;

import static wizardo.game.Maps.MapGeneration.ChunkPaths.*;
import static wizardo.game.Utils.Constants.PPM;
import static wizardo.game.Wizardo.player;

public class MapManager {

    public int chunksLoaded = 0;

    public Wizardo game;
    public BattleScreen screen;
    public OrthographicCamera camera;

    public static final int CHUNK_SIZE = 1920;   // 60 x 60 -> 32px tiles
    public static final int INITIAL_GRID_SIZE = 1; // 9x9

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
        player.pawn.body.setTransform((1920f / PPM) * startingTileX + 960f/PPM, (1920f / PPM) * startingTileY + 960f/PPM, 0);
        camera.position.set(player.pawn.getPosition().scl(PPM), 0);
    }

    private void loadMapChunk(String mapPath, int chunkX, int chunkY) {
        chunksLoaded ++;
        String chunkKey = chunkKey(chunkX, chunkY);
        if (!chunks.containsKey(chunkKey)) {
            MapChunk newChunk;
            if (biome.equals("Dungeon")) {
                newChunk = new DungeonChunk(mapPath, chunkX * CHUNK_SIZE, chunkY * CHUNK_SIZE, game, screen);
                newChunk.canHaveShop = canHaveShop(chunkX, chunkY);
            } else {
                newChunk = new DungeonChunk(mapPath, chunkX * CHUNK_SIZE, chunkY * CHUNK_SIZE, game, screen);
                newChunk.canHaveShop = canHaveShop(chunkX, chunkY);
            }
            chunks.put(chunkKey, newChunk);
        }
    }
    private String chunkKey(int x, int y) {
        return x + "," + y;
    }

    private void loadStartingChunk(int playerChunkX, int playerChunkY, String biome) {
        if (biome.equals("Dungeon")) {
            loadMapChunk("Maps/DungeonMaps/Chunks/StartingChunk.tmx", startingTileX, startingTileY);
        } else {
            loadMapChunk("Maps/DungeonMaps/Chunks/StartingChunk.tmx", startingTileX, startingTileY);
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
                if (x == startingTileX && y == startingTileY) continue;  // Skip starting chunk
                String chunkKey = chunkKey(x, y);
                if(chunks.containsKey(chunkKey)) continue;              // Skip if already loaded;

                String chunkPath = getConnectedChunkPath(x, y);
                loadMapChunk(chunkPath, x, y);
            }
        }
    }

    private String getConnectedChunkPath(int x_position, int y_position) {
        Map<String, List<Integer>> newTileRequiredConnections = new HashMap<>();

        if (chunkExists(x_position, y_position - 1)) {
            List<Integer> connection = getNeighborConnection(x_position, y_position - 1, "north");
            if (!connection.isEmpty()) {  // Only add non-empty connections
                newTileRequiredConnections.put("south", connection);
            }
        }
        if (chunkExists(x_position, y_position + 1)) {
            List<Integer> connection = getNeighborConnection(x_position, y_position + 1, "south");
            if (!connection.isEmpty()) {  // Only add non-empty connections
                newTileRequiredConnections.put("north", connection);
            }
        }
        if (chunkExists(x_position - 1, y_position)) {
            List<Integer> connection = getNeighborConnection(x_position - 1, y_position, "east");
            if (!connection.isEmpty()) {  // Only add non-empty connections
                newTileRequiredConnections.put("west", connection);
            }
        }
        if (chunkExists(x_position + 1, y_position)) {
            List<Integer> connection = getNeighborConnection(x_position + 1, y_position, "west");
            if (!connection.isEmpty()) {  // Only add non-empty connections
                newTileRequiredConnections.put("east", connection);
            }
        }

        return findMatchingTile(newTileRequiredConnections);

    }

    public String findMatchingTile(Map<String, List<Integer>> requiredConnections) {
        ArrayList<String> paths = new ArrayList<>();

        for(String checkedTile : chunkPaths) {
            Map<String, List<Integer>> checkedTileConnections = chunkConnections.get(checkedTile);
            boolean matching = true;

            for (Map.Entry<String, List<Integer>> entry : requiredConnections.entrySet()) {
                String direction = entry.getKey();
                List<Integer> requiredConnection = entry.getValue();

                // If the tile connection does not exist or doesn't match, mark as non-matching
                List<Integer> tileConnection = checkedTileConnections.get(direction);

                if (tileConnection != null && !tileConnection.equals(requiredConnection)) {
                    matching = false;
                    break;
                }
            }

            if (matching) {
                paths.add(checkedTile);
            }
        }

        // Ensure paths list is not empty before accessing
        if (paths.isEmpty()) {
            return startingChunkPath(); // Return a fallback path if no match found
        }

        // Shuffle the list and return a random matching path
        Collections.shuffle(paths);
        return paths.getFirst();  // Now you're calling get(0) which works for ArrayLists, not getFirst() which doesn't exist
    }

    private List<Integer> getNeighborConnection(int neighborX, int neighborY, String direction) {
        String neighborKey = chunks.get(chunkKey(neighborX, neighborY)).pathToFile;
        Map<String, List<Integer>> connections = chunkConnections.get(neighborKey);

        if (connections != null) {
            List<Integer> connection = connections.get(direction);
            if (connection != null) return connection;
        }
        return Collections.emptyList();  // No connection available, return an empty list
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
                    String chunkPath = getConnectedChunkPath(x, y);
                    loadMapChunk(chunkPath, x, y);
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
                    String chunkPath = getConnectedChunkPath(x, y);
                    loadMapChunk(chunkPath, x, y);
                }
            }
        }
    }

    private boolean canHaveShop(int x_position, int y_position) {
        boolean canHaveShop = true;

        for (int dx = x_position -2; dx <= x_position + 2; dx++) {
            for (int dy = y_position -2 ; dy <= y_position + 2; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }

                if(chunkExists(dx, dy)) {
                    if(chunks.get(chunkKey(dx, dy)).canHaveShop) {
                        canHaveShop = false;
                    }
                }
            }
        }
        return canHaveShop;
    }

    private boolean chunkExists(int x_position, int y_position) {
        // This method checks if a chunk at the given position already exists in the loaded map
        String chunkKey = chunkKey(x_position, y_position);
        return chunks.containsKey(chunkKey);
    }


    public void dispose() {
        for (MapChunk chunk : chunks.values()) {
            chunk.dispose();
        }
    }
}
