package org.hse.mainbuilder;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainInterfaceImpl implements MainInterface {
    List<Person> participants;
    File substrate;
    Sheet sheet;

    public MainInterfaceImpl() {
        this.participants = new ArrayList<>();
    }

    @Override
    public boolean loadSheet(File sheetFile) throws IOException {
        try {
            FileInputStream inputStream = new FileInputStream(sheetFile);
            Workbook workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                String lastName = row.getCell(0).toString();
                String firstName = row.getCell(1).toString();
                int place = (int) row.getCell(2).getNumericCellValue();
                participants.add(new Person(firstName, lastName, place));

            }
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean loadSubstrate(File substrate) {
        try {
            this.substrate = substrate;
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean editTemplate() {
        return false;
    }

    @Override
    public boolean makeDiplomas() {
        return false;
    }

    @Override
    public List<File> uploadDiplomas() {
        return null;
    }
}
