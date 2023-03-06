package org.hse.mainbuilder;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainBuilder {

    public void parseSheets(){
        for (Row row: sheet){
            String lastName = row.getCell(0).toString();
            String firstName = row.getCell(1).toString();
            int place = (int)row.getCell(2).getNumericCellValue();
            participants.add(new Person(firstName, lastName, place));
        }
    }

    public MainBuilder(String sheetLocation) throws IOException {
        FileInputStream file = new FileInputStream(new File(sheetLocation));
        Workbook workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(0);
    }

    List<Person> participants = new ArrayList<>();
    Sheet sheet;
}

class Person{
    String firstName;
    String lastName;
    int position;

    public Person(String firstName, String lastName, int position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
    }
}
