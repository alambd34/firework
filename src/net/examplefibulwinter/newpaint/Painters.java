package net.examplefibulwinter.newpaint;

import android.graphics.*;
import net.examplefibulwinter.firework.RandUtils;

public class Painters {
    private Icons icons;

    public Painters(Icons icons) {
        this.icons = icons;
    }

    public Painter big(final int color) {
        return new Painter() {
            Paint paint;

            {
                paint = new Paint();
//                paint.setColor(Color.WHITE);
                paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
            }


            @Override
            public void draw(Canvas canvas, Particle particle, float virtualToRealK, float phase) {
                Bitmap particleIconBitmap = icons.getParticleIconBitmap();
                paint.setAlpha((int) (255 * (1 - (1 - phase) * FadingCanvas.FADING_FACTOR)));
                canvas.drawBitmap(particleIconBitmap, particle.getPosition().x * virtualToRealK - particleIconBitmap.getWidth() / 2,
                        particle.getPosition().y * virtualToRealK - particleIconBitmap.getHeight() / 2, paint);
//                canvas.drawPoint(particle.getPosition().x * virtualToRealK+ RandUtils.rand(-20,20),
//                        particle.getPosition().y * virtualToRealK+ RandUtils.rand(-20,20),paint);
            }
        };
    }

    public Painter bigSparking(final int color) {
        return new Painter() {
            Paint paint;

            {
                paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
            }


            @Override
            public void draw(Canvas canvas, Particle particle, float virtualToRealK, float phase) {
                Bitmap particleIconBitmap = icons.getParticleIconBitmap();
                canvas.drawBitmap(particleIconBitmap, particle.getPosition().x * virtualToRealK - particleIconBitmap.getWidth() / 2,
                        particle.getPosition().y * virtualToRealK - particleIconBitmap.getHeight() / 2, paint);
                if (RandUtils.withProbability(0.2f)) {
                    canvas.drawPoint(
                            particle.getPosition().x * virtualToRealK + RandUtils.rand(-2f, 2f),
                            particle.getPosition().x * virtualToRealK + RandUtils.rand(-2f, 2f),
                            paint);
                }
//                canvas.drawPoint(particle.getPosition().x * virtualToRealK+ RandUtils.rand(-20,20),
//                        particle.getPosition().y * virtualToRealK+ RandUtils.rand(-20,20),paint);
            }
        };
    }

    public static Painter small(final int color) {
        return new Painter() {
            Paint paint;

            {
                paint = new Paint();
                paint.setColor(color);
            }


            @Override
            public void draw(Canvas canvas, Particle particle, float virtualToRealK, float phase) {
                canvas.drawPoint(particle.getPosition().x * virtualToRealK,
                        particle.getPosition().y * virtualToRealK, paint);
            }
        };
    }

    public static Painter blinking(final int color, final float blinkShare) {
        return new Painter() {
            Paint paint;

            {
                paint = new Paint();
                paint.setColor(color);
            }


            @Override
            public void draw(Canvas canvas, Particle particle, float virtualToRealK, float phase) {
                if (!RandUtils.withProbability(blinkShare)) {
                    canvas.drawPoint(particle.getPosition().x * virtualToRealK,
                            particle.getPosition().y * virtualToRealK, paint);
                }
            }
        };
    }

    public Painter head(final int color) {
        return new Painter() {
            Paint paint;

            {
                paint = new Paint();
//                paint.setColor(Color.WHITE);
                paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
            }


            @Override
            public void draw(Canvas canvas, Particle particle, float virtualToRealK, float phase) {
                Bitmap particleIconBitmap = icons.getHeadIconBitmap();
                canvas.drawBitmap(particleIconBitmap, particle.getPosition().x * virtualToRealK - particleIconBitmap.getWidth() / 2,
                        particle.getPosition().y * virtualToRealK - particleIconBitmap.getHeight() / 2, paint);
//                canvas.drawPoint(particle.getPosition().x * virtualToRealK+ RandUtils.rand(-20,20),
//                        particle.getPosition().y * virtualToRealK+ RandUtils.rand(-20,20),paint);
            }
        };
    }
}
