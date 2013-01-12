package net.examplefibulwinter.newpaint;

import android.graphics.Canvas;
import net.examplefibulwinter.firework.V;

import java.util.ArrayList;
import java.util.List;

public class Particle {
    private static final V GRAVITY = new V(0, 1, 0);

    private V position;
    private V velocity;
    private float speedDegradingFactor = 0.9f;
    private Painter painter;
    private Painter realPainter;
    private boolean remove;
    private int age = 0;
    private List<Emitter> emitters = new ArrayList<Emitter>();
    private V gravity = new V(GRAVITY);

    public Particle(V position, V velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void setSpeedDegradingFactor(float speedDegradingFactor) {
        this.speedDegradingFactor = speedDegradingFactor;
        this.gravity = new V(GRAVITY);
        gravity.scale(speedDegradingFactor);
    }

    public void setGravityDegradingFactor(float gravityDegradingFactor) {
        this.gravity = new V(GRAVITY);
        gravity.scale(gravityDegradingFactor);
    }

    public void paint(Canvas canvas, Particles particles) {
        physicalUpdate(particles);
        paint(canvas, VirtualScreen.virtualToReal(canvas));
    }

    public void add(Emitter emitter) {
        emitters.add(emitter);
    }

    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    public void setRealPainter(Painter realPainter) {
        this.realPainter = realPainter;
    }

    public V getPosition() {
        return position;
    }

    public boolean isRemove() {
        return remove;
    }

    private void physicalUpdate(Particles particles) {
        velocity.add(gravity);
        velocity.scale(speedDegradingFactor);
        age++;
        for (Emitter emitter : emitters) {
            emitter.emit(this, particles);
        }
    }

    private void paint(Canvas canvas, float virtualToRealK) {
        if (painter != null) {
            int steps = calculatePaintingStepsCount(virtualToRealK);
            V scaledVelocity = new V(velocity);
            scaledVelocity.scale(1f / steps);
            for (int i = 0; i < steps; i++) {
                position.add(scaledVelocity);
                //                canvas.drawPoint(painter.getPosition().x, painter.getPosition().y, firePaint);
                painter.draw(canvas, this, virtualToRealK);
            }
        }
    }

    private int calculatePaintingStepsCount(float virtualToRealK) {
        int steps = (int) (Math.max(Math.abs(velocity.x), Math.abs(velocity.y)) * virtualToRealK);
        if (steps == 0) {
            steps = 1;
        }
        return steps;
    }

    public int getAge() {
        return age;
    }

    public void die() {
        remove = true;
    }

    public V getVelocity() {
        return velocity;
    }

    public void paintReal(Canvas realCanvas) {
        if (realPainter != null) {
            realPainter.draw(realCanvas, this, VirtualScreen.virtualToReal(realCanvas));
        }
    }

    public Painter getPainter() {
        return painter;
    }
}
