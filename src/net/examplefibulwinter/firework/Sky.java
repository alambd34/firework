package net.examplefibulwinter.firework;

import android.graphics.Color;

import java.util.*;

public class Sky {
    private List<Fire> fires=new ArrayList<Fire>();

    public Sky() {
    }

    public void init(final int firstHue, final int secondHue, int width) {
        fires.add(new Fire(new V(width/2,400,0), new V((float) ((Math.random()-0.5f)*5),(float) ((Math.random()-0.5f)*5)-50,0), Color.WHITE, 20, new Payload() {
            @Override
            public List<Fire> generate(Fire master) {
                ArrayList<Fire> fires = new ArrayList<Fire>();
                for(int i=0;i<25;i++){
                    V velocity1 = new V(master.getVelocity());
                    velocity1.add(randomVelocity(20));
                    fires.add(new Fire(new V(master.getPosition()), velocity1, randomSubColor(firstHue), 5, new Payload() {
                        @Override
                        public List<Fire> generate(Fire master) {
                            ArrayList<Fire> fires = new ArrayList<Fire>();
                            for(int i=0;i<10;i++){
                                V velocity1 = new V(master.getVelocity());
                                velocity1.add(randomVelocity(5));
                                fires.add(new Fire(new V(master.getPosition()), velocity1,
                                        randomSubColor(secondHue), 10, null, false));
                            }
                            return fires;
                        }
                    }, false));
                }
                return fires;
            }
        }, false));
    }

    public void init2(final int firstHue, int width) {
        fires.add(new Fire(new V(width/2,400,0), new V((float) ((Math.random()-0.5f)*10),(float) ((Math.random()-0.5f)*20)-50,0), Color.WHITE, 20, new Payload() {
            @Override
            public List<Fire> generate(Fire master) {
                ArrayList<Fire> fires = new ArrayList<Fire>();
                for(int i=0;i<50;i++){
                    V velocity1 = new V(master.getVelocity());
                    velocity1.add(randomVelocity(20));
                    final int color = randomSubColor(firstHue);
                    fires.add(new Fire(new V(master.getPosition()), velocity1,
                            color, 20, null, false){
                        @Override
                        public Fire createSparks() {
                            return new Fire(new V(this.getPosition()), new V(0, 0, 0), color, 5, null, true);
                        }
                    });
                }
                return fires;
            }
        }, false));
    }

    private int randomSubColor(int firstHue) {
        return Color.HSVToColor((int)(Math.random()*50+200), new float[]{((float)(Math.random()*50-25)+firstHue)%360, (float)(Math.random()*0.3+0.7), 1});  //To change body of created methods use File | Settings | File Templates.
    }

    private V[] rands=new V[117];
    private int ri=0;
    {
        for(int i=0;i<rands.length;i++){
            V v=new V(0,0,0);
            do{
                v.x=(float) (Math.random()*2 - 1);
                v.y=(float) (Math.random()*2 - 1);
                v.z=(float) (Math.random()*2 - 1);
            }while (v.squareLength()>1);
            v.scale((float) (1/Math.sqrt(v.squareLength())));
            rands[i]=v;
        }
    }

    private V randomVelocity(float speed) {
        ri=(ri+1)%rands.length;
        V v = new V(rands[ri]);
        v.scale(speed);
        return v;
    }

    public List<Fire> getFires() {
        return fires;
    }

    public void update(){
        LinkedList<Fire> newFires = new LinkedList<Fire>();
        for (Fire fire : fires) {
            fire.update(newFires);
        }
        for (Iterator<Fire> iterator = fires.iterator(); iterator.hasNext(); ) {
            if(iterator.next().isRemove()){
                iterator.remove();
            };
        }
        fires.addAll(newFires);
    }
}
