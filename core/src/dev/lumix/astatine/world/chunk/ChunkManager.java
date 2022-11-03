package dev.lumix.astatine.world.chunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import dev.lumix.astatine.engine.Camera;
import dev.lumix.astatine.world.WorldGeneration;
import dev.lumix.astatine.world.block.BlockType;

public class ChunkManager {
    private int totalChunksLoaded = 0;
    public static int TILE_SIZE = 8;
    public static int CHUNKS_X = 24;
    public static int CHUNKS_Y = 16;
    public static int CHUNK_RADIUS = 3;
    private final Chunk[][] chunks = new Chunk[CHUNKS_X][CHUNKS_Y];
    private final Chunk[] loadedChunks = new Chunk[(int) Math.pow(CHUNK_RADIUS * 2, 2)];
    private WorldGeneration worldGeneration;

    public ChunkManager() {
        worldGeneration = new WorldGeneration(this);
        initChunks();
        worldGeneration.generateTerrain();
    }

    private void initChunks() {
        for (int y = 0; y < CHUNKS_Y; y++) {
            for (int x = 0; x < CHUNKS_X; x++) {
                Gdx.app.log("ChunkManager.initChunks", String.format("(%d,%d)", x, y));
                chunks[x][y] = new Chunk(this, x, y);
//                chunks[x][y].fillWithBlocks(MathUtils.randomBoolean() ? BlockType.DIRT : BlockType.STONE);
                chunks[x][y].setGenerated(true);
            }
        }
    }

    // only used for testing purposes with small worlds
    public void renderAllChunks(Camera camera, SpriteBatch sb) {
        for (int y = 0; y < CHUNKS_Y; y++) {
            for (int x = 0; x < CHUNKS_X; x++) {
                Gdx.app.log("ChunkManager.renderAllChunks", String.format("(%d,%d)", x, y));
                chunks[x][y].render(camera, sb);
            }
        }
    }

    public void renderChunk(Camera camera, SpriteBatch sb, int x, int y) {
        // if out of bounds then dont render
        if (!(x < CHUNKS_X && x >= 0 && y >= 0 && y < CHUNKS_Y)) {
            return;
        }

        chunks[x][y].render(camera, sb);
    }

    public void loadChunks(int cx, int cy) {
        // loading optimization (hardcoded for red=3) needs fixing for null center chunk
        if (loadedChunks[21] != null && loadedChunks[21].getX() == cx && loadedChunks[21].getY() == cy) {
            return;
        }

        totalChunksLoaded = 0;
        int i = -1;
        for (int y = cy - CHUNK_RADIUS; y < cy + CHUNK_RADIUS; y++) {
            for (int x = cx - CHUNK_RADIUS; x < cx + CHUNK_RADIUS; x++) {
                i++;
                if (!(x < CHUNKS_X && x >= 0 && y >= 0 && y < CHUNKS_Y)) {
//                    Gdx.app.log("chmgr.loadch", String.format("[%d] (%d, %d) NULL", i, x, y));
                    loadedChunks[i] = null;
                } else {
//                    Gdx.app.log("chmgr.loadch", String.format("[%d] (%d, %d)", i, x, y));
                    loadedChunks[i] = chunks[x][y];
                    totalChunksLoaded++;
                }
            }
        }
    }

    public void renderLoadedChunks(Camera camera, SpriteBatch sb) {
        int i = -1;
        for (Chunk chunk : loadedChunks) {
            i++;
            if (chunk == null) {
                continue;
            }
//            Gdx.app.log("chmgr.rendloadch", String.format("[%d] (%d, %d)", i, chunk.getX(), chunk.getY()));
            renderChunk(camera, sb, chunk.getX(), chunk.getY());
        }
    }

    public Chunk getChunk(int x, int y) {
        if (!(x < CHUNKS_X && x >= 0 && y >= 0 && y < CHUNKS_Y)) {
            return null;
        }

        return chunks[x][y];
    }

    public BlockType getBlockType(int x, int y) {
        Chunk chunk = getChunk(MathUtils.floor(x / Chunk.CHUNK_SIZE), MathUtils.floor(y / Chunk.CHUNK_SIZE));
        return chunk.getBlockType(x % Chunk.CHUNK_SIZE, y % Chunk.CHUNK_SIZE);
    }

    public void setBlockType(int x, int y, BlockType type) {
        Chunk chunk = getChunk(MathUtils.floor(x / Chunk.CHUNK_SIZE), MathUtils.floor(y / Chunk.CHUNK_SIZE));
        chunk.setBlockType(x % Chunk.CHUNK_SIZE, y % Chunk.CHUNK_SIZE, type);
    }

    public int getTotalChunksLoaded() {
        return totalChunksLoaded;
    }
}