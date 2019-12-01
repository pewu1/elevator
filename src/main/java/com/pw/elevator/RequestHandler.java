package com.pw.elevator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestHandler implements Runnable {

    private Set<Request> requests = new HashSet<>();
    private ElevatorHandler elevatorHandler;

    public RequestHandler() {
    }

    public void setElevatorHandler(ElevatorHandler elevatorHandler) {
        this.elevatorHandler = elevatorHandler;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void addRequest(final int fromFloor, final int toFloor) throws IllegalArgumentException {

        if (!Main.isInputDataOk(fromFloor, toFloor)) {
            throw new IllegalArgumentException();
        }

        Request request = new Request(fromFloor, toFloor);
        request.setDirection(Main.calculateDirection(fromFloor, toFloor));
        requests.add(request);
        System.out.println("New request: " + request.toString());
    }

    private Request processRequests() {
        Set<Request> processedRequests = new HashSet<>();
        Request processedRequest = new Request();
        Optional<Request> firstRequest = requests.stream().min(Comparator.comparingInt(Request::getFromFloor));
        if (firstRequest.isPresent()) {
            processedRequest.setFromFloor(firstRequest.get().getFromFloor());
            if (firstRequest.get().getDirection() == Direction.UP) {
                processedRequests.addAll(requests.stream().filter(request1 -> request1.getFromFloor() >= firstRequest.get().getFromFloor() && request1.getDirection() == Direction.UP).collect(Collectors.toSet()));
                processedRequest.setToFloor(processedRequests.stream().max(Comparator.comparingInt(Request::getToFloor)).get().getToFloor());
            } else {
                processedRequests.addAll(requests.stream().filter(request1 -> request1.getFromFloor() <= firstRequest.get().getFromFloor() && request1.getDirection() == Direction.DOWN).collect(Collectors.toSet()));
                processedRequest.setToFloor(processedRequests.stream().min(Comparator.comparingInt(Request::getToFloor)).get().getToFloor());
            }
        }
        processedRequest.setDirection(Main.calculateDirection(processedRequest.getFromFloor(), processedRequest.getToFloor()));
        requests.removeAll(processedRequests);
        return processedRequest;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            while (!requests.isEmpty()) {
                elevatorHandler.getRequestQueue().add(processRequests());
            }


        }

    }
}
