package com.pw.elevator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestHandlerTest {

    @Test
    void shouldReturnCorrectDirection() {
        int fromFloor = 0;
        int toFloor = 10;
        Direction result1 = Main.calculateDirection(fromFloor, toFloor);
        assertThat(result1).isEqualTo(Direction.UP);

        fromFloor = 10;
        toFloor = 0;
        Direction result2 = Main.calculateDirection(fromFloor, toFloor);
        assertThat(result2).isEqualTo(Direction.DOWN);

    }

    @Test
    void shouldThrowExceptionWhenInputIsInvalid() {
        final int fromFloor = 0;
        final int toFloor = Main.NUMBER_OF_FLOORS + 10;
        RequestHandler requestHandler = new RequestHandler();
        assertThrows(IllegalArgumentException.class, () -> {requestHandler.addRequest(fromFloor, toFloor);
    });
    }

}