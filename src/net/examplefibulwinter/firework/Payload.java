package net.examplefibulwinter.firework;

import java.util.List;

public interface Payload {
    List<Fire> generate(Fire master);
}
