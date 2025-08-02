package edu.arizona.josesosa.structural.decorator;

public class FakeFileDataSource implements DataSource {
    @SuppressWarnings("unused")
    private String name;
    private String data;

    public FakeFileDataSource(String name) {
        this.name = name;
    }

    @Override
    public void writeData(String data) {
        this.data = data;
    }

    @Override
    public String readData() {
        return data;
    }
}