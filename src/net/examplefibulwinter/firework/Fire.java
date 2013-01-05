package net.examplefibulwinter.firework;

import java.util.LinkedList;

public class Fire {
    private static V GRAVITY = new V(0, 0.5f, 0);
    private V position;
    private V velocity;
    private int color;
    private int age;
    private Payload sparks;
    private int cyclesToExplode;
    private Payload explosion;
    private boolean spark;
    private float blinking;
    private boolean remove;
    private float speedDegrading;

    public Fire(V position, V velocity, int color, boolean spark, int cyclesToExplode, Payload sparks, Payload explosion, float blinking, float speedDegrading) {
        this.position = position;
        this.velocity = velocity;
        this.color = color;
        this.sparks = sparks;
        this.cyclesToExplode = cyclesToExplode;
        this.explosion = explosion;
        this.spark = spark;
        this.blinking = blinking;
        this.speedDegrading = speedDegrading;
    }

    public void update(LinkedList<Fire> fires) {
        age++;
        velocity.add(GRAVITY);
        velocity.scale(speedDegrading);
        position.add(velocity);
        if (age >= cyclesToExplode) {
            if (explosion != null) {
                explosion.generate(this, fires);
            }
            remove = true;
        } else {
            if (sparks != null) {
                sparks.generate(this, fires);
            }
        }
    }

    public float getBlinking() {
        return blinking;
    }

    public V getPosition() {
        return position;
    }

    public int getAge() {
        return age;
    }

    public V getVelocity() {
        return velocity;
    }

    public int getColor() {
        return color;
    }

    public boolean isSpark() {
        return spark;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setSparks(Payload sparks) {
        this.sparks = sparks;
    }

    public void setExplosion(Payload explosion) {
        this.explosion = explosion;
    }
}
