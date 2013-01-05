package net.examplefibulwinter.newpaint;

import android.graphics.*;
import net.examplefibulwinter.firework.V;

public class Painter {
    private static final V GRAVITY = new V(0, 1, 0);

    private final Icons icons;
    private V position;
    private V velocity;
    private V scaledVelocity;
    private int color;
    private Paint paint;
    private boolean remove;
    private int ttl = (int) (15 + Math.random() * 5);

    public Painter(Icons icons, V position, V velocity, int color) {
        this.icons = icons;
        this.position = position;
        this.velocity = velocity;
        this.scaledVelocity = new V(velocity);
        this.color = color;
        paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
    }

    public V getPosition() {
        return position;
    }

    public int getColor() {
        return color;
    }

    private void update() {
        position.add(scaledVelocity);
    }

    private int getCycles(float virtualToRealK) {
        velocity.add(GRAVITY);
        int steps = (int) (Math.max(Math.abs(velocity.x), Math.abs(velocity.y)) * virtualToRealK);
        if (steps == 0) {
            steps = 1;
        }
        scaledVelocity.setScaled(velocity, 1f / steps);
        return steps;
    }

    public void paint(Canvas canvas, Painters painters) {
        Bitmap particleIconBitmap = icons.getParticleIconBitmap();
        float virtualToRealK = VirtualScreen.virtualToReal(canvas);
        int cycles = getCycles(virtualToRealK);
        for (int i = 0; i < cycles; i++) {
            update();
//                canvas.drawPoint(painter.getPosition().x, painter.getPosition().y, firePaint);
            canvas.drawBitmap(particleIconBitmap, getPosition().x * virtualToRealK - particleIconBitmap.getWidth() / 2,
                    getPosition().y * virtualToRealK - particleIconBitmap.getHeight() / 2, paint);
        }
        ttl--;
        if (ttl <= 0) {
            remove = true;
        }
    }

    public boolean isRemove() {
        return remove;
    }
}
