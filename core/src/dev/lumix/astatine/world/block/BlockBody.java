package dev.lumix.astatine.world.block;

import dev.lumix.astatine.world.BodyType;
import dev.lumix.astatine.world.WorldBody;
import dev.lumix.astatine.world.chunk.ChunkManager;
import dev.lumix.astatine.world.collision.CollisionInfo;

public class BlockBody extends WorldBody {
    public BlockBody() {
        super();
    }

    public BlockBody(float x, float y, BodyType bodyType) {
        super(x, y, ChunkManager.TILE_SIZE, ChunkManager.TILE_SIZE, bodyType);
    }

    public BlockBody(float x, float y) {
        super(x, y, ChunkManager.TILE_SIZE, ChunkManager.TILE_SIZE, BodyType.STATIC);
    }

    @Override
    public void update() {}

    @Override
    public boolean collision(WorldBody obj, CollisionInfo info) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof BlockBody) {
            BlockBody tb = (BlockBody) o;
            return tb.getBodyType() == bodyType && tb.getX() == x && tb.getY() == y;
        }
        return false;
    }
}
