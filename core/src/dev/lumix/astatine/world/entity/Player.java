package dev.lumix.astatine.world.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dev.lumix.astatine.engine.drawables.ResourceManager;
import dev.lumix.astatine.world.BodyType;

public class Player extends LivingEntity {
    private final float walkingVelocity = 12f;

    public Player(float x, float y) {
        super(ResourceManager.getInstance().getDrawable("grass"), x, y, BodyType.DYNAMIC, 12f, 100, 100, 6.5f);
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (canJump())
                jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            addVelocity(-walkingVelocity, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            addVelocity(walkingVelocity, 0);
        }
    }
}
