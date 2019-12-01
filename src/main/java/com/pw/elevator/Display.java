package com.pw.elevator;

public class Display implements Runnable {

    private ElevatorHandler elevatorHandler;
    private RequestHandler requestHandler;

    public Display(ElevatorHandler elevatorHandler, RequestHandler requestHandler) {
        this.elevatorHandler = elevatorHandler;
        this.requestHandler = requestHandler;
    }

    private void print(){
        System.out.flush();
        elevatorHandler.getElevators().stream().forEach(System.out::println);
        requestHandler.getRequests().stream().forEach(System.out::println);

    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            print();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
