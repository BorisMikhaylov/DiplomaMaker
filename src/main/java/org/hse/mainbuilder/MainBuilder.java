package org.hse.mainbuilder;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainBuilder {

    private void parseSheets() {
        for (Row row : sheet) {
            String lastName = row.getCell(0).toString();
            String firstName = row.getCell(1).toString();
            int place = (int) row.getCell(2).getNumericCellValue();
            participants.add(new Person(firstName, lastName, place));
        }
    }

    public void createPdfs() throws IOException, DocumentException {
        for (Person person : participants) {
            createDiploma(person);
        }
    }

    private void createDiploma(Person person) throws IOException, DocumentException {
        Document document = new Document();
        FileOutputStream outputStream = new FileOutputStream(new File(""));
        PdfWriter.getInstance(document, outputStream);
        document.open();
        document.add(new Paragraph("Congrats " + person.firstName + " " + person.lastName + "on " + person.position + " place!"));
        document.close();
        outputStream.close();
    }

    public MainBuilder(String sheetLocation) throws IOException {
        FileInputStream file = new FileInputStream(new File(sheetLocation));
        Workbook workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(0);
        parseSheets();
    }

    List<Person> participants = new ArrayList<>();
    Sheet sheet;
}

class Person {
    String firstName;
    String lastName;
    int position;

    public Person(String firstName, String lastName, int position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
    }
}
