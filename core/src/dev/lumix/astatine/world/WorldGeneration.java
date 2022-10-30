package dev.lumix.astatine.world;

import dev.lumix.astatine.world.block.BlockType;
import dev.lumix.astatine.world.chunk.ChunkManager;

public class WorldGeneration {
    private final ChunkManager chunkManager;

    public WorldGeneration(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
    }

    public void generateTerrain() {
        chunkManager.getChunk(0, 0).setBlockType(0, 0, BlockType.AIR);
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }
}
