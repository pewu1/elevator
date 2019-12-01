package com.pw.elevator;

public class ReqGenerator implements Runnable {

    private RequestHandler requestHandler;

    public ReqGenerator(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            int a = (int) (Math.random() * Main.NUMBER_OF_FLOORS);
            int b;
            do {
                b = (int) (Math.random() * Main.NUMBER_OF_FLOORS);
            } while (a == b);
            try {
                requestHandler.addRequest(a, b);
            } catch (IllegalArgumentException e) {
                System.out.println("Input data invalid (" + a + ", " + b + ")");
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
