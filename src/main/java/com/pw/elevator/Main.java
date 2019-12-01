package com.pw.elevator;

public class Main {

    public static final int NUMBER_OF_ELEVATORS = 7;
    public static final int NUMBER_OF_FLOORS = 55;

    public static void main(String[] args) throws InterruptedException {
        RequestHandler requestHandler = new RequestHandler();
        ElevatorHandler elevatorHandler = new ElevatorHandler();
        requestHandler.setElevatorHandler(elevatorHandler);

        Display display = new Display(elevatorHandler, requestHandler);

        Thread requestHandlerTh = new Thread(requestHandler);
        requestHandlerTh.start();

        Thread elevatorHandlerTh = new Thread(elevatorHandler);
        elevatorHandlerTh.start();

        Thread displayTh = new Thread(display);
        displayTh.start();

        while (true) {

            int a = (int) (Math.random() * 55);
            int b;
            do {
                b = (int) (Math.random() * 55);
            } while (a == b);
           try {
               requestHandler.addRequest(a, b);
           } catch (IllegalArgumentException e) {
               System.out.println("Input data invalid (" + a + ", " + b + ")");
           }
            Thread.sleep(1000);


        }


    }

    public static boolean isInputDataOk(int currentFloor, int destinationFloor) {
        return currentFloor >= 0
                && currentFloor <= NUMBER_OF_FLOORS
                && destinationFloor >= 0
                && destinationFloor <= NUMBER_OF_FLOORS
                && currentFloor != destinationFloor;
    }

    public static Direction calculateDirection(int fromFloor, int toFloor) {
        if (fromFloor < toFloor) {
            return Direction.UP;
        } else if (fromFloor > toFloor) {
            return Direction.DOWN;
        } else {
            return Direction.STOP;
        }
    }

    public static Direction calculateDirection(Request request) {
        return calculateDirection(request.getFromFloor(), request.getToFloor());
    }
}
