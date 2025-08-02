package edu.arizona.josesosa.structural.decorator;

public interface DataSource {
    void writeData(String data);

    String readData();
}