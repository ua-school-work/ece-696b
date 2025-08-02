package edu.arizona.josesosa.structural.decorator.impl;

import edu.arizona.josesosa.structural.decorator.DataSource;
import edu.arizona.josesosa.structural.decorator.DataSourceDecorator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class CompressionDecorator extends DataSourceDecorator {
    private int compLevel = 6;

    public CompressionDecorator(DataSource source) {
        super(source);
    }

    public int getCompressionLevel() {
        return compLevel;
    }

    public void setCompressionLevel(int value) {
        compLevel = value;
    }

    @Override
    public void writeData(String data) {
        super.writeData(compress(data));
    }

    @Override
    public String readData() {
        return decompress(super.readData());
    }

    private String compress(String stringData) {
        byte[] data = stringData.getBytes();
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
            DeflaterOutputStream dos = new DeflaterOutputStream(bout, new Deflater(compLevel));
            dos.write(data);
            dos.close();
            bout.close();
            return Base64.getEncoder().encodeToString(bout.toByteArray());
        } catch (IOException ex) {
            return null;
        }
    }

    private String decompress(String stringData) {
        byte[] data = Base64.getDecoder().decode(stringData);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            InflaterInputStream iis = new InflaterInputStream(bis);
            ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
            
            byte[] buffer = new byte[1024];
            int len;
            while ((len = iis.read(buffer)) > 0) {
                bout.write(buffer, 0, len);
            }
            
            bout.close();
            iis.close();
            bis.close();
            
            return new String(bout.toByteArray());
        } catch (IOException ex) {
            return null;
        }
    }
}