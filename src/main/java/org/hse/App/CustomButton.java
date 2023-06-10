package org.hse.App;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    private String imagePath;

    public CustomButton(String text, String imagePath) {
        super(text);
        this.imagePath = imagePath;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        // Устанавливаем цвет и шрифт для текста
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        // Определяем размеры текста
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(getText());
        int textHeight = fontMetrics.getHeight();

        // Определяем координаты для центрирования текста
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2 + fontMetrics.getAscent();

        // Рисуем текст на кнопке
        g.drawString(getText(), x, y);
    }
}
