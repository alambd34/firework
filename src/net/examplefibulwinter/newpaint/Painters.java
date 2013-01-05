package net.examplefibulwinter.newpaint;

import net.examplefibulwinter.firework.V;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Painters {
    private List<Painter> painters = new ArrayList<Painter>();
    private List<Painter> newPainters = new ArrayList<Painter>();


    public List<Painter> getPainters() {
        return painters;
    }

    public void add(Painter painter) {
        newPainters.add(painter);
    }

    public void cycle(int height, int width) {
        removeDead(height, width);
        addNew();
    }

    private void removeDead(int height, int width) {
        for (Iterator<Painter> iterator = painters.iterator(); iterator.hasNext(); ) {
            Painter painter = iterator.next();
            V position = painter.getPosition();
            if (painter.isRemove() || position.y < 0 || position.y > height || position.x < 0 || position.x > width) {
                iterator.remove();
            }
        }
    }

    private void addNew() {
        painters.addAll(newPainters);
        newPainters.clear();
    }


}
