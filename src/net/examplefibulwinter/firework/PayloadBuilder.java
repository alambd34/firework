package net.examplefibulwinter.firework;

import java.util.List;

public class PayloadBuilder {
    private float speed = 0;
    private int color = -1;
    private boolean randomSubColor = false;
    private boolean spark = false;
    private float blinking = 0.0f;
    private int ticks;
    private Payload sparks;
    private Payload explosion;
    private float speedDegrading = 0.9f;

    public PayloadBuilder(int ticks) {
        this.ticks = ticks;
    }

    public PayloadBuilder randomSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public PayloadBuilder color(int color) {
        this.color = color;
        return this;
    }

    public PayloadBuilder randomSubColor() {
        this.randomSubColor = true;
        return this;
    }

    public PayloadBuilder asSpark() {
        this.spark = true;
        return this;
    }

    public PayloadBuilder blinking(float blinking) {
        this.blinking = blinking;
        return this;
    }

    public PayloadBuilder withSparks(Payload sparks) {
        this.sparks = sparks;
        return this;
    }

    public PayloadBuilder withExplosion(Payload explosion) {
        this.explosion = explosion;
        return this;
    }

    public PayloadBuilder speedDegrading(float speedDegrading) {
        this.speedDegrading = speedDegrading;
        return this;
    }

    public Payload build() {
        return new Payload() {
            @Override
            public void generate(Fire master, List<Fire> fires) {
                V velocity = new V(master.getVelocity());
                if (speed > 0) {
                    velocity.add(RandUtils.randomVelocity(speed));
                }
                int c = color == -1 ? master.getColor() : color;
                if (randomSubColor) {
                    c = RandUtils.randomSubColor(c);
                }
                fires.add(new Fire(new V(master.getPosition()), velocity,
                        c, spark, ticks, sparks, explosion, blinking, speedDegrading));
            }
        };
    }
}
