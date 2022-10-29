package dev.lumix.astatine.world.chunk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lumix.astatine.engine.Camera;
import dev.lumix.astatine.world.block.BlockManager;
import dev.lumix.astatine.world.block.BlockType;

public class Chunk {
    public static int CHUNK_SIZE = 32;
    private final BlockType[][] blocks = new BlockType[CHUNK_SIZE][CHUNK_SIZE];
    private ChunkManager chunkManager;
    private final int x;
    private final int y;
    private boolean generated = false;

    public Chunk(ChunkManager chunkManager, int x, int y) {
        this.chunkManager = chunkManager;
        this.x = x;
        this.y = y;
    }

    public Chunk(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(Camera camera, SpriteBatch sb) {
        for (int y = 0; y < CHUNK_SIZE; y++) {
            for (int x = 0; x < CHUNK_SIZE; x++) {
                BlockType type = blocks[x][y];
                if (type != null && type != BlockType.AIR) {
                    BlockManager.getBlock(type).render(camera, sb, x * 8 + (CHUNK_SIZE * 8 * this.x), y * 8 + (CHUNK_SIZE * 8 * this.y));
                }
            }
        }
    }

    public void fillWithBlocks(BlockType type) {
        for (int y = 0; y < CHUNK_SIZE; y++) {
            for (int x = 0; x < CHUNK_SIZE; x++) {
                blocks[x][y] = type;
            }
        }
        blocks[0][0] = BlockType.GRASS;
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

    //    public void update(Camera camera, float centerX, float centerY) {
//        // entity shit
//    }
//
//    public void render(Camera camera, SpriteBatch sb, float centerX, float centerY, int startYDraw, int endYDraw, int startXDraw, int endXDraw) {
//        Gdx.app.log("Chunk.render", startX + "," + startY);
////        for (int y = startYDraw; y < endYDraw; y++) {
////            for (int x = startXDraw; x < endXDraw; x++) {
////                if (ChunkManager.isOnScreen(x * ChunkManager.TILE_SIZE + (getStartX() * Chunk.CHUNK_SIZE * ChunkManager.TILE_SIZE), y * ChunkManager.TILE_SIZE + (startY * Chunk.CHUNK_SIZE * ChunkManager.TILE_SIZE), camera, centerX, centerY)) {
////                    BlockType type = getBlock(x, y);
////                    if (type != null && type != BlockType.AIR) {
////                        BlockManager.getBlock(type).render(camera, sb, x * ChunkManager.TILE_SIZE + (startX * Chunk.CHUNK_SIZE * ChunkManager.TILE_SIZE), y * ChunkManager.TILE_SIZE + (startY * Chunk.CHUNK_SIZE * ChunkManager.TILE_SIZE));
////                    }
////                }
////            }
////        }
//    }
//
//    public int getStartX() {
//        return startX;
//    }
//
//    public int getStartY() {
//        return startY;
//    }
//
//    public void setBlock(BlockType type, int x, int y) {
//        if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
//            blocks[x][y] = type;
//        }
//    }
//
//    public BlockType getBlock(int x, int y) {
//        if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
//            BlockType type = blocks[x][y];
//            if (type == null) {
//                blocks[x][y] = BlockType.AIR;
//                return BlockType.AIR;
//            }
//            return type;
//        }
//        return null;
//    }
//
//
//    public boolean isGenerated() {
//        return isGenerated;
//    }
//
//    public void setGenerated(boolean generated) {
//        isGenerated = generated;
//    }
}