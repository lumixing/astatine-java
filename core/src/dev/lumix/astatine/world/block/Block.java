package dev.lumix.astatine.world.block;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lumix.astatine.engine.drawables.Drawable;

public class Block {
    protected final BlockType type;
    protected Drawable drawable;
    protected boolean collidable;

    public Block(BlockType type, Drawable drawable, boolean collidable) {
        this.type = type;
        this.drawable = drawable;
        this.collidable = collidable;
    }

    public void render(SpriteBatch sb, float x, float y) {
        drawable.render(sb, x, y);
    }

    public boolean isCollidable() {
        return collidable;
    }

    public BlockType getType() {
        return type;
    }
}
