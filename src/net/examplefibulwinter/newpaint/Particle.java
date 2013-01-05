package net.examplefibulwinter.newpaint;

import android.graphics.Canvas;
import net.examplefibulwinter.firework.V;

public class Particle {
    private static final V GRAVITY = new V(0, 1, 0);

    private V position;
    private V velocity;
    private PainterR painter;
    private boolean remove;
    private int ttl = (int) (15 + Math.random() * 5);

    public Particle(V position, V velocity, PainterR painter) {
        this.position = position;
        this.velocity = velocity;
        this.painter = painter;
    }

    public void paint(Canvas canvas, Particles particles) {
        physicalUpdate();
        paint(canvas, VirtualScreen.virtualToReal(canvas));
    }

    public V getPosition() {
        return position;
    }

    public boolean isRemove() {
        return remove;
    }

    private void physicalUpdate() {
        velocity.add(GRAVITY);
        ttl--;
        if (ttl <= 0) {
            remove = true;
        }
    }

    private void paint(Canvas canvas, float virtualToRealK) {
        int steps = calculatePaintingStepsCount(virtualToRealK);
        V scaledVelocity = new V(velocity);
        scaledVelocity.scale(1f / steps);
        for (int i = 0; i < steps; i++) {
            position.add(scaledVelocity);
            //                canvas.drawPoint(painter.getPosition().x, painter.getPosition().y, firePaint);
            painter.draw(canvas, this, virtualToRealK);
        }
    }

    private int calculatePaintingStepsCount(float virtualToRealK) {
        int steps = (int) (Math.max(Math.abs(velocity.x), Math.abs(velocity.y)) * virtualToRealK);
        if (steps == 0) {
            steps = 1;
        }
        return steps;
    }
}
