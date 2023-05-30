package org.hse.parsers;

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

public class GoogleSheetsParser {
  private static final String APPLICATION_NAME = "Google Sheets Parser";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  public static void main(String[] args) {
    String spreadsheetId = "1qh6PrL92l6pIYWWKfqszniqozmSDHYV5GGTK1ojaZhg";
    String range = "Лист1!A1:G"; // Укажите нужный диапазон ячеек

    try {
      Sheets service = createSheetsService();
      List<List<Object>> values = getDataFromSpreadsheet(service, spreadsheetId, range);

      ArrayList<Diploma> diplomas = new ArrayList<>();

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
      e.printStackTrace();
    }
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
}