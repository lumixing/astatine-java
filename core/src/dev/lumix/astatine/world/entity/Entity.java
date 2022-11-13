package dev.lumix.astatine.world.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Entity {
    private final BodyDef bodyDef;
    private Body body;
    private final PolygonShape box;
    private final FixtureDef fixtureDef;
    private final Texture texture;

    public Entity(BodyDef.BodyType type, float bx, float by, float density, float friction, float restitution, Texture texture) {
        bodyDef = new BodyDef();
        bodyDef.type = type;

        box = new PolygonShape();
        box.setAsBox(bx, by);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = box;

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        this.texture = texture;
    }

    public abstract void update();

    public void render(SpriteBatch sb) {
        Sprite sprite = new Sprite(texture, 0, 0, 8, 8);
        sprite.setPosition(body.getPosition().x - 4, body.getPosition().y - 4);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(sb);
    }

    public void setPosition(float x, float y) {
        bodyDef.position.set(x, y);
    }

    public void createBody(World physicsWorld) {
        body = physicsWorld.createBody(bodyDef);
    }

    public void createFixture() {
        body.createFixture(fixtureDef);
        box.dispose();
    }

    public void addVelocity(float x, float y) {
        body.setLinearVelocity(body.getLinearVelocity().x + x, body.getLinearVelocity().y + y);
    }

    public Body getBody() {
        return body;
    }

    public Texture getTexture() {
        return texture;
    }
}
