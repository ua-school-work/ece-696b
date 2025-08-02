package edu.arizona.josesosa.structural.decorator;

import edu.arizona.josesosa.structural.decorator.impl.CompressionDecorator;
import edu.arizona.josesosa.structural.decorator.impl.EncryptionDecorator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecoratorTest {

    String originalRecords = "Name,Salary\nJeff Bezos,100000\nElon Musk,912000";
    String encodedRecords = "Zkt7e1Q5eU8yUm1Qe0ZsdHJ2VXpUbDJNVjRDTHNkcHcya0YxQkJGdjI2ezlRQllnMXZLdElWdUVKN0JCQkdxakVjOT4=";

    @Test
    void encoded() {
        FakeFileDataSource file = new FakeFileDataSource("Ignored.txt");
        DataSourceDecorator encoded = new CompressionDecorator(
                new EncryptionDecorator(file));
        encoded.writeData(originalRecords);

        assertEquals(encodedRecords, file.readData());
    }

    @Test
    void decode() {
        FakeFileDataSource file = new FakeFileDataSource("Ignored.txt");
        file.writeData(encodedRecords);
        DataSourceDecorator encoded = new CompressionDecorator(
                new EncryptionDecorator(file));
        encoded.writeData(originalRecords);

        assertEquals(originalRecords, encoded.readData());
    }

}
