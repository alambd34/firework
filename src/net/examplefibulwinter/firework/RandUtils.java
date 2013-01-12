package net.examplefibulwinter.firework;

import android.graphics.Color;

public class RandUtils {
    private static V[] rands = new V[117];
    private static int ri = 0;

    static {
        for (int i = 0; i < rands.length; i++) {
            V v = new V(0, 0, 0);
            do {
                v.x = (float) (Math.random() * 2 - 1);
                v.y = (float) (Math.random() * 2 - 1);
                v.z = (float) (Math.random() * 2 - 1);
            } while (v.squareLength() > 1);
            v.scale((float) (1 / Math.sqrt(v.squareLength())));
            rands[i] = v;
        }
        for (int i = 0; i < 100; i++) {
            spread();
        }
    }

    private static void spread() {
        for (int i = 0; i < rands.length; i++) {
            V vector = new V(0, 0, 0);
            for (V v : rands) {
                if (v.squareLength(rands[i]) < 0.3 * 0.3) {
                    vector.add(rands[i]);
                    vector.sub(v);
                }
            }
            vector.normalize();
            vector.scale(0.1f);
            rands[i].add(vector);
            rands[i].normalize();
        }
    }

    public static V randomVelocity(float speed) {
        ri = (ri + 1) % rands.length;
        V v = new V(rands[ri]);
        v.scale(speed);
        return v;
    }

    public static int randomSubColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[0] = ((float) (Math.random() * 50 - 25) + hsv[0]) % 360;
//        hsv[1] = (float) (Math.random() * 0.3 + 0.7);
//        hsv[2] = 1;
        return Color.HSVToColor((int) (Math.random() * 50 + 200), hsv);
    }

    public static int randomColor() {
        float[] hsv = new float[3];
        hsv[0] = ((float) (Math.random() * 360));
        hsv[1] = 1;
        hsv[2] = 1;
        return Color.HSVToColor(hsv);
    }

    public static float rand(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }

    public static int rand(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    public static boolean withProbability(float probaility) {
        return Math.random() < probaility;
    }
}
