package dev.lumix.astatine.world.chunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import dev.lumix.astatine.world.WorldGeneration;
import dev.lumix.astatine.world.block.BlockType;
import dev.lumix.astatine.world.entity.BlockBody;

public class ChunkManager {
    public static int TILE_SIZE = 8;
    public static int CHUNKS_X = 24;
    public static int CHUNKS_Y = 16;
    public static int CHUNK_RADIUS = 3;

    private int totalChunksLoaded = 0;
    private final Chunk[][] chunks = new Chunk[CHUNKS_X][CHUNKS_Y];
    private final Chunk[] loadedChunks = new Chunk[(int) Math.pow(CHUNK_RADIUS * 2, 2)];
    private final Array<BlockBody> blockBodies = new Array<>();

    public ChunkManager() {
        initChunks();
        WorldGeneration worldGeneration = new WorldGeneration(this);
        worldGeneration.generateTerrain();
    }

    private void initChunks() {
        for (int y = 0; y < CHUNKS_Y; y++) {
            for (int x = 0; x < CHUNKS_X; x++) {
                Gdx.app.log("ChunkManager.initChunks", String.format("(%d, %d)", x, y));
                chunks[x][y] = new Chunk(x, y);
                chunks[x][y].setGenerated(true);
            }
        }
    }

    public void renderChunk(SpriteBatch sb, int x, int y) {
        Chunk chunk = getChunk(x, y);
        if (chunk == null) return; // if out of bounds then dont render
        chunk.render(sb);
    }

    public void loadChunks(int cx, int cy) {
        // loading optimization (hardcoded for red=3) needs fixing for null center chunk
        if (loadedChunks[21] != null && loadedChunks[21].getX() == cx && loadedChunks[21].getY() == cy)
            return;

        totalChunksLoaded = 0;
        int i = 0;
        for (int y = cy - CHUNK_RADIUS; y < cy + CHUNK_RADIUS; y++) {
            for (int x = cx - CHUNK_RADIUS; x < cx + CHUNK_RADIUS; x++) {
                loadedChunks[i] = getChunk(x, y);
                if (loadedChunks[i] != null) totalChunksLoaded++;
                i++;
            }
        }
    }

    public void renderLoadedChunks(SpriteBatch sb) {
        for (Chunk chunk : loadedChunks) {
            if (chunk == null) continue;
            renderChunk(sb, chunk.getX(), chunk.getY());
        }
    }

    public Chunk getChunk(int x, int y) {
        if (!(x < CHUNKS_X && x >= 0 && y >= 0 && y < CHUNKS_Y))
            return null;
        return chunks[x][y];
    }

    public BlockType getBlockType(float x, float y) {
        Chunk chunk = getChunk(MathUtils.floor(x / Chunk.CHUNK_SIZE), MathUtils.floor(y / Chunk.CHUNK_SIZE));
        if (chunk == null) return null;
        return chunk.getBlockType((int) (x % Chunk.CHUNK_SIZE), (int) (y % Chunk.CHUNK_SIZE));
    }

    public void setBlockType(float x, float y, BlockType type) {
        Chunk chunk = getChunk(MathUtils.floor(x / Chunk.CHUNK_SIZE), MathUtils.floor(y / Chunk.CHUNK_SIZE));
        chunk.setBlockType((int) (x % Chunk.CHUNK_SIZE), (int) (y % Chunk.CHUNK_SIZE), type);
    }

    public int getTotalChunksLoaded() {
        return totalChunksLoaded;
    }

    public static int pixelToBlockPosition(float p) {
        return (int) p / TILE_SIZE;
    }

    public void unloadAllBlockBodies(World physicsWorld) {
        for (BlockBody blockBody : blockBodies) {
            physicsWorld.destroyBody(blockBody.getBody());
        }
        blockBodies.clear();
    }

    public void loadBlockBodiesNear(World physicsWorld, int x, int y) {
        for (int i = x - 3; i < x + 3; i++) {
            for (int j = y - 3; j < y + 3; j++) {
                loadBlockBodyAt(physicsWorld, i, j);
            }
        }
    }

    private void loadBlockBodyAt(World physicsWorld, int x, int y) {
        BlockType blockType = getBlockType(x, y);
        if (blockType == null || blockType == BlockType.AIR) return;

        BlockBody blockBody = new BlockBody(physicsWorld, x * TILE_SIZE + TILE_SIZE / 2f, y * TILE_SIZE + TILE_SIZE / 2f);
        blockBodies.add(blockBody);
    }
}