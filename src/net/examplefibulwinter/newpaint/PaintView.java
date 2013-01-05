package net.examplefibulwinter.newpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;
import net.examplefibulwinter.firework.RandUtils;
import net.examplefibulwinter.firework.V;

public class PaintView extends ImageView {

    private Handler h;
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            postInvalidate();
        }
    };
    private static final int FRAME_RATE = 10;
    private Painters painters = new Painters();
    private FpsCounter fpsCounter;
    private FadingCanvas fadingCanvas;
    private Icons icons;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        h = new Handler();
        fpsCounter = new FpsCounter();
        fadingCanvas = new FadingCanvas();
        icons = new Icons(context.getResources());
    }

    @Override
    protected void onDraw(Canvas realCanvas) {
        fadingCanvas.makeSureWeHaveCanvas(realCanvas);
        fadingCanvas.fade();
        for (Painter painter : painters.getPainters()) {
            painter.paint(fadingCanvas.getCanvas(), painters);
        }
        painters.cycle(getHeight(), getWidth());
        createNewPainter();
        fadingCanvas.drawOn(realCanvas);
        fpsCounter.updateAndShowFps(painters.getPainters().size(), realCanvas);
        h.postDelayed(r, FRAME_RATE);
    }

    private void createNewPainter() {
        if (Math.random() < 0.05) {
            V center = new V((float) (Math.random() / 2 + 0.25) * getWidth(), (float) (Math.random() / 2 + 0.25) * getHeight(), 0);
            int color = RandUtils.randomColor();
            V up = new V(0, -7, 0);
            for (int i = 0; i < 50; i++) {
                V velocity = RandUtils.randomVelocity(10);
                velocity.add(up);
                painters.add(new Painter(icons, new V(center), velocity, RandUtils.randomSubColor(color)));
            }
        }
    }
}
