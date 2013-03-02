package net.examplefibulwinter.newpaint;

import android.graphics.Canvas;

public interface Painter {
    void draw(Canvas canvas, Particle particle, float virtualToRealK, float phase);
}
