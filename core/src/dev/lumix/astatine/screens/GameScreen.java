package dev.lumix.astatine.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import dev.lumix.astatine.engine.Camera;
import dev.lumix.astatine.engine.Screen;
import dev.lumix.astatine.world.World;

public class GameScreen implements Screen {
    private Camera camera;
    private World world;
    private BitmapFont font;
    private SpriteBatch debugSB;
    private final Array<String> debugTexts = new Array<>();

    @Override
    public void create() {
        camera = new Camera();
        world = new World();
        font = new BitmapFont();
        debugSB = new SpriteBatch();

        resize(1280, 720);
        camera.zoom = 0.5f;
        camera.setPosition(300, 3800);
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        // TODO: move to Player.java
//        if (Gdx.input.isTouched()) {
//            Vector2 unprojPos = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
//            Vector2 blockPos = unprojPos.cpy().scl(1/8f);
//
//            world.getChunkManager().setBlockType((int) blockPos.x, (int) blockPos.y, BlockType.AIR);
//        }

        Vector3 desired = new Vector3(camera.position.x, camera.position.y, 0);
        camera.position.set(desired.lerp(new Vector3(world.getPlayer().getBody().getPosition().x, world.getPlayer().getBody().getPosition().y, 0), 1f));

        updateDebugTexts();

        camera.update();
        world.update(camera);
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(31/255f, 203/255f, 255/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        world.render(camera, sb);
        sb.end();

        debugSB.begin();
        for (int i = 0; i < debugTexts.size; i++) {
            font.draw(debugSB, debugTexts.get(i), 4, 716 - 20 * i);
        }
        debugSB.end();
    }

    private void updateDebugTexts() {
        debugTexts.clear();

        Vector2 unprojPos = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
        Vector2 blockPos = unprojPos.cpy().scl(1/8f);
        Vector2 chunkPos = blockPos.cpy().scl(1/32f);
        Vector2 centerUnprojPos = camera.unprojectCoordinates(1280/2f, 720/2f);
        Vector2 centerChunkPos = centerUnprojPos.scl(1/256f);

        debugTexts.add(String.format("fps: %d (%dms)", Gdx.graphics.getFramesPerSecond(), MathUtils.floor(Gdx.graphics.getDeltaTime() * 1000)));
        debugTexts.add(String.format("unprojected: (%d, %d)", MathUtils.floor(unprojPos.x), MathUtils.floor(unprojPos.y)));
        debugTexts.add(String.format("block: (%d, %d)", MathUtils.floor(blockPos.x), MathUtils.floor(blockPos.y)));
        debugTexts.add(String.format("chunk: (%d, %d)", MathUtils.floor(chunkPos.x), MathUtils.floor(chunkPos.y)));
        debugTexts.add(String.format("center chunk: (%d, %d)", MathUtils.floor(centerChunkPos.x), MathUtils.floor(centerChunkPos.y)));
        debugTexts.add(String.format("loaded chunks: %d", world.getChunkManager().getTotalChunksLoaded()));
        debugTexts.add(String.format("entities: %d", world.getEntities().size));
    }

    @Override
    public void dispose() { }

    @Override
    public void resize(int width, int height) {
        camera.resize();
    }

    @Override
    public void onBackPressed() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }
}
