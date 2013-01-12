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
        this(v.x, v.y, v.z);
    }

    public void add(V v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void sub(V v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public float squareLength() {
        return x * x + y * y + z * z;
    }

    public float squareLength(V v) {
        float dx = x - v.x;
        float dy = y - v.y;
        float dz = z - v.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public void scale(float scale) {
        x *= scale;
        y *= scale;
        z *= scale;
    }

    public float len() {
        return (float) Math.sqrt(squareLength());
    }

    public void normalize() {
        if (squareLength() != 0) {
            scale(1f / len());
        }
    }

    public void setScaled(V v, float scale) {
        x = v.x * scale;
        y = v.y * scale;
        z = v.z * scale;
    }
}
