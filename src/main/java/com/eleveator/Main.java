package com.eleveator;

public class Main {
    public static void main(String[] args) throws Exception {
        Elevator elevator = new Elevator();
        elevator.addObserver((o, arg) -> System.out.println((String) arg));
        elevator.call(7);
        elevator.call(6);
        elevator.call(4);
        elevator.move();


        elevator.call(7);
        elevator.call(4);
        elevator.call(3);
        elevator.call(6);
        elevator.move();
    }
}
