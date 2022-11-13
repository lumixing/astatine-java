package dev.lumix.astatine.world.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {
    public Player(World physicsWorld, float x, float y) {
        super(BodyDef.BodyType.DynamicBody, 4, 4, 0.1f, 10.5f, 0.2f, new Texture("entities/lumix.png"));
        setPosition(x, y);
        createBody(physicsWorld);
        createFixture();
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            addVelocity(0, 75f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            addVelocity(-10f, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            addVelocity(10f, 0);
        }
    }
}