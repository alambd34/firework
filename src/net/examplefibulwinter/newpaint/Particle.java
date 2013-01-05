package net.examplefibulwinter.newpaint;

import android.graphics.Canvas;
import net.examplefibulwinter.firework.V;

import java.util.ArrayList;
import java.util.List;

public class Particle {
    private static final V GRAVITY = new V(0, 1, 0);

    private V position;
    private V velocity;
    private Painter painter;
    private boolean remove;
    private int age = 0;
    private List<Emitter> emitters = new ArrayList<Emitter>();

    public Particle(V position, V velocity) {
        this.position = position;
        this.velocity = velocity;
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

    public V getPosition() {
        return position;
    }

    public boolean isRemove() {
        return remove;
    }

    private void physicalUpdate(Particles particles) {
        velocity.add(GRAVITY);
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
}
