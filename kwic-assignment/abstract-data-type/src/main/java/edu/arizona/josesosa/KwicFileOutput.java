package edu.arizona.josesosa;

public class KwicFileOutput extends Output{
    public KwicFileOutput(String filename) throws Exception {
        super(filename);
    }

    @Override
    protected void writeLines(java.util.List<String> lines) {
        for (String line : lines) {
            out.println(line);
        }
    }

    @Override
    public void close() {
        out.close();
    }

}
