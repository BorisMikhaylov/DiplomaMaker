package org.hse.parsers;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MyApp {
  private JFrame frame;
  private JPanel startPanel;
  private JPanel pageAPanel;
  private JPanel pageBPanel;

  public MyApp() {
    // Создаем главное окно приложения
    frame = new JFrame("My App");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 200);

    // Создаем стартовую панель
    startPanel = new JPanel();
    startPanel.setLayout(new GridLayout(2, 1));

    // Создаем кнопку A
    JButton buttonA = new JButton("A");
    buttonA.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showPageA();
      }
    });
    startPanel.add(buttonA);

    // Создаем кнопку B
    JButton buttonB = new JButton("B");
    buttonB.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showPageB();
      }
    });
    startPanel.add(buttonB);

    // Добавляем стартовую панель в окно и отображаем его
    frame.getContentPane().add(startPanel);
    frame.setVisible(true);
  }

  private void showPageA() {
    // Удаляем стартовую панель
    frame.getContentPane().remove(startPanel);

    // Создаем панель страницы A
    pageAPanel = new JPanel();
    pageAPanel.setLayout(new GridLayout(4, 1));

    // Создаем кнопки на странице A
    JButton button1 = new JButton("1");
    pageAPanel.add(button1);

    JButton button2 = new JButton("2");
    pageAPanel.add(button2);

    JButton button3 = new JButton("3");
    pageAPanel.add(button3);

    JButton backButton = new JButton("Назад");
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        goBack();
      }
    });
    pageAPanel.add(backButton);

    // Добавляем панель страницы A в окно и обновляем его
    frame.getContentPane().add(pageAPanel);
    frame.revalidate();
    frame.repaint();
  }

  private void showPageB() {
    // Удаляем стартовую панель
    frame.getContentPane().remove(startPanel);

    // Создаем панель страницы B
    pageBPanel = new JPanel();
    pageBPanel.setLayout(new GridLayout(4, 1));

    // Создаем кнопки на странице B
    JButton button1 = new JButton("1");
    pageBPanel.add(button1);

    JButton button4 = new JButton("4");
    pageBPanel.add(button4);

    JButton button5 = new JButton("5");
    pageBPanel.add(button5);

    JButton backButton = new JButton("Назад");
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        goBack();
      }
    });
    pageBPanel.add(backButton);

    // Добавляем панель страницы B в окно и обновляем его
    frame.getContentPane().add(pageBPanel);
    frame.revalidate();
    frame.repaint();
  }

  private void goBack() {
    // Удаляем текущую панель
    if (pageAPanel != null) {
      frame.getContentPane().remove(pageAPanel);
      pageAPanel = null;
    } else if (pageBPanel != null) {
      frame.getContentPane().remove(pageBPanel);
      pageBPanel = null;
    }

    // Добавляем стартовую панель обратно в окно и обновляем его
    frame.getContentPane().add(startPanel);
    frame.revalidate();
    frame.repaint();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new MyApp();
      }
    });
  }
}