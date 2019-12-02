package com.pw.elevator.utils;

import com.pw.elevator.enums.Direction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ElevatorUtilTest {

    @Test
    void shouldReturnCorrectDirection() {
        int fromFloor = 0;
        int toFloor = 10;
        Direction result1 = ElevatorUtil.calculateDirection(fromFloor, toFloor);
        assertThat(result1).isEqualTo(Direction.UP);

        fromFloor = 10;
        toFloor = 0;
        Direction result2 = ElevatorUtil.calculateDirection(fromFloor, toFloor);
        assertThat(result2).isEqualTo(Direction.DOWN);

        fromFloor = 0;
        toFloor = 0;
        Direction result3 = ElevatorUtil.calculateDirection(fromFloor, toFloor);
        assertThat(result3).isEqualTo(Direction.STOP);
    }
}