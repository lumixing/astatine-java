package dev.lumix.astatine.world.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lumix.astatine.engine.drawables.Drawable;
import dev.lumix.astatine.world.BodyType;
import dev.lumix.astatine.world.WorldBody;
import dev.lumix.astatine.world.collision.CollisionInfo;

public class TexturedEntity extends WorldBody {
    protected Drawable drawable;
    protected float speed;

    public TexturedEntity(Drawable drawable, float x, float y, BodyType bodyType, float speed) {
        super(x, y, drawable.getWidth(), drawable.getHeight(), bodyType);
        this.drawable = drawable;
        this.speed = speed;
    }

    public void render(SpriteBatch sb) {
        drawable.render(sb, x, y);
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean collision(WorldBody obj, CollisionInfo info) {
        return false;
    }
}
