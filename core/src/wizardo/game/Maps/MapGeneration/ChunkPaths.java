package wizardo.game.Maps.MapGeneration;

import java.util.ArrayList;

public class ChunkPaths {

    public static ArrayList<String> chunkPaths = new ArrayList<>();

    public static void fillChunks(String biome) {
        chunkPaths.clear();
        if(biome.equals("Dungeon")) {
            chunkPaths.add("Maps/TEST/Map_Project2.tmx");
        }
    }
}
