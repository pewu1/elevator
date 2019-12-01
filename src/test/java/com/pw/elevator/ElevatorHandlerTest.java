package com.pw.elevator;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ElevatorHandlerTest {

    @Test
    void shouldCreateCorrectNumberOfElevators() {
        ElevatorHandler elevatorHandler = new ElevatorHandler();

        assertThat(elevatorHandler.getElevators().size()).isEqualTo(Main.NUMBER_OF_ELEVATORS);
    }

    @Test
    void shouldReturnNearestElevator() {
        ElevatorHandler elevatorHandler = new ElevatorHandler();
        Request request = new Request();
        Main.NUMBER_OF_ELEVATORS = 4;
        Main.NUMBER_OF_FLOORS = 50;
        request.setFromFloor(5);
        request.setToFloor(15);
        request.setDirection(Direction.UP);

        elevatorHandler.getElevators().get(0).setCurrentFloor(40);
        elevatorHandler.getElevators().get(0).setDestinationFloor(40);
        elevatorHandler.getElevators().get(0).setDirection(Direction.STOP);

        elevatorHandler.getElevators().get(1).setCurrentFloor(30);
        elevatorHandler.getElevators().get(1).setDestinationFloor(40);
        elevatorHandler.getElevators().get(1).setDirection(Direction.UP);

        elevatorHandler.getElevators().get(2).setCurrentFloor(4);
        elevatorHandler.getElevators().get(2).setDestinationFloor(20);
        elevatorHandler.getElevators().get(2).setDirection(Direction.UP);

        elevatorHandler.getElevators().get(3).setCurrentFloor(6);
        elevatorHandler.getElevators().get(3).setDestinationFloor(0);
        elevatorHandler.getElevators().get(3).setDirection(Direction.DOWN);

        Optional<Elevator> result = elevatorHandler.getNearestElevator(request);
        assertThat(result.isPresent()).isEqualTo(true);

        if (result.isPresent()) {
            assertThat(elevatorHandler.getNearestElevator(request).get()).isEqualTo(elevatorHandler.getElevators().get(2));
        }

        request.setFromFloor(39);
        request.setToFloor(40);
        request.setDirection(Direction.UP);

        result = elevatorHandler.getNearestElevator(request);
        assertThat(result.isPresent()).isEqualTo(true);

        if (result.isPresent()) {
            assertThat(elevatorHandler.getNearestElevator(request).get()).isEqualTo(elevatorHandler.getElevators().get(0));
        }
    }

    @Test
    void shouldMoveElevatorAsRequested() {
        ElevatorHandler elevatorHandler = new ElevatorHandler();
        Request request = new Request();
        Main.NUMBER_OF_ELEVATORS = 4;
        Main.NUMBER_OF_FLOORS = 50;
        request.setFromFloor(5);
        request.setToFloor(15);
        request.setDirection(Direction.UP);

        elevatorHandler.getElevators().get(0).setCurrentFloor(2);
        elevatorHandler.getElevators().get(0).setDestinationFloor(2);
        elevatorHandler.getElevators().get(0).setDirection(Direction.STOP);

        elevatorHandler.moveElevator(elevatorHandler.getElevators().get(0), request);

        Thread[] threads = new Thread[Thread.activeCount()];
        Thread.enumerate(threads);
        Optional<Thread> elevatorTh = Stream.of(threads).filter(thread -> thread.getName().contains("1")).findAny();
        if (elevatorTh.isPresent()) {
            while (elevatorTh.get().isAlive()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        assertThat(elevatorHandler.getElevators().get(0).getCurrentFloor()).isEqualTo(15);
    }
}