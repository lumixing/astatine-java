package dev.lumix.astatine.world.chunk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lumix.astatine.world.block.BlockManager;
import dev.lumix.astatine.world.block.BlockType;

public class Chunk {
    public static int CHUNK_SIZE = 32;
    private final BlockType[][] blocks = new BlockType[CHUNK_SIZE][CHUNK_SIZE];
    private final int x;
    private final int y;
    private boolean generated = false;

    public Chunk(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch sb) {
        for (int y = 0; y < CHUNK_SIZE; y++) {
            for (int x = 0; x < CHUNK_SIZE; x++) {
                BlockType type = blocks[x][y];
                if (type != null && type != BlockType.AIR) {
                    float rx = x * ChunkManager.TILE_SIZE + (CHUNK_SIZE * ChunkManager.TILE_SIZE * this.x);
                    float ry = y * ChunkManager.TILE_SIZE + (CHUNK_SIZE * ChunkManager.TILE_SIZE * this.y);
                    BlockManager.getBlock(type).render(sb, rx, ry);
                }
            }
        }
    }

    public BlockType getBlockType(int x, int y) {
        if (!(x < CHUNK_SIZE && x >= 0 && y >= 0 && y < CHUNK_SIZE))
            return null;
        return blocks[x][y];
    }

    public void setBlockType(int x, int y, BlockType type) {
        if (!(x < CHUNK_SIZE && x >= 0 && y >= 0 && y < CHUNK_SIZE))
            return;
        blocks[x][y] = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }
}