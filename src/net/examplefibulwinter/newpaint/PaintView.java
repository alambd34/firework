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
    private Particles particles = new Particles();
    private FpsCounter fpsCounter;
    private FadingCanvas fadingCanvas;
    private Painters painters;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        h = new Handler();
        fpsCounter = new FpsCounter();
        fadingCanvas = new FadingCanvas();
        painters = new Painters(new Icons(context.getResources()));
    }

    @Override
    protected void onDraw(Canvas realCanvas) {
        fadingCanvas.makeSureWeHaveCanvas(realCanvas);
        fadingCanvas.fade();
        for (Particle particle : particles.getParticles()) {
            particle.paint(fadingCanvas.getCanvas(), particles);
        }
        particles.cycle();
        createNewPainter();
        fadingCanvas.drawOn(realCanvas);
        fpsCounter.updateAndShowFps(particles.getParticles().size(), realCanvas);
        h.postDelayed(r, FRAME_RATE);
    }

    private void createNewPainter() {
        if (Math.random() < 0.05) {
            V center = VirtualScreen.getRelative((float) (Math.random() / 2 + 0.25), (float) (Math.random() / 2 + 0.25));
            int color = RandUtils.randomColor();
            V up = new V(0, -7, 0);
            for (int i = 0; i < 50; i++) {
                V velocity = RandUtils.randomVelocity(10);
                velocity.add(up);
                particles.add(new Particle(new V(center), velocity, painters.big(RandUtils.randomSubColor(color))));
            }
        }
    }
}
