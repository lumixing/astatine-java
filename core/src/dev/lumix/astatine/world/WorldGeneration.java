package dev.lumix.astatine.world;

import com.badlogic.gdx.math.MathUtils;
import dev.lumix.astatine.engine.noise.SimplexNoise;
import dev.lumix.astatine.world.block.BlockType;
import dev.lumix.astatine.world.chunk.Chunk;
import dev.lumix.astatine.world.chunk.ChunkManager;

public class WorldGeneration {
    private final ChunkManager chunkManager;
    private final SimplexNoise surfaceNoise;
    private final SimplexNoise caveNoise;
    private final SimplexNoise oresNoise;

    private final int MAX_X = ChunkManager.CHUNKS_X * Chunk.CHUNK_SIZE;
    private final int MAX_Y = ChunkManager.CHUNKS_Y * Chunk.CHUNK_SIZE;

    public WorldGeneration(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
        surfaceNoise = new SimplexNoise();
        caveNoise = new SimplexNoise();
        oresNoise = new SimplexNoise();
    }

    public void generateTerrain() {
        generateDirt();
        generateSurface();
        generateStone();
        generateCaves();
        generateOres();
    }

    private void generateDirt() {
        for (int y = 0; y < MAX_Y; y++) {
            for (int x = 0; x < MAX_X; x++) {
                chunkManager.setBlockType(x, y, BlockType.DIRT);
            }
        }
    }

    private void generateSurface() {
        float length = 24f;
        float height = 8f;
        int offset = MAX_Y - 30;

        for (int x = 0; x < MAX_X; x++) {
            int val = (int) (surfaceNoise.generate(x / length, 0) * height) + offset;
            chunkManager.setBlockType(x, val, BlockType.GRASS);

            for (int y = val + 1; y < MAX_Y; y++) {
                chunkManager.setBlockType(x, y, BlockType.AIR);
            }
        }
    }

    private void generateStone() {
        float length = 0.4f;
        float height = 1.6f;
        int offset = MAX_Y - 50;

        for (int x = 0; x < MAX_X; x++) {
            int val = (int) (MathUtils.sin(x * length) * height + offset);

            for (int y = val; y >= 0; y--) {
                if (y < val - 5)
                    chunkManager.setBlockType(x, y, BlockType.STONE);
                else
                    chunkManager.setBlockType(x, y, MathUtils.randomBoolean() ? BlockType.STONE : BlockType.DIRT);
            }
        }
    }

    private void generateCaves() {
        float zoom = 10f;
        float threshold = -0.2f; // smaller number smaller caves

        for (int y = 0; y < MAX_Y; y++) {
            for (int x = 0; x < MAX_X; x++) {
                if (chunkManager.getBlockType(x, y) != BlockType.STONE)
                    continue;

                float val = caveNoise.generate(x / zoom, y / zoom);
                if (val < threshold)
                    chunkManager.setBlockType(x, y, BlockType.AIR);
            }
        }
    }

    private void generateOres() {
        float zoom = 8f;
        float threshold = -0.7f; // smaller number smaller ores

        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                if (chunkManager.getBlockType(x, y) != BlockType.STONE)
                    continue;

                float val = oresNoise.generate(x / zoom, y / zoom);
                if (val < threshold)
                    chunkManager.setBlockType(x, y, BlockType.ORE);
            }
        }
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }
}
