package dev.lumix.astatine.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import dev.lumix.astatine.engine.Camera;
import dev.lumix.astatine.world.chunk.ChunkManager;
import dev.lumix.astatine.world.entity.Box;
import dev.lumix.astatine.world.entity.Entity;

public class World {
    private final com.badlogic.gdx.physics.box2d.World physicsWorld;
    private final ChunkManager chunkManager;
    private final Box2DDebugRenderer debugRenderer;
    private final Box.Player player;
    private final Box box;
    private final Array<Entity> entities = new Array<>();
//    private int prevX = 0;
//    private int prevY = 0;

    public World(Camera camera) {
        physicsWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, -150), true);
        chunkManager = new ChunkManager();
        debugRenderer = new Box2DDebugRenderer();

        player = new Box.Player(physicsWorld);
        entities.add(player);
        box = new Box(physicsWorld);
        entities.add(box);
    }

    public void update(Camera camera) {
        Vector2 unprojPos = camera.unprojectCoordinates(1280/2f, 720/2f);
        Vector2 chunkPos = unprojPos.scl(1/256f);
        chunkManager.loadChunks((int) chunkPos.x, (int) chunkPos.y);

        physicsWorld.step(1/60f, 6, 2);

        chunkManager.unloadAllBlockBodies(physicsWorld);
        for (Entity entity : entities) {
            Vector2 unprojEntityPos = new Vector2(entity.body.getPosition().x, entity.body.getPosition().y);
            Vector2 blockEntityPos = unprojEntityPos.scl(1/8f);
            chunkManager.loadBlockBodiesNear(physicsWorld, (int) blockEntityPos.x, (int) blockEntityPos.y);
            entity.update();
        }

//        Vector2 unprojTestyPos = new Vector2(player.body.getPosition().x, player.body.getPosition().y);
//        Vector2 blockTestyPos = unprojTestyPos.scl(1/8f);
//        if ((prevX != (int)blockTestyPos.x) || (prevY != (int)blockTestyPos.y)) {
////            Gdx.app.log("wo", String.format("UPDATING BLOCK BODIES AT (%d, %d)", (int) blockTestyPos.x, (int) blockTestyPos.y));
//            chunkManager.unloadAllBlockBodies(physicsWorld);
//            chunkManager.loadBlockBodiesNear(physicsWorld, (int) blockTestyPos.x, (int) blockTestyPos.y);
//        }
//        prevX = (int) blockTestyPos.x;
//        prevY = (int) blockTestyPos.y;

//        player.update();
        debugRenderer.render(physicsWorld, camera.combined);
    }

    public void render(Camera camera, SpriteBatch sb) {
        chunkManager.renderLoadedChunks(camera, sb);

        for (Entity entity : entities) {
            if (entity.texture == null) continue;
            entity.render(sb);
        }
//        player.render(sb);
//        box.render(sb);
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public Box.Player getPlayer() {
        return player;
    }
}

