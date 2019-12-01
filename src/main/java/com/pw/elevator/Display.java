package com.pw.elevator;

import java.io.IOException;

public class Display implements Runnable {

    private ElevatorHandler elevatorHandler;
    private RequestHandler requestHandler;

    public Display(ElevatorHandler elevatorHandler, RequestHandler requestHandler) {
        this.elevatorHandler = elevatorHandler;
        this.requestHandler = requestHandler;
    }

    private void print(){
        clearScreen();
        elevatorHandler.getElevators().stream().forEach(System.out::println);
        System.out.println();
        System.out.println("Pending requests: ");
        elevatorHandler.getRequestQueue().stream().forEach(System.out::println);

    }

    private void clearScreen() {
        if (System.getProperty("os.name").contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                Runtime.getRuntime().exec("clear");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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