package com.pw.elevator.services;

import com.pw.elevator.enums.Direction;
import com.pw.elevator.pojos.Elevator;
import com.pw.elevator.pojos.Request;
import com.pw.elevator.utils.ElevatorUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class ElevatorHandler implements Runnable {

    private List<Elevator> elevators = new ArrayList<>();
    private List<Request> requestQueue = new CopyOnWriteArrayList<>();

    public ElevatorHandler() {
        createElevators();
    }

    private void createElevators() {
        IntStream.range(0, ElevatorUtil.NUMBER_OF_ELEVATORS)
                .forEach(index -> elevators.add(index, new Elevator(index + 1, 0, 0, Direction.STOP)));
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
        Optional<Elevator> elevator = getNearestElevator(request);
        if (elevator.isPresent()) {
            moveElevator(elevator.get(), request);
            requestQueue.remove(request);
        }
    }

    public List<Request> getRequestQueue() {
        return requestQueue;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            while (!requestQueue.isEmpty()) {
                requestQueue.stream().forEach(request -> callElevator(request));
            }
        }

    }
}