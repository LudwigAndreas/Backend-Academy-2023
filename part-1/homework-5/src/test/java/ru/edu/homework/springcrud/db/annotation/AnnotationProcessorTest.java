package ru.edu.homework.springcrud.db.annotation;

import org.junit.jupiter.api.Test;
import ru.edu.homework.springcrud.exception.db.DataSourceException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationProcessorTest {

    @Test
    void isUnique_withUniqueField_shouldReturnTrue() {
        // Arrange
        TestObject testObject1 = new TestObject(1, "value1");
        TestObject testObject2 = new TestObject(2, "value2");
        Map<Integer, TestObject> map = new HashMap<>();
        map.put(1, testObject1);

        // Act
        boolean result = AnnotationProcessor.isUnique(testObject2, map);

        // Assert
        assertTrue(result);
    }

    @Test
    void isUnique_withNonUniqueField_shouldReturnFalse() {
        // Arrange
        TestObject testObject1 = new TestObject(1, "value");
        TestObject testObject2 = new TestObject(2, "value");
        Map<Integer, TestObject> map = new HashMap<>();
        map.put(1, testObject1);

        // Act
        boolean result = AnnotationProcessor.isUnique(testObject2, map);

        // Assert
        assertFalse(result);
    }

    @Test
    void getIdFieldValue_shouldReturnIdFieldValue() {
        // Arrange
        TestObject testObject = new TestObject(1, "value");

        // Act
        Integer result = AnnotationProcessor.getIdFieldValue(testObject);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void getIdFieldValue_withoutIdField_shouldThrowException() {
        // Arrange
        TestObjectWithoutIdField testObject = new TestObjectWithoutIdField(null);

        // Act & Assert
        assertThrows(DataSourceException.class, () -> AnnotationProcessor.getIdFieldValue(testObject));
    }

    private static class TestObject {
        @IdField
        private final Integer id;

        @UniqueField
        private final String value;

        public TestObject(Integer id, String value) {
            this.id = id;
            this.value = value;
        }
    }

    private static class TestObjectWithoutIdField {
        @UniqueField
        private final String value;

        public TestObjectWithoutIdField(String value) {
            this.value = value;
        }

    }
}
