package net.examplefibulwinter.newpaint;

import net.examplefibulwinter.firework.RandUtils;
import net.examplefibulwinter.firework.V;

public class Emitters {
    public static Emitter at(final int age, final Emitter emitter) {
        return new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                if (master.getAge() == age) {
                    emitter.emit(master, particles);
                }
            }
        };
    }

    public static Emitter repeat(final int count, final Emitter emitter) {
        return new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                for (int i = 0; i < count; i++) {
                    emitter.emit(master, particles);
                }
            }
        };
    }

    public static Emitter timeToLive(final int ttl) {
        return at(ttl, new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                master.die();
            }
        });
    }

    public static Emitter explode(final Painters painters, final int color) {
        return new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                V velocity = RandUtils.randomVelocity(5);
                velocity.add(master.getVelocity());
                Particle particle = new Particle(new V(master.getPosition()), velocity);
                particle.setPainter(painters.small(RandUtils.randomSubColor(color)));
                particle.add(timeToLive(RandUtils.rand(3, 5)));
                particles.add(particle);
            }
        };
    }
}
