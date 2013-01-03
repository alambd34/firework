package net.examplefibulwinter.firework;

import java.util.List;

public class FireGenerators {
    public static Payload sparks(final int color) {
        return new Payload() {
            @Override
            public void generate(Fire master, List<Fire> fires) {
                fires.add(new Fire(new V(master.getPosition()), new V(0, 0, 0), color, true, 5, null, null));
            }
        };
    }

    public static Fire shot(int color, float speed, int screenWidth, int screenHeight) {
        return new Fire(new V(screenWidth / 2, screenHeight, 0),
                new V((float) ((Math.random() - 0.5f) * 10), (float) ((Math.random() - 0.5f) * 20) - 50, 0),
                color, false, 20, null, null);
    }

    public static Payload big(final int color) {
        return big(color, null);
    }

    public static Payload big(final int color, final Payload sparks) {
        return new Payload() {
            @Override
            public void generate(Fire master, List<Fire> fires) {
                V velocity1 = new V(master.getVelocity());
                velocity1.add(RandUtils.randomVelocity(20));
                int subColor = RandUtils.randomSubColor(color);
                fires.add(new Fire(new V(master.getPosition()), velocity1,
                        subColor, false, 20, sparks, null));
            }
        };
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

    public static Payload small(final int color) {
        return new Payload() {
            @Override
            public void generate(Fire master, List<Fire> fires) {
                V velocity1 = new V(master.getVelocity());
                velocity1.add(RandUtils.randomVelocity(5));
                fires.add(new Fire(new V(master.getPosition()), velocity1,
                        RandUtils.randomSubColor(color), false, 10, null, null));

            }
        };
    }

    public static Payload bigExpl(final int color, final Payload explosion) {
        return new Payload() {
            @Override
            public void generate(Fire master, List<Fire> fires) {
                V velocity1 = new V(master.getVelocity());
                velocity1.add(RandUtils.randomVelocity(20));
                fires.add(new Fire(new V(master.getPosition()), velocity1, RandUtils.randomSubColor(color), false, 5, null, explosion));
            }
        };
    }
}
