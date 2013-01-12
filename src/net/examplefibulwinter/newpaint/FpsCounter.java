package net.examplefibulwinter.newpaint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FpsCounter {
    private Paint fpsPaint;
    private long second;
    private int lastFps;
    private int fps;


    public FpsCounter() {
        fpsPaint = new Paint();
        fpsPaint.setColor(Color.WHITE);
        second = System.currentTimeMillis() / 1000;
    }

    public void updateAndShowFps(int objectCount, Canvas realCanvas, boolean paused) {
        long currentSecond = System.currentTimeMillis() / 1000;
        if (currentSecond != second) {
            second = currentSecond;
            lastFps = fps;
            fps = 0;
        } else {
            fps++;
        }
        fps++;
        String canvasSize = (paused ? "PAUSED" : "") + realCanvas.getWidth() + "x" + realCanvas.getHeight();
        realCanvas.drawText(canvasSize + " FPS=" + lastFps + " " + " C=" + objectCount, 20, 20, fpsPaint);
    }

}
