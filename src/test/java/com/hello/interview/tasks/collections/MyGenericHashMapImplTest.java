package com.hello.interview.tasks.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MyGenericHashMapImplTest {

    private MyGenericHashMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new MyGenericHashMapImpl<>();
    }

    @ParameterizedTest
    @DisplayName("Put and get simple key-value pairs")
    @CsvSource({
            "apple, 1",
            "banana, 2",
            "grape, 3",
            "orange, 4"
    })
    void testPutAndGet(String key, int value) {
        map.put(key, value);
        assertEquals(value, map.get(key));
    }

    @ParameterizedTest
    @DisplayName("Overwrite existing key")
    @CsvSource({
            "a, 10, 100",
            "b, 20, 200",
            "c, 30, 300"
    })
    void testOverwrite(String key, int initialValue, int updatedValue) {
        map.put(key, initialValue);
        assertEquals(initialValue, map.get(key));

        map.put(key, updatedValue);
        assertEquals(updatedValue, map.get(key));
    }

    @ParameterizedTest
    @DisplayName("Remove existing key")
    @CsvSource({
            "x, 42",
            "y, 99",
            "z, 123"
    })
    void testRemoveExistingKey(String key, int value) {
        map.put(key, value);
        assertEquals(value, map.get(key));

        map.remove(key);
        assertNull(map.get(key));
    }

    @ParameterizedTest
    @DisplayName("Remove nonexistent key")
    @CsvSource({
            "missing1",
            "missing2",
            "ghost"
    })
    void testRemoveNonexistentKey(String key) {
        map.remove(key); // should not throw
        assertNull(map.get(key));
    }

    @ParameterizedTest
    @DisplayName("Get key that was never added")
    @CsvSource({
            "unknown",
            "absent",
            "neverThere"
    })
    void testGetNonexistentKey(String key) {
        assertNull(map.get(key));
    }
}