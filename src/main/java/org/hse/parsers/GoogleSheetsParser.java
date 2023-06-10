package org.hse.parsers;

import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;


public class GoogleSheetsParser implements Parser{
  private static final String APPLICATION_NAME = "Google Sheets Parser";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private String link;


  public GoogleSheetsParser(String link) {
    this.link = link;
  }

  public List<Diploma> parse() {
    ArrayList<Diploma> diplomas = new ArrayList<>();
    String spreadsheetId;
    try {
      int editIndex = link.indexOf("/edit");
      int dIndex = link.indexOf("/d/");
      spreadsheetId = link.substring(dIndex + 3, editIndex);
      System.out.println(spreadsheetId);
    } catch (IndexOutOfBoundsException e) {
      return null;
    }

    try {
      Sheets service = createSheetsService();
      String range = "Лист1!A1:G"; // Укажите нужный диапазон ячеек
      List<List<Object>> values = getDataFromSpreadsheet(service, spreadsheetId, range);

      for (List<Object> row : values) {
        Diploma diploma = new DiplomaImpl();

        // Парсинг данных из ячеек
        diploma.setID(Integer.parseInt(row.get(0).toString()));
        diploma.setFirstName(row.get(1).toString());
        diploma.setLastName(row.get(2).toString());
        diploma.setPatronymic(row.get(3).toString());
        diploma.setSchool(row.get(4).toString());
        diploma.setSubject(row.get(5).toString());
        diploma.setDegree(Integer.parseInt(row.get(6).toString()));

        diplomas.add(diploma);
      }

      // Вывод данных
      for (Diploma diploma : diplomas) {
        System.out.println(diploma.toString());
      }
    } catch (IOException | GeneralSecurityException e) {
      return null;
    }
    return diplomas;
  }

  private static Sheets createSheetsService() throws IOException, GeneralSecurityException {
    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    Credential credential = GoogleCredential.fromStream(
            new FileInputStream("/home/maksimk/IdeaProjects/project_final/DiplomaMaker/src/main/java/org/hse/parsers/compact.json"))
        .createScoped(SheetsScopes.all());

    return new Sheets.Builder(httpTransport, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }

  private static List<List<Object>> getDataFromSpreadsheet(Sheets service, String spreadsheetId, String range)
      throws IOException {
    ValueRange response = service.spreadsheets().values()
        .get(spreadsheetId, range)
        .execute();

    return response.getValues();
  }

  private static int getSheetIndex(Sheets sheetsService, String spreadsheetId) throws IOException {
    Spreadsheet spreadsheet = sheetsService.spreadsheets().get(spreadsheetId).execute();
    List<Sheet> sheets = spreadsheet.getSheets();

    for (int i = 0; i < sheets.size(); i++) {
      Sheet sheet = sheets.get(i);
      if (sheet.getProperties().getTitle().equals("Sheet1")) {
        return i;
      } else if (sheet.getProperties().getTitle().equals("Лист1")) {
        return i;
      }
    }

    throw new IllegalArgumentException("Sheet with name '" + "Sheet1" + "' not found.");
  }
}