package com.pw.elevator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestHandlerTest {

    @Test
    void shouldThrowExceptionWhenInputIsInvalid() {
        final int fromFloor = 0;
        final int toFloor = Main.NUMBER_OF_FLOORS + 10;
        RequestHandler requestHandler = new RequestHandler();
        assertThrows(IllegalArgumentException.class, () -> {requestHandler.addRequest(fromFloor, toFloor);
    });
    }

    @Test
    void shouldReturnCorrectlyProcessedRequest() {
        ElevatorHandler elevatorHandler = new ElevatorHandler();

        RequestHandler requestHandler = new RequestHandler();
        requestHandler.setElevatorHandler(elevatorHandler);
        requestHandler.addRequest(0, 20);
        requestHandler.addRequest(15, 30);

        Request expected = new Request();
        expected.setFromFloor(0);
        expected.setToFloor(30);
        expected.setDirection(Direction.UP);

        assertThat(requestHandler.processRequests()).isEqualTo(expected);

        requestHandler.addRequest(0, 20);
        requestHandler.addRequest(15,0);

        expected.setFromFloor(0);
        expected.setToFloor(20);
        expected.setDirection(Direction.UP);

        assertThat(requestHandler.processRequests()).isEqualTo(expected);

        expected.setFromFloor(15);
        expected.setToFloor(0);
        expected.setDirection(Direction.DOWN);

        assertThat(requestHandler.processRequests()).isEqualTo(expected);

        requestHandler.addRequest(20, 0);
        requestHandler.addRequest(15, 5);
        requestHandler.addRequest(0, 1);

        expected.setFromFloor(0);
        expected.setToFloor(1);
        expected.setDirection(Direction.UP);

        assertThat(requestHandler.processRequests()).isEqualTo(expected);

        expected.setFromFloor(15);
        expected.setToFloor(5);
        expected.setDirection(Direction.DOWN);

        assertThat(requestHandler.processRequests()).isEqualTo(expected);
    }

}