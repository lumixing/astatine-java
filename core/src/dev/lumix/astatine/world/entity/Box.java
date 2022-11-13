package dev.lumix.astatine.world.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Box extends Entity {
    public Box(World physicsWorld) {
        super(BodyDef.BodyType.DynamicBody, 4, 4, 0.3f, 1f, 0f, new Texture("blocks/stone.png"));
        setPosition(160, 3900);
        createBody(physicsWorld);
        createFixture();
    }

    @Override
    public void update() {

    }

    public static class Player extends Entity {
        public Player(World physicsWorld) {
            super(BodyDef.BodyType.DynamicBody, 4, 4, 0.1f, 10.5f, 0.2f, new Texture("entities/lumix.png"));
            setPosition(150, 3900);
            createBody(physicsWorld);
            createFixture();
        }

    //    public void render(SpriteBatch sb) {
    //        super.render(sb, new Texture("entities/lumix.png"));
    //    }

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

    //    public void update() {
    //        Vector2 pos = body.getPosition();
    //
    //        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
    //            body.applyForce(0, 120f, pos.x, pos.y, true);
    //        }
    //
    //        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
    //            body.applyLinearImpulse(-12f, 0, pos.x, pos.y, true);
    //        }
    //
    //        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
    //            body.applyLinearImpulse(12f, 0, pos.x, pos.y, true);
    //        }
    //    }
    //
    //    public void render(SpriteBatch sb) {
    //        Texture texture = new Texture("entities/lumix.png");
    //        Sprite sprite = new Sprite(texture, 0, 0, 8, 8);
    //        sprite.setPosition(body.getPosition().x - 4, body.getPosition().y - 4);
    //        sprite.setRotation(body.getAngle()*MathUtils.radiansToDegrees);
    //        sprite.draw(sb);
    //    }
    }
}
