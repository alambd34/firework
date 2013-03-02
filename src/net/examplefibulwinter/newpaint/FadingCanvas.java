package net.examplefibulwinter.newpaint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class FadingCanvas {

    public static final float FADING_FACTOR = 75f / 256f;
    public static final int FADER = Color.argb((int) (FADING_FACTOR * 256), 0, 0, 0);
    private Canvas canvas;
    private Bitmap bmp;


    public void makeSureWeHaveCanvas(int width, int height) {
        if (bmp == null) {
            Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
            bmp = Bitmap.createBitmap(width, height, conf);
            canvas = new Canvas(bmp);
        }
    }

    public void fade() {
        canvas.drawColor(FADER);
    }

    public Bitmap getBmp() {
        return bmp;
    }


    public Canvas getCanvas() {
        return canvas;
    }
}
