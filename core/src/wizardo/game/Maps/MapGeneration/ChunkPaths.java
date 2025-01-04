package wizardo.game.Maps.MapGeneration;

import java.util.*;

public class ChunkPaths {

    public static Map<String, Map<String, List<Integer>>> chunkConnections = new HashMap<>();
    // 1 = middle only
    // 2 = two paths
    // 3 = one path left/up corner of edge
    // 4 = one path right/down corner of edge

    public static ArrayList<String> chunkPaths = new ArrayList<>();
    public static String startingChunkPath() {
        return "Maps/DungeonMaps/Chunks/StartingChunk.tmx";
    }

    public static void fillChunks(String biome) {
        chunkPaths.clear();
        if(biome.equals("Dungeon")) {
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

        }
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
            case 1 -> List.of(1); // Single connection in the middle
            case 2 -> List.of(1, 1); // Two paths
            case 3 -> List.of(1, 0); // One path on the left/up corner
            case 4 -> List.of(0, 1); // One path on the right/down corner
            default -> throw new IllegalArgumentException("Invalid connection type: " + connectionType);
        };
    }

}
