package net.examplefibulwinter.newpaint;

import android.graphics.*;

public class Painters {
    private Icons icons;

    public Painters(Icons icons) {
        this.icons = icons;
    }

    public PainterR big(final int color) {
        return new PainterR() {
            Paint paint;

            {
                paint = new Paint();
//                paint.setColor(Color.WHITE);
                paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
            }


            @Override
            public void draw(Canvas canvas, Particle particle, float virtualToRealK) {
                Bitmap particleIconBitmap = icons.getParticleIconBitmap();
                canvas.drawBitmap(particleIconBitmap, particle.getPosition().x * virtualToRealK - particleIconBitmap.getWidth() / 2,
                        particle.getPosition().y * virtualToRealK - particleIconBitmap.getHeight() / 2, paint);
//                canvas.drawPoint(particle.getPosition().x * virtualToRealK+ RandUtils.rand(-20,20),
//                        particle.getPosition().y * virtualToRealK+ RandUtils.rand(-20,20),paint);
            }
        };
    }
}
