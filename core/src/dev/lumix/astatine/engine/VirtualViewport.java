package dev.lumix.astatine.engine;

public class VirtualViewport {
    private final float virtualWidth;
    private final float virtualHeight;

    public VirtualViewport(float virtualWidth, float virtualHeight) {
        this(virtualWidth, virtualHeight, false);
    }

    public VirtualViewport(float virtualWidth, float virtualHeight, boolean shrink) {
        this.virtualWidth = virtualWidth;
        this.virtualHeight = virtualHeight;
    }

    public float getVirtualWidth() {
        return virtualWidth;
    }

    public float getVirtualHeight() {
        return virtualHeight;
    }

    public float getWidth(float screenWidth, float screenHeight) {
        float virtualAspect = virtualWidth / virtualHeight;
        float aspect = screenWidth / screenHeight;

        if (aspect > virtualAspect || (Math.abs(aspect - virtualAspect) < 0.01f)) {
            return virtualHeight * aspect;
        } else {
            return virtualWidth;
        }
    }

    public float getHeight(float screenWidth, float screenHeight) {
        float virtualAspect = virtualWidth / virtualHeight;
        float aspect = screenWidth / screenHeight;

        if (aspect > virtualAspect || (Math.abs(aspect - virtualAspect) < 0.01f)) {
            return virtualHeight;
        } else {
            return virtualWidth / aspect;
        }
    }
}
