package dev.lumix.astatine.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import dev.lumix.astatine.engine.Camera;
import dev.lumix.astatine.world.chunk.ChunkManager;
import dev.lumix.astatine.world.entity.Box;
import dev.lumix.astatine.world.entity.Entity;
import dev.lumix.astatine.world.entity.Player;

public class World {
    private final com.badlogic.gdx.physics.box2d.World physicsWorld;
    private final ChunkManager chunkManager;
    private final Box2DDebugRenderer debugRenderer;
    private final Player player;
    private final Array<Entity> entities = new Array<>();

    public World() {
        physicsWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, -150), true);
        chunkManager = new ChunkManager();
        debugRenderer = new Box2DDebugRenderer();

        player = new Player(physicsWorld, 150, 3900);
        entities.add(player);

        Box box = new Box(physicsWorld, 160, 3900);
        entities.add(box);
    }

    public void update(Camera camera) {
        physicsWorld.step(1/60f, 6, 2);

        Vector2 centerUnprojPos = camera.unprojectCoordinates(1280/2f, 720/2f);
        Vector2 centerChunkPos = centerUnprojPos.scl(1/256f);
        chunkManager.loadChunks((int) centerChunkPos.x, (int) centerChunkPos.y);

        // TODO: dont load overlapping block bodies
        chunkManager.unloadAllBlockBodies(physicsWorld);
        for (Entity entity : entities) {
            Vector2 entityUnprojPos = new Vector2(entity.getBody().getPosition().x, entity.getBody().getPosition().y);
            Vector2 entityBlockPos = entityUnprojPos.scl(1/8f);
            chunkManager.loadBlockBodiesNear(physicsWorld, (int) entityBlockPos.x, (int) entityBlockPos.y);
            entity.update();
        }

        // rendering here because of white texture bug
        debugRenderer.render(physicsWorld, camera.combined);
    }

    public void render(Camera camera, SpriteBatch sb) {
        chunkManager.renderLoadedChunks(sb);

        for (Entity entity : entities) {
            if (entity.getTexture() == null) continue;
            entity.render(sb);
        }
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Entity> getEntities() {
        return entities;
    }
}

