package net.examplefibulwinter.newpaint;

import net.examplefibulwinter.firework.RandUtils;
import net.examplefibulwinter.firework.V;

import java.util.ArrayList;
import java.util.List;

public class EmitterBuilder {

    private float speed = 0;
    private Painter painter = null;
    private Painters painters;
    private int color = -1;
    private boolean randomSubColor = false;
    private boolean spark = false;
    private float blinking = 0.0f;
    private List<Emitter> emitters = new ArrayList<Emitter>();
    private float speedDegrading = 0.9f;
    private boolean freeFall = false;
    private boolean shot;

    public EmitterBuilder ttl(int ticks) {
        emitters.add(Emitters.timeToLive(ticks));
        return this;
    }

    public EmitterBuilder randomSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public EmitterBuilder painter(Painter painter) {
        this.painter = painter;
        return this;
    }

    public EmitterBuilder color(Painters painters, int color) {
        this.painters = painters;
        this.color = color;
        return this;
    }

    public EmitterBuilder randomSubColor() {
        this.randomSubColor = true;
        return this;
    }

    public EmitterBuilder asSpark() {
        this.spark = true;
        return this;
    }

    public EmitterBuilder freeFall() {
        this.freeFall = true;
        return this;
    }

    public EmitterBuilder blinking(float blinking) {
        this.blinking = blinking;
        return this;
    }

    public EmitterBuilder withEmitter(Emitter emitter) {
        this.emitters.add(emitter);
        return this;
    }

    public EmitterBuilder speedDegrading(float speedDegrading) {
        this.speedDegrading = speedDegrading;
        return this;
    }

    public Emitter build() {
        return new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                V position = shot ? VirtualScreen.getRelative(0.5f, 1f) : new V(master.getPosition());
                V velocity;
                if (shot) {
                    velocity = new V(RandUtils.rand(-2.5f, 2.5f), RandUtils.rand(-30, -25), 0);
                    ttl(20);
                } else {
                    velocity = freeFall ? new V(0, 0, 0) : new V(master.getVelocity());
                    if (speed > 0) {
                        velocity.add(RandUtils.randomVelocity(speed));
                    }
                }
                Painter p;
                if (color != -1 && randomSubColor) {
                    p = painters.big(RandUtils.randomSubColor(color));
                } else {
                    p = painter == null ? master.getPainter() : painter;
                }
                Particle particle = new Particle(position, velocity);
                particle.setSpeedDegradingFactor(speedDegrading);
                particle.setPainter(p);
                for (Emitter emitter : emitters) {
                    particle.add(emitter);
                }
                particles.add(particle);
//                particles.add(new Particle(new V(master.getPosition()), velocity,
//                        c, spark, (int) ((0.8 + Math.random() * 0.4) * ttl), sparks, explosion, blinking, speedDegrading));
            }
        };
    }

    public EmitterBuilder shoot() {
        this.shot = true;
        return this;
    }
}
