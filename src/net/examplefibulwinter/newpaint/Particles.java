package net.examplefibulwinter.newpaint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Particles {
    private List<Particle> particles = new ArrayList<Particle>();
    private List<Particle> newParticles = new ArrayList<Particle>();


    public List<Particle> getParticles() {
        return particles;
    }

    public void add(Particle particle) {
        newParticles.add(particle);
    }

    public void cycle() {
        removeDead();
        addNew();
    }

    private void removeDead() {
        for (Iterator<Particle> iterator = particles.iterator(); iterator.hasNext(); ) {
            Particle particle = iterator.next();
            if (particle.isRemove() || !VirtualScreen.isInside(particle.getPosition())) {
                iterator.remove();
            }
        }
    }

    private void addNew() {
        particles.addAll(newParticles);
        newParticles.clear();
    }


}
