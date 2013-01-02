package net.examplefibulwinter.firework;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Fire {
    private static V GRAVITY = new V(0,1,0);
    private V position;
    private V velocity;
    private int color;
    private int cyclesToExplode;
    private Payload payload;
    private boolean spark;
    private boolean remove;

    public Fire(V position, V velocity, int color, int cyclesToExplode, Payload payload, boolean spark) {
        this.position = position;
        this.velocity = velocity;
        this.color = color;
        this.cyclesToExplode = cyclesToExplode;
        this.payload = payload;
        this.spark = spark;
    }

    public void update(LinkedList<Fire> fires){
        velocity.add(GRAVITY);
        velocity.scale(0.9f);
        position.add(velocity);
        cyclesToExplode--;
        if(cyclesToExplode<=0){
            if(payload!=null){
                fires.addAll(payload.generate(this));
            }
            remove=true;
        }else{
            Fire spark = createSparks();
            if(spark!=null){
                fires.add(spark);
            }
        }
    }

    public Fire createSparks(){
        return null;
    }

    public V getPosition() {
        return position;
    }

    public V getVelocity() {
        return velocity;
    }

    public int getColor() {
        return color;
    }

    public boolean isSpark() {
        return spark;
    }

    public boolean isRemove() {
        return remove;
    }
}
