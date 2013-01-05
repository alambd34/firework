package net.examplefibulwinter.newpaint;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import net.examplefibulwinter.firework.R;

public class Icons {
    private Bitmap particleIconBitmap;

    public Icons(Resources resources) {
        BitmapDrawable particleIcon = (BitmapDrawable) resources.getDrawable(R.drawable.particle);
        particleIconBitmap = particleIcon.getBitmap();
    }

    public Bitmap getParticleIconBitmap() {
        return particleIconBitmap;
    }
}
