package dev.lumix.astatine.world.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Box extends Entity {
    public Box(World physicsWorld, float x, float y) {
        super(BodyDef.BodyType.DynamicBody, 4, 4, 0.3f, 1f, 0f, new Texture("blocks/stone.png"));
        setPosition(x, y);
        createBody(physicsWorld);
        createFixture();
    }

    @Override
    public void update() { }
}
