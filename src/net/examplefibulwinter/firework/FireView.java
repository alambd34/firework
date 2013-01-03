package net.examplefibulwinter.firework;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FireView extends ImageView {

    private Handler h;
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            postInvalidate();
        }
    };
    private static final int FRAME_RATE = 40;
    private Paint bgPaint;
    private Paint firePaint;
    private final Sky sky;
    private final BitmapDrawable particleIcon;
    private Bitmap particleIconBitmap;
    private Canvas canvas;
    private Bitmap bmp;

    public FireView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bgPaint = new Paint();
        bgPaint.setColor(Color.BLUE);
        firePaint = new Paint();
        firePaint.setColor(Color.GREEN);
        h = new Handler();
        sky = new Sky();
        particleIcon = (BitmapDrawable) context.getResources().getDrawable(R.drawable.particle);
        particleIconBitmap = particleIcon.getBitmap();
    }

    @Override
    protected void onDraw(Canvas realCanvas) {
        if (bmp == null) {
            Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
            bmp = Bitmap.createBitmap(getWidth(), getHeight(), conf);
            canvas = new Canvas(bmp);
        }
//        if (Math.random() < 0.02) {
//            sky.init(getWidth(), getHeight());
//        }
//        if (Math.random() < 0.05) {
//            sky.init2(getWidth(), getHeight());
//        }
        if (Math.random() < 0.03) {
            sky.init3(getWidth(), getHeight());
        }
        sky.update();
        canvas.drawColor(Color.argb(128, 0, 0, 0));
        for (Fire fire : sky.getFires()) {
            firePaint.setColor(fire.getColor());
            firePaint.setColorFilter(new PorterDuffColorFilter(fire.getColor(), PorterDuff.Mode.MULTIPLY));
            if (fire.getBlinking() > 0 && Math.random() < fire.getBlinking()) continue;
            if (fire.isSpark()) {
                canvas.drawPoint(fire.getPosition().x, fire.getPosition().y, firePaint);
            } else {
                canvas.drawBitmap(particleIconBitmap, fire.getPosition().x - particleIconBitmap.getWidth() / 2,
                        fire.getPosition().y - particleIconBitmap.getHeight() / 2, firePaint);
            }
        }
        realCanvas.drawBitmap(bmp, 0f, 0f, null);
        h.postDelayed(r, FRAME_RATE);
    }

    private int randomColor() {
        return (int) (Math.random() * 360);
    }
}
