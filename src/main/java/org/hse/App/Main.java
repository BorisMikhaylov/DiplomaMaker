package org.hse.App;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import org.hse.parsers.CSVParser;
import org.hse.parsers.Diploma;
import org.hse.parsers.ExcelParser;
import org.hse.parsers.GoogleSheetsParser;

import static java.lang.Math.max;

public class Main extends JFrame implements ActionListener {
  private static final List<TextObject> textObjects = new ArrayList<>();
  private static final int fontSize = 50; // размер шрифта по умолчанию

  private final CustomButton tableButton;
  private final CustomButton sampleButton;
  private final CustomButton diplomaButton;
  private final CustomButton quick_diplomaButton;
  private CustomButton editSampleButton;
  private CustomButton nameButton;
  private final JPanel tablePanel;
  private final JButton edButton;
  private final JPanel samplePanel;
  private final JPanel startPanel;
  private JPanel pageAPanel;
  private boolean editTableInd = false;
  private boolean editSampleInd = false;
  File table_file;
  private boolean sampleInd = true;
  private File pdf_file;
  private static List<Diploma> persons;
  private String link;
  private final String button_link = "src/main/java/org/hse/Source/button_phone.jpg";
  private final String p_link = "src/main/java/org/hse/Source/phone.jpg";

  public Main() {
    super("DiplomaMaker");

    // создаем кнопку "Таблица"
    tableButton = new CustomButton("Таблица", button_link);

    // устанавливаем слушатель событий для кнопки "Таблица"
    tableButton.addActionListener(this);

    // создаем кнопку "Шаблон"
    sampleButton = new CustomButton("Шаблон", button_link);

    // устанавливаем слушатель событий для кнопки "Шаблон"
    sampleButton.addActionListener(this);

    // создаем кнопку "Редактирование"
    edButton = new CustomButton("Редактор", button_link);

    // устанавливаем слушатель событий для кнопки "Редактирование"
    edButton.addActionListener(this);

    // создаем кнопку "Загрузка"
    diplomaButton = new CustomButton("Загрузка", button_link);

    // устанавливаем слушатель событий для кнопки "Загрузка"
    diplomaButton.addActionListener(this);

    // создаем кнопку "Быстрая Загрузка"
    quick_diplomaButton = new CustomButton("Быстрая Загрузка", button_link);

    // устанавливаем слушатель событий для кнопки "Быстрая Загрузка"
    quick_diplomaButton.addActionListener(this);

    // создаем панели и добавляем на нее кнопки
    tablePanel = new JPanel();
    tablePanel.setOpaque(false);
    tablePanel.add(tableButton);
    samplePanel = new JPanel();
    samplePanel.setOpaque(false);
    samplePanel.add(sampleButton);
    JPanel edPanel = new JPanel();
    edPanel.setOpaque(false);
    edPanel.add(edButton);
    JPanel diplomaPanel = new JPanel();
    diplomaPanel.setOpaque(false);
    diplomaPanel.add(diplomaButton);
    JPanel quick_diplomaPanel = new JPanel();
    quick_diplomaPanel.setOpaque(false);
    quick_diplomaPanel.add(quick_diplomaButton);
    //JLabel background = new JLabel(new ImageIcon("src/main/java/org/hse/App/phone.png"));

    startPanel = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon imageIcon = new ImageIcon(p_link);
        Image image = imageIcon.getImage();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
      }
    };

    // Создание макета
    startPanel.setLayout(new GridLayout(2, 3));

    //startPanel.add(background);

    // Добавление панелей на форму
    startPanel.add(tablePanel);
    startPanel.add(samplePanel);
    startPanel.add(edPanel);
    startPanel.add(diplomaPanel);
    startPanel.add(quick_diplomaPanel);
    add(startPanel);

    // настраиваем параметры окна
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //setExtendedState(JFrame.MAXIMIZED_BOTH);
    setSize(1500, 1500);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  void table_load() {
    // загружаем таблицу
    JFileChooser table = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("XLSX, CSV Files", "xlsx", "csv");
    table.setFileFilter(filter);
    table.showOpenDialog(null);
    table_file = table.getSelectedFile();
    if (table_file != null) {
      if (table_file.isFile() && (table_file.getName().endsWith(".xlsx")
              || table_file.getName().endsWith(".csv"))) {
        if (table_file.getName().endsWith(".xlsx")) {
          ExcelParser parser = new ExcelParser(table_file);
          persons = parser.parse();
        } else {
          CSVParser parser = new CSVParser(table_file);
          persons = parser.parse();
        }
      } else {
        JOptionPane.showMessageDialog(null, "Выберите файл с расширением .xlsx или .csv", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(null, "Выберите файл с расширением .xlsx или .csv", "Ошибка",
              JOptionPane.ERROR_MESSAGE);
    }
  }

  void sample_load() {
    // загружаем шаблон
    JFileChooser sample = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
    sample.setFileFilter(filter);
    sample.showOpenDialog(null);
    pdf_file = sample.getSelectedFile();
    if (pdf_file != null && pdf_file.isFile() && pdf_file.getName().endsWith(".pdf")) {
      editSampleInd = true;
      sampleInd = false;
      sampleButton.setText(pdf_file.getName());
      // выполнить действия с загруженным файлом
    } else {
      JOptionPane.showMessageDialog(null, "Выберите файл с расширением .pdf", "Ошибка",
              JOptionPane.ERROR_MESSAGE);
    }
  }

  void editor() throws IOException {
    // загрузка pdf-файла
    if (pdf_file == null) {
      JOptionPane.showMessageDialog(null, "Выберите файл с расширением .pdf", "Ошибка",
              JOptionPane.ERROR_MESSAGE);
    }
    PDDocument document = PDDocument.load(pdf_file);

    List<TextObject> new_textObjects = new ArrayList<>();

    // получение изображения из первой страницы pdf
    PDFRenderer pdfRenderer = new PDFRenderer(document);
    BufferedImage image = pdfRenderer.renderImage(0);

    // создание окна с изображением
    JFrame frame = new JFrame();
    frame.getContentPane().setLayout(new BorderLayout());

    ImagePanel imagePanel = new ImagePanel(image, new_textObjects);
    frame.getContentPane().add(imagePanel, BorderLayout.CENTER);

    frame.setSize(image.getWidth(), image.getHeight());
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    // добавление обработчика кликов мыши
    imagePanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        String text = JOptionPane.showInputDialog(frame, "Введите текст:");
        if (text != null && !text.isEmpty()) {
          String fontSizeString = JOptionPane.showInputDialog(frame, "Введите размер шрифта (по умолчанию: " + fontSize + "):");
          int inputFontSize = fontSize;
          if (fontSizeString != null && !fontSizeString.isEmpty()) {
            try {
              inputFontSize = Integer.parseInt(fontSizeString);
            } catch (NumberFormatException ex) {
              // Обработка некорректного ввода
              JOptionPane.showMessageDialog(frame, "Некорректный размер шрифта. Используется значение по умолчанию: " + fontSize);
            }
          }
          new_textObjects.add(new TextObject(text, e.getX(), e.getY(), inputFontSize));
          imagePanel.repaint();
        }
      }
    });

    // добавление кнопки сохранения
    JButton saveButton = new JButton("Сохранить");
    saveButton.addActionListener(u -> {
      Graphics2D g2d = image.createGraphics();

      textObjects.addAll(new_textObjects);

      for (TextObject textObject : textObjects) {
        g2d.setFont(new Font("Arial", Font.PLAIN, textObject.getFontSize()));
        g2d.setColor(Color.BLACK);
        g2d.drawString(textObject.getText(), textObject.getX(), textObject.getY());
      }

      try {
        // сохранение измененного изображения
        File outputFile = new File("result.png");
        ImageIO.write(image, "png", outputFile);

        PNGtoPDFConverter tp = new PNGtoPDFConverter();
        PDDocument doc = tp.to_pdf("result.png", "result.pdf", null);
        outputFile.delete();
        JOptionPane.showMessageDialog(frame, "Изображение успешно сохранено в файл result.pdf");
      } catch (IOException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(frame, "Ошибка при сохранении изображения: " + ex.getMessage());
      }
    });
    frame.getContentPane().add(saveButton, BorderLayout.SOUTH);
  }

  private int countWidth(String text, FontMetrics fontMetrics) {
    int width = 0;
    for (int i = 0; i < text.length(); i++) {
      int charWidth = fontMetrics.charWidth(text.charAt(i));
      width += charWidth;
    }
    return width;
  }

  private static File chooseSaveFolder() {
    // Создание диалогового окна выбора папки
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    // Отображение диалогового окна и получение выбранной папки
    int result = fileChooser.showSaveDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile();
    } else {
      return null;
    }
  }

  static List<TextPositionSequence> findSubwords(PDDocument document, String searchTerm)
          throws IOException {
    final List<TextPositionSequence> hits = new ArrayList<TextPositionSequence>();
    PDFTextStripper stripper = new PDFTextStripper() {
      @Override
      protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        TextPositionSequence word = new TextPositionSequence(textPositions);
        String string = word.toString();

        int fromIndex = 0;
        int index;
        while ((index = string.indexOf(searchTerm, fromIndex)) > -1) {
          hits.add(word.subSequence(index, index + searchTerm.length()));
          fromIndex = index + 1;
        }
        super.writeString(text, textPositions);
      }
    };

    stripper.setSortByPosition(true);
    stripper.setStartPage(1);
    stripper.setEndPage(1);
    stripper.getText(document);
    return hits;
  }

  static void replacefio(PDDocument document, String surname, String name) throws IOException {
    for (PDPage page : document.getPages()) {
      // Нахождение координат заменяемого текста
      java.util.List<TextPositionSequence> fpositions = findSubwords(document, "Фамилия");
      TextPositionSequence fhit = fpositions.get(0);
      TextPosition fposition = fhit.textPositionAt(0);
      PDRectangle fmediaBox = page.getMediaBox();
      float fx = fposition.getXDirAdj();
      float fy = fmediaBox.getHeight() - fposition.getYDirAdj();
      float fheight = (float) (fposition.getHeightDir() * 1.1);

      // Нахождение координат заменяемого текста
      List<TextPositionSequence> ipositions = findSubwords(document, "Имя");
      TextPositionSequence ihit = ipositions.get(0);
      TextPosition iposition = ihit.textPositionAt(0);
      float ix = iposition.getXDirAdj();
      float iwidth = ihit.getWidth();
      float iheight = (float) (iposition.getHeightDir() * 1.1);

      PDPage pag = document.getPage(0); // получение первой страницы
      PDPageContentStream contentStream = new PDPageContentStream(document, pag,
              PDPageContentStream.AppendMode.APPEND, true);

      contentStream.addRect(fx, fy, iwidth + ix - fx,
              max(fheight, iheight)); // добавление прямоугольника
      contentStream.setNonStrokingColor(Color.WHITE);
      contentStream.fill(); // закрашивание прямоугольника

      contentStream.beginText();

      String text = surname + " " + name;
      PDFont font = PDType0Font.load(document, new File("src/main/java/org/hse/Source/arial.TTF"));
      float fontSize = 12;

      float stringWidth = font.getStringWidth(text) * fontSize / 1000f;
      float startX = fx + ((iwidth + ix - fx) - stringWidth) / 2;

      contentStream.setFont(font, fontSize);
      contentStream.setNonStrokingColor(0, 0, 0); // черный цвет текста
      contentStream.newLineAtOffset(startX, fy);
      contentStream.showText(text);
      contentStream.endText();

      contentStream.close(); // закрытие content stream
    }
  }

  // обработчик событий для кнопок
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == tableButton) {
      getContentPane().remove(startPanel);
      link = null;
      table_file = null;

      // Создаем панель страницы A
      pageAPanel = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          ImageIcon imageIcon = new ImageIcon(p_link);
          Image image = imageIcon.getImage();
          g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
      };
      JButton button1 = new CustomButton("Ввести ссылку", button_link);
      JButton button2 = new CustomButton("Загрузка с компьютера", button_link);
      JPanel buttonPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          ImageIcon imageIcon = new ImageIcon(p_link);
          Image image = imageIcon.getImage();
          g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
      };
      buttonPanel.add(button1);
      buttonPanel.add(button2);

      JTextField textField = new JTextField(20);
      JPanel textPanel = new JPanel();
      textPanel.add(textField);
      textField.setHorizontalAlignment(JTextField.CENTER);
      pageAPanel.setLayout(new GridLayout(3, 1));

      button1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          link = textField.getText(); // Сохраняем текст из поля
          GoogleSheetsParser parser = new GoogleSheetsParser(link);
          persons = parser.parse();
          if (persons == null) {
            JOptionPane.showMessageDialog(null, "Простите, но таблица неверная", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
          } else {
            goBack();
          }
        }
      });

      button2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          table_load();
          if (persons == null) {
            JOptionPane.showMessageDialog(null, "Простите, но таблица неверная", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
          } else {
            goBack();
          }
        }
      });

      pageAPanel.add(buttonPanel);
      pageAPanel.add(textField, FlowLayout.CENTER);

      pageAPanel.setVisible(true);

      // Добавляем панель страницы A в окно и обновляем его
      getContentPane().add(pageAPanel);
      revalidate();
      repaint();
    }
    if ((e.getSource() == sampleButton && sampleInd) || e.getSource() == editSampleButton) {
      sample_load();

      // создаем и добавляем кнопку "Редактирование"
      if (editSampleInd) {
        editSampleButton = new CustomButton("Редактирование шаблона", button_link);
        samplePanel.add(editSampleButton);
      }

      // устанавливаем слушатель событий для кнопки "Редактирование"
      editSampleButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          sample_load();
        }
      });
      samplePanel.revalidate();
      samplePanel.repaint();
    }
    if (e.getSource() == diplomaButton) {
      File saveFolder = chooseSaveFolder();
      for (Diploma person : persons) {
        String lastname = person.getLastName();
        String name = person.getFirstName();
        String str = lastname + " " + name + ".pdf";
        String fi = lastname + " " + name;
        try {
          // Создаем новый документ
          if (pdf_file == null) {
            JOptionPane.showMessageDialog(null, "Выберите файл с расширением .pdf", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
          }
          PDDocument document = PDDocument.load(pdf_file);

          // получение изображения из первой страницы pdf
          PDFRenderer pdfRenderer = new PDFRenderer(document);
          BufferedImage image = pdfRenderer.renderImage(0);

          Graphics2D g2d = image.createGraphics();

          for (TextObject textObject : textObjects) {
            String ex_text = "Фамилия Имя";
            Font font = new Font("Arial", Font.PLAIN, textObject.getFontSize());
            g2d.setFont(font);
            g2d.setColor(Color.BLACK);
            if (textObject.getText().equals(ex_text)) {
              FontMetrics fontMetrics = g2d.getFontMetrics(font);
              int old_width = countWidth(ex_text, fontMetrics);
              int new_width = countWidth(fi, fontMetrics);
              g2d.drawString(fi, textObject.getX() + (old_width - new_width) / 2 , textObject.getY());
            }
            else {
              g2d.drawString(textObject.getText(), textObject.getX(), textObject.getY());
            }
          }

          try {
            // сохранение измененного изображения
            File outputFile = new File("result.png");
            ImageIO.write(image, "png", outputFile);

            PNGtoPDFConverter tp = new PNGtoPDFConverter();
            PDDocument doc = tp.to_pdf("result.png", str, saveFolder);
            outputFile.delete();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    }
    if (e.getSource() == quick_diplomaButton) {
      for (Diploma person : persons) {
        String lastname = person.getLastName();
        String name = person.getFirstName();

        String str = lastname + " " + name + ".pdf";
        try {
          PDDocument sourceDoc = PDDocument.load(pdf_file);
          // Создаем новый документ
          PDDocument newDoc = new PDDocument();

          // Копируем каждую страницу из исходного документа в новый документ
          for (int j = 0; j < sourceDoc.getNumberOfPages(); j++) {
            newDoc.addPage(sourceDoc.getPage(j));
          }

          replacefio(newDoc, lastname, name);

          // Сохраняем новый документ
          newDoc.save(str);
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    }
    if (e.getSource() == edButton) {
      try {
        editor();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  private void goBack() {
    // Удаляем текущую панель
    if (pageAPanel != null) {
      getContentPane().remove(pageAPanel);
      pageAPanel = null;
    }

    // Добавляем стартовую панель обратно в окно и обновляем его
    if (!editTableInd) {
      nameButton = new CustomButton("", button_link);
    }
    tableButton.setText("Редактирование таблицы");
    tableButton.revalidate();
    if (table_file != null) {
      nameButton.setText(table_file.getName());
    } else {
      nameButton.setText("Google Sheet");
    }
    tablePanel.add(nameButton);
    tablePanel.add(tableButton);
    tablePanel.revalidate();
    editTableInd = true;

    getContentPane().add(startPanel);
    revalidate();
    repaint();
  }

  private static class ImagePanel extends JPanel {
    private final BufferedImage image;
    private final List <TextObject> new_textObjects;

    public ImagePanel(BufferedImage image, List <TextObject> new_textObjects) {
      this.image = image;
      this.new_textObjects = new_textObjects;
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

      Graphics2D g2d = (Graphics2D) g;

      for (TextObject textObject : textObjects) {
        g2d.setFont(new Font("Arial", Font.PLAIN, textObject.getFontSize()));
        g2d.setColor(Color.BLACK);
        g2d.drawString(textObject.getText(), textObject.getX(), textObject.getY());
      }
      for (TextObject textObject : new_textObjects) {
        g2d.setFont(new Font("Arial", Font.PLAIN, textObject.getFontSize()));
        g2d.setColor(Color.BLACK);
        g2d.drawString(textObject.getText(), textObject.getX(), textObject.getY());
      }
    }
  }
  private static class TextObject {
    private final String text;
    private final int x;
    private final int y;
    private final int fontSize;

    public TextObject(String text, int x, int y, int fontSize) {
      this.text = text;
      this.x = x;
      this.y = y;
      this.fontSize = fontSize;
    }

    public String getText() {
      return text;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    public int getFontSize() {
      return fontSize;
    }

  }

  private static class PNGtoPDFConverter {
    private final PDDocument document = new PDDocument();

    public PDDocument to_pdf(String pngFilePath, String pdfFilePath, File saveFolder) {
      try {
        // Загрузка изображения из файла PNG
        BufferedImage image = ImageIO.read(new File(pngFilePath));

        // Создание нового PDF-документа
        PDPage page = new PDPage();
        document.addPage(page);

        // Получение контента страницы для рисования
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Рисование изображения на странице
        float width = page.getMediaBox().getWidth();
        float height = page.getMediaBox().getHeight();
        contentStream.drawImage(
                org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject.createFromFile(pngFilePath, document),
                0, 0, width, height);

        // Закрытие контента и сохранение документа
        contentStream.close();
        if (saveFolder != null) {
          document.save(new File(saveFolder, pdfFilePath));
        }
        else {
          document.save(pdfFilePath);
        }
        document.close();
      } catch (IOException e) {
        System.out.println("Ошибка при преобразовании PNG в PDF: " + e.getMessage());
      }
      return document;
    }
  }

  public static void main(String[] args) {
    new Main();
  }
}