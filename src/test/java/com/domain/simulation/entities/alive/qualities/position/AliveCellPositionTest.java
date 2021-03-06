package com.domain.simulation.entities.alive.qualities.position;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AliveCellPositionTest {

    private final Position pos = new PositionEntity(3, 3);

    @Test
    void getX() {

        assertEquals(3, pos.x());
    }

    @Test
    void getY() {

        assertEquals(3, pos.y());
    }

    @Test
    void testHashCodeThenEquals() {

        var positions = Stream.of(
                new PositionEntity(1, 1),
                new PositionEntity(1, 1),
                new PositionEntity(1, 2),
                new PositionEntity(2, 1),
                new PositionEntity(2, 1),
                new PositionEntity(-1, 1),
                new PositionEntity(-2, 1),
                new PositionEntity(-2, 1),
                new PositionEntity(-1, -1),
                new PositionEntity(1, -1)
        ).collect(Collectors.toCollection(HashSet::new));

        assertEquals(7, positions.size());
    }
}