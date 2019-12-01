package com.pw.elevator;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Math.abs;

public class ElevatorHandler implements Runnable {

    private volatile List<Elevator> elevators = new ArrayList<>();
    private List<Request> requestQueue = new CopyOnWriteArrayList<>();

    ElevatorHandler() {
        createElevators();
    }

    private void createElevators() {
        for (int i = 0; i < Main.NUMBER_OF_ELEVATORS; i++) {
            elevators.add(i, new Elevator(i + 1, 0, 0, Direction.STOP));
        }
    }

    protected int calculateAvgCurrentFloor() {
        return elevators.stream()
                .mapToInt(Elevator::getCurrentFloor)
                .reduce(Integer::sum)
                .getAsInt() / elevators.size();
    }

    protected Optional<Elevator> getNearestElevator(final Request request) {
        Optional<Elevator> nearestElev;
        nearestElev = elevators.stream()
                .filter(elevator -> elevator.getDirection() == request.getDirection() || elevator.getDirection() == Direction.STOP).min(Comparator.comparingInt(elevator -> abs(elevator.getCurrentFloor() - request.getFromFloor())));
        return nearestElev;
    }

    protected void moveElevator(final Elevator elevator, final Request request) {
        if (elevator.getDirection() == Direction.STOP) {
            elevator.setFromFloor(request.getFromFloor());
            elevator.setToFloor(request.getToFloor());
            Thread elevatorTh = new Thread(elevator);
            elevatorTh.setName(String.valueOf(elevator.getElevatorNum()));
            elevatorTh.start();
        } else if ((elevator.getDirection() == Direction.UP && elevator.getToFloor() < request.getToFloor())
                || (elevator.getDirection() == Direction.DOWN && elevator.getToFloor() > request.getToFloor())) {
            elevator.setToFloor(request.getToFloor());
        }
    }

    private void callElevator(final Request request) {
        if (getNearestElevator(request).isPresent()) {
            Elevator elevator = getNearestElevator(request).get();
            moveElevator(elevator, request);
            requestQueue.remove(request);
        }
    }

    public synchronized List<Request> getRequestQueue() {
        return requestQueue;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            while (!requestQueue.isEmpty()) {
                callElevator(requestQueue.get(0));
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}