package ru.edu.homework.springcrud.db.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.homework.springcrud.db.annotation.AnnotationProcessor;
import ru.edu.homework.springcrud.db.annotation.IdField;
import ru.edu.homework.springcrud.db.annotation.UniqueField;
import ru.edu.homework.springcrud.exception.db.DataSourceException;
import ru.edu.homework.springcrud.exception.db.DataSourceReadException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DataFileTest {

    @Mock
    private AnnotationProcessor annotationProcessor;

    @InjectMocks
    private DataFile<TestObject, Integer> dataFile = new DataFile<>("testFile.dat");

    @TempDir
    static Path tempDir;

    @Test
    void save_shouldWriteDataToFile() throws DataSourceReadException, IOException {
        // Arrange
        File tempFile = new File(tempDir.toFile(), "testFile.dat");
        dataFile.setFile(tempFile);

        Map<Integer, TestObject> data = new HashMap<>();
        TestObject testObject = new TestObject(1, "value");
        data.put(1, testObject);

        // Act
        dataFile.save(data);

        // Assert
        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);
    }

    @Test
    void load_shouldReadDataFromFile() throws DataSourceReadException, IOException {
        // Arrange
        File tempFile = new File(tempDir.toFile(), "testFile.dat");
        dataFile.setFile(tempFile);

        Map<Integer, TestObject> expectedData = new HashMap<>();
        TestObject testObject = new TestObject(1, "value");
        expectedData.put(1, testObject);

        dataFile.save(expectedData);

        // Act
        Map<Integer, TestObject> loadedData = dataFile.load();

        // Assert
        assertEquals(expectedData, loadedData);
    }

    @Test
    void load_emptyFile_shouldReturnEmptyMap() throws DataSourceReadException, IOException {

        File tempFile = new File(tempDir.toFile(), "testFile.dat");
        PrintWriter writer = new PrintWriter(tempFile);
        writer.print("");
        writer.close();

        dataFile.setFile(tempFile);

        Map<Integer, TestObject> loadedData = dataFile.load();

        assertTrue(loadedData.isEmpty());
    }

    @Test
    void load_invalidDataInFile_shouldThrowDataSourceReadException() throws IOException {
        // Arrange
        File tempFile = new File(tempDir.toFile(), "testFile.dat");
        dataFile.setFile(tempFile);

        // Create a file with invalid data
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("InvalidData");
        }

        // Act & Assert
        assertThrows(DataSourceException.class, () -> dataFile.load());
    }

    private static class TestObject implements Serializable {
        @IdField
        private final Integer id;

        @UniqueField
        private final String value;

        public TestObject(Integer id, String value) {
            this.id = id;
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TestObject) {
                TestObject other = (TestObject) obj;
                return id.equals(other.id) && value.equals(other.value);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return id.hashCode() + value.hashCode();
        }
    }
}
