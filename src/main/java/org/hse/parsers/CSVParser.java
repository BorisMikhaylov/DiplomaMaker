package org.hse.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser implements Parser {
  private static File file;

  public CSVParser(File file) {
    CSVParser.file = file;
  }
//  public static void main(String[] args) {
//    String filePath = "/home/maksimk/IdeaProjects/project/src/Hello.csv"; // Путь к файлу CSV
//
//    List<Diploma> diplomas = parseCSV(filePath);
//
//    // Вывод данных
//    for (Diploma diploma : diplomas) {
//      System.out.println(diploma.toString());
//    }
//  }

  @Override
  public List<Diploma> parse() {
    List<Diploma> diplomas = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        if (values.length == 7) {
          Diploma diploma = new DiplomaImpl();
          diploma.setID(Integer.parseInt(values[0]));
          diploma.setFirstName(values[1]);
          diploma.setLastName(values[2]);
          diploma.setPatronymic(values[3]);
          diploma.setSchool(values[4]);
          diploma.setSubject(values[5]);
          diploma.setDegree(Integer.parseInt(values[6]));

          diplomas.add(diploma);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return diplomas;
  }

}