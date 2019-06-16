package com.eleveator;

public class Main {
    public static void main(String[] args) throws Exception {
        Elevator elevator = new Elevator();
        elevator.call(3);
        elevator.call(5);
        elevator.call(2);
//        Thread.sleep(2000);
//        elevator.call(5);
//        Thread.sleep(2000);
//        elevator.call(7);
//        Thread.sleep(2000);
//        elevator.call(6);
//        Thread.sleep(2000);
//        elevator.call(2);

        for (int i = 0; i < 100; i++) {
            System.out.println(elevator.getCurrentStatus());
            Thread.sleep(2000);
        }
    }
}
