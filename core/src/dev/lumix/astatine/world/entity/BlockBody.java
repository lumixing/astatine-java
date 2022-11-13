package dev.lumix.astatine.world.entity;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class BlockBody extends Entity {
    public BlockBody(World physicsWorld, float x, float y) {
        super(BodyDef.BodyType.StaticBody, 4, 4, 0.3f, 1f, 0f, null);
        setPosition(x, y);
        createBody(physicsWorld);
        createFixture();
    }

    @Override
    public void update() {

    }
}
