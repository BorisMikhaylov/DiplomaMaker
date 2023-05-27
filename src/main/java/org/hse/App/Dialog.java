import javax.swing.JDialog;

/*
 * Класс второго окна
 * */
public class Dialog extends JDialog {
    //Конструктор второго окна
    public Dialog() {
        //Делаем невидимым окно
        setVisible(false);
        //Устанавливаем размеры
        setSize(200, 250);
        //Отображаем по центру
        setLocationRelativeTo(null);
    }
}