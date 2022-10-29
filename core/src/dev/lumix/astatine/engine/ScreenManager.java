package dev.lumix.astatine.engine;

public class ScreenManager {
    private static Screen current, last;

    public static void setScreen(Screen screen) {
        if (current != null) {
            current.dispose();
        }

        last = current;
        current = screen;
        current.create();
    }

    public static void goBack() {
        if (current != null) {
            current.dispose();
        }

        Screen temp = last;
        last = current;
        current = temp;
        current.resume();
    }

    public static Screen getCurrent() {
        return current;
    }
}
