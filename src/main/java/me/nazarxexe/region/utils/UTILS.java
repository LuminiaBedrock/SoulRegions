package me.nazarxexe.region.utils;

public class UTILS {

    public static String chunkCoordsIntoString(int x, int z) {
        return x + " " + z;
    }
    public static int[] StringToChunkCoords(String chunk) {
        String[] raw = chunk.split(" ");
        return new int[] { Integer.parseInt(raw[0]), Integer.parseInt(raw[1]) };
    }

}
