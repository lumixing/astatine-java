package dev.lumix.astatine.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.lumix.astatine.engine.Camera;
import dev.lumix.astatine.world.chunk.ChunkManager;
import dev.lumix.astatine.world.entity.Player;

public class World {
    private final ChunkManager chunkManager;
    private final PhysicsWorld physicsWorld;
    private final Player player;

    public World() {
        chunkManager = new ChunkManager();

        physicsWorld = new PhysicsWorld(chunkManager, 0, -12f);
        physicsWorld.setMaxYVelocity(8);
        physicsWorld.setMaxXVelocity(4f);
        physicsWorld.setXFriction(6f);

        player = new Player(64, 64);
        physicsWorld.setGravityToEntity(player);
    }

    public void update(Camera camera) {
        Vector2 unprojPos = camera.unprojectCoordinates(1280/2f, 720/2f);
        Vector2 blockPos = unprojPos.scl(1/8f);
        Vector2 chunkPos = blockPos.scl(1/32f);

        chunkManager.loadChunks((int) chunkPos.x, (int) chunkPos.y);

        physicsWorld.updateWorldBody(player);
    }

    public void render(Camera camera, SpriteBatch sb) {
        chunkManager.renderLoadedChunks(camera, sb);
        player.render(sb);
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public PhysicsWorld getPhysicsWorld() {
        return physicsWorld;
    }

    public Player getPlayer() {
        return player;
    }
}

