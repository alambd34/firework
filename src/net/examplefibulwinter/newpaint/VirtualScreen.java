package net.examplefibulwinter.newpaint;

import android.graphics.Canvas;
import net.examplefibulwinter.firework.V;

public class VirtualScreen {
    public static final int WIDTH = 320;
    public static final int HEIGHT = 480;

    public static boolean isInside(V position) {
        return position.y >= 0 && position.y <= HEIGHT && position.x >= 0 && position.x <= WIDTH;
    }

    public static V getRelative(float ax, float ay) {
        return new V(ax * WIDTH, ay * HEIGHT, 0);
    }

    public static float virtualToReal(Canvas realCanvas) {
        return Math.max(((float) realCanvas.getWidth()) / WIDTH, ((float) realCanvas.getHeight()) / HEIGHT);
    }
}
