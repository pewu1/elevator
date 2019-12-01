package com.pw.elevator;

import java.util.Objects;

public class Request {
    private int fromFloor;
    private int toFloor;
    private Direction direction;

    Request(int fromFloor, int toFloor) {
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
    }

    public Request() {
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public void setFromFloor(int fromFloor) {
        this.fromFloor = fromFloor;
    }

    public int getToFloor() {
        return toFloor;
    }

    public void setToFloor(int toFloor) {
        this.toFloor = toFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return  "Request from: " + fromFloor +
                ", to: " + toFloor +
                ", direction: " + direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return fromFloor == request.fromFloor &&
                toFloor == request.toFloor &&
                direction == request.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromFloor, toFloor, direction);
    }
}
