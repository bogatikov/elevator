package com.eleveator;

import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

enum Status {
    UP,
    BOTTOM,
    WAIT
}
public class Elevator extends Observable {

    private static final int FLOORS = 7;
    private int currentFloor = 7;
    private int speed = 10; // seconds per floor
    private int delay = 2; //
    private Status status = Status.WAIT;
    private Queue<Integer> queue;



    public Elevator() {
        queue = new ConcurrentLinkedQueue<> ();
    }

    // Call the elevator
    public void call(int floor) throws Exception {
        if (floor > FLOORS || floor < 0) throw new Exception("Wrong way");
        if (currentFloor == floor) return;
        queue.add(floor);
    }

    void move() {
        long start;
        while (queue.size() > 0) {

            String mes = "";
            for (Integer i:
                 queue) {
                mes += " " + i;
            }
            setChanged();
            notifyObservers("Queue: " + mes);


            int currentGoal = queue.poll();

            while (currentFloor != currentGoal) {
                if (currentGoal > currentFloor) status = Status.UP;
                else status = Status.BOTTOM;

                start = System.currentTimeMillis();

                setChanged();
                notifyObservers("Current floor: " + currentFloor +  " Current goal: "+ currentGoal + " Moving " + status.name());

                while (System.currentTimeMillis() - start < 10000) {
                    // Elevator moves
                }
                currentFloor = (status == Status.BOTTOM) ? --currentFloor : ++currentFloor;


                if (queue.contains(currentFloor)) {
                    queue.remove(currentFloor);
                    stopOnTheFloor(currentFloor);
                }
            }

            stopOnTheFloor(currentFloor);
        }
        status = Status.WAIT;
        notifyObservers(status.name());
    }

    void stopOnTheFloor(int floor){
        long start = System.currentTimeMillis();
        while ((System.currentTimeMillis() - start < 4000)) {
            if (System.currentTimeMillis() - start < 2000) {
                // stop
            }
        }
        setChanged();
        notifyObservers("Stop at " + floor);
    }

}

