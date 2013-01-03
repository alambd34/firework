package net.examplefibulwinter.firework;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static net.examplefibulwinter.firework.FireGenerators.*;

public class Sky {
    private List<Fire> fires = new ArrayList<Fire>();

    public Sky() {
    }

    public void init(int width, int height) {
//        fires.add(new Fire(new V(width/2,400,0), new V((float) ((Math.random()-0.5f)*5),(float) ((Math.random()-0.5f)*5)-50,0), Color.WHITE, false, 20, null, new Payload() {
//            @Override
//            public void generate(Fire master, List<Fire> fires) {
//                for(int i=0;i<25;i++){
//                    V velocity1 = new V(master.getVelocity());
//                    velocity1.add(randomVelocity(20));
//                    fires.add(new Fire(new V(master.getPosition()), velocity1, randomSubColor(firstHue), false, 5, null, new Payload() {
//                        @Override
//                        public void generate(Fire master, List<Fire> fires) {
//                            for(int i=0;i<10;i++){
//                                V velocity1 = new V(master.getVelocity());
//                                velocity1.add(randomVelocity(5));
//                                fires.add(new Fire(new V(master.getPosition()), velocity1,
//                                        randomSubColor(secondHue), false, 10, null, null));
//                            }
//                        }
//                    }));
//                }
//            }
//        }));

//        int colorBig = RandUtils.randomColor();
//        int colorSmall = RandUtils.randomColor();
//        Fire shot = FireGenerators.shot(Color.WHITE, -50, width, height);
//        shot.setExplosion(multi(25, FireGenerators.bigExpl(colorBig, FireGenerators.multi(10, FireGenerators.smallExplosion(colorSmall)))));
//        fires.add(shot);

        int colorBig = RandUtils.randomColor();
        int colorSmall = RandUtils.randomColor();

        Payload smallExplosion = multi(10, new PayloadBuilder(10).randomSpeed(5).color(colorSmall).randomSubColor().build());
        Payload bigExplosion = multi(25, new PayloadBuilder(5).randomSpeed(20).color(colorBig).randomSubColor().withExplosion(smallExplosion).build());
        Fire shot = FireGenerators.shot(Color.WHITE, -50, width, height);
        shot.setExplosion(bigExplosion);
        fires.add(shot);


    }

    public void init2(int width, int height) {
//        fires.add(new Fire(new V(width/2,400,0), new V((float) ((Math.random()-0.5f)*10),(float) ((Math.random()-0.5f)*20)-50,0), Color.WHITE, false, 20, null, new Payload() {
//            @Override
//            public void generate(Fire master, List<Fire> fires) {
//                for(int i=0;i<50;i++){
//                    V velocity1 = new V(master.getVelocity());
//                    velocity1.add(randomVelocity(20));
//                    final int color = randomSubColor(firstHue);
//                    fires.add(new Fire(new V(master.getPosition()), velocity1,
//                            color, false, 20, FireGenerators.sparks(color), null));
//                }
//            }
//        }));
        int color = RandUtils.randomColor();
        Payload spark = new PayloadBuilder(5).asSpark().build();
        Payload bigExplosion = multi(50, new PayloadBuilder(20).randomSpeed(20).color(color).randomSubColor().withSparks(spark).build());
        Fire shot = FireGenerators.shot(Color.WHITE, -50, width, height);
        shot.setExplosion(bigExplosion);
        fires.add(shot);

    }

    public void init3(int width, int height) {
        Fire shot = FireGenerators.shot(Color.WHITE, -50, width, height);
        shot.setSparks(FireGenerators.sparks(Color.RED));
        shot.setExplosion(multi(50, big(Color.GREEN, sparks(Color.YELLOW))));
        fires.add(shot);
    }

    private int randomSubColor(int firstHue) {
        return Color.HSVToColor((int) (Math.random() * 50 + 200), new float[]{((float) (Math.random() * 50 - 25) + firstHue) % 360, (float) (Math.random() * 0.3 + 0.7), 1});  //To change body of created methods use File | Settings | File Templates.
    }

    private V[] rands = new V[117];
    private int ri = 0;

    {
        for (int i = 0; i < rands.length; i++) {
            V v = new V(0, 0, 0);
            do {
                v.x = (float) (Math.random() * 2 - 1);
                v.y = (float) (Math.random() * 2 - 1);
                v.z = (float) (Math.random() * 2 - 1);
            } while (v.squareLength() > 1);
            v.scale((float) (1 / Math.sqrt(v.squareLength())));
            rands[i] = v;
        }
    }

    public V randomVelocity(float speed) {
        ri = (ri + 1) % rands.length;
        V v = new V(rands[ri]);
        v.scale(speed);
        return v;
    }

    public List<Fire> getFires() {
        return fires;
    }

    public void update() {
        LinkedList<Fire> newFires = new LinkedList<Fire>();
        for (Fire fire : fires) {
            fire.update(newFires);
        }
        for (Iterator<Fire> iterator = fires.iterator(); iterator.hasNext(); ) {
            if (iterator.next().isRemove()) {
                iterator.remove();
            }
            ;
        }
        fires.addAll(newFires);
    }
}
