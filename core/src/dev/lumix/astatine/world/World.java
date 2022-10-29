package dev.lumix.astatine.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.lumix.astatine.engine.Camera;
import dev.lumix.astatine.world.chunk.ChunkManager;

public class World {
    private final ChunkManager chunkManager;

    public World() {
        chunkManager = new ChunkManager();
    }

    public void update(Camera camera) {
        Vector2 unprojPos = camera.unprojectCoordinates(1280/2f, 720/2f);
        Vector2 blockPos = unprojPos.scl(1/8f);
        Vector2 chunkPos = blockPos.scl(1/32f);

        chunkManager.loadChunks((int) chunkPos.x, (int) chunkPos.y);
    }

    public void render(Camera camera, SpriteBatch sb) {
        chunkManager.renderLoadedChunks(camera, sb);
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }
}

