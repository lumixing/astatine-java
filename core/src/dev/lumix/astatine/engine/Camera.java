package dev.lumix.astatine.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Camera extends OrthographicCamera {
    private Vector3 tmp = new Vector3();
    private Vector2 origin = new Vector2();
    private VirtualViewport virtualViewport;
    private Vector2 pos = new Vector2();
    private Vector3 cachedVec3 = new Vector3();

    public Camera() {
        this(new VirtualViewport(1280, 720));
    }

    public Camera(VirtualViewport virtualViewport) {
        this(virtualViewport, 0f, 0f);
    }

    public Camera(VirtualViewport virtualViewport, float cx, float cy) {
        this.virtualViewport = virtualViewport;
        origin.set(cx, cy);
    }

    public void setVirtualViewport(VirtualViewport virtualViewport) {
        this.virtualViewport = virtualViewport;
    }

    public void setPosition(float x, float y) {
        position.set(x - viewportWidth * origin.x, y - viewportHeight * origin.y, 0f);
        pos.set(x, y);
    }

    public void updatePosition(float x, float y) {
        setPosition(pos.x + x, pos.y + y);
    }

    public Vector2 getPos() {
        return pos;
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            updatePosition(0, 10);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            updatePosition(-10, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            updatePosition(0, -10);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            updatePosition(10, 0);
        }

        float left = zoom * -viewportWidth / 2 + virtualViewport.getVirtualWidth() * origin.x;
        float right = zoom * viewportWidth / 2 + virtualViewport.getVirtualWidth() * origin.x;
        float top = zoom * viewportHeight / 2 + virtualViewport.getVirtualHeight() * origin.y;
        float bottom = zoom * -viewportHeight / 2 + virtualViewport.getVirtualHeight() * origin.y;

        projection.setToOrtho(left, right, bottom, top, Math.abs(near), Math.abs(far));
        view.setToLookAt(position, tmp.set(position).add(direction), up);
        combined.set(projection);
        Matrix4.mul(combined.val, view.val);
        invProjectionView.set(combined);
        Matrix4.inv(invProjectionView.val);
        frustum.update(invProjectionView);
    }

    public void updateViewport() {
        setToOrtho(false, virtualViewport.getWidth(1280, 720), virtualViewport.getHeight(1280, 720));
    }

    public void updateViewport(float width, float height) {
        setToOrtho(false, width, height);
    }

    public Vector2 unprojectCoordinates(float x, float y) {
        cachedVec3.set(x, y,0);
        unproject(cachedVec3);
        return new Vector2(cachedVec3.x, cachedVec3.y);
    }

    public float unprojectXCoordinate(float x, float y) {
        cachedVec3.set(x, y,0);
        unproject(cachedVec3);
        return cachedVec3.x;
    }

    public float unprojectYCoordinate(float x, float y) {
        cachedVec3.set(x, y, 0);
        unproject(cachedVec3);
        return cachedVec3.y;
    }

    public void resize() {
        VirtualViewport virtualViewport = new VirtualViewport(1280, 720);
        setVirtualViewport(virtualViewport);
        updateViewport(1280, 720);
    }

    public void resize(int width, int height) {
        VirtualViewport virtualViewport = new VirtualViewport(width, height);
        setVirtualViewport(virtualViewport);
        updateViewport(1280, 720);
    }
}
