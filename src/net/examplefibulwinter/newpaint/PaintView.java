package net.examplefibulwinter.newpaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;
import net.examplefibulwinter.firework.R;
import net.examplefibulwinter.firework.RandUtils;
import net.examplefibulwinter.firework.V;

import java.util.Iterator;

public class PaintView extends ImageView {

    private Handler h;
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            postInvalidate();
        }
    };
    private static final int FRAME_RATE = 10;
    private Paint fpsPaint;
    private Paint firePaint;
    private Bitmap particleIconBitmap;
    private Canvas canvas;
    private Bitmap bmp;

    private Painters painters = new Painters();
    private long second;
    private int lastFps;
    private int fps;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        firePaint = new Paint();
//        firePaint.setColor(Color.GREEN);
//        firePaint.setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY));
        fpsPaint = new Paint();
        fpsPaint.setColor(Color.WHITE);
        h = new Handler();
        BitmapDrawable particleIcon = (BitmapDrawable) context.getResources().getDrawable(R.drawable.particle);
        particleIconBitmap = particleIcon.getBitmap();
        second = System.currentTimeMillis() / 1000;
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
        canvas.drawColor(Color.argb(50, 0, 0, 0));
        for (Painter painter : painters.getPainters()) {
            painter.paint(canvas, particleIconBitmap, painters);
        }
        for (Iterator<Painter> iterator = painters.getPainters().iterator(); iterator.hasNext(); ) {
            Painter painter = iterator.next();
            V position = painter.getPosition();
            if (painter.isRemove() || position.y < 0 || position.y > getHeight() || position.x < 0 || position.x > getWidth()) {
                iterator.remove();
            }
        }
        painters.addNew();
        createNewPainter();
        realCanvas.drawBitmap(bmp, 0f, 0f, null);
        long currentSecond = System.currentTimeMillis() / 1000;
        if (currentSecond != second) {
            second = currentSecond;
            lastFps = fps;
            fps = 0;
        } else {
            fps++;
        }
        fps++;
        realCanvas.drawText("C=" + painters.getPainters().size() + " FPS=" + lastFps, 20, 20, fpsPaint);
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
                painters.add(new Painter(new V(center), velocity, RandUtils.randomSubColor(color)));
            }
        }
    }
}
