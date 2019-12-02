package com.pw.elevator.utils;

import com.pw.elevator.enums.Direction;
import com.pw.elevator.pojos.Request;

public class ElevatorUtil {

    public static int NUMBER_OF_ELEVATORS = 7;
    public static int NUMBER_OF_FLOORS = 55;
    public static boolean ELEVATOR_GOES_DOWN_TO_ZERO_FLOOR_ONLY = true;

    /**
     * private constructor to prevent initalization
     */
    private ElevatorUtil() {
    }

    public static boolean isInputDataValid(int currentFloor, int destinationFloor) {
        return currentFloor >= 0
                && currentFloor <= NUMBER_OF_FLOORS
                && destinationFloor >= 0
                && destinationFloor <= NUMBER_OF_FLOORS
                && currentFloor != destinationFloor;
    }

    public static Direction calculateDirection(int fromFloor, int toFloor) {
        if (fromFloor < toFloor) {
            return Direction.UP;
        }
        if (fromFloor > toFloor) {
            return Direction.DOWN;
        }

        return Direction.STOP;
    }

    public static Direction calculateDirection(Request request) {
        return calculateDirection(request.getFromFloor(), request.getToFloor());
    }

}
