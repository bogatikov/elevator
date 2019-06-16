package com.eleveator;

import java.util.ArrayList;
import java.util.List;

enum Status {
    UP,
    DOWN,
    WAIT
}
public class Elevator {

    private static final int FLOORS = 7;
    private int startFloor = 7;
    private Status status = Status.WAIT;
    private List<Dir> queue;


    public Elevator() {
        queue = new ArrayList<>();
    }

    // Call the elevator
    public void call(int floor) throws Exception {
        if (floor > FLOORS || floor < 0) throw new Exception("Wrong way");
        queue.add(new Dir(floor));
    }


    private boolean queueContain(int floor) {
        for (Dir d:
             queue) {
            if (d.moveTo == floor) {
                return true;
            }
        }
        return false;
    }
    String getCurrentStatus () {
        long now = System.currentTimeMillis();
        long time = queue.get(0).start;
        int currentFloor = startFloor;
        int currentGoal = queue.get(0).moveTo;
        for (Dir d:
             queue) {
            d.applyed = false;
        }

        while (hasNotApplyed()){
            status = (currentFloor > currentGoal) ? Status.DOWN : Status.UP;
            if (status == Status.UP) {
                for (int k = 0; k < queue.size(); k++) {
                    Dir d = queue.get(k);
                    if (d.moveTo > currentGoal && d.start - time <= (Math.abs(currentFloor-currentGoal) * 10000)) {
                        d.applyed = true;
                        currentGoal = d.moveTo;
                    }
                }


                for (int k = currentFloor; k <= currentGoal; k++) {

                    if (now - time >= 10000) {
                        if (k == currentGoal) {
                            if (now - time <= 12000)
                                return "Open door on " + currentFloor;
                            else if (now - time <= 14000)
                                return "Close door on " + currentFloor;
                            time += 4000;
                        }
                        time += 10000;
                        currentFloor++;
                    }  else {
                        return "Moving on " + currentFloor + " to " + currentGoal;
                    }
                }
            }
            if (status == Status.DOWN) {
                for (int k = 0; k < queue.size(); k++) {
                    Dir d = queue.get(k);
                    if (d.moveTo < currentGoal && d.start - time <= (Math.abs(currentFloor-currentGoal) * 10000)) {
                        d.applyed = true;
                        currentGoal = d.moveTo;
                    }
                }


                for (int k = currentFloor; k >= currentGoal; k--) {

                    if (now - time >= 10000) {
                        if (queueContain(currentFloor)) {
                            if (now - time <= 12000)
                                return "Open door on " + currentFloor;
                            else if (now - time <= 14000)
                                return "Close door on " + currentFloor;
                            time += 4000;
                        }
                        time += 10000;
                        currentFloor--;
                    }  else {
                        return "Moving on " + currentFloor + " to " + currentGoal;
                    }
                }
            }
        }

        return "Nothing to do on " + currentFloor;
    }

    private boolean hasNotApplyed() {
        boolean res = false;
        for (Dir dir:
             queue) {
            if (!dir.applyed)
                return true;
        }
        return res;
    }

        /*    String getCurrentStatus() {
            long now = System.currentTimeMillis();
            if (queue.size() == 0) {
                return "Waiting command";
            }
            int currentFloor = startFloor;
            long time;
            time = queue.get(0).start;

            for (int k = 0; k < queue.size(); k++) {
                Dir d = queue.get(k);
                if (status == Status.UP) {
                    for (int j = k; j < queue.size(); j++) {
                        if (queue.get(j).moveTo > d.moveTo) {
                            d = queue.get(j);
                        }
                    }
                }
                if (status == Status.DOWN) {
                    for (int j = k; j < queue.size(); j++) {
                        if (queue.get(j).moveTo < d.moveTo) {
                            d = queue.get(j);
                        }
                    }
                }
                status = d.moveTo > currentFloor ? Status.UP : Status.DOWN;
                for (int i = 0; i < Math.abs(d.moveTo - currentFloor); i++) {

                    long dt = now - time;
                    if (dt >= 10000) {
                        if (queueContain(currentFloor) && status == Status.DOWN) {
                            if (dt <= 12000) {
                                return "Open door on " + currentFloor;
                            } else if (dt <= 14000) {
                                return "Close door on " + currentFloor;
                            }
                            time += 4000;
                        }
                        time += 10000;
                        currentFloor = (status == Status.DOWN) ? --currentFloor : ++currentFloor;
                    } else {
                        return "Moving " + status.name() + " on " + currentFloor;
                    }
                }
            }

            return "Nothing to do";
        }*/



    class Dir {
        long start;
        int moveTo;
        boolean applyed;

        public Dir(int moveTo) {
            this.start = System.currentTimeMillis();
            this.moveTo = moveTo;
        }
    }
}

