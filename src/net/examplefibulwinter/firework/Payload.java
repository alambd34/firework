package net.examplefibulwinter.firework;

import java.util.List;

public interface Payload {
    void generate(Fire master, List<Fire> fires);
}
