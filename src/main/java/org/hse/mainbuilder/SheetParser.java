package org.hse.mainbuilder;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

public class SheetParser {
    public static List<Person> parse(Sheet sheet) {
        List<Person> participants = new ArrayList<>();
        for (Row row : sheet) {
            String lastName = row.getCell(0).toString();
            String firstName = row.getCell(1).toString();
            int place = (int) row.getCell(2).getNumericCellValue();
            participants.add(new Person(firstName, lastName, place));
        }
        return participants;
    }
}
