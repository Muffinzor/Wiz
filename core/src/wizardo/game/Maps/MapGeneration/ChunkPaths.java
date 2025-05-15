package wizardo.game.Maps.MapGeneration;

import java.util.*;

public class ChunkPaths {

    public static Map<String, Map<String, List<Integer>>> chunkConnections = new HashMap<>();
    // String 1 = Path of the tmx file,  String 2 = Cardinal direction of the connections
    // 1 = middle only
    // 2 = two paths
    // 3 = one path left/up corner of edge
    // 4 = one path right/down corner of edge

    public static ArrayList<String> chunkPaths = new ArrayList<>();
    public static String startingChunkPath(String biome) {
        if(biome.equals("Dungeon")) {
            return "Maps/DungeonMaps/Chunks/StartingChunk.tmx";
        } else {
            return "Maps/ForestMaps/Chunks/Forest_StartingChunk.tmx";
        }

    }

    public static void fillChunks(String biome) {
        if(biome.equals("Dungeon")) {
            chunkConnections.put(startingChunkPath(biome), createTileConnections(2,2,2,2));
        } else {
            chunkConnections.put(startingChunkPath(biome), createTileConnections(0,0,0,0));
        }
        chunkPaths.clear();

        if(biome.equals("Dungeon")) {
            dungeonChunks();
        } else {
            forestChunks();
        }
    }
    public static void forestChunks() {
        String tile1 = "Maps/ForestMaps/Chunks/Forest_Chunk_1.tmx";
        chunkPaths.add(tile1);
        chunkConnections.put(tile1, createTileConnections(0,0,0,0));

        String tile2 = "Maps/ForestMaps/Chunks/Forest_Chunk_2.tmx";
        chunkPaths.add(tile2);
        chunkConnections.put(tile2, createTileConnections(0,0,0,1));

        String tile3 = "Maps/ForestMaps/Chunks/Forest_Chunk_3.tmx";
        chunkPaths.add(tile3);
        chunkConnections.put(tile3, createTileConnections(0,1,0,0));

        String tile4 = "Maps/ForestMaps/Chunks/Forest_Chunk_4.tmx";
        chunkPaths.add(tile4);
        chunkConnections.put(tile4, createTileConnections(0,0,0,0));
    }

    public static void dungeonChunks() {
        String tile1 = "Maps/DungeonMaps/Chunks/Tile1.tmx";
        chunkPaths.add(tile1);
        chunkConnections.put(tile1, createTileConnections(1,1,2,1));

        String tile2 = "Maps/DungeonMaps/Chunks/Tile2.tmx";
        chunkPaths.add(tile2);
        chunkConnections.put(tile2, createTileConnections(1,1,1,2));

        String tile3 = "Maps/DungeonMaps/Chunks/Tile3.tmx";
        chunkPaths.add(tile3);
        chunkConnections.put(tile3, createTileConnections(2,1,1,1));

        String tile4 = "Maps/DungeonMaps/Chunks/Tile4.tmx";
        chunkPaths.add(tile4);
        chunkConnections.put(tile4, createTileConnections(1,2,1,1));

        String tile5 = "Maps/DungeonMaps/Chunks/Tile5.tmx";
        chunkPaths.add(tile5);
        chunkConnections.put(tile5, createTileConnections(1,1,1,1));

        String tile6 = "Maps/DungeonMaps/Chunks/Tile6.tmx";
        chunkPaths.add(tile6);
        chunkConnections.put(tile6, createTileConnections(2,1,2,1));

        String tile7 = "Maps/DungeonMaps/Chunks/Tile7.tmx";
        chunkPaths.add(tile7);
        chunkConnections.put(tile7, createTileConnections(1,2,1,2));

        String tile8 = "Maps/DungeonMaps/Chunks/Tile8.tmx";
        chunkPaths.add(tile8);
        chunkConnections.put(tile8, createTileConnections(1,1,2,2));

        String tile9 = "Maps/DungeonMaps/Chunks/Tile9.tmx";
        chunkPaths.add(tile9);
        chunkConnections.put(tile9, createTileConnections(2,1,1,2));

        String tile10 = "Maps/DungeonMaps/Chunks/Tile10.tmx";
        chunkPaths.add(tile10);
        chunkConnections.put(tile10, createTileConnections(2,2,1,1));
    }

    public static Map<String, List<Integer>> createTileConnections(int north, int east, int south, int west) {
        Map<String, List<Integer>> connections = new LinkedHashMap<>(); // Maintain consistent order
        connections.put("north", createConnectionList(north));
        connections.put("east", createConnectionList(east));
        connections.put("south", createConnectionList(south));
        connections.put("west", createConnectionList(west));
        return connections;
    }

    private static List<Integer> createConnectionList(int connectionType) {
        return switch (connectionType) {
            case 0 -> List.of(0, 0);  // No connections
            case 1 -> List.of(1); // Single connection in the middle
            case 2 -> List.of(1, 1); // Two paths
            case 3 -> List.of(1, 0); // One path on the left/up corner
            case 4 -> List.of(0, 1); // One path on the right/down corner
            default -> throw new IllegalArgumentException("Invalid connection type: " + connectionType);
        };
    }

    public static ArrayList<String> getAllDungeonMapPaths() {
        ArrayList<String> dungeonMapPaths = new ArrayList<>();
        dungeonMapPaths.add("Maps/HubMap/Map_HUB.tmx");
        dungeonMapPaths.add("Maps/DungeonMaps/Chunks/StartingChunk.tmx");
        dungeonMapPaths.add("Maps/DungeonMaps/Chunks/Tile1.tmx");
        dungeonMapPaths.add("Maps/DungeonMaps/Chunks/Tile2.tmx");
        dungeonMapPaths.add("Maps/DungeonMaps/Chunks/Tile3.tmx");
        dungeonMapPaths.add("Maps/DungeonMaps/Chunks/Tile4.tmx");
        dungeonMapPaths.add("Maps/DungeonMaps/Chunks/Tile5.tmx");
        dungeonMapPaths.add("Maps/DungeonMaps/Chunks/Tile6.tmx");
        dungeonMapPaths.add("Maps/DungeonMaps/Chunks/Tile7.tmx");
        dungeonMapPaths.add("Maps/DungeonMaps/Chunks/Tile8.tmx");
        dungeonMapPaths.add("Maps/DungeonMaps/Chunks/Tile9.tmx");
        dungeonMapPaths.add("Maps/DungeonMaps/Chunks/Tile10.tmx");
        return dungeonMapPaths;
    }

    public static ArrayList<String> getAllForestMapPaths() {
        ArrayList<String> forestMapPaths = new ArrayList<>();
        forestMapPaths.add("Maps/ForestMaps/Chunks/Forest_StartingChunk.tmx");
        forestMapPaths.add("Maps/ForestMaps/Chunks/Forest_Chunk_1.tmx");
        forestMapPaths.add("Maps/ForestMaps/Chunks/Forest_Chunk_2.tmx");
        forestMapPaths.add("Maps/ForestMaps/Chunks/Forest_Chunk_3.tmx");
        forestMapPaths.add("Maps/ForestMaps/Chunks/Forest_Chunk_4.tmx");
        return forestMapPaths;
    }

}
