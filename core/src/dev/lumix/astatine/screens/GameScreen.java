package dev.lumix.astatine.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.lumix.astatine.engine.Camera;
import dev.lumix.astatine.engine.Screen;
import dev.lumix.astatine.world.World;
import dev.lumix.astatine.world.block.BlockType;

public class GameScreen implements Screen {
    private Camera camera;
    private Texture texture;
    private World world;
    private BitmapFont font;
    private SpriteBatch debugSB;

    @Override
    public void create() {
        camera = new Camera();
        texture = new Texture("badlogic.jpg");
        world = new World(camera);
        font = new BitmapFont();
        resize(1280, 720);
        camera.zoom = 0.5f;
        camera.setPosition(300, 3800);
        debugSB = new SpriteBatch();
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isTouched()) {
            Vector2 unprojPos = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
            Vector2 blockPos = unprojPos.cpy().scl(1/8f);

            world.getChunkManager().setBlockType((int) blockPos.x, (int) blockPos.y, BlockType.AIR);
        }

        Vector3 desired = new Vector3(camera.position.x, camera.position.y, 0);
        camera.position.set(desired.lerp(new Vector3(world.getPlayer().body.getPosition().x, world.getPlayer().body.getPosition().y, 0), 1f));
//        camera.setPosition(world.getTesty().body.getPosition().x, world.getTesty().body.getPosition().y);
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
//        sb.draw(texture, 0, 0, 16, 16);
        sb.end();

        debugSB.begin();

        Vector2 unprojPos = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
        Vector2 blockPos = unprojPos.cpy().scl(1/8f);
        Vector2 chunkPos = blockPos.cpy().scl(1/32f);

        Vector2 centerUnprojPos = camera.unprojectCoordinates(1280/2f, 720/2f);
        Vector2 centerBlockPos = centerUnprojPos.scl(1/8f);
        Vector2 centerChunkPos = centerBlockPos.scl(1/32f);

        font.draw(debugSB, String.format("fps: %d (%dms)", Gdx.graphics.getFramesPerSecond(), MathUtils.floor(Gdx.graphics.getDeltaTime() * 1000)), 4, 716);
        font.draw(debugSB, String.format("unprojected: (%d, %d)", MathUtils.floor(unprojPos.x), MathUtils.floor(unprojPos.y)), 4, 716-20);
        font.draw(debugSB, String.format("block: (%d, %d)", MathUtils.floor(blockPos.x), MathUtils.floor(blockPos.y)), 4, 716-40);
        font.draw(debugSB, String.format("chunk: (%d, %d)", MathUtils.floor(chunkPos.x), MathUtils.floor(chunkPos.y)), 4, 716-60);
        font.draw(debugSB, String.format("center chunk: (%d, %d)", MathUtils.floor(centerChunkPos.x), MathUtils.floor(centerBlockPos.y)), 4, 716-80);
        font.draw(debugSB, String.format("loaded chunks: %d", world.getChunkManager().getTotalChunksLoaded()), 4, 716-100);
//        font.draw(debugSB, String.format("testy pos: %s", world.getTesty().body.getPosition()), 4, 716-120);

        debugSB.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {
        camera.resize();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
