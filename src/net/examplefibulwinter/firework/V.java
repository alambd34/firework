package net.examplefibulwinter.firework;

public class V {
    public final float x;
    public final float y;
    public final float z;

    public V(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public V add(V v) {
        return new V(x+v.x,y+v.y,z+v.z);
    }

    public float squareLength() {
        return x*x+y*y+z*z;
    }

    public V scale(float scale) {
        return new V(x*scale,y*scale,z*scale);
    }
}
