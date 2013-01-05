package net.examplefibulwinter.newpaint;

import java.util.ArrayList;
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

    public void addNew() {
        painters.addAll(newPainters);
        newPainters.clear();
    }
}
