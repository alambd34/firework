package net.examplefibulwinter.newpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;
import net.examplefibulwinter.firework.RandUtils;
import net.examplefibulwinter.firework.V;

import static net.examplefibulwinter.newpaint.Emitters.at;
import static net.examplefibulwinter.newpaint.Emitters.repeat;

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
        fadingCanvas.makeSureWeHaveCanvas(realCanvas.getWidth(), realCanvas.getHeight());
        fadingCanvas.fade();
        for (Particle particle : particles.getParticles()) {
            particle.paint(fadingCanvas.getCanvas(), particles);
        }
        realCanvas.drawBitmap(fadingCanvas.getBmp(),
                new Rect(0, 0, fadingCanvas.getBmp().getWidth(), fadingCanvas.getBmp().getHeight()),
                new Rect(0, 0, realCanvas.getWidth(), realCanvas.getHeight()),
                null);
        for (Particle particle : particles.getParticles()) {
            particle.paintReal(realCanvas);
        }
        particles.cycle();
        createNewPainter();
        fpsCounter.updateAndShowFps(particles.getParticles().size(), realCanvas);
        h.postDelayed(r, FRAME_RATE);
    }

    private void createNewPainter() {
        if (Math.random() < 0.05) {
            foo2();
        }
    }

    private void foo2() {
        int colorBig = RandUtils.randomColor();
        int colorSmall = RandUtils.randomColor();
        EmitterBuilder shot = new EmitterBuilder().shoot().painter(painters.big(Color.WHITE)).speedDegrading(1.0f);
        Emitter smallEmitter = new EmitterBuilder().color(painters, colorSmall).randomSubColor()
                .randomSpeed(10).speedDegrading(0.5f).ttl(10).build();
        Emitter bigEmitter = new EmitterBuilder().color(painters, colorBig).randomSubColor().ttl(5)
                .randomSpeed(20).withEmitter(at(5, repeat(10, smallEmitter))).build();
        Emitter sparkEmitter = new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                V position = new V(master.getPosition());
                position.add(RandUtils.randomVelocity(5));
                Particle spark = new Particle(position, new V(master.getVelocity()));
                spark.setPainter(Painters.blinking(Color.WHITE, 0.2f));
                spark.add(Emitters.timeToLive(10));
                spark.setSpeedDegradingFactor(0.5f);
                particles.add(spark);
            }
        };
        shot.withEmitter(at(20, repeat(40, bigEmitter)));
        shot.withEmitter(repeat(3, sparkEmitter));
        shot.build().emit(null, particles);


//        Payload smallExplosion = multi(10, new PayloadBuilder(10).randomSpeed(5).color(colorSmall).randomSubColor().build());
//        Payload bigExplosion = multi(25, new PayloadBuilder(5).randomSpeed(20).color(colorBig).randomSubColor().withExplosion(smallExplosion).build());
//        Fire shot = FireGenerators.shot(Color.WHITE, -50, width, height);
//        shot.setExplosion(bigExplosion);
//        fires.add(shot);
    }

    private void foo() {
        V center = VirtualScreen.getRelative((float) (Math.random() / 2 + 0.25), (float) (Math.random() / 2 + 0.25));
        int color = RandUtils.randomColor();
        V up = new V(0, -7, 0);
        int altColor = RandUtils.randomColor();
        for (int i = 0; i < 50; i++) {
            V velocity = RandUtils.randomVelocity(10);
            velocity.add(up);
            Particle particle = new Particle(new V(center), velocity);
            particle.setPainter(painters.big(RandUtils.randomSubColor(color)));
            particle.add(Emitters.timeToLive((int) (15 + Math.random() * 5)));
            particle.add(at(12, repeat(10, Emitters.explode(painters, altColor))));
            particles.add(particle);
        }
    }
}
