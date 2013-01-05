package net.examplefibulwinter.newpaint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class FadingCanvas {

    private Canvas canvas;
    private Bitmap bmp;


    public void makeSureWeHaveCanvas(Canvas realCanvas) {
        if (bmp == null) {
            Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
            bmp = Bitmap.createBitmap(realCanvas.getWidth(), realCanvas.getHeight(), conf);
            canvas = new Canvas(bmp);
        }
    }

    public void fade() {
        canvas.drawColor(Color.argb(50, 0, 0, 0));
    }

    public void drawOn(Canvas realCanvas) {
        realCanvas.drawBitmap(bmp, 0f, 0f, null);
    }


    public Canvas getCanvas() {
        return canvas;
    }
}
