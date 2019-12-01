package com.pw.elevator;

public class Elevator implements Runnable {

    private int currentFloor;
    private int destinationFloor;
    private int fromFloor;
    private int toFloor;
    private int elevatorNum;
    private Direction direction;

    Elevator(int elevatorNum, int currentFloor, int destinationFloor, Direction direction) {
        this.elevatorNum = elevatorNum;
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        this.direction = direction;
    }

    public int getElevatorNum() {
        return elevatorNum;
    }

    public void setElevatorNum(int elevatorNum) {
        this.elevatorNum = elevatorNum;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public void setFromFloor(int fromFloor) {
        this.fromFloor = fromFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    @Override
    public String toString() {
        return "Elevator " + elevatorNum + " [" +
                "current floor: " + currentFloor +
                " destination floor: " + destinationFloor +
                " direction: " + direction +
                ']';
    }

    public int getToFloor() {
        return toFloor;
    }

    public void setToFloor(int toFloor) {
        this.toFloor = toFloor;
    }

    @Override
    public void run() {

        if (currentFloor != fromFloor) {
            try {
                destinationFloor = fromFloor;
                move();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            destinationFloor = toFloor;
            move();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void move() throws InterruptedException {
        direction = Main.calculateDirection(currentFloor, destinationFloor);
        while (currentFloor != destinationFloor) {
            Thread.sleep(1000);

            if (direction == Direction.UP) {
                currentFloor++;
            } else if (direction == Direction.DOWN) {
                currentFloor--;
            }
        }
        direction = Direction.STOP;
    }
}

