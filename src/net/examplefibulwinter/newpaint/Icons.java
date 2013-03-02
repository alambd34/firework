package net.examplefibulwinter.newpaint;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import net.examplefibulwinter.firework.R;

public class Icons {
    private Bitmap particleIconBitmap;
    private Bitmap headIconBitmap;

    public Icons(Resources resources) {
        particleIconBitmap = ((BitmapDrawable) resources.getDrawable(R.drawable.particle3x3b9x9)).getBitmap();
        headIconBitmap = ((BitmapDrawable) resources.getDrawable(R.drawable.particle3x3b7x7)).getBitmap();
    }

    public Bitmap getParticleIconBitmap() {
        return particleIconBitmap;
    }

    public Bitmap getHeadIconBitmap() {
        return headIconBitmap;
    }
}
