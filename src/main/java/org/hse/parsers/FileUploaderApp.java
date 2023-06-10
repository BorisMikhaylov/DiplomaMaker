package org.hse.parsers;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileUploaderApp extends JFrame {

  public FileUploaderApp() {
    setTitle("File Uploader");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);
    setLayout(new FlowLayout());

    JButton excelButton = new JButton("Загрузить Excel");
    JButton pdfImageButton = new JButton("Загрузить PDF/Изображение");
    JButton settingsButton = new JButton("Настройки");

    excelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл Excel");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(FileUploaderApp.this);
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          if (isExcelFile(selectedFile)) {
            // Действия при успешной загрузке Excel файла
            JOptionPane.showMessageDialog(FileUploaderApp.this, "Файл Excel успешно загружен.");
          } else {
            JOptionPane.showMessageDialog(FileUploaderApp.this, "Выбранный файл не является Excel файлом.",
                "Ошибка", JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });

    pdfImageButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл PDF/Изображение");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF & Images", "pdf", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(FileUploaderApp.this);
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          if (isPdfOrImageFile(selectedFile)) {
            // Действия при успешной загрузке PDF/изображения
            JOptionPane.showMessageDialog(FileUploaderApp.this, "Файл PDF/изображение успешно загружен.");
          } else {
            JOptionPane.showMessageDialog(FileUploaderApp.this,
                "Выбранный файл не является PDF или изображением.", "Ошибка", JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });

    settingsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Действия при нажатии на кнопку настроек
      }
    });

    add(excelButton);
    add(pdfImageButton);
    add(settingsButton);
  }

  private boolean isExcelFile(File file) {
    // Проверка, является ли файл Excel файлом
    // Ваш код для проверки формата файла Excel
    return true; // Вернуть true, если файл является Excel файлом, иначе false
  }

  private boolean isPdfOrImageFile(File file) {
    // Проверка, является ли файл PDF или изображением
    // Ваш код для проверки формата файла PDF или изображения
    return true; // Вернуть true, если файл является PDF или изображением, иначе false
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        FileUploaderApp app = new FileUploaderApp();
        app.setVisible(true);
      }
    });
  }
}