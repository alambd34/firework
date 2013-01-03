package net.examplefibulwinter.firework;

import java.util.List;

public class FireGenerators {
    public static Fire shot(int color, float speed, int screenWidth, int screenHeight) {
        return new Fire(new V(screenWidth / 2, screenHeight, 0),
                new V((float) ((Math.random() - 0.5f) * 10), (float) ((Math.random() - 0.5f) * 20) - 50, 0),
                color, false, 20, null, null, 0.0f, 0.9f);
    }

    public static Payload multi(final int count, final Payload payload) {
        return new Payload() {
            @Override
            public void generate(Fire master, List<Fire> fires) {
                for (int i = 0; i < count; i++) {
                    payload.generate(master, fires);
                }
            }
        };
    }

    public static Payload combine(final Payload... payloads) {
        return new Payload() {
            @Override
            public void generate(Fire master, List<Fire> fires) {
                for (Payload payload : payloads) {
                    payload.generate(master, fires);
                }
            }
        };
    }
}
