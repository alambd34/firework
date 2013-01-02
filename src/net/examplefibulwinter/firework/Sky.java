package net.examplefibulwinter.firework;

import android.graphics.Color;

import java.util.*;

public class Sky {
    private List<Fire> fires=new ArrayList<Fire>();

    public Sky() {
    }

    public void init(final int firstHue, final int secondHue) {
        fires.add(new Fire(new V(160,400,0), new V((float) ((Math.random()-0.5f)*5),-50,0), Color.WHITE, 20, new Payload() {
            @Override
            public List<Fire> generate(Fire master) {
                ArrayList<Fire> fires = new ArrayList<Fire>();
                for(int i=0;i<50;i++){
                    fires.add(new Fire(master.getPosition(), master.getVelocity().add(randomVelocity(20)),
                            randomSubColor(firstHue), 10, new Payload() {
                        @Override
                        public List<Fire> generate(Fire master) {
                            ArrayList<Fire> fires = new ArrayList<Fire>();
                            for(int i=0;i<10;i++){
                                fires.add(new Fire(master.getPosition(), master.getVelocity().add(randomVelocity(5)),
                                        randomSubColor(secondHue), 10, null, false));
                            }
                            return fires;
                        }
                    }, false){
                        @Override
                        public Collection<Fire> createSparks() {
                            ArrayList<Fire> sparks = new ArrayList<Fire>();
                            Fire spark = new Fire(this.getPosition(), new V(0, 0, 0), Color.WHITE, 5, null, true);
                            sparks.add(spark);
                            return sparks;
                        }
                    });
                }
                return fires;
            }
        }, false));
    }

    public void init2(final int firstHue) {
        fires.add(new Fire(new V(160,400,0), new V((float) ((Math.random()-0.5f)*5),-50,0), Color.WHITE, 20, new Payload() {
            @Override
            public List<Fire> generate(Fire master) {
                ArrayList<Fire> fires = new ArrayList<Fire>();
                for(int i=0;i<50;i++){
                    fires.add(new Fire(master.getPosition(), master.getVelocity().add(randomVelocity(20)),
                            randomSubColor(firstHue), 20, null, false){
                        @Override
                        public Collection<Fire> createSparks() {
                            ArrayList<Fire> sparks = new ArrayList<Fire>();
                            Fire spark = new Fire(this.getPosition(), new V(0, 0, 0), Color.WHITE, 5, null, true);
                            sparks.add(spark);
                            return sparks;
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

    private V randomVelocity(float speed) {
        V v;
        do{
            v = new V((float) (Math.random()*2 - 1), (float) (Math.random()*2 - 1), (float) (Math.random()*2 - 1));
        }while (v.squareLength()>1);
        return v.scale((float) (speed/Math.sqrt(v.squareLength())));
    }

    public List<Fire> getFires() {
        return fires;
    }

    public void update(){
        LinkedList<Fire> newFires = new LinkedList<Fire>();
        for (Fire fire : fires) {
            fire.update(newFires);
        }
        fires=newFires;
    }
}
