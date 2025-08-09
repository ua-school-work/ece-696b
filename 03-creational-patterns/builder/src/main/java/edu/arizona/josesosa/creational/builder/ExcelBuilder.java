package edu.arizona.josesosa.creational.builder;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Builder for creating Excel files from Contact data using Apache POI.
 */
public class ExcelBuilder implements ContactBuilder {
    private String name;
    private String email;
    private String phone;
    private Workbook workbook;
    private Sheet sheet;
    private boolean built = false;
    
    /**
     * Creates a new Excel builder.
     */
    public ExcelBuilder() {
        workbook = new XSSFWorkbook(); // XLSX format
        sheet = workbook.createSheet("Contacts");
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        
        Cell nameHeader = headerRow.createCell(0);
        nameHeader.setCellValue("Name");
        nameHeader.setCellStyle(headerStyle);
        
        Cell emailHeader = headerRow.createCell(1);
        emailHeader.setCellValue("Email");
        emailHeader.setCellStyle(headerStyle);
        
        Cell phoneHeader = headerRow.createCell(2);
        phoneHeader.setCellValue("Phone");
        phoneHeader.setCellStyle(headerStyle);
    }
    
    @Override
    public void addName(String name) {
        this.name = name;
    }
    
    @Override
    public void addEmail(String email) {
        this.email = email;
    }
    
    @Override
    public void addPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public ContactBuilder build() {
        if (!built) {
            // Create data row
            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue(name != null ? name : "");
            dataRow.createCell(1).setCellValue(email != null ? email : "");
            dataRow.createCell(2).setCellValue(phone != null ? phone : "");
            
            // Auto-size columns
            for (int i = 0; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }
            
            built = true;
        }
        return this;
    }
    
    @Override
    public void save(String filePath) throws IOException {
        if (!built) {
            build();
        }
        
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
    }
    
    @Override
    public String getContent() {
        if (!built) {
            build();
        }
        return "Excel workbook with contact data";
    }
    
    /**
     * Adds a new contact to the Excel sheet.
     * 
     * @param name the contact's name
     * @param email the contact's email
     * @param phone the contact's phone
     * @return this builder for method chaining
     */
    public ExcelBuilder addContact(String name, String email, String phone) {
        int lastRowNum = sheet.getLastRowNum();
        Row dataRow = sheet.createRow(lastRowNum + 1);
        dataRow.createCell(0).setCellValue(name != null ? name : "");
        dataRow.createCell(1).setCellValue(email != null ? email : "");
        dataRow.createCell(2).setCellValue(phone != null ? phone : "");
        return this;
    }
    
    /**
     * Closes the workbook and releases resources.
     * 
     * @throws IOException if an I/O error occurs
     */
    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
}