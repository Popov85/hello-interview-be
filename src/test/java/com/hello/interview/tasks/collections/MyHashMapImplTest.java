package com.hello.interview.tasks.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapImplTest {

    private MyHashMap map;

    @BeforeEach
    void setUp() {
        map = new MyHashMapImpl();
    }

    @ParameterizedTest
    @CsvSource({
            "1, 10",
            "2, 20",
            "1009, 99",   // same bucket as key=0 if size=1009
            "0, 88",
            "-1, -100"
    })
    void testPutAndGet(int key, int value) {
        map.put(key, value);
        assertEquals(value, map.get(key), "Expected value for key " + key);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 10, 100",
            "2, 20, 200",
            "500, 55, 555"
    })
    void testOverwrite(int key, int initialValue, int updatedValue) {
        map.put(key, initialValue);
        assertEquals(initialValue, map.get(key));

        map.put(key, updatedValue);
        assertEquals(updatedValue, map.get(key));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 10",
            "2, 20",
            "3, 30"
    })
    void testRemove(int key, int value) {
        map.put(key, value);
        assertEquals(value, map.get(key));

        map.remove(key);
        assertEquals(-1, map.get(key), "After removal, key should return -1");
    }

    @ParameterizedTest
    @CsvSource({
            "999",
            "-500",
            "123456"
    })
    void testGetNonExistentKey(int key) {
        assertEquals(-1, map.get(key), "Non-existent key should return -1");
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 2, 2, 3, 3",
            "1009, 9, 2018, 18, 3027, 27", // all map to same bucket if size=1009
    })
    void testMultipleKeys(
            int k1, int v1,
            int k2, int v2,
            int k3, int v3
    ) {
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);

        assertEquals(v1, map.get(k1));
        assertEquals(v2, map.get(k2));
        assertEquals(v3, map.get(k3));
    }
}