package dev.lumix.astatine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lumix.astatine.engine.ScreenManager;
import dev.lumix.astatine.engine.drawables.ResourceManager;
import dev.lumix.astatine.screens.GameScreen;

public class Astatine extends ApplicationAdapter {
    public static Astatine instance;
    protected SpriteBatch batch;

    @Override
    public void create() {
        instance = this;

        ResourceManager.getInstance().loadTexturedDrawable("grass", "blocks/grass.png");
        ResourceManager.getInstance().loadTexturedDrawable("dirt", "blocks/dirt.png");
        ResourceManager.getInstance().loadTexturedDrawable("stone", "blocks/stone.png");
        ResourceManager.getInstance().loadTexturedDrawable("ore", "blocks/ore.png");
        ResourceManager.getInstance().loadTexturedDrawable("lumix", "entities/lumix.png");

        batch = new SpriteBatch();
        batch.enableBlending();

        ScreenManager.setScreen(new GameScreen());
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 1, 1);

        if (ScreenManager.getCurrent() != null) {
            ScreenManager.getCurrent().render(batch);
            ScreenManager.getCurrent().update();
        }
    }
}
