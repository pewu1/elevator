package com.pw.elevator;

import com.pw.elevator.services.DisplayService;
import com.pw.elevator.services.ElevatorHandler;
import com.pw.elevator.services.ReqGeneratorService;
import com.pw.elevator.services.RequestHandler;

public class Main {

    public static void main(String[] args) {
        ElevatorHandler elevatorHandler = new ElevatorHandler();

        RequestHandler requestHandler = new RequestHandler();
        requestHandler.setElevatorHandler(elevatorHandler);

        DisplayService displayService = new DisplayService(elevatorHandler, requestHandler);

        ReqGeneratorService reqGeneratorService = new ReqGeneratorService(requestHandler);

        Thread requestHandlerTh = new Thread(requestHandler);
        requestHandlerTh.start();

        Thread elevatorHandlerTh = new Thread(elevatorHandler);
        elevatorHandlerTh.start();

        Thread displayTh = new Thread(displayService);
        displayTh.start();

        Thread reqGeneratorTh = new Thread(reqGeneratorService);
        reqGeneratorTh.start();
    }
}
