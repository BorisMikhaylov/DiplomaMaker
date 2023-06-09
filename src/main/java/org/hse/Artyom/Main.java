import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import static java.lang.Math.max;

public class Main extends JFrame implements ActionListener {
    private final JButton tableButton;
    private final JButton sampleButton;
    private final JButton diplomaButton;
    private final JButton quick_diplomaButton;
    private JButton editTableButton;
    private JButton editSampleButton;
    private final JPanel tablePanel;
    private final JPanel samplePanel;
    private boolean editTableInd = false;
    private boolean editSampleInd = false;
    private boolean tableInd = true;
    private boolean sampleInd = true;
    private final String[] lastname = new String[] {"Ivanov", "Petrov", "Nikolaev", "Sviridov"};
    private final String[] name = new String[]{"Ivan", "Matvey", "Stepan", "Yuri"};
    private File pdf_file;
    private static List<Diploma> persons;

    public Main() {
        super("DiplomaMaker");

        // создаем кнопку "Таблица"
        tableButton = new JButton("Таблица");

        // устанавливаем слушатель событий для кнопки "Таблица"
        tableButton.addActionListener(this);

        // создаем кнопку "Шаблон"
        sampleButton = new JButton("Шаблон");

        // устанавливаем слушатель событий для кнопки "Шаблон"
        sampleButton.addActionListener(this);

        // создаем кнопку "Редактирование"
        JButton edButton = new JButton("Редактирование");

        // устанавливаем слушатель событий для кнопки "Редактирование"
        edButton.addActionListener(this);

        // создаем кнопку "Загрузка"
        diplomaButton = new JButton("Загрузка");

        // устанавливаем слушатель событий для кнопки "Загрузка"
        diplomaButton.addActionListener(this);

        // создаем кнопку "Быстрая Загрузка"
        quick_diplomaButton = new JButton("Быстрая Загрузка");

        // устанавливаем слушатель событий для кнопки "Быстрая Загрузка"
        quick_diplomaButton.addActionListener(this);

        // создаем панели и добавляем на нее кнопки
        tablePanel = new JPanel();
        tablePanel.add(tableButton);
        samplePanel = new JPanel();
        samplePanel.add(sampleButton);
        JPanel edPanel = new JPanel();
        edPanel.add(edButton);
        JPanel diplomaPanel = new JPanel();
        diplomaPanel.add(diplomaButton);
        JPanel quick_diplomaPanel = new JPanel();
        quick_diplomaPanel.add(quick_diplomaButton);

        // Создание макета
        setLayout(new GridLayout(2, 3));

        // Добавление панелей на форму
        add(tablePanel);
        add(samplePanel);
        add(edPanel);
        add(diplomaPanel);
        add(quick_diplomaPanel);

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
        File table_file = table.getSelectedFile();
        if (table_file != null) {
            if (table_file != null && table_file.isFile() && (table_file.getName().endsWith(".xlsx") || table_file.getName().endsWith(".csv"))) {
                editTableInd = true;
                tableInd = false;
                tableButton.setText(table_file.getName());
                if (table_file.getName().endsWith(".xlsx")) {
                    ExcelParser parser = new ExcelParser(table_file);
                    persons = parser.parse();
                }
                else {
                    CSVParser parser = new CSVParser(table_file);
                    persons = parser.parse();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Выберите файл с расширением .xlsx или .csv", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Выберите файл с расширением .xlsx или .csv", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Выберите файл с расширением .pdf", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
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

    static List<TextPositionSequence> findSubwords(PDDocument document, int page, String searchTerm) throws IOException {
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
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        stripper.getText(document);
        return hits;
    }
    static void replacefio(PDDocument document, String surname, String name) throws IOException {
        for (PDPage page : document.getPages()) {
            // Нахождение координат заменяемого текста
            java.util.List<TextPositionSequence> fpositions = findSubwords(document, 1, "Фамилия");
            TextPositionSequence fhit = fpositions.get(0);
            TextPosition fposition = fhit.textPositionAt(0);
            PDRectangle fmediaBox = page.getMediaBox();
            float fx = fposition.getXDirAdj();
            float fy = fmediaBox.getHeight() - fposition.getYDirAdj();
            float fwidth = fhit.getWidth();
            float fheight = (float) (fposition.getHeightDir() * 1.1);

            // Нахождение координат заменяемого текста
            List<TextPositionSequence> ipositions = findSubwords(document, 1, "Имя");
            TextPositionSequence ihit = ipositions.get(0);
            TextPosition iposition = ihit.textPositionAt(0);
            PDRectangle imediaBox = page.getMediaBox();
            float ix = iposition.getXDirAdj();
            float iy = imediaBox.getHeight() - iposition.getYDirAdj();
            float iwidth = ihit.getWidth();
            float iheight = (float) (iposition.getHeightDir() * 1.1);

            PDPage pag = document.getPage(0); // получение первой страницы
            PDPageContentStream contentStream = new PDPageContentStream(document, pag, PDPageContentStream.AppendMode.APPEND, true);

            contentStream.addRect(fx, fy, iwidth + ix - fx, max(fheight, iheight)); // добавление прямоугольника
            contentStream.setNonStrokingColor(Color.WHITE);
            contentStream.fill(); // закрашивание прямоугольника

            contentStream.beginText();

            String text = surname + " " + name;
            PDFont font = PDType0Font.load(document, new File("src/arial.TTF"));
            float fontSize = 12;
            float leading = 1.5f * fontSize;

            float stringWidth = font.getStringWidth(text) * fontSize / 1000f;
            float startX = fx + ((iwidth + ix - fx) - stringWidth) / 2;
            float startY = fy;

            contentStream.setFont(font, fontSize);
            contentStream.setNonStrokingColor(0, 0, 0); // черный цвет текста
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText(text);
            contentStream.endText();

            contentStream.close(); // закрытие content stream
        }
    }

    // обработчик событий для кнопок
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == tableButton && tableInd == true) || e.getSource() == editTableButton) {
            table_load();

            // создаем и добавляем кнопку "Редактирование"
            if (editTableInd) {
                editTableButton = new JButton("Редактирование");
                tablePanel.add(editTableButton);
            }

            // устанавливаем слушатель событий для кнопки "Редактирование"
            editTableButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // действия при нажатии на кнопку "Редактирование"
                    table_load();
                }
            });
            tablePanel.revalidate();
            tablePanel.repaint();
        }
        if ((e.getSource() == sampleButton && sampleInd == true) || e.getSource() == editSampleButton) {
            sample_load();

            // создаем и добавляем кнопку "Редактирование"
            if (editSampleInd) {
                editSampleButton = new JButton("Редактирование");
                samplePanel.add(editSampleButton);
            }

            // устанавливаем слушатель событий для кнопки "Редактирование"
            editSampleButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // действия при нажатии на кнопку "Редактирование"
                    sample_load();
                }
            });
            samplePanel.revalidate();
            samplePanel.repaint();
        }
        if (e.getSource() == diplomaButton) {
            File saveFolder = chooseSaveFolder();
            for (int i = 0; i < name.length; i++) {
                String str = lastname[i] + " " + name[i] + ".pdf";
                try {
                    PDDocument sourceDoc = PDDocument.load(pdf_file);
                    // Создаем новый документ
                    PDDocument newDoc = new PDDocument();

                    // Копируем каждую страницу из исходного документа в новый документ
                    for (int j = 0; j < sourceDoc.getNumberOfPages(); j++) {
                        newDoc.addPage(sourceDoc.getPage(j));
                    }

                    replacefio(newDoc, lastname[i], name[i]);

                    // Сохраняем новый документ
                    if (saveFolder != null) {
                        // Сохранение PDDocument в выбранную папку
                        newDoc.save(new File(saveFolder, str));
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (e.getSource() == quick_diplomaButton) {
            for (int i = 0; i < persons.size(); i++) {
                Diploma person = persons.get(i);
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
    }

    public static void main(String[] args) {
        new Main();
    }
}
