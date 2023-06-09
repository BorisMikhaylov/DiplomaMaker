import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser implements Parser {

  private static File file;

  public ExcelParser(File file) {
    ExcelParser.file = file;
  }
  @Override
  public List<Diploma> parse() {
    ArrayList<Diploma> diplomas = new ArrayList<>();

    try (FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis)) {

      Sheet sheet = workbook.getSheetAt(0); // Получаем первый лист
      Iterator<Row> iterator = sheet.iterator();

      // Пропускаем заголовок
      if (iterator.hasNext()) {
        iterator.next();
      }

      while (iterator.hasNext()) {
        Row currentRow = iterator.next();
        Diploma diploma = new DiplomaImpl();

        // Парсинг данных из ячеек
        diploma.setID((int) currentRow.getCell(0).getNumericCellValue());
        diploma.setFirstName(currentRow.getCell(1).getStringCellValue());
        diploma.setLastName(currentRow.getCell(2).getStringCellValue());
        diploma.setPatronymic(currentRow.getCell(3).getStringCellValue());
        diploma.setSchool(currentRow.getCell(4).getStringCellValue());
        diploma.setSubject(currentRow.getCell(5).getStringCellValue());
        diploma.setDegree((int) currentRow.getCell(6).getNumericCellValue());

        diplomas.add(diploma);
      }

      // Вывод данных
      for (Diploma diploma : diplomas) {
        System.out.println(diploma.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return diplomas;
  }
}