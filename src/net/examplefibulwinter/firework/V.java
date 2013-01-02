package net.examplefibulwinter.firework;

public class V {
    public float x;
    public float y;
    public float z;

    public V(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public V(V v) {
        this(v.x,v.y,v.z);
    }

    public void add(V v) {
        x+=v.x;
        y+=v.y;
        z+=v.z;
    }

    public float squareLength() {
        return x*x+y*y+z*z;
    }

    public void scale(float scale) {
        x*=scale;
        y*=scale;
        z*=scale;
    }
}
