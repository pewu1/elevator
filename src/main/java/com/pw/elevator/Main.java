package com.pw.elevator;

public class Main {

    public static int NUMBER_OF_ELEVATORS = 7;
    public static int NUMBER_OF_FLOORS = 55;

    public static void main(String[] args) {
        ElevatorHandler elevatorHandler = new ElevatorHandler();

        RequestHandler requestHandler = new RequestHandler();
        requestHandler.setElevatorHandler(elevatorHandler);

        Display display = new Display(elevatorHandler, requestHandler);

        ReqGenerator reqGenerator = new ReqGenerator(requestHandler);

        Thread requestHandlerTh = new Thread(requestHandler);
        requestHandlerTh.start();

        Thread elevatorHandlerTh = new Thread(elevatorHandler);
        elevatorHandlerTh.start();

        Thread displayTh = new Thread(display);
        displayTh.start();

        Thread reqGeneratorTh = new Thread(reqGenerator);
        reqGeneratorTh.start();
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
