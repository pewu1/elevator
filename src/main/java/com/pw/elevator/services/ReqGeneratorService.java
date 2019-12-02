package com.pw.elevator.services;

import com.pw.elevator.utils.ElevatorUtil;

public class ReqGeneratorService implements Runnable {

    private RequestHandler requestHandler;

    public ReqGeneratorService(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    private int getRandomFloor(){
        return (int) (Math.random() * ElevatorUtil.NUMBER_OF_FLOORS);
    }

    private void addRequest(int fromFloor, int toFloor) {
        try {
            requestHandler.addRequest(fromFloor, toFloor);
        } catch (IllegalArgumentException e) {
            System.out.println("Input data invalid (" + fromFloor + ", " + toFloor + ")");
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {


            if (ElevatorUtil.ELEVATOR_GOES_DOWN_TO_ZERO_FLOOR_ONLY) {
                addRequest(0, getRandomFloor());
                pause();
                addRequest(getRandomFloor(), 0);
            } else {
                addRequest(getRandomFloor(), getRandomFloor());
            }
            pause();
        }
    }

    private void pause() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
